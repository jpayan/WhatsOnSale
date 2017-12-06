package mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.ReviewDetailsActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.SaleDetailsActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Adapters.SaleAdapter;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Adapters.SaleReviewAdapter;
import mx.cetys.jorgepayan.whatsonsale.Models.Sale;
import mx.cetys.jorgepayan.whatsonsale.Models.SaleReview;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleReviewHelper;

public class CustomerReviewHomeFragment extends Fragment {
    public static String CALLING_ACTIVITY = "CALLING_ACTIVITY";
    public static String SALE_REVIEW = "SALE_REVIEW";
    public static String SUCCESS = "SUCCESS";

    int SALES_DETAILS_REQUEST = 1;

    SaleReviewHelper saleReviewHelper;
    SaleReviewAdapter saleReviewAdapter;
    ListView listViewReviews;
    public static CustomerReviewHomeFragment newInstance() {
        return new CustomerReviewHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate( savedInstanceState );}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_review_home, container, false);

        saleReviewHelper =
                new SaleReviewHelper(getActivity().getApplicationContext());

        saleReviewAdapter = new SaleReviewAdapter(getContext().getApplicationContext());
        listViewReviews = (ListView) view.findViewById(R.id.list_view_saleReview);
        listViewReviews.setAdapter(saleReviewAdapter);

        final FloatingActionButton btnAddSaleReview = view.findViewById(R.id.btn_addSaleReview);

        btnAddSaleReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(),
                        ReviewDetailsActivity.class);
                startActivityForResult(intent, SALES_DETAILS_REQUEST);
            }
        });

        return view;
    }
    private void fillSaleListView(String saleReviewSaleId, SaleAdapter saleAdapter){
        SaleReview customerSaleReview = saleReviewHelper.getSaleReview(saleReviewSaleId);
        saleReviewAdapter.clear();
        saleReviewAdapter.add(customerSaleReview);

        }
    }

