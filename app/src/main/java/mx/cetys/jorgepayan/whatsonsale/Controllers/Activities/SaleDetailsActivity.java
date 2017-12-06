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

import mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments.SaleHomeFragment;
import mx.cetys.jorgepayan.whatsonsale.Models.Sale;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.CategoryHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.SaleHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.MultiSelectionSpinner;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;

public class SaleDetailsActivity extends AppCompatActivity
        implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    EditText editTextSaleDescription;
    EditText editTextSaleExpirationDate;
    Spinner spinnerCategory;
    Button btnSaveSale;
    Button btnDeleteSale;

    SaleHelper saleHelper;
    Sale sale = new Sale();
    boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_sale_details);

        saleHelper = new SaleHelper(getApplicationContext());
        final CategoryHelper categoryHelper = new CategoryHelper(getApplicationContext());
        editTextSaleDescription = (EditText)findViewById(R.id.edit_text_saleReviewDescription );
        editTextSaleExpirationDate = (EditText)findViewById(R.id.edit_text_expirationDate);
        spinnerCategory = (Spinner) findViewById(R.id.spinner_sales );
        btnSaveSale = (Button)findViewById(R.id.btn_saveSale );
        btnDeleteSale = (Button)findViewById(R.id.btn_deleteSale);

        Intent fromActivity = getIntent();
        int callingActivity =
                fromActivity.getIntExtra( SaleHomeFragment.CALLING_ACTIVITY, 0);

        if (callingActivity == 0) {
            btnDeleteSale.setVisibility(View.INVISIBLE);
            btnDeleteSale.setClickable(false);
        } else {
            edit = true;
            sale = fromActivity.getParcelableExtra(SaleHomeFragment.SALE);
            editTextSaleDescription.setText(sale.getDescription());
            editTextSaleExpirationDate.setText(sale.getExpirationDate());
        }

        final FragmentManager fm = getSupportFragmentManager();

        editTextSaleExpirationDate.setText(
            new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, categoryHelper.getAllCategories());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(adapter);

        btnSaveSale.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sale.setDescription(editTextSaleDescription.getText().toString());
                sale.setExpirationDate(editTextSaleExpirationDate.getText().toString());
                sale.setCategoryName(spinnerCategory.getSelectedItem().toString());

                if(sale.getDescription().length() > 0 && sale.getExpirationDate().length() > 0){
                    final String saleId = (edit) ? sale.getSaleId():
                            saleHelper.addSale("",
                            BusinessHomeActivity.currentBusiness.getBusinessId(),
                            sale.getCategoryName(), sale.getDescription(),
                                    sale.getExpirationDate());

                    if(edit){
                        saleHelper.updateSale(sale);
                    }

                    Utils.post("sale", getApplicationContext(), new HashMap<String, String>(){{
                        put("business_id", BusinessHomeActivity.currentBusiness.getBusinessId());
                        put("description", sale.getDescription());
                        put("category_name", sale.getCategoryName());
                        put("expiration_date", sale.getExpirationDate());
                        put("sale_id", saleId);
                    }});

                    Intent data = new Intent();
                    data.putExtra( "SUCCESS","saved." );
                    setResult(RESULT_OK,data);
                    finish();
                } else {
                    SimpleDialog emptyFieldsDialog =
                            new SimpleDialog("Fill up all the fields before adding sale.",
                                    "Ok");
                    emptyFieldsDialog.show(fm, "Alert Dialog Fragment");
                }
            }
        } );

        editTextSaleExpirationDate.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar mcurrentDate=Calendar.getInstance();
            int mYear=mcurrentDate.get(Calendar.YEAR);
            int mMonth=mcurrentDate.get(Calendar.MONTH);
            int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker=new DatePickerDialog(SaleDetailsActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth,
                                      int selectedDay) {
                    editTextSaleExpirationDate.setText(selectedmonth+1 + "/" + selectedDay + "/" +
                        selectedyear );
                }
            },mYear, mMonth, mDay);
            mDatePicker.setTitle("Select expiration date");
            mDatePicker.show();  }
    } );

        btnDeleteSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saleHelper.deleteSale(sale.getSaleId());

                Utils.delete("sale", getApplicationContext(), new ArrayList<String>() {{
                    add(sale.getSaleId());
                }}, fm);

                Intent data = new Intent();
                data.putExtra(SaleHomeFragment.SUCCESS, "deleted.");
                setResult(RESULT_OK, data);
                finish();
            }
        });

        String[] array = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner);
        multiSelectionSpinner.setItems(array);
        multiSelectionSpinner.setSelection(new int[]{2, 6});
        multiSelectionSpinner.setListener(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK) {
                sale.setDescription(editTextSaleExpirationDate.getText().toString());
                sale.setExpirationDate(editTextSaleExpirationDate.getText().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
