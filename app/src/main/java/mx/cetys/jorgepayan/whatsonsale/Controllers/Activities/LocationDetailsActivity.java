package mx.cetys.jorgepayan.whatsonsale.Controllers.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.LocationHomeFragment;
import mx.cetys.jorgepayan.whatsonsale.Models.Location;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.LocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;

public class LocationDetailsActivity extends AppCompatActivity {
    int PLACE_PICKER_REQUEST = 1;

    EditText editTextLocationAddress;
    EditText editTextLocationName;
    Button btnSaveLocation;
    Button btnDeleteLocation;

    LocationHelper locationHelper;
    Location location = new Location();

    boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        locationHelper  = new LocationHelper(getApplicationContext());
        editTextLocationAddress = (EditText) findViewById(R.id.edit_text_locationAddress);
        editTextLocationName = (EditText) findViewById(R.id.edit_txt_locationName);
        btnSaveLocation = (Button) findViewById(R.id.btn_saveLocation);
        btnDeleteLocation = (Button) findViewById(R.id.btn_deleteLocation);

        Intent fromActivity = getIntent();
        int callingActivity = fromActivity.getIntExtra(LocationHomeFragment.CALLING_ACTIVITY, 0);

        if (callingActivity == 0) {
            btnDeleteLocation.setVisibility(View.INVISIBLE);
            btnDeleteLocation.setClickable(false);
        } else {
            edit = true;
            location = fromActivity.getParcelableExtra(LocationHomeFragment.LOCATION);
            System.out.println("Location");
            System.out.println(location);
            editTextLocationName.setText(location.getName());
            editTextLocationAddress.setText(location.getAddress());
        }

        final FragmentManager fm = getSupportFragmentManager();


        editTextLocationAddress.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(LocationDetailsActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        } );

        btnSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location.setName(editTextLocationName.getText().toString());

                if(location.getName().length() > 0 && location.getAddress().length() > 0) {
                    final String locationId = (edit) ? location.getLocationId() :
                        locationHelper.addLocation("", location.getName(),
                        BusinessHomeActivity.currentBusiness.getBusinessId(),
                        location.getLatitude(), location.getLongitude(),
                        location.getAddress());

                    if (edit) {
                        locationHelper.updateLocation(location);
                    }

                    Utils.post("location", getApplicationContext(), new HashMap<String, String>(){{
                        put("address", location.getAddress());
                        put("location_name", location.getName());
                        put("latitude", String.valueOf(location.getLatitude()));
                        put("longitude", String.valueOf(location.getLongitude()));
                        put("business_id", BusinessHomeActivity.currentBusiness.getBusinessId());
                        put("location_id", locationId);
                    }});

                    Intent data = new Intent();
                    data.putExtra(LocationHomeFragment.SUCCESS, "saved.");
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                     SimpleDialog emptyFieldsDialog =
                         new SimpleDialog(
                             "Fill up all the fields before saving the location.", "Ok");
                    emptyFieldsDialog.show(fm, "Alert Dialog Fragment");
                }
            }
        });

        btnDeleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationHelper.deleteLocation(location.getLocationId());

                Utils.delete("location", getApplicationContext(), new ArrayList<String>() {{
                    add(location.getLocationId());
                }}, fm);

                Intent data = new Intent();
                data.putExtra(LocationHomeFragment.SUCCESS, "deleted.");
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                location.setAddress((String) place.getAddress());
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);

                editTextLocationAddress.setText(location.getAddress());
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
