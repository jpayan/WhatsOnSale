package mx.cetys.jorgepayan.whatsonsale.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class Location implements Parcelable {
    private String locationId;
    private String name;
    private String businessId;
    private double latitude;
    private double longitude;
    private String address;

    public Location() {

    }

    public Location(String locationId, String name, String businessId, double latitude, double longitude, String address) {
        this.locationId = locationId;
        this.name = name;
        this.businessId = businessId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public Location(Parcel in) {
        locationId = in.readString();
        name = in.readString();
        businessId = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        address = in.readString();
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(locationId);
        parcel.writeString(name);
        parcel.writeString(businessId);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(address);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
