package Model;


import Entity.Product;
import Exception.ValidationException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import Constants.Constants;


public class ShoppingCart implements Serializable {

    private Map<Integer, CartItem> products = new LinkedHashMap<>();
    private int totalCount = 0;
    private BigDecimal totalCost = BigDecimal.ZERO;

    public void addProduct(Product product, int count) {
        validateShoppingCartSize(product.getId());
        CartItem cartItem = products.get(product.getId());
        if (cartItem == null) {
            validateProductCount(count);
            cartItem = new CartItem(product, count);
            products.put(product.getId(), cartItem);
        } else {
            validateProductCount(count + cartItem.getCount());
            cartItem.setCount(cartItem.getCount() + count);
        }
        refreshStatistics();
    }

    public void removeProduct(Integer idProduct, int count) {
        CartItem cartItem = products.get(idProduct);
        if (cartItem != null) {
            if (cartItem.getCount() > count) {
                cartItem.setCount(cartItem.getCount() - count);
            } else {
                products.remove(idProduct);
            }
            refreshStatistics();
        }
    }

    public Collection<CartItem> getItems() {
        return products.values();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    private void validateProductCount(int count) {
        if (count > Constants.MAX_PRODUCTS_COUNT) {
            throw new ValidationException("Limit for product count reached: count=" + count);
        }
    }

    private void validateShoppingCartSize(int idProduct) {
        if (products.size() > Constants.MAX_PRODUCTS_CART_CAPACITY ||
                (products.size() == Constants.MAX_PRODUCTS_CART_CAPACITY && !products.containsKey(idProduct))) {
            throw new ValidationException("Limit for ShoppingCart size reached: size=" + products.size());
        }
    }

    private void refreshStatistics() {
        totalCount = 0;
        totalCost = BigDecimal.ZERO;
        for (CartItem cartItem : getItems()) {
            totalCount += cartItem.getCount();
            totalCost = totalCost.add(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getCount())));
        }
    }

    @Override
    public String toString() {
        return String.format("ShoppingCart [products=%s, totalCount=%s, totalCost=%s]", products, totalCount, totalCost);
    }
}
