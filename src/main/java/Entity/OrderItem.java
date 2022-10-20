package Entity;

public class OrderItem extends AbstractEntity<Long> {

    private Long idOrder;
    private Product product;
    private int count;

    public OrderItem() {
    }

    public OrderItem(Product product, int count) {
        super();
        this.product = product;
        this.count = count;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getIdItem() {
        return idOrder;
    }

    public void setIdItem(Long idItem) {
        this.idOrder = idItem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("OrderItem [id=%s, idOrder=%s, product=%s, count=%s]", getId(), idOrder, product, count);
    }
}
