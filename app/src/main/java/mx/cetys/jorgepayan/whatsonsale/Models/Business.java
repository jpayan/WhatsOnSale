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

    public Business(JSONObject jsonObject) {
        try {
            this.businessId = jsonObject.getString("business_id");
            this.userEmail = jsonObject.getString("user_email");
            this.businessName = jsonObject.getString("business_name");
            this.hqAddress = jsonObject.getString("hq_address");
        }
        catch(JSONException e){

        }
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
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
