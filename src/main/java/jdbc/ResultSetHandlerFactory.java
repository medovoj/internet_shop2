package jdbc;

import Entity.*;


import java.util.ArrayList;
import java.util.List;

public final class ResultSetHandlerFactory {

    public final static ResultSetHandler<Product> PRODUCT_RESULT_SET_HANDLER = rs -> {
        Product p = new Product();
        p.setCategory(rs.getString("category"));
        p.setDescription(rs.getString("description"));
        p.setId(rs.getInt("id"));
        p.setImageLink(rs.getString("image_link"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setProducer(rs.getString("producer"));
        return p;
    };

    public final static ResultSetHandler<Category> CATEGORY_RESULT_SET_HANDLER = rs -> {
        Category c = new Category();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setUrl(rs.getString("url"));
        c.setProductCount(rs.getInt("product_count"));
        return c;
    };

    public final static ResultSetHandler<Producer> PRODUCER_RESULT_SET_HANDLER = rs -> {
        Producer p = new Producer();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setProductCount(rs.getInt("product_count"));
        return p;
    };

    public final static ResultSetHandler<Account> ACCOUNT_RESULT_SET_HANDLER = rs -> {
        Account a = new Account();
        a.setId(rs.getInt("id"));
        a.setEmail(rs.getString("email"));
        a.setName(rs.getString("name"));
        return a;
    };

    public final static ResultSetHandler<OrderItem> ORDER_ITEM_RESULT_SET_HANDLER = rs -> {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getLong("oid"));
        orderItem.setCount(rs.getInt("count"));
        orderItem.setIdOrder(rs.getLong("id_order"));
        Product p = PRODUCT_RESULT_SET_HANDLER.handle(rs);
        orderItem.setProduct(p);
        return orderItem;
    };

    public final static ResultSetHandler<Order> ORDER_RESULT_SET_HANDLER = rs -> {
        Order o = new Order();
        o.setId(rs.getLong("id"));
        o.setCreated(rs.getTimestamp("created"));
        o.setIdAccount(rs.getInt("id_account"));
        return o;
    };



    public static ResultSetHandler<Integer> getCountResultSetHandler() {
        return rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        };
    }

    public static <T> ResultSetHandler<T> getSingleResultSetHandler(final ResultSetHandler<T> oneRowResultSetHandler) {
        return rs -> {
            if (rs.next()) {
                return oneRowResultSetHandler.handle(rs);
            } else {
                return null;
            }
        };
    }

    public static <T> ResultSetHandler<List<T>> getListResultSetHandler(final ResultSetHandler<T> oneRowResultSetHandler) {
        return rs -> {
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                list.add(oneRowResultSetHandler.handle(rs));
            }
            return list;
        };
    }

    private ResultSetHandlerFactory() {
    }
}