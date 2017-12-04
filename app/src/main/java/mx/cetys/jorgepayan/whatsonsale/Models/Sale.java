package mx.cetys.jorgepayan.whatsonsale.Models;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class Sale {
    private String saleId;
    private String saleBusinessId;
    private String categoryName;
    private String description;
    private String expirationDate;

    public Sale(String saleId, String saleBusinessId, String categoryName, String description, String expirationDate) {
        this.saleId = saleId;
        this.saleBusinessId = saleBusinessId;
        this.categoryName = categoryName;
        this.description = description;
        this.expirationDate = expirationDate;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSaleBusinessId(){return saleBusinessId;}

    public void setSaleBusinessId(String saleBusinesId) {this.saleBusinessId = saleBusinesId; }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
