package mx.cetys.jorgepayan.whatsonsale.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Models.Sale;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class SaleHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;

    private String[] SALE_TABLE_COLUMNS = {
            DBUtils.SALE_ID,
            DBUtils.SALE_BUSINESS_ID,
            DBUtils.SALE_CATEGORY_NAME,
            DBUtils.SALE_DESCRIPTION,
            DBUtils.SALE_EXPIRATION_DATE
    };

    public SaleHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Sale getSale(String sale_id) {
        Cursor cursor = database.query(DBUtils.SALE_TABLE_NAME, SALE_TABLE_COLUMNS,
                DBUtils.SALE_ID + " = '" + sale_id+"'", null, null, null, null);

        cursor.moveToFirst();
        Sale sale = parseSale(cursor);
        cursor.close();

        return sale;
    }

    public ArrayList<Sale> getBusinessSales(String businessId) {
        ArrayList<Sale> saleArray = new ArrayList<>();

        open();
        Cursor cursor = database.query(DBUtils.SALE_TABLE_NAME, SALE_TABLE_COLUMNS,
                DBUtils.SALE_BUSINESS_ID + " = '" + businessId+"'", null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            saleArray.add(parseSale(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();

        return saleArray;
    }

    public void addSale(String saleBussinesId, String categoryName, String description, String expiration_date) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.SALE_ID, Utils.generateId());
        values.put(DBUtils.SALE_BUSINESS_ID, saleBussinesId);
        values.put(DBUtils.SALE_CATEGORY_NAME, categoryName);
        values.put(DBUtils.SALE_DESCRIPTION, description);
        values.put(DBUtils.SALE_EXPIRATION_DATE, expiration_date);

        database.insert(DBUtils.SALE_TABLE_NAME, null, values);
        close();
    }

    public void updateSale(Sale Sale) {
        ContentValues values = new ContentValues();

        values.put(DBUtils.SALE_ID, Sale.getSaleId());
        values.put(DBUtils.SALE_CATEGORY_NAME, Sale.getcategoryName());
        values.put(DBUtils.SALE_DESCRIPTION, Sale.getDescription());
        values.put(DBUtils.SALE_EXPIRATION_DATE, Sale.getExpirationDate());


        database.update(DBUtils.SALE_TABLE_NAME, values, DBUtils.SALE_ID + " = " + Sale.getSaleId(),
                null);
    }

    public void deleteSale(String SaleId) {
        database.delete(DBUtils.SALE_TABLE_NAME, DBUtils.SALE_ID + " = " + SaleId, null);
    }

    public void clearTable() {
        database.execSQL("DELETE FROM " + DBUtils.SALE_TABLE_NAME);
    }

    private Sale parseSale(Cursor cursor) {

        String SaleId = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_ID));
        String saleBusinessId = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_BUSINESS_ID ));
        String categoryName = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_CATEGORY_NAME));
        String description = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_DESCRIPTION));
        String expirationdate =
                cursor.getString(cursor.getColumnIndex(DBUtils.SALE_EXPIRATION_DATE));


        return new Sale(SaleId, saleBusinessId, categoryName, description, expirationdate);
    }
}
