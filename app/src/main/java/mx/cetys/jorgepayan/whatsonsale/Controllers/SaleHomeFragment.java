package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Models.Location;
import mx.cetys.jorgepayan.whatsonsale.Models.Sale;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.LocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SaleHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;

import static android.app.Activity.RESULT_OK;

public class SaleHomeFragment extends Fragment {
    int SALES_DETAILS_REQUEST = 1;
    SaleHelper saleHelper;
    SaleAdapter saleAdapter;
    ListView listViewSale;

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
        View view = inflater.inflate(R.layout.fragment_sale_home, container, false);

        saleHelper =
                new SaleHelper(getActivity().getApplicationContext());

        saleAdapter = new SaleAdapter(getContext().getApplicationContext());
        listViewSale = (ListView) view.findViewById(R.id.list_view_sale);
        listViewSale.setAdapter(saleAdapter);

        fillSaleListView(BusinessHomeActivity.currentBusiness.getBusinessId(), saleAdapter);

        final FloatingActionButton btnAddSale = view.findViewById(R.id.btn_addSale);

        btnAddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(),
                        SaleDetailsActivity.class);
                startActivityForResult(intent, SALES_DETAILS_REQUEST);
            }
        });
        return view;
    }

    private void fillSaleListView(String businessId, SaleAdapter saleAdapter){
        ArrayList<Sale> businessSales = saleHelper.getBusinessSales(businessId);
        saleAdapter.clear();
        if(!businessSales.isEmpty()){
            for(Sale sale : businessSales){
                saleAdapter.add(sale);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == SALES_DETAILS_REQUEST){
            if(resultCode == RESULT_OK){
                fillSaleListView( BusinessHomeActivity.currentBusiness.getBusinessId(),saleAdapter);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SimpleDialog successDialog =
                        new SimpleDialog("Sale successfully saved.", "Ok" );
                successDialog.show( fm,"Alert Dialog Fragment");
            }
        }

    }
}
