package mx.cetys.jorgepayan.whatsonsale.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.BusinessHomeActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.CustomerHomeActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.MainActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.RegisterBusinessActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.RegisterCustomerActivity;
import mx.cetys.jorgepayan.whatsonsale.Models.BusinessLocation;
import mx.cetys.jorgepayan.whatsonsale.Models.Sale;
import mx.cetys.jorgepayan.whatsonsale.Models.User;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.BusinessHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.CategoryHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.CustomerCategoryHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.CustomerHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.LocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleLocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleReviewHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleViewHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.UserHelper;

/**
 * Created by fidel on 12/2/2017.
 */

public class Utils {
    private static String api = "https://rybo0zqlw9.execute-api.us-east-1.amazonaws.com/api/";

    private static UserHelper userHelper;
    public static LocationHelper locationHelper;
    private static SaleHelper saleHelper;
    private static CustomerCategoryHelper customerCategoryHelper;
    private static SaleLocationHelper saleLocationHelper;

    /* ------------------------------------------ API ------------------------------------------- */
    private static JsonArrayRequest getAll(final String entity, final Context context) {
        String url = api + entity;
        return new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        switch (entity) {
                            case "user":
                                System.out.println(response);

                                userHelper.clearTable();

                                for(int i = 0; i < response.length(); i++){
                                    JSONObject user = response.getJSONObject(i);
                                    userHelper.addUser(user.getString("email"),
                                        user.getString("password"), user.getString("type"));
                                }

                                System.out.println("Users synchronized correctly.");
                                break;
                            case "category":
                                System.out.println(response);

                                CategoryHelper categoryHelper = new CategoryHelper(context);
                                categoryHelper.clearTable();

                                for(int i = 0; i < response.length(); i++){
                                    JSONObject category = response.getJSONObject(i);
                                    categoryHelper.addCategory(category.getString("name"));
                                }

                                System.out.println("Categories synchronized correctly.");
                                break;
                            case "business":
                                System.out.println(response);

                                BusinessHelper businessHelper = new BusinessHelper(context);
                                businessHelper.clearTable();

                                for(int i = 0; i < response.length(); i++){
                                    JSONObject business = response.getJSONObject(i);
                                    businessHelper.addBusiness(business.getString("business_id"),
                                        business.getString("user_email"),
                                        business.getString("business_name"),
                                        business.getString("hq_address"));
                                }

                                System.out.println("Businesses synchronized correctly.");
                                break;
                            case "customer":
                                System.out.println("customers" + response);

                                CustomerHelper customerHelper = new CustomerHelper(context);
                                customerHelper.clearTable();

                                for(int i = 0; i < response.length(); i++){
                                    JSONObject customer = response.getJSONObject(i);
                                    customerHelper.addCustomer(customer.getString("customer_id"),
                                        customer.getString("user_email"),
                                        customer.getString("name"), customer.getInt("age"),
                                        customer.getString("gender"));
                                }

                                System.out.println("Customers synchronized correctly.");
                                break;
                            case "customer_category":
                                System.out.println(response);

                                CustomerCategoryHelper customerCategoryHelper =
                                        new CustomerCategoryHelper(context);
                                customerCategoryHelper.clearTable();

                                for(int i = 0; i < response.length(); i++){
                                    JSONObject customerCategory = response.getJSONObject(i);
                                    customerCategoryHelper.addCustomerCategory(
                                        customerCategory.getString("customer_id"),
                                        customerCategory.getString("category_name"));
                                }

                                System.out.println("Customers synchronized correctly.");
                            case "location":
                                System.out.println(response);

                                LocationHelper locationHelper = new LocationHelper(context);
                                locationHelper.clearTable();

                                for(int i = 0; i < response.length(); i++){
                                    JSONObject location = response.getJSONObject(i);
                                    locationHelper.addLocation(location.getString("location_id"),
                                        location.getString("location_name"),
                                        location.getString("business_id"),
                                        location.getDouble("latitude"),
                                        location.getDouble("longitude"),
                                        location.getString("address"));
                                }

                                System.out.println("Locations synchronized correctly.");
                                break;
                            case "sale":
                                System.out.println(response);

                                SaleHelper saleHelper = new SaleHelper(context);
                                saleHelper.clearTable();

                                for(int i = 0; i < response.length(); i++){
                                    JSONObject sale = response.getJSONObject(i);
                                    saleHelper.addSale(sale.getString("sale_id"),
                                        sale.getString("business_id"),
                                        sale.getString("category_name"),
                                        sale.getString("description"),
                                        sale.getString("expiration_date"));
                                }

                                System.out.println("Sales synchronized correctly.");
                                break;
                            case "sale_location":
                                System.out.println(response);

                                SaleLocationHelper saleLocationHelper =
                                        new SaleLocationHelper(context);
                                saleLocationHelper.clearTable();

                                for(int i = 0; i < response.length(); i++){
                                    JSONObject saleLocation = response.getJSONObject(i);
                                    saleLocationHelper.addSaleLocation(
                                        saleLocation.getString("sale_id"),
                                        saleLocation.getString("location_id"));
                                }

                                System.out.println("Sale Locations synchronized correctly.");
                                break;
                            case "sale_review":
                                System.out.println(response);

                                SaleReviewHelper saleReviewHelper =
                                        new SaleReviewHelper(context);
                                saleReviewHelper.clearTable();

                                for(int i = 0; i < response.length(); i++){
                                    JSONObject saleReview = response.getJSONObject(i);
                                    saleReviewHelper.addSaleReview(
                                        saleReview.getString("sale_id"),
                                        saleReview.getString("customer_id"),
                                        saleReview.getString("description"),
                                        saleReview.getString("date"),
                                        saleReview.getString("liked"));
                                }

                                System.out.println("Sale Reviews synchronized correctly.");
                                break;
                            case "sale_view":
                                System.out.println(response);

                                SaleViewHelper saleViewHelper =
                                        new SaleViewHelper(context);
                                saleViewHelper.clearTable();

                                for(int i = 0; i < response.length(); i++){
                                    JSONObject saleReview = response.getJSONObject(i);
                                    saleViewHelper.addSaleView(
                                            saleReview.getString("sale_id"),
                                            saleReview.getString("customer_id"));
                                }

                                System.out.println("Sale Views synchronized correctly.");
                                break;
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            }
        );
    }

    public static void synchronizeDB(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        queue.add(getAll("user", context));
        queue.add(getAll("category", context));
        queue.add(getAll("customer_category", context));
        queue.add(getAll("business", context));
        queue.add(getAll("customer", context));
        queue.add(getAll("location", context));
        queue.add(getAll("sale", context));
        queue.add(getAll("sale_location", context));
        queue.add(getAll("sale_review", context));
        queue.add(getAll("sale_view", context));
    }

    public static void post(final String entity, final Context context,
                            HashMap<String, String> params) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = api + entity;

        JsonObjectRequest post = new JsonObjectRequest(url, new JSONObject(params),
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        VolleyLog.v("Response:%n %s", response.toString(4));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            }
        );
        queue.add(post);
    }

    public static void delete(final String entity, final Context context, ArrayList<String> params,
                              final FragmentManager fm) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = api + entity;
        for (String param : params) {
            url += "/" + param;
        }

        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    new SimpleDialog("Item deleted successfully.", "Ok").show(fm, "Alert Dialog");
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            }
        );
        queue.add(dr);

    }

    /* ------------------------------------------ MISC ------------------------------------------ */
    public static void initHelpers(Context context) {
        userHelper = new UserHelper(context);
        locationHelper = new LocationHelper(context);
        saleHelper = new SaleHelper(context);
        customerCategoryHelper = new CustomerCategoryHelper(context);
        saleLocationHelper = new SaleLocationHelper(context);
    }

    public static String generateId(){
        return UUID.randomUUID().toString();
    }

    private static void goToIntent(Context context, Class<?> cls, String[] values) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(MainActivity.MAIN_EMAIL, values[0]);
        intent.putExtra(MainActivity.MAIN_PASS, values[1]);
        context.startActivity(intent);
    }

    public static void goToIntent(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void logIn(Context context, String email, String password, FragmentManager fm,
                             boolean business) {

        final SimpleDialog emptyFieldsDialog =
            new SimpleDialog("Fill up all the fields before logging in or registering.", "Ok");
        final SimpleDialog incorrectCredentialsDialog =
            new SimpleDialog("Incorrect email or password.", "Ok");
        final SimpleDialog unexistingUserDialog =
            new SimpleDialog("No user with the specified email is registered.\nVerify the type " +
                "of user selected.\nIf this is your first time using the app, press the " +
                "register button.", "Ok");

        if (email.length() == 0 || password.length() == 0) {
            emptyFieldsDialog.show(fm, "Alert Dialog Fragment");
        } else {
            if (business) {
                if (userHelper.userExists(email, "business")) {
                    if (userHelper.validateCredentials(email, password)) {
                        MainActivity.currentUser = new User(email, password, "business");
                        goToIntent(context, BusinessHomeActivity.class,
                            new String[]{email, password});
                    } else {
                        incorrectCredentialsDialog.show(fm, "Alert Dialog Fragment");
                    }
                } else {
                    unexistingUserDialog.show(fm, "Alert Dialog Fragment");
                }
            } else {
                if (userHelper.userExists(email, "customer")) {
                    if (userHelper.validateCredentials(email, password)) {
                        MainActivity.currentUser = new User(email, password, "customer");
                        goToIntent(context, CustomerHomeActivity.class,
                            new String[]{email, password});
                    } else {
                        incorrectCredentialsDialog.show(fm, "Alert Dialog Fragment");
                    }
                } else {
                    unexistingUserDialog.show(fm, "Alert Dialog Fragment");
                }
            }
        }
    }

    public static void register(Context context, String email, String password, FragmentManager fm,
                                boolean business) {

        final SimpleDialog emptyFieldsDialog =
            new SimpleDialog("Fill up all the fields before logging in or registering.", "Ok");
        final SimpleDialog existingUserDialog =
            new SimpleDialog("A user with this email has already been registered.\nIf the user " +
                "registered with that email belongs to you, press the login button.", "Ok");

        if (email.length() == 0 || password.length() == 0) {
            emptyFieldsDialog.show(fm, "Alert Dialog Fragment");
        } else {
            if (business) {
                if (userHelper.userExists(email, "business")) {
                    existingUserDialog.show(fm, "Alert Dialog Fragment");
                } else {
                    goToIntent(context, RegisterBusinessActivity.class,
                        new String[]{email, password});
                }
            } else {
                if (userHelper.userExists(email, "customer")) {
                    existingUserDialog.show(fm, "Alert Dialog Fragment");
                } else {
                    goToIntent(context, RegisterCustomerActivity.class,
                        new String[]{email, password});
                }
            }
        }
    }

    public static ArrayList<BusinessLocation> filterLocationsByCustomerCategory(String customerId) {
        Log.d("Utils: Customer Id", customerId);
        ArrayList<String> customerCategories =
            customerCategoryHelper.getCustomerCategoryByCustomerId(customerId);
        Log.d("Utils: Categories", customerCategories.toString());
        ArrayList<String> salesIds = saleHelper.getSalesIdsByCategoryNames(customerCategories);
        ArrayList<String> locationIds = saleLocationHelper.getLocationIdsBySalesIds(salesIds);
        Log.d("Utils: Location Ids", locationIds.toString());
        return locationHelper.getLocationsByIds(locationIds);
    }

    public static ArrayList<Sale> getLocationValidSales(String locationId, ArrayList<String> categories) {
        ArrayList<String> salesIds = saleLocationHelper.getSalesIdsByLocationId(locationId);
        ArrayList<Sale> validSales = new ArrayList<>();

        Date date = new Date();
        String DATE_FORMAT = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            for(Sale sale : saleHelper.getSalesByIds(salesIds)) {
                if (date.before(sdf.parse(sale.getExpirationDate()))
                    && categories.contains(sale.getCategoryName())
                    && !CustomerHomeActivity.salesViewed.contains(sale.getSaleId())) {
                    validSales.add(sale);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return validSales;
    }

    public static ArrayList<Sale> getValidSales(ArrayList<String> categories) {
        ArrayList<Sale> validSales = new ArrayList<>();

        Date date = new Date();
        String DATE_FORMAT = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            for(Sale sale : saleHelper.getAllSales()) {
                if (date.before(sdf.parse(sale.getExpirationDate()))
                    && categories.contains(sale.getCategoryName())
                    && !CustomerHomeActivity.salesViewed.contains(sale.getSaleId())) {
                    validSales.add(sale);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return validSales;
    }
}
