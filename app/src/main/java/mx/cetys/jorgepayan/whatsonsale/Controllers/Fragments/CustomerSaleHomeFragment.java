package mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.BusinessHomeActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.CustomerHomeActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.LocationDetailsActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Adapters.LocationAdapter;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Adapters.SaleAdapter;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Adapters.SaleLocationAdapter;
import mx.cetys.jorgepayan.whatsonsale.Models.Location;
import mx.cetys.jorgepayan.whatsonsale.Models.Sale;
import mx.cetys.jorgepayan.whatsonsale.Models.SaleLocation;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleLocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;

import static android.app.Activity.RESULT_OK;

public class CustomerSaleHomeFragment extends Fragment {
    public static String CALLING_ACTIVITY = "CALLING_ACTIVITY";
    public static String SALE = "SALE";
    public static String SUCCESS = "SUCCESS";

    int SALE_DETAILS_REQUEST = 1;

    SaleLocation saleLocation;
    SaleLocationHelper saleLocationHelper;
    SaleLocationAdapter saleLocationAdapter;
    ListView listViewSale;
    public static CustomerSaleHomeFragment newInstance() {
        return new CustomerSaleHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate( savedInstanceState ); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_sale_home, container, false);

        saleLocationHelper = new SaleLocationHelper(getActivity().getApplicationContext());
        saleLocationAdapter = new SaleLocationAdapter(getActivity().getApplicationContext());
        listViewSale = (ListView) view.findViewById(R.id.list_view_customer_sale);
        listViewSale.setAdapter(saleLocationAdapter);

//        fillSaleListView(saleLocationAdapter);

        listViewSale.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sale sale = (Sale) listViewSale.getItemAtPosition(position);

                Intent intent = new Intent(getContext().getApplicationContext(),
                        LocationDetailsActivity.class);
                intent.putExtra(CALLING_ACTIVITY, 1);
                intent.putExtra(SALE, (Parcelable) sale );
                startActivityForResult(intent, SALE_DETAILS_REQUEST);
            }
        });

        return view;

    }

//    private void fillSaleListView(SaleLocationAdapter saleLocationAdapter){
//        ArrayList<SaleLocation> customerSalesLocations = saleLocationHelper.getSaleLocation(saleLocation.getSaleId(),saleLocation.getLocationId());
//        saleLocationAdapter.clear();
//        if(!customerSalesLocations.isEmpty()){
//            for(SaleLocation saleLocation : customerSalesLocations){
//                saleLocationAdapter.add(saleLocation);
//            }
//        }
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SALE_DETAILS_REQUEST) {
            if (resultCode == RESULT_OK) {
//                fillSaleListView(saleLocationAdapter);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SimpleDialog successDialog = new SimpleDialog("Location successfully "
                        + data.getStringExtra(SUCCESS), "Ok");
                successDialog.show(fm, "Alert Dialog Fragment");
            }
        }
    }

}
