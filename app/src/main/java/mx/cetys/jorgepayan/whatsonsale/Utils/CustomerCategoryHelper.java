package mx.cetys.jorgepayan.whatsonsale.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import mx.cetys.jorgepayan.whatsonsale.Models.CustomerCategory;
import mx.cetys.jorgepayan.whatsonsale.Models.SaleReview;


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

    public CustomerCategory getCustomerCategory(int customerCategoryCustomerId) {
        open();
        Cursor cursor = database.query(DBUtils.CUSTOMER_CATEGORY_TABLE_NAME, CUSTOMER_CATEGORY_TABLE_COLUMNS,
                DBUtils.CUSTOMER_CATEGORY_CUSTOMER_ID + " = " + customerCategoryCustomerId, null, null, null, null);

        cursor.moveToFirst();
        CustomerCategory customerCategory = parseCustomerCategory(cursor);
        cursor.close();
        close();

        return customerCategory;
    }

    public long addCustomerCategory(int customerId, int categoryId) {
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
        values.put(DBUtils.CUSTOMER_CATEGORY_CATEGORY_NAME, customerCategory.getCategoryId());

        int response = database.update(DBUtils.CUSTOMER_CATEGORY_TABLE_NAME, values, DBUtils.CUSTOMER_CATEGORY_CUSTOMER_ID + " = " + customerCategory.getCustomerId(),
                null);
        close();
        return response;
    }

    public void deleteCustomerCategory(int customerCategoryCustomer_id) {
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
        int customerCategoryCustomerId = cursor.getInt(cursor.getColumnIndex(DBUtils.CUSTOMER_CATEGORY_CUSTOMER_ID));
        int customerCategoryCategoryId = cursor.getInt(cursor.getColumnIndex(DBUtils.CUSTOMER_CATEGORY_CATEGORY_NAME));

        return new CustomerCategory(customerCategoryCustomerId, customerCategoryCustomerId);
    }
}
