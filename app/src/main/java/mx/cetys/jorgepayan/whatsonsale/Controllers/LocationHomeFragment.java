package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Models.Location;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.LocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;

import static android.app.Activity.RESULT_OK;

public class LocationHomeFragment extends Fragment {
    int LOCATION_DETAILS_REQUEST = 1;

    LocationHelper locationHelper;
    LocationAdapter locationAdapter;
    ListView listViewLocation;

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

        locationHelper =
                new LocationHelper(getActivity().getApplicationContext());

        locationAdapter = new LocationAdapter(getContext().getApplicationContext());
        listViewLocation = (ListView) view.findViewById(R.id.list_view_location);
        listViewLocation.setAdapter(locationAdapter);

        fillLocationListView(BusinessHomeActivity.currentBusiness.getBusinessId(), locationAdapter);

        final FloatingActionButton btnAddLocation = view.findViewById(R.id.btn_addLocation);

        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(),
                        LocationDetailsActivity.class);
                startActivityForResult(intent, LOCATION_DETAILS_REQUEST);

            }
        });
        return view;
    }

    private void fillLocationListView(String businessId, LocationAdapter locationAdapter){
        ArrayList<Location> businessLocations = locationHelper.getBusinessLocations(businessId);
        locationAdapter.clear();
        if(!businessLocations.isEmpty()){
            for(Location location : businessLocations){
                locationAdapter.add(location);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOCATION_DETAILS_REQUEST) {
            if (resultCode == RESULT_OK) {
                fillLocationListView(BusinessHomeActivity.currentBusiness.getBusinessId(),
                    locationAdapter);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SimpleDialog successDialog = new SimpleDialog("Location successfully saved.", "Ok");
                successDialog.show(fm, "Alert Dialog Fragment");
            }
        }
    }
}