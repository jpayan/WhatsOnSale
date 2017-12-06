package mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import mx.cetys.jorgepayan.whatsonsale.Models.SaleReview;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.DBUtils;

/**
 * Created by jorge.payan on 12/1/17.
 */

public class SaleReviewHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;

    private String[] SALE_REVIEW_TABLE_COLUMNS = {
            DBUtils.SALE_REVIEW_SALE_ID,
            DBUtils.SALE_REVIEW_CUSTOMER_ID,
            DBUtils.SALE_REVIEW_DESCRIPTION,
            DBUtils.SALE_REVIEW_DATE,
            DBUtils.SALE_REVIEW_LIKED
    };

    public SaleReviewHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SaleReview getSaleReview(String saleReviewsSaleId) {
        open();
        Cursor cursor = database.query(DBUtils.SALE_REVIEW_TABLE_NAME, SALE_REVIEW_TABLE_COLUMNS,
                DBUtils.SALE_REVIEW_SALE_ID + " = " + saleReviewsSaleId, null, null, null, null);

        cursor.moveToFirst();
        SaleReview saleReview = parseSaleReview(cursor);
        cursor.close();
        close();

        return saleReview;
    }

    public long addSaleReview(String saleId, String customerId, String description,
                              String date, String liked) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.SALE_REVIEW_SALE_ID, saleId);
        values.put(DBUtils.SALE_REVIEW_CUSTOMER_ID, customerId);
        values.put(DBUtils.SALE_REVIEW_DESCRIPTION, description);
        values.put(DBUtils.SALE_REVIEW_DATE, date);
        values.put(DBUtils.SALE_REVIEW_LIKED, liked);

        long saleReviewId = database.insert(DBUtils.SALE_REVIEW_TABLE_NAME, null, values);
        close();
        return saleReviewId;
    }

    public int updateSaleReview(SaleReview saleReview) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.SALE_REVIEW_SALE_ID, saleReview.getSaleId());
        values.put(DBUtils.SALE_REVIEW_CUSTOMER_ID, saleReview.getCustomerId());
        values.put(DBUtils.SALE_REVIEW_DESCRIPTION, saleReview.getDescription());
        values.put(DBUtils.SALE_REVIEW_DATE, saleReview.getDate());
        values.put(DBUtils.SALE_REVIEW_LIKED, saleReview.getLiked());

        int response = database.update(DBUtils.SALE_REVIEW_TABLE_NAME, values,
                DBUtils.SALE_REVIEW_SALE_ID + " = " + saleReview.getSaleId(), null);
        close();
        return response;
    }

    public void deleteSaleReview(String saleReviewSale_id) {
        open();
        database.delete(DBUtils.SALE_REVIEW_TABLE_NAME, DBUtils.SALE_REVIEW_SALE_ID + " = " +
                saleReviewSale_id, null);
        close();
    }

    public void clearTable() {
        open();
        database.execSQL("DELETE FROM " + DBUtils.SALE_REVIEW_TABLE_NAME);
        close();
    }

    private SaleReview parseSaleReview(Cursor cursor) {
        String saleReviewSaleId = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_REVIEW_SALE_ID));
        String saleReviewCustomerId = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_REVIEW_CUSTOMER_ID));
        String saleReviewDescription = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_REVIEW_DESCRIPTION));
        String saleReviewDate = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_REVIEW_DATE));
        String saleReviewLiked = cursor.getString(cursor.getColumnIndex(DBUtils.SALE_REVIEW_LIKED));

        return new SaleReview(saleReviewSaleId, saleReviewCustomerId, saleReviewDescription,
            saleReviewDate, saleReviewLiked);
    }
}
