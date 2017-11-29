package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.LocationHelper;

public class LocationHomeFragment extends Fragment {

    public static LocationHomeFragment newInstance() {
        return new LocationHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_home, container, false);

        final EditText editTextLocation = view.findViewById(R.id.edit_text_location);
        final Button btnAddLocation = view.findViewById(R.id.btn_addLocation);
        final Button btnViewLocations = view.findViewById(R.id.btn_viewLocations);

        final LocationHelper locationHelper =
                new LocationHelper(getActivity().getApplicationContext());

        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = editTextLocation.getText().toString();
                LatLng latlng =
                        getLocationFromAddress(getActivity().getApplicationContext(), address);
                double latitude = latlng.latitude;
                double longitude = latlng.longitude;
                int businessId = BusinessHomeActivity.currentBusiness.getBusinessId();
                locationHelper.addlocation(businessId, latitude, longitude, address);
                locationHelper.getAllLocations();
            }
        });

        btnViewLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> addresses;
        LatLng p1 = null;

        try {
            addresses = coder.getFromLocationName(strAddress, 5);
            if (addresses == null) {
                return null;
            }
            Address location = addresses.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return p1;
    }

}