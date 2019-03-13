package pl.com.bottega.ecommerce.sales.domain.offer;

public class Discount {

    private Money value;
    private String cause;

    public Discount(Money value, String cause) {
        this.value = value;
        this.cause = cause;
    }

    public Money getValue() {
        return value;
    }

    public void setValue(Money value) {
        this.value = value;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
