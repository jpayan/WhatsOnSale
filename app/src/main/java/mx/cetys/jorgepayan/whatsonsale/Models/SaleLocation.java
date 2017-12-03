package mx.cetys.jorgepayan.whatsonsale.Models;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class SaleLocation {
    private String saleId;
    private String locationId;

    public SaleLocation(String saleId, String locationId) {
        this.saleId = saleId;
        this.locationId = locationId;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
