package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.LocationHelper;

public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener, OnMapReadyCallback,
        LocationListener{

    LocationHelper locationHelper;
    ArrayList<mx.cetys.jorgepayan.whatsonsale.Models.Location> locationArray =  new ArrayList<>();
    LocationHomeFragment locationHomeFragment = new LocationHomeFragment();

    protected GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_NORMAL};
    private int curMapTypeIndex = 0;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationHelper = new LocationHelper(getActivity().getApplicationContext());

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setHasOptionsMenu(true);

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();

        initListeners();
        System.out.println("GOOGLE API CLIENT: " + mGoogleApiClient);
    }

    private void initListeners() {
        try {
            getMap().setOnMarkerClickListener(this);
            getMap().setOnMapLongClickListener(this);
            getMap().setOnInfoWindowClickListener(this);
            getMap().setOnMapClickListener(this);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation( mGoogleApiClient );

        System.out.println("CURRENT LOCATION: " + mCurrentLocation);

        initCamera( mCurrentLocation );
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        MarkerOptions options = new MarkerOptions().position( latLng );
//        options.title( getAddressFromLatLng( latLng ) );
//
//        options.icon( BitmapDescriptorFactory.defaultMarker() );
//        getMap().addMarker( options );
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title( getAddressFromLatLng( latLng ) );

        options.icon( BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource( getResources(),
                        R.mipmap.ic_launcher ) ) );

        getMap().addMarker( options );
    }

    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String address = "";
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

//    private void drawCircle( LatLng location ) {
//        CircleOptions options = new CircleOptions();
//        options.center( location );
//        //Radius in meters
//        options.radius( 10 );
//        options.fillColor( getResources()
//                .getColor( R.color.fill_color ) );
//        options.strokeColor( getResources()
//                .getColor( R.color.stroke_color ) );
//        options.strokeWidth( 10 );
//        getMap().addCircle(options);
//    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void initCamera( Location location ) {
        System.out.println("ENTRE");
        if (location != null) {
            System.out.println("NO ES NULL");
            CameraPosition position = CameraPosition.builder()
                    .target(new LatLng(location.getLatitude(),
                            location.getLongitude()))
                    .zoom(16f)
                    .bearing(0.0f)
                    .tilt(0.0f)
                    .build();

            getMap().animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), null);
            getMap().setMapType(MAP_TYPES[curMapTypeIndex]);
            getMap().setTrafficEnabled(true);
            getMap().setMyLocationEnabled(true);
            getMap().getUiSettings().setZoomControlsEnabled(true);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions options = new MarkerOptions();
        ArrayList<LatLng> latlngs = new ArrayList<>();
        locationHelper.open();
        locationArray = locationHelper.getAllLocations();
        System.out.println("LOCATIONS SIZE: " + locationArray.size());
        for (mx.cetys.jorgepayan.whatsonsale.Models.Location loc: locationArray) {
            Double lat = loc.getLatitude();
            Double lon = loc.getLongitude();
            System.out.println("LAT: " + lat);
            System.out.println("LON: " + lon);
            latlngs.add(new LatLng(lat, lon));
        }
        locationHelper.close();
        for (LatLng point : latlngs) {
            System.out.println("LATLNGS: " + latlngs.size());
            options.position(point);
            options.title("Marker Title");
            options.snippet("Marker Desc");
            googleMap.addMarker(options);
        }
    }
    @Override
    public void onLocationChanged(Location location) {

    }
}