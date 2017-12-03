package mx.cetys.jorgepayan.whatsonsale.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jorge.payan on 11/17/17.
 */

public class DBUtils extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "WhatsOnSale.db";
    public static final int DATABASE_VERSION = 12;

    /******************************************** USER ********************************************/

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_TYPE = "type";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE_NAME + "(" +
                    USER_EMAIL + " TEXT PRIMARY KEY, " +
                    USER_PASSWORD + " TEXT NOT NULL, " +
                    USER_TYPE + " TEXT NOT NULL" +
                    ");";

    /****************************************** CATEGORY ******************************************/

    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_NAME = "name";

    public static final String CREATE_CATEGORY_TABLE =
            "CREATE TABLE " + CATEGORY_TABLE_NAME + "(" +
                    CATEGORY_NAME + " TEXT PRIMARY KEY" +
                    ");";

    /****************************************** BUSINESS ******************************************/

    public static final String BUSINESS_TABLE_NAME = "business";
    public static final String BUSINESS_ID = "business_id";
    public static final String BUSINESS_USER_EMAIL = "user_email";
    public static final String BUSINESS_NAME = "business_name";
    public static final String BUSINESS_HQ_ADDRESS = "hq_address";

    public static final String CREATE_BUSINESS_TABLE =
            "CREATE TABLE " + BUSINESS_TABLE_NAME + "(" +
                    BUSINESS_ID + " TEXT PRIMARY KEY, " +
                    BUSINESS_NAME + " TEXT NOT NULL, " +
                    BUSINESS_HQ_ADDRESS + " TEXT NOT NULL, " +
                    BUSINESS_USER_EMAIL + " TEXT NOT NULL" +
                    ");";

    /****************************************** CUSTOMER ******************************************/

    public static final String CUSTOMER_TABLE_NAME = "customer";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_USER_EMAIL = "user_email";
    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_AGE = "age";
    public static final String CUSTOMER_GENDER = "gender";

    public static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE " + CUSTOMER_TABLE_NAME + "(" +
                    CUSTOMER_ID + " TEXT PRIMARY KEY, " +
                    CUSTOMER_NAME + " TEXT NOT NULL, " +
                    CUSTOMER_AGE + " TEXT NOT NULL, " +
                    CUSTOMER_GENDER + " TEXT, " +
                    CUSTOMER_USER_EMAIL + " TEXT NOT NULL " +
                    ");";

    /****************************************** LOCATION ******************************************/

    public static final String LOCATION_TABLE_NAME = "location";
    public static final String LOCATION_ID = "location_id";
    public static final String LOCATION_NAME = "location_name";
    public static final String LOCATION_BUSINESS_ID = "business_id";
    public static final String LOCATION_LATITUDE = "latitude";
    public static final String LOCATION_LONGITUDE = "longitude";
    public static final String LOCATION_ADDRESS = "address";

    public static final String CREATE_LOCATION_TABLE =
        "CREATE TABLE " + LOCATION_TABLE_NAME + "(" +
            LOCATION_ID + " TEXT PRIMARY KEY, " +
            LOCATION_NAME + " TEXT NOT NULL, " +
            LOCATION_LATITUDE + " REAL NOT NULL, " +
            LOCATION_LONGITUDE + " REAL NOT NULL, " +
            LOCATION_ADDRESS + " TEXT NOT NULL, " +
            LOCATION_BUSINESS_ID + " TEXT NOT NULL " +
        ");";

    /******************************************** SALE ********************************************/

    public static final String SALE_TABLE_NAME = "sale";
    public static final String SALE_ID = "sale_id";
    public static final String SALE_BUSINESS_ID = "sale_business_id";
    public static final String SALE_CATEGORY_NAME = "category_name";
    public static final String SALE_DESCRIPTION = "description";
    public static final String SALE_EXPIRATION_DATE = "expiration_date";

    public static final String CREATE_SALE_TABLE =
            "CREATE TABLE " + SALE_TABLE_NAME + "(" +
                    SALE_ID + " TEXT PRIMARY KEY, " +
                    SALE_BUSINESS_ID + " TEXT NOT NULL, " +
                    SALE_DESCRIPTION + " TEXT NOT NULL, " +
                    SALE_EXPIRATION_DATE + " TEXT NOT NULL, " +
                    SALE_CATEGORY_NAME + " TEXT NOT NULL " +
                    ");";

    /*************************************** SALE LOCATION ****************************************/

    public static final String SALE_LOCATION_TABLE_NAME = "sale_location";
    public static final String SALE_LOCATION_SALE_ID = "sale_id";
    public static final String SALE_LOCATION_LOCATION_ID = "location_id";

    public static final String CREATE_SALE_LOCATION_TABLE =
            "CREATE TABLE " + SALE_LOCATION_TABLE_NAME + "(" +
                    SALE_LOCATION_SALE_ID + " TEXT NOT NULL, " +
                    SALE_LOCATION_LOCATION_ID + " TEXT NOT NULL " +
                    ");";

    /************************************* CUSTOMER CATEGORY **************************************/

    public static final String CUSTOMER_CATEGORY_TABLE_NAME = "customer_category";
    public static final String CUSTOMER_CATEGORY_CUSTOMER_ID= "customer_id";
    public static final String CUSTOMER_CATEGORY_CATEGORY_NAME = "category_name";

    public static final String CREATE_CUSTOMER_CATEGORY_TABLE =
            "CREATE TABLE " + CUSTOMER_CATEGORY_TABLE_NAME + "(" +
                    CUSTOMER_CATEGORY_CUSTOMER_ID + " TEXT NOT NULL, " +
                    CUSTOMER_CATEGORY_CATEGORY_NAME + " TEXT NOT NULL " +
                    ");";

    /*************************************** SALE REVIEW ******************************************/

    public static final String SALE_REVIEW_TABLE_NAME = "sale_review";
    public static final String SALE_REVIEW_SALE_ID= "customer_id";
    public static final String SALE_REVIEW_CUSTOMER_ID = "location_id";
    public static final String SALE_REVIEW_DATE = "date";
    public static final String SALE_REVIEW_LIKED = "liked";

    public static final String CREATE_SALE_REVIEW_TABLE =
            "CREATE TABLE " + SALE_REVIEW_TABLE_NAME + "(" +
                    SALE_REVIEW_SALE_ID + " TEXT NOT NULL, " +
                    SALE_REVIEW_CUSTOMER_ID + " TEXT NOT NULL, " +
                    SALE_REVIEW_DATE + " TEXT NOT NULL, " +
                    SALE_REVIEW_LIKED + " TEXT NOT NULL " +
                    ");";

    public DBUtils(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(CREATE_BUSINESS_TABLE);
        sqLiteDatabase.execSQL(CREATE_CUSTOMER_TABLE);
        sqLiteDatabase.execSQL(CREATE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(CREATE_SALE_TABLE);
        sqLiteDatabase.execSQL(CREATE_SALE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(CREATE_CUSTOMER_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(CREATE_SALE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BUSINESS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SALE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SALE_LOCATION_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_CATEGORY_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SALE_REVIEW_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}