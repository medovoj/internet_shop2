package service.impl;

import Entity.Account;
import Entity.Order;
import Entity.OrderItem;
import Entity.Product;
import Form.ProductForm;
import Model.CartItem;
import Model.CurrentAccount;
import Model.ShoppingCart;
import Model.SocialAccount;
import jdbc.JDBCUtils;
import jdbc.ResultSetHandler;
import jdbc.ResultSetHandlerFactory;
import service.OrderService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Exception.InternalServerErrorException;
import Exception.AccessDeniedException;
import Exception.ResourceNotFoundException;


class OrderServiceImpl implements OrderService {
    private static final ResultSetHandler<Product> productResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);
    private static final ResultSetHandler<Account> accountResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.ACCOUNT_RESULT_SET_HANDLER);
    private final ResultSetHandler<Order> orderResultSetHandler =
            ResultSetHandlerFactory.getSingleResultSetHandler(ResultSetHandlerFactory.ORDER_RESULT_SET_HANDLER);
    private final ResultSetHandler<List<OrderItem>> orderItemListResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.ORDER_ITEM_RESULT_SET_HANDLER);
    private final ResultSetHandler<List<Order>> ordersResultSetHandler =
            ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.ORDER_RESULT_SET_HANDLER);
    private final ResultSetHandler<Integer> countResultSetHandler =
            ResultSetHandlerFactory.getCountResultSetHandler();


    private final DataSource dataSource;

    public OrderServiceImpl(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    @Override
    public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
        try (Connection c = dataSource.getConnection()) {
            Product product = JDBCUtils.select(c, "select p.*, c.name as category, pr.name as producer from my_shop.product p, my_shop.producer pr, my_shop.category c "
                    + "where c.id=p.id_category and pr.id=p.id_producer and p.id=?", productResultSetHandler, productForm.getIdProduct());
            if(product == null) {
                throw new InternalServerErrorException("Product not found by id="+productForm.getIdProduct());
            }
            shoppingCart.addProduct(product, productForm.getCount());
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
        }
    }

    @Override
    public void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart) {
            shoppingCart.removeProduct(form.getIdProduct(), form.getCount());
        }



    @Override
    public String serializeShoppingCart(ShoppingCart shoppingCart) {
        StringBuilder res = new StringBuilder();
        for (CartItem item : shoppingCart.getItems()) {
            res.append(item.getProduct().getId()).append("-").append(item.getCount()).append("|");
        }
        if (res.length() > 0) {
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    }

    @Override
    public ShoppingCart deserializeShoppingCart(String string) {
        ShoppingCart shoppingCart = new ShoppingCart();
        String[] items = string.split("\\|");
        for (String item : items) {
            try {
                String[] data = item.split("-");
                int idProduct = Integer.parseInt(data[0]);
                int count = Integer.parseInt(data[1]);
                addProductToShoppingCart(new ProductForm(idProduct, count), shoppingCart);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return shoppingCart.getItems().isEmpty() ? null : shoppingCart;
    }

    @Override
    public CurrentAccount authenticate(SocialAccount socialAccount) {
        try (Connection c = dataSource.getConnection()) {
            Account account = JDBCUtils.select(c, "select * from my_shop.account where email=?", accountResultSetHandler, socialAccount.getEmail());
            if (account == null) {
                account = new Account(socialAccount.getName(), socialAccount.getEmail());
                account = JDBCUtils.insert(c, "insert into account values (nextval('account_seq'),?,?)", accountResultSetHandler, account.getName(), account.getEmail());
                c.commit();
            }
            return account;
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
        }
    }

    @Override
    public long makeOrder(ShoppingCart shoppingCart, CurrentAccount currentAccount) {
        if (shoppingCart == null || shoppingCart.getItems().isEmpty()) {
            throw new InternalServerErrorException("shoppingCart is null or empty");
        }
        try (Connection c = dataSource.getConnection()) {
            Order order = JDBCUtils.insert(c, "insert into my_shop.order values(nextval('my_shop.order_seq'),?,?)", orderResultSetHandler,
                    currentAccount.getId(), new Timestamp(System.currentTimeMillis()));
            JDBCUtils.insertBatch(c, "insert into my_shop.order_item values(nextval('my_shop.order_item_seq'),?,?,?)",
                    toOrderItemParameterList(order.getId(), shoppingCart.getItems()));
            c.commit();
            return order.getId();
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
        }
    }

    @Override
    public Order findOrderById(long id, CurrentAccount currentAccount) {
        try (Connection c = dataSource.getConnection()) {
            Order order = JDBCUtils.select(c, "select * from my_shop.order where id=?", orderResultSetHandler, id);
            if (order == null) {
                throw new ResourceNotFoundException("Order not found by id: " + id);
            }
            if (!order.getIdAccount().equals(currentAccount.getId())) {
                throw new AccessDeniedException("Account with id=" + currentAccount.getId() + " is not owner for order with id=" + id);
            }
            List<OrderItem> list = JDBCUtils.select(c,
                    "select o.id as oid, o.id_order as id_order, o.id_product, o.count, p.*, c.name as category, pr.name as producer " +
                            "from my_shop.order_item o, my_shop.product p, my_shop.category c, my_shop.producer pr "
                            + "where pr.id=p.id_producer and c.id=p.id_category and o.id_product=p.id and o.id_order=?",
                    orderItemListResultSetHandler, id);
            order.setItems(list);
            return order;
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
        }
    }

    private List<Object[]> toOrderItemParameterList(long idOrder, Collection<CartItem> items) {
        List<Object[]> parametersList = new ArrayList<>();
        for (CartItem item : items) {
            parametersList.add(new Object[] { idOrder, item.getProduct().getId(), item.getCount() });
        }
        return parametersList;
    }

    @Override
    public List<Order> listMyOrders(CurrentAccount currentAccount, int page, int limit) {
        int offset = (page - 1) * limit;
        try (Connection c = dataSource.getConnection()) {
            return JDBCUtils.select(c, "select * from my_shop.order where id_account=? order by id desc limit ? offset ?",
                    ordersResultSetHandler, currentAccount.getId(), limit, offset);
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
        }
    }

    @Override
    public int countMyOrders(CurrentAccount currentAccount) {
        try (Connection c = dataSource.getConnection()) {
            return JDBCUtils.select(c, "select count(*) from my_shop.order where id_account=?", countResultSetHandler, currentAccount.getId());
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
        }
    }
}
