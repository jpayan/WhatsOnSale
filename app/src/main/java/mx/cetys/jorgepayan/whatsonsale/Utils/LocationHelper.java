package mx.cetys.jorgepayan.whatsonsale.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Models.Location;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class LocationHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;

    private String[] LOCATION_TABLE_COLUMNS = {
            DBUtils.LOCATION_ID,
            DBUtils.LOCATION_BUSINESS_ID,
            DBUtils.LOCATION_LATITUDE,
            DBUtils.LOCATION_LONGITUDE,
            DBUtils.LOCATION_ADDRESS
    };

    public LocationHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Location getLocation(String locationId) {
        open();
        Cursor cursor = database.query(DBUtils.LOCATION_TABLE_NAME, LOCATION_TABLE_COLUMNS,
                DBUtils.LOCATION_ID + " = " + locationId, null, null, null, null);

        cursor.moveToFirst();
        Location location = parseLocation(cursor);
        cursor.close();
        close();

        return location;
    }

    public ArrayList<Location> getAllLocations() {
        ArrayList<Location> locationArray = new ArrayList<>();
        open();
        Cursor cursor = database.query(DBUtils.LOCATION_TABLE_NAME, LOCATION_TABLE_COLUMNS,
                null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            locationArray.add(parseLocation(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        close();

        return locationArray;
    }

    public void addlocation(int businessId, double latitude, double longitude, String address) {
        ContentValues values = new ContentValues();

        values.put(DBUtils.LOCATION_BUSINESS_ID, businessId);
        values.put(DBUtils.LOCATION_LATITUDE, latitude);
        values.put(DBUtils.LOCATION_LONGITUDE, longitude);
        values.put(DBUtils.LOCATION_ADDRESS, address);

        open();
        database.insert(DBUtils.LOCATION_TABLE_NAME, null, values);
        close();
    }

    public void updateLocation(Location location) {
        ContentValues values = new ContentValues();

        values.put(DBUtils.LOCATION_ID, location.getLocationId());
        values.put(DBUtils.LOCATION_BUSINESS_ID, location.getBusinessId());
        values.put(DBUtils.LOCATION_LATITUDE, location.getLatitude());
        values.put(DBUtils.LOCATION_LONGITUDE, location.getLongitude());
        values.put(DBUtils.LOCATION_ADDRESS, location.getAddress());

        open();
        database.update(DBUtils.LOCATION_TABLE_NAME, values, DBUtils.LOCATION_ID + " = " +
                        location.getLocationId(), null);
        close();
    }

    public void deleteLocation(int locationId) {
        open();
        database.delete(DBUtils.LOCATION_TABLE_NAME, DBUtils.LOCATION_ID + " = " + locationId,
                        null);
        close();
    }

    public void clearTable() {
        open();
        database.execSQL("DELETE FROM " + DBUtils.LOCATION_TABLE_NAME);
        close();
    }

    private Location parseLocation(Cursor cursor) {

        int locationId = cursor.getInt(cursor.getColumnIndex(DBUtils.LOCATION_ID));
        int businessId = cursor.getInt(cursor.getColumnIndex(DBUtils.LOCATION_BUSINESS_ID));
        double latitude = cursor.getDouble(cursor.getColumnIndex(DBUtils.LOCATION_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(DBUtils.LOCATION_LONGITUDE));
        String address = cursor.getString(cursor.getColumnIndex(DBUtils.LOCATION_ADDRESS));

        return new Location(locationId, businessId, latitude, longitude, address);
    }
}
