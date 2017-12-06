package mx.cetys.jorgepayan.whatsonsale.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class Sale implements Parcelable {
    private String saleId;
    private String saleBusinessId;
    private String categoryName;
    private String description;
    private String expirationDate;

    public Sale(){

    }

    public Sale(String saleId, String saleBusinessId, String categoryName, String description, String expirationDate) {
        this.saleId = saleId;
        this.saleBusinessId = saleBusinessId;
        this.categoryName = categoryName;
        this.description = description;
        this.expirationDate = expirationDate;
    }

    public Sale(Parcel in) {
        saleId = in.readString();
        saleBusinessId = in.readString();
        categoryName = in.readString();
        description = in.readString();
        expirationDate = in.readString();
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSaleBusinessId(){return saleBusinessId;}

    public void setSaleBusinessId(String saleBusinesId) {this.saleBusinessId = saleBusinesId; }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString( saleId );
        parcel.writeString( saleBusinessId );
        parcel.writeString( categoryName );
        parcel.writeString( description );
        parcel.writeString( expirationDate );
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Sale createFromParcel(Parcel in) {
            return new Sale(in);
        }

        @Override
        public Sale[] newArray(int size) {
            return new Sale[size];
        }
    };
}
