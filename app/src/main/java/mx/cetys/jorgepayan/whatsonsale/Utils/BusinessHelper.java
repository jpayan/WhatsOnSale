package mx.cetys.jorgepayan.whatsonsale.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import mx.cetys.jorgepayan.whatsonsale.Models.Business;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class BusinessHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;

    private String[] BUSINESS_TABLE_COLUMNS = {
            DBUtils.BUSINESS_ID,
            DBUtils.BUSINESS_USER_EMAIL,
            DBUtils.BUSINESS_NAME,
            DBUtils.BUSINESS_HQ_ADDRESS
    };

    public BusinessHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public Business getBusiness(String businessId) {
        open();
        Cursor cursor = database.query(DBUtils.BUSINESS_TABLE_NAME, BUSINESS_TABLE_COLUMNS,
                DBUtils.BUSINESS_ID + " = " + businessId, null, null, null, null);

        cursor.moveToFirst();
        Business user = parseBusiness(cursor);
        cursor.close();
        close();
        return user;
    }

    public Business getBusinessByEmail(String userEmail) {
        open();
        Cursor cursor = database.query(DBUtils.BUSINESS_TABLE_NAME, BUSINESS_TABLE_COLUMNS,
                DBUtils.BUSINESS_USER_EMAIL + " = '" + userEmail + "'", null, null, null, null);

        cursor.moveToFirst();
        Business user = parseBusiness(cursor);
        cursor.close();
        close();
        return user;
    }

    public long addBusiness(String businessUserEmail, String businessName,
                            String hqAddress) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.BUSINESS_ID, Utils.generateId().toString());
        values.put(DBUtils.BUSINESS_USER_EMAIL, businessUserEmail);
        values.put(DBUtils.BUSINESS_NAME, businessName);
        values.put(DBUtils.BUSINESS_HQ_ADDRESS, hqAddress);

        long businessId = database.insert(DBUtils.BUSINESS_TABLE_NAME, null, values);
        close();

        return businessId;
    }

    public int updateBusiness(Business business) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.BUSINESS_ID, business.getBusinessId());
        values.put(DBUtils.BUSINESS_USER_EMAIL, business.getuserEmail());
        values.put(DBUtils.BUSINESS_NAME, business.getBusinessName());
        values.put(DBUtils.BUSINESS_HQ_ADDRESS, business.getHqAddress());

        int response = database.update(DBUtils.BUSINESS_TABLE_NAME, values, DBUtils.BUSINESS_ID + " = " +
            business.getuserEmail(), null);
        close();
        return response;
    }

    public void deleteBusiness(String businessId) {
        open();
        database.delete(DBUtils.BUSINESS_TABLE_NAME, DBUtils.BUSINESS_ID + " = " + businessId, null);
        close();
    }

    public void clearTable() {
        open();
        database.execSQL("DELETE FROM " + DBUtils.BUSINESS_TABLE_NAME);
        close();
    }

    private Business parseBusiness(Cursor cursor) {
        String businessId = cursor.getString(cursor.getColumnIndex(DBUtils.BUSINESS_ID));
        String businessUserEmail =
            cursor.getString(cursor.getColumnIndex(DBUtils.BUSINESS_USER_EMAIL));
        String businessName = cursor.getString(cursor.getColumnIndex(DBUtils.BUSINESS_NAME));
        String businessHQAddress =
            cursor.getString(cursor.getColumnIndex(DBUtils.BUSINESS_HQ_ADDRESS));

        return new Business(businessId, businessUserEmail, businessName, businessHQAddress);
    }
}
