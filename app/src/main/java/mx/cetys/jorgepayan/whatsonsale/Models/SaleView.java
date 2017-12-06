package mx.cetys.jorgepayan.whatsonsale.Models;

/**
 * Created by jorge.payan on 12/5/17.
 */

public class SaleView {
    private String saleId;
    private String customerId;

    public SaleView(String saleId, String customerId) {
        this.saleId = saleId;
        this.customerId = customerId;
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
}
