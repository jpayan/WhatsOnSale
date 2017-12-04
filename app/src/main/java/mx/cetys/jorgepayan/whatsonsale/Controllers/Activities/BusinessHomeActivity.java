package mx.cetys.jorgepayan.whatsonsale.Controllers.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.BusinessSettingsFragment;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.LocationHomeFragment;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.SaleHomeFragment;
import mx.cetys.jorgepayan.whatsonsale.Models.Business;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.BusinessHelper;

public class BusinessHomeActivity extends AppCompatActivity {

    public static Business currentBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);

        final BusinessHelper businessHelper = new BusinessHelper(getApplicationContext());
        Intent fromMain = getIntent();
        String email = fromMain.getStringExtra(MainActivity.MAIN_EMAIL);

        currentBusiness = businessHelper.getBusinessByEmail(email);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
            (new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.item_location:
                            selectedFragment = LocationHomeFragment.newInstance();
                            break;
                        case R.id.item_sale:
                            selectedFragment = SaleHomeFragment.newInstance();
                            break;
                        case R.id.item_businessSettings:
                            selectedFragment = BusinessSettingsFragment.newInstance();
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                }
            });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, LocationHomeFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
