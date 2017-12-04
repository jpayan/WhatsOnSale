package mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.cetys.jorgepayan.whatsonsale.R;

public class CustomerSaleHomeFragment extends Fragment {
    int SALE_DETAILS_REQUEST = 1;
    public static CustomerSaleHomeFragment newInstance() {
        return new CustomerSaleHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate( savedInstanceState ); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_customer_sale_home, container, false );
    }

}
