package mx.cetys.jorgepayan.whatsonsale.Models;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class SaleReview {
    private String saleId;
    private String customerId;
    private String description;
    private String date;
    private String liked;

    public SaleReview(String saleId, String customerId, String description, String date, String liked) {
        this.saleId = saleId;
        this.customerId = customerId;
        this.description = description;
        this.date = date;
        this.liked = liked;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }
}
