package service;

import Entity.Order;
import Form.ProductForm;
import Model.CurrentAccount;
import Model.ShoppingCart;
import Model.SocialAccount;

import java.util.List;

public interface OrderService {

    void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);

    void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart);

    String serializeShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart deserializeShoppingCart(String string);

    CurrentAccount authenticate(SocialAccount socialAccount);

    long makeOrder(ShoppingCart shoppingCart, CurrentAccount currentAccount);

    Order findOrderById(long id, CurrentAccount currentAccount);

    List<Order> listMyOrders(CurrentAccount currentAccount, int page, int limit);

    int countMyOrders(CurrentAccount currentAccount);

}
