package mx.cetys.jorgepayan.whatsonsale.Controllers.Activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.CustomerReviewHomeFragment;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.CustomerSaleHomeFragment;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.CustomerSettingsFragment;
import mx.cetys.jorgepayan.whatsonsale.Models.BusinessLocation;
import mx.cetys.jorgepayan.whatsonsale.Models.Category;
import mx.cetys.jorgepayan.whatsonsale.Models.Customer;
import mx.cetys.jorgepayan.whatsonsale.Models.Sale;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Services.GeofenceTrasitionService;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.CustomerCategoryHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.CustomerHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.LocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;

public class CustomerHomeActivity extends AppCompatActivity
    implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ResultCallback<Status> {

    public static GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationRequest locationRequest;

    public static Context context;

    public static final String TAG = CustomerHomeActivity.class.getSimpleName();
    private static final float RADIUS = 500;
    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final int REQ_PERMISSION = 999;
    private static final int GEOFENCE_REQ_CODE = 0;
    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";

    // TODO CHANGE TIME INTERVALS
    private final int UPDATE_INTERVAL =  5 * 1000; // 5 secs
    private final int FASTEST_INTERVAL = 1000; // 1 sec
//    private final int UPDATE_INTERVAL =  3 * 60 * 1000; // 3 minutes
//    private final int FASTEST_INTERVAL = 30 * 1000;  // 30 secs

    public static Customer currentCustomer;
    private CustomerHelper customerHelper;

    public static ArrayList<Sale> validSales;
    public static ArrayList<String> customerCategories;
    public static ArrayList<String> salesViewed = new ArrayList<>();

    private BottomNavigationView bottomNavigationView;

    private LocationHelper locationHelper;
    private ArrayList<BusinessLocation> locations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        context = getApplicationContext();

        customerHelper = new CustomerHelper(getApplicationContext());
        Intent fromMain = getIntent();
        String email = fromMain.getStringExtra(MainActivity.MAIN_EMAIL);

        currentCustomer = customerHelper.getCustomerByEmail(email);

        customerCategories = new CustomerCategoryHelper(getApplicationContext())
                .getCustomerCategoryByCustomerId(currentCustomer.getCustomerId());

        Log.d("Customer Categories", customerCategories.toString());

        validSales = Utils.getValidSales(customerCategories);

        Log.d("Valid Sales", validSales.toString());

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.customer_navigation);

        setBottomNavigationItemSelectedListener();

        locationHelper = new LocationHelper(this);

        locations = Utils.filterLocationsByCustomerCategory(currentCustomer.getCustomerId());

        createGoogleApi();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.customer_frame_layout, CustomerSettingsFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        googleApiClient.disconnect();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Log.d(TAG, "onPointerCaptureChanged()");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected()");
        askPermission();
        clearGeofences();
        getLastKnownLocation();
        Log.d(TAG, locations.toString());
        setGeofences(locations);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed()");
    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.i(TAG, "onResult: " + status);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        Log.d(TAG, "onLocationChanged ["+location+"]");
        lastLocation = location;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ( requestCode ) {
            case REQ_PERMISSION: {
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    // Permission granted

                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }

    private void createGoogleApi() {
        Log.d(TAG, "createGoogleApi()");
        if ( googleApiClient == null ) {
            Log.d(TAG, "Instantiating googleApiClient");
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .addApi( LocationServices.API )
                    .build();
            Log.d(TAG, googleApiClient.toString());
        }
    }

    private void clearGeofences() {
        Log.d(TAG, "clearGeofence()");
        LocationServices.GeofencingApi.removeGeofences(googleApiClient,
            createGeofencePendingIntent()).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    Log.d(TAG, "Geofences removed.");
                }
            }
        );
    }

    private void setBottomNavigationItemSelectedListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener
            (new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.item_home:
                            selectedFragment = CustomerSaleHomeFragment.newInstance();
                            break;
                        case R.id.item_reviews:
                            selectedFragment = CustomerReviewHomeFragment.newInstance();
                            break;
                        case R.id.item_customerSettings:
                            selectedFragment = CustomerSettingsFragment.newInstance();
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.customer_frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                }
            });
    }

    private void startLocationUpdates(){
        Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if ( checkPermission() )
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation()");
        if ( checkPermission() ) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if ( lastLocation != null ) {
                Log.i(TAG, "LasKnown location. " +
                        "Long: " + lastLocation.getLongitude() +
                        " | Lat: " + lastLocation.getLatitude());
                startLocationUpdates();
            } else {
                Log.w(TAG, "No location retrieved yet");
                startLocationUpdates();
            }
        }
        else askPermission();
    }

    private void permissionsDenied() {
        Log.w(TAG, "permissionsDenied()");
        // TODO close app and warn user
    }

    public static Intent makeNotificationIntent(Context context, String msg) {
        Log.d(TAG, "makeNotificationIntent()");
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(NOTIFICATION_MSG, msg);
        return intent;
    }

    private Geofence createGeofence(BusinessLocation businessLocation) {
        Log.d(TAG, "createGeofence()");
        return new Geofence.Builder()
                .setRequestId(businessLocation.getLocationId())
                .setCircularRegion(businessLocation.getLatitude(), businessLocation.getLongitude(), RADIUS)
                .setExpirationDuration( GEO_DURATION )
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();
    }

    private GeofencingRequest createGeofenceRequest(Geofence geofence) {
        Log.d(TAG, "createGeofenceRequest()");
        return new GeofencingRequest.Builder()
                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER )
                .addGeofence( geofence )
                .build();
    }

    private PendingIntent createGeofencePendingIntent() {
        Log.d(TAG, "createGeofencePendingIntent()");
        Intent intent = new Intent(this, GeofenceTrasitionService.class);
        return PendingIntent.getService(this, GEOFENCE_REQ_CODE, intent,
            PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    private void askPermission() {
        Log.d(TAG, "askPermission()");
        if (!checkPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQ_PERMISSION);
        }
    }

    private void addGeofence(GeofencingRequest request) {
        Log.d(TAG, "addGeofence()");
        if (checkPermission()) {
            LocationServices.GeofencingApi.addGeofences(googleApiClient, request,
                createGeofencePendingIntent()).setResultCallback(this);
        }
    }

    private void startGeofence(BusinessLocation businessLocation) {
        Log.i(TAG, "startGeofence()");
        Geofence geofence = createGeofence(businessLocation);
        GeofencingRequest geofenceRequest = createGeofenceRequest(geofence);
        addGeofence(geofenceRequest);
    }

    private void setGeofences(ArrayList<BusinessLocation> businessLocations) {
        Log.d(TAG, "setGeofences()");
        for(BusinessLocation businessLocation : businessLocations) {
            startGeofence(businessLocation);
            Log.d(TAG, businessLocation.toString());
        }
    }

}
