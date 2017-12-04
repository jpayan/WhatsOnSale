package mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Models.Category;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.DBUtils;

/**
 * Created by jorge.payan on 12/3/17.
 */

public class CategoryHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;

    private String[] CATEGORY_TABLE_COLUMNS = {
            DBUtils.CATEGORY_NAME
    };

    public CategoryHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        open();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBUtils.CATEGORY_TABLE_NAME, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            categories.add(parseCategory(cursor).getName());
            cursor.moveToNext();
        }
        cursor.close();
        close();

        return categories;
    }

    public void addCategory(String name) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.CATEGORY_NAME, name);

        database.insert(DBUtils.CATEGORY_TABLE_NAME, null, values);
        close();
    }

    public void clearTable() {
        open();
        database.execSQL("DELETE FROM " + DBUtils.CATEGORY_TABLE_NAME);
        close();
    }

    private Category parseCategory(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(DBUtils.CATEGORY_NAME));

        return new Category(name);
    }
}
