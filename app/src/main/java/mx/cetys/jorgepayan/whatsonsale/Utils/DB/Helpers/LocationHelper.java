package mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Models.BusinessLocation;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.DBUtils;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class LocationHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;

    private String[] LOCATION_TABLE_COLUMNS = {
            DBUtils.LOCATION_ID,
            DBUtils.LOCATION_NAME,
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

    public BusinessLocation getLocation(String locationId) {
        open();
        Cursor cursor = database.query(DBUtils.LOCATION_TABLE_NAME, LOCATION_TABLE_COLUMNS,
                DBUtils.LOCATION_ID + " = '" + locationId + "'", null, null, null, null);

        cursor.moveToFirst();
        BusinessLocation businessLocation = parseLocation(cursor);
        cursor.close();
        close();

        return businessLocation;
    }

    public ArrayList<BusinessLocation> getLocationsByIds(ArrayList<String> locationIds) {
        ArrayList<BusinessLocation> locations = new ArrayList<>();
        if (!locationIds.isEmpty()) {
            String locationIdsGroup = "(";
            for (String locationId : locationIds) {
                locationIdsGroup += String.format("'%s',", locationId);
            }
            locationIdsGroup = locationIdsGroup.replaceFirst(".$","") + ")";
            open();
            Cursor cursor = database.query(DBUtils.LOCATION_TABLE_NAME, LOCATION_TABLE_COLUMNS,
                    DBUtils.LOCATION_ID + " IN " + locationIdsGroup, null, null, null, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                locations.add(parseLocation(cursor));
                cursor.moveToNext();
            }
            cursor.close();
            close();
        }
        return locations;
    }

    public ArrayList<BusinessLocation> getAllLocations() {
        ArrayList<BusinessLocation> businessLocations = new ArrayList<>();
        open();
        Cursor cursor = database.query(DBUtils.LOCATION_TABLE_NAME, LOCATION_TABLE_COLUMNS, null,
            null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            businessLocations.add(parseLocation(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();

        return businessLocations;
    }

    public ArrayList<BusinessLocation> getBusinessLocations(String businessId) {
        ArrayList<BusinessLocation> businessLocationArray = new ArrayList<>();

        open();
        Cursor cursor = database.query(DBUtils.LOCATION_TABLE_NAME, LOCATION_TABLE_COLUMNS,
                DBUtils.LOCATION_BUSINESS_ID + " = '" + businessId + "'", null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            businessLocationArray.add(parseLocation(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();

        return businessLocationArray;
    }

    public String addLocation(String locationId, String name, String businessId, double latitude, double longitude, String address) {
        open();
        ContentValues values = new ContentValues();

        locationId = (locationId.isEmpty()) ? Utils.generateId() : locationId;

        values.put(DBUtils.LOCATION_ID, locationId);
        values.put(DBUtils.LOCATION_NAME, name);
        values.put(DBUtils.LOCATION_BUSINESS_ID, businessId);
        values.put(DBUtils.LOCATION_LATITUDE, latitude);
        values.put(DBUtils.LOCATION_LONGITUDE, longitude);
        values.put(DBUtils.LOCATION_ADDRESS, address);

        database.insert(DBUtils.LOCATION_TABLE_NAME, null, values);
        close();

        return locationId;
    }

    public void updateLocation(BusinessLocation businessLocation) {
        ContentValues values = new ContentValues();

        values.put(DBUtils.LOCATION_ID, businessLocation.getLocationId());
        values.put(DBUtils.LOCATION_NAME, businessLocation.getName());
        values.put(DBUtils.LOCATION_BUSINESS_ID, businessLocation.getBusinessId());
        values.put(DBUtils.LOCATION_LATITUDE, Double.valueOf(businessLocation.getLatitude()));
        values.put(DBUtils.LOCATION_LONGITUDE, Double.valueOf(businessLocation.getLongitude()));
        values.put(DBUtils.LOCATION_ADDRESS, businessLocation.getAddress());

        open();
        database.update(DBUtils.LOCATION_TABLE_NAME, values, DBUtils.LOCATION_ID + " = '" +
                        businessLocation.getLocationId() + "'", null);
        close();
    }

    public void deleteLocation(String locationId) {
        open();
        database.delete(DBUtils.LOCATION_TABLE_NAME, DBUtils.LOCATION_ID + " = '" + locationId + "'",
                        null);
        close();
    }

    public void clearTable() {
        open();
        database.execSQL("DELETE FROM " + DBUtils.LOCATION_TABLE_NAME);
        close();
    }

    private BusinessLocation parseLocation(Cursor cursor) {

        String locationId = cursor.getString(cursor.getColumnIndex(DBUtils.LOCATION_ID));
        String name = cursor.getString(cursor.getColumnIndex(DBUtils.LOCATION_NAME));
        String businessId = cursor.getString(cursor.getColumnIndex(DBUtils.LOCATION_BUSINESS_ID));
        double latitude = cursor.getDouble(cursor.getColumnIndex(DBUtils.LOCATION_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(DBUtils.LOCATION_LONGITUDE));
        String address = cursor.getString(cursor.getColumnIndex(DBUtils.LOCATION_ADDRESS));

        return new BusinessLocation(locationId, name, businessId, latitude, longitude, address);
    }
}
