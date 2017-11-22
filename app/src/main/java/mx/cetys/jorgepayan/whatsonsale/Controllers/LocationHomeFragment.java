package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.cetys.jorgepayan.whatsonsale.R;

public class LocationHomeFragment extends Fragment {
    public static LocationHomeFragment newInstance() {
        LocationHomeFragment fragment = new LocationHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_home, container, false);
    }
}
