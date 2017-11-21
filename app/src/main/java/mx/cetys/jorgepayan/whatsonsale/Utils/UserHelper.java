package mx.cetys.jorgepayan.whatsonsale.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Models.User;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class UserHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;

    private String[] USER_TABLE_COLUMNS = {
            DBUtils.USER_ID,
            DBUtils.USER_EMAIL,
            DBUtils.USER_PASSWORD,
            DBUtils.USER_TYPE
    };

    public UserHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public User getUser(String userId) {
        open();
        Cursor cursor = database.query(DBUtils.USER_TABLE_NAME, USER_TABLE_COLUMNS,
                DBUtils.USER_ID + " = " + userId, null, null, null, null);

        cursor.moveToFirst();
        User user = parseUser(cursor);
        cursor.close();
        close();

        return user;
    }

    public long addUser(String email, String password, String type) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.USER_EMAIL, email);
        values.put(DBUtils.USER_PASSWORD, password);
        values.put(DBUtils.USER_TYPE, type);

        long userId = database.insert(DBUtils.USER_TABLE_NAME, null, values);
        close();

        return userId;
    }

    public int updateUser(User user) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBUtils.USER_ID, user.getUserId());
        values.put(DBUtils.USER_EMAIL, user.getEmail());
        values.put(DBUtils.USER_PASSWORD, user.getPassword());
        values.put(DBUtils.USER_TYPE, user.getType());

        int response = database.update(DBUtils.USER_TABLE_NAME, values, DBUtils.USER_ID + " = " + user.getUserId(),
                        null);
        close();
        return response;
    }

    public void deleteUser(int userId) {
        open();
        database.delete(DBUtils.USER_TABLE_NAME, DBUtils.USER_ID + " = " + userId, null);
        close();
    }

    public void clearTable() {
        open();
        database.execSQL("DELETE FROM " + DBUtils.USER_TABLE_NAME);
        close();
    }

    private User parseUser(Cursor cursor) {
        int userId = cursor.getInt(cursor.getColumnIndex(DBUtils.USER_ID));
        String email = cursor.getString(cursor.getColumnIndex(DBUtils.USER_EMAIL));
        String password = cursor.getString(cursor.getColumnIndex(DBUtils.USER_PASSWORD));
        String type = cursor.getString(cursor.getColumnIndex(DBUtils.USER_TYPE));

        return new User(userId, email, password, type);
    }
}
