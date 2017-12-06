package mx.cetys.jorgepayan.whatsonsale.Models;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class CustomerCategory {
    private String customerId;
    private String categoryName;

    public CustomerCategory(String customerId, String categoryName) {
        this.customerId = customerId;
        this.categoryName = categoryName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
