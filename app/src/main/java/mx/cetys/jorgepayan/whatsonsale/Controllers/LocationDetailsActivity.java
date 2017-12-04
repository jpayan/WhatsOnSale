package mx.cetys.jorgepayan.whatsonsale.Controllers;

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

import mx.cetys.jorgepayan.whatsonsale.Utils.LocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.R;

public class LocationDetailsActivity extends AppCompatActivity {
    int PLACE_PICKER_REQUEST = 1;

    EditText editTextLocationAddress;
    EditText editTextLocationName;
    Button btnSaveLocation;

    String locationName;
    String locationAddress;
    LatLng locationCoordenates;
    LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_location_details );
        final FragmentManager fm = getSupportFragmentManager();

        locationHelper  = new LocationHelper(getApplicationContext());
        editTextLocationAddress = (EditText) findViewById(R.id.edit_text_locationAddress);
        editTextLocationName = (EditText) findViewById(R.id.edit_txt_locationName);
        btnSaveLocation = (Button) findViewById(R.id.btn_saveLocation);

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
                locationName = editTextLocationName.getText().toString();
                locationAddress = editTextLocationAddress.getText().toString();
                if(locationName.length() > 0 && locationAddress.length() > 0) {
                    locationHelper.addLocation("", locationName,
                        BusinessHomeActivity.currentBusiness.getBusinessId(),
                        locationCoordenates.latitude, locationCoordenates.longitude,
                        locationAddress);
                    Intent data = new Intent();
                    data.putExtra("SUCCESS", "Success.");
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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                locationAddress = (String) place.getAddress();
                locationCoordenates = place.getLatLng();
                editTextLocationAddress.setText(locationAddress);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
