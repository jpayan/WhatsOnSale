package mx.cetys.jorgepayan.whatsonsale.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class Business {
    private String businessId;
    private String userEmail;
    private String businessName;
    private String hqAddress;

    public Business(String businessId, String userEmail, String businessName, String hqAddress) {
        this.businessId = businessId;
        this.userEmail = userEmail;
        this.businessName = businessName;
        this.hqAddress = hqAddress;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
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
