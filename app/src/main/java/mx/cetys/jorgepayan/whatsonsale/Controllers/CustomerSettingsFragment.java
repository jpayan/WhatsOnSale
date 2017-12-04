package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.cetys.jorgepayan.whatsonsale.R;

public class CustomerSettingsFragment extends Fragment {

    public static CustomerSettingsFragment newInstance() {
        CustomerSettingsFragment fragment = new CustomerSettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate( savedInstanceState ); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_customer_settings, container, false );
    }
}
