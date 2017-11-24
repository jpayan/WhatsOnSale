package mx.cetys.jorgepayan.whatsonsale.Models;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class Business {
    private int businessId;
    private String userEmail;
    private String businessName;
    private String hqAddress;

    public Business(int businessId, String userEmail, String businessName, String hqAddress) {
        this.businessId = businessId;
        this.userEmail = userEmail;
        this.businessName = businessName;
        this.hqAddress = hqAddress;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getuserEmail() {
        return userEmail;
    }

    public void setuserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getHqAddress() {
        return hqAddress;
    }

    public void setHqAddress(String hqAddress) {
        this.hqAddress = hqAddress;
    }
}
