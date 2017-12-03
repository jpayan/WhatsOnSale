package mx.cetys.jorgepayan.whatsonsale.Models;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class Sale {
    private String saleId;
    private String saleBusinesId;
    private String categoryName;
    private String description;
    private String expirationDate;

    public Sale(String saleId, String saleBusinesId, String categoryId, String description, String expirationDate) {
        this.saleId = saleId;
        this.saleBusinesId = saleBusinesId;
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

    public String getSaleBusinesId(){return saleBusinesId;}

    public void setSaleBusinesId(String saleBusinesId) {this.saleBusinesId = saleBusinesId; }

    public String getcategoryName() {
        return categoryName;
    }

    public void setcategoryName(String categoryName) {
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
