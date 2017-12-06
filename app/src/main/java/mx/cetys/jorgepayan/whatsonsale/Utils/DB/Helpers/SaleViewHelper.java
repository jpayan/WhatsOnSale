package mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Models.SaleView;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.DBUtils;


/**
 * Created by jorge.payan on 12/1/17.
 */

public class SaleViewHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;

    private String[] SALE_VIEW_TABLE_COLUMNS = {
            DBUtils.SALE_VIEW_SALE_ID,
            DBUtils.SALE_VIEW_CUSTOMER_ID
    };

    public SaleViewHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public SaleView getSaleView(String saleId, String customerId) {
        open();
        Cursor cursor = database.query(DBUtils.SALE_VIEW_TABLE_NAME, SALE_VIEW_TABLE_COLUMNS,
                DBUtils.SALE_VIEW_SALE_ID + " = " + saleId + " AND " +
                        DBUtils.SALE_VIEW_CUSTOMER_ID + " = " + customerId, null, null, null, null);

        cursor.moveToFirst();
        SaleView saleLocation = parseSaleView(cursor);
        cursor.close();
        close();

        return saleLocation;
    }

    public ArrayList<String> getSalesIdsByCustomerId(String customerId) {
        ArrayList<String> salesIds = new ArrayList<>();
        open();
        Cursor cursor = database.query(DBUtils.SALE_VIEW_TABLE_NAME, SALE_VIEW_TABLE_COLUMNS,
                DBUtils.SALE_VIEW_CUSTOMER_ID + " = '" + customerId + "'", null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            salesIds.add(parseSaleView(cursor).getSaleId());
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return salesIds;
    }

    public long addSaleView(String saleId, String customerId) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.SALE_VIEW_SALE_ID, saleId);
        values.put(DBUtils.SALE_VIEW_CUSTOMER_ID, customerId);

        long saleLocationId = database.insert(DBUtils.SALE_VIEW_TABLE_NAME, null, values);
        close();
        return saleLocationId;
    }

    public void clearTable() {
        open();
        database.execSQL("DELETE FROM " + DBUtils.SALE_VIEW_TABLE_NAME);
        close();
    }

    private SaleView parseSaleView(Cursor cursor) {
        String saleLocationSaleId = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_VIEW_SALE_ID));
        String saleLocationCustomerId = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_VIEW_CUSTOMER_ID));

        return new SaleView(saleLocationSaleId, saleLocationCustomerId);
    }
}
