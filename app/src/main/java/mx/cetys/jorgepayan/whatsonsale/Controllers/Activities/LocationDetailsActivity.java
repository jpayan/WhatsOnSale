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

import java.util.ArrayList;
import java.util.HashMap;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.LocationHomeFragment;
import mx.cetys.jorgepayan.whatsonsale.Models.BusinessLocation;
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
    BusinessLocation businessLocation = new BusinessLocation();

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
            businessLocation = fromActivity.getParcelableExtra(LocationHomeFragment.LOCATION);
            System.out.println("BusinessLocation");
            System.out.println(businessLocation);
            editTextLocationName.setText(businessLocation.getName());
            editTextLocationAddress.setText(businessLocation.getAddress());
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
                businessLocation.setName(editTextLocationName.getText().toString());

                if(businessLocation.getName().length() > 0 && businessLocation.getAddress().length() > 0) {
                    final String locationId = (edit) ? businessLocation.getLocationId() :
                        locationHelper.addLocation("", businessLocation.getName(),
                        BusinessHomeActivity.currentBusiness.getBusinessId(),
                        businessLocation.getLatitude(), businessLocation.getLongitude(),
                        businessLocation.getAddress());

                    if (edit) {
                        locationHelper.updateLocation(businessLocation);
                    }

                    Utils.post("location", getApplicationContext(), new HashMap<String, String>(){{
                        put("address", businessLocation.getAddress());
                        put("location_name", businessLocation.getName());
                        put("latitude", String.valueOf(businessLocation.getLatitude()));
                        put("longitude", String.valueOf(businessLocation.getLongitude()));
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
                             "Fill up all the fields before saving the businessLocation.", "Ok");
                    emptyFieldsDialog.show(fm, "Alert Dialog Fragment");
                }
            }
        });

        btnDeleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationHelper.deleteLocation(businessLocation.getLocationId());

                Utils.delete("businessLocation", getApplicationContext(), new ArrayList<String>() {{
                    add(businessLocation.getLocationId());
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

                businessLocation.setAddress((String) place.getAddress());
                businessLocation.setLatitude(place.getLatLng().latitude);
                businessLocation.setLongitude(place.getLatLng().longitude);

                editTextLocationAddress.setText(businessLocation.getAddress());
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
