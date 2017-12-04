package mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import mx.cetys.jorgepayan.whatsonsale.Models.SaleLocation;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.DBUtils;


/**
 * Created by jorge.payan on 12/1/17.
 */

public class SaleLocationHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;

    private String[] SALE_LOCATION_TABLE_COLUMNS = {
        DBUtils.SALE_LOCATION_SALE_ID,
        DBUtils.SALE_LOCATION_LOCATION_ID
    };

    public SaleLocationHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SaleLocation getSaleLocation(String saleId, String locationId) {
        open();
        Cursor cursor = database.query(DBUtils.SALE_LOCATION_TABLE_NAME, SALE_LOCATION_TABLE_COLUMNS,
            DBUtils.SALE_LOCATION_SALE_ID + " = " + saleId + " AND " +
            DBUtils.SALE_LOCATION_LOCATION_ID + " = " + locationId, null, null, null, null);

        cursor.moveToFirst();
        SaleLocation saleLocation = parseSaleLocation(cursor);
        cursor.close();
        close();

        return saleLocation;
    }

    public long addSaleLocation(String saleId, String locationId) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.SALE_LOCATION_SALE_ID, saleId);
        values.put(DBUtils.SALE_LOCATION_LOCATION_ID, locationId);

        long saleLocationId = database.insert(DBUtils.SALE_LOCATION_TABLE_NAME, null, values);
        close();
        return saleLocationId;
    }

    public int updateSaleLocation(SaleLocation saleLocation) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.SALE_LOCATION_SALE_ID, saleLocation.getSaleId());
        values.put(DBUtils.SALE_LOCATION_LOCATION_ID, saleLocation.getLocationId());

        int response = database.update(DBUtils.SALE_LOCATION_TABLE_NAME, values,
            DBUtils.SALE_LOCATION_SALE_ID + " = " + saleLocation.getSaleId() + " AND " +
            DBUtils.SALE_LOCATION_LOCATION_ID + " = " + saleLocation.getLocationId(), null);
        close();
        return response;
    }

    public void deleteSaleLocation(String saleLocationSale_id) {
        open();
        database.delete(DBUtils.SALE_LOCATION_TABLE_NAME, DBUtils.SALE_LOCATION_SALE_ID + " = " +
                saleLocationSale_id, null);
        close();
    }

    public void clearTable() {
        open();
        database.execSQL("DELETE FROM " + DBUtils.SALE_LOCATION_TABLE_NAME);
        close();
    }

    private SaleLocation parseSaleLocation(Cursor cursor) {
        String saleLocationSaleId = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_LOCATION_SALE_ID));
        String saleLocationLocationId = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_LOCATION_LOCATION_ID));

        return new SaleLocation(saleLocationSaleId, saleLocationSaleId);
    }
}
