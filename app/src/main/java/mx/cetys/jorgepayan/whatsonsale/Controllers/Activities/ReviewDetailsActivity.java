package mx.cetys.jorgepayan.whatsonsale.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.CustomerReviewHomeFragment;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.SaleHomeFragment;
import mx.cetys.jorgepayan.whatsonsale.Models.Sale;
import mx.cetys.jorgepayan.whatsonsale.Models.SaleLocation;
import mx.cetys.jorgepayan.whatsonsale.Models.SaleReview;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleLocationHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleReviewHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;

public class ReviewDetailsActivity extends AppCompatActivity {

    EditText review_description;
    EditText review_date;
    Spinner spinner_reviewLocation;
    Spinner spinner_reviewSale;

    Button btn_saveReview;
    Button btn_deleteReview;

    SaleHelper saleHelper;
    SaleReviewHelper saleReviewHelper;
    SaleLocationHelper saleLocationHelper;
    SaleReview saleReview = new SaleReview();
    boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_review_details );
//
//        saleHelper = new SaleHelper(getApplicationContext());
//        saleReviewHelper = new SaleReviewHelper(getApplicationContext());
//        saleLocationHelper = new SaleLocationHelper(getApplicationContext());
//
//        review_description = (EditText)findViewById(R.id.edit_text_saleReviewDescription);
//        review_date = (EditText)findViewById(R.id.edit_text_reviewDate);
//        spinner_reviewLocation = (Spinner)findViewById(R.id.spinner_salesLocation);
//        spinner_reviewSale = (Spinner)findViewById(R.id.spinner_reviewSales);
//
//        btn_deleteReview = (Button)findViewById(R.id.btn_deleteSaleReview);
//        btn_saveReview = (Button)findViewById(R.id.btn_saveReview);
//
//        Intent fromActivity = getIntent();
//        int callingActivity =
//                fromActivity.getIntExtra( CustomerReviewHomeFragment.CALLING_ACTIVITY, 0);
//
//        if (callingActivity == 0) {
//            btn_deleteReview.setVisibility( View.INVISIBLE);
//            btn_deleteReview.setClickable(false);
//        } else {
//            edit = true;
//            saleReview = fromActivity.getParcelableExtra(CustomerReviewHomeFragment.SALE_REVIEW);
//            review_description.setText(saleReview.getDescription());
//            review_date.setText(saleReview.getDate());
//        }
//
//        final FragmentManager fm = getSupportFragmentManager();
//
//        review_date.setText(
//                new SimpleDateFormat("MM/dd/yyyy").format( Calendar.getInstance().getTime()));
//
//        ArrayAdapter<Sale> saleArrayAdapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, saleHelper.getAllSales());
//
//        ArrayAdapter<SaleLocation> saleLocationArrayAdapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, saleLocationHelper.getSaleLocation());
//
//        saleArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_reviewSale.setAdapter(saleArrayAdapter);
//
//        saleLocationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_reviewLocation.setAdapter(saleLocationArrayAdapter);
//
//        btn_saveReview.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saleReview.setCustomerId( CustomerHomeActivity.currentCustomer.getCustomerId());
//                saleReview.setSaleId(  );
//                saleReview.setDescription(review_description.getText().toString());
//                saleReview.setLiked( );
//                saleReview.setSaleId(spinner_reviewSale.getSelectedItem().toString());
//
//                if(saleReview.getDescription().length() > 0 && saleReview.getDate().length() > 0){
//                    final String saleId = (edit) ? saleReview.getSaleId():
//                            (String) saleReviewHelper.addSaleReview( saleReview.getSaleId(),
//                                    CustomerHomeActivity.currentCustomer.getCustomerId(),
//                                    saleReview.getDate(), saleReview.getLiked(),
//                                    saleReview.getDescription() );
//
//                    if(edit){
//                        saleReviewHelper.updateSaleReview(saleReview);
//                    }
//
//                    Utils.post("sale_review", getApplicationContext(), new HashMap<String, String>(){{
//                        put("sale_id", saleReview.getSaleId());
//                        put("customer_id", CustomerHomeActivity.currentCustomer.getCustomerId());
//                        put("date", saleReview.getDate());
//                        put("liked", saleReview.getLiked());
//                        put("description", saleReview.getDescription());
//                    }});
//
//                    Intent data = new Intent();
//                    data.putExtra( "SUCCESS","saved." );
//                    setResult(RESULT_OK,data);
//                    finish();
//                } else {
//                    SimpleDialog emptyFieldsDialog =
//                            new SimpleDialog("Fill up all the fields before adding sale.",
//                                    "Ok");
//                    emptyFieldsDialog.show(fm, "Alert Dialog Fragment");
//                }
//            }
//        } );
//
//        review_date.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar mcurrentDate=Calendar.getInstance();
//                int mYear=mcurrentDate.get(Calendar.YEAR);
//                int mMonth=mcurrentDate.get(Calendar.MONTH);
//                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog mDatePicker=new DatePickerDialog(ReviewDetailsActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth,
//                                                  int selectedDay) {
//                                review_date.setText(selectedmonth+1 + "/" + selectedDay + "/" +
//                                        selectedyear );
//                            }
//                        },mYear, mMonth, mDay);
//                mDatePicker.setTitle("Select expiration date");
//                mDatePicker.show();  }
//        } );
//
//        btn_deleteReview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                saleReviewHelper.deleteSaleReview(saleReview.getSaleId());
//
//                Utils.delete("sale_review", getApplicationContext(), new ArrayList<String>() {{
//                    add(saleReview.getSaleId());
//                }}, fm);
//
//                Intent data = new Intent();
//                data.putExtra(CustomerReviewHomeFragment.SUCCESS, "deleted.");
//                setResult(RESULT_OK, data);
//                finish();
//            }
//        });

//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            if (resultCode == RESULT_OK) {
//                saleReview.setDescription(review_description.getText().toString());
//                saleReview.setDate(review_date.getText().toString());
//            }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void selectedIndices(List<Integer> indices) {
//
//    }
//
//    public void selectedStrings(List<String> strings) {
//        Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//    }
    }
}

