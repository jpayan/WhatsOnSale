package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.cetys.jorgepayan.whatsonsale.R;

public class SaleHomeFragment extends Fragment {
    public static SaleHomeFragment newInstance() {
        SaleHomeFragment fragment = new SaleHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale_home, container, false);
    }
}
