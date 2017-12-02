package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.cetys.jorgepayan.whatsonsale.R;

public class CustomerHomeActivity extends AppCompatActivity {
    MapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        mapFragment = new MapFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.map, mapFragment);
        transaction.commit();
    }

}
