package mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.BusinessHomeActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.LocationDetailsActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Adapters.LocationAdapter;
import mx.cetys.jorgepayan.whatsonsale.Models.Location;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.LocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;

import static android.app.Activity.RESULT_OK;

public class LocationHomeFragment extends Fragment {
    public static String CALLING_ACTIVITY = "CALLING_ACTIVITY";
    public static String LOCATION = "LOCATION";
    public static String SUCCESS = "SUCCESS";
    
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

        locationHelper = new LocationHelper(getActivity().getApplicationContext());

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
                intent.putExtra(CALLING_ACTIVITY, 0);
                startActivityForResult(intent, LOCATION_DETAILS_REQUEST);

            }
        });

        listViewLocation.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location location = (Location) listViewLocation.getItemAtPosition(position);

                Intent intent = new Intent(getContext().getApplicationContext(),
                    LocationDetailsActivity.class);
                intent.putExtra(CALLING_ACTIVITY, 1);
                intent.putExtra(LOCATION, location);
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
                SimpleDialog successDialog = new SimpleDialog("Location successfully " + data.getStringExtra(SUCCESS), "Ok");
                successDialog.show(fm, "Alert Dialog Fragment");
            }
        }
    }
}