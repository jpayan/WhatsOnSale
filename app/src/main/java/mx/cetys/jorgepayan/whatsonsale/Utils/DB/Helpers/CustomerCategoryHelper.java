package mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Models.CustomerCategory;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.DBUtils;


/**
 * Created by jorge.payan on 12/1/17.
 */

public class CustomerCategoryHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;

    private String[] CUSTOMER_CATEGORY_TABLE_COLUMNS = {
            DBUtils.CUSTOMER_CATEGORY_CUSTOMER_ID,
            DBUtils.CUSTOMER_CATEGORY_CATEGORY_NAME
    };

    public CustomerCategoryHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public ArrayList<String> getCustomerCategoryByCustomerId(String customerId) {
        ArrayList<String> customerCategories = new ArrayList<>();
        open();
        Cursor cursor = database.query(DBUtils.CUSTOMER_CATEGORY_TABLE_NAME,
            CUSTOMER_CATEGORY_TABLE_COLUMNS, DBUtils.CUSTOMER_CATEGORY_CUSTOMER_ID + " = '" +
                customerId + "'", null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            customerCategories.add(parseCustomerCategory(cursor).getCategoryName());
            cursor.moveToNext();
        }
        cursor.close();
        close();

        return customerCategories;
    }


    public long addCustomerCategory(String customerId, String categoryId) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.CUSTOMER_CATEGORY_CUSTOMER_ID, customerId);
        values.put(DBUtils.CUSTOMER_CATEGORY_CATEGORY_NAME, categoryId);

        long customerCategoryId = database.insert(DBUtils.CUSTOMER_CATEGORY_TABLE_NAME, null, values);
        close();
        return customerCategoryId;
    }

    public int updateCustomerCategory(CustomerCategory customerCategory) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.CUSTOMER_CATEGORY_CUSTOMER_ID, customerCategory.getCustomerId());
        values.put(DBUtils.CUSTOMER_CATEGORY_CATEGORY_NAME, customerCategory.getCategoryName());

        int response = database.update(DBUtils.CUSTOMER_CATEGORY_TABLE_NAME, values, DBUtils.CUSTOMER_CATEGORY_CUSTOMER_ID + " = " + customerCategory.getCustomerId(),
                null);
        close();
        return response;
    }

    public void deleteCustomerCategory(String customerCategoryCustomer_id) {
        open();
        database.delete(DBUtils.CUSTOMER_CATEGORY_TABLE_NAME, DBUtils.CUSTOMER_CATEGORY_CUSTOMER_ID + " = " + customerCategoryCustomer_id, null);
        close();
    }

    public void clearTable() {
        open();
        database.execSQL("DELETE FROM " + DBUtils.CUSTOMER_CATEGORY_TABLE_NAME);
        close();
    }

    private CustomerCategory parseCustomerCategory(Cursor cursor) {
        String customerCategoryCustomerId = cursor.getString(cursor.getColumnIndex(DBUtils.CUSTOMER_CATEGORY_CUSTOMER_ID));
        String customerCategoryCategoryName = cursor.getString(cursor.getColumnIndex(DBUtils.CUSTOMER_CATEGORY_CATEGORY_NAME));

        return new CustomerCategory(customerCategoryCustomerId, customerCategoryCategoryName);
    }
}
