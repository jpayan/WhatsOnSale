package mx.cetys.jorgepayan.whatsonsale.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import mx.cetys.jorgepayan.whatsonsale.Models.Category;

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

    public Category getCategory(int categoryId) {
        open();
        Cursor cursor = database.query(DBUtils.CATEGORY_TABLE_NAME, CATEGORY_TABLE_COLUMNS,
                DBUtils.CATEGORY_NAME + " = '" + categoryId + "'", null, null, null, null);

        cursor.moveToFirst();
        Category category = parseCategory(cursor);
        cursor.close();
        close();

        return category;
    }

    public void addCategory(String name) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.CATEGORY_NAME, name);

        database.insert(DBUtils.CATEGORY_TABLE_NAME, null, values);
        close();
    }

    public void updateCategory(Category category) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.CATEGORY_NAME, category.getName());

        database.update(DBUtils.CATEGORY_TABLE_NAME, values, DBUtils.CATEGORY_NAME + " = '" +
                        category.getName() + "'", null);
        close();
    }

    public void deleteCategory(int categoryId) {
        open();
        database.delete(DBUtils.CATEGORY_TABLE_NAME, DBUtils.CATEGORY_NAME + " = '" + categoryId +
                        "'", null);
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
