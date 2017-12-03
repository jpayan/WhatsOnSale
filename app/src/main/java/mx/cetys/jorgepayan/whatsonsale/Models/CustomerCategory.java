package mx.cetys.jorgepayan.whatsonsale.Models;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class CustomerCategory {
    private String customerId;
    private String categoryId;

    public CustomerCategory(String customerId, String categoryId) {
        this.customerId = customerId;
        this.categoryId = categoryId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
