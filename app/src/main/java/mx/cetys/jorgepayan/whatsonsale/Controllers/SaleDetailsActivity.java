package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.SaleHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;

public class SaleDetailsActivity extends AppCompatActivity {

    EditText editTextSaleDescription;
    EditText editTextSaleExpirationDate;
    Spinner spinnerCategory;
    Button btnAddSale;

    String saleDescription;
    String saleExpirationDate;
    String category;
    //TODO category spinner

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_sale_details);
        final FragmentManager fm = getSupportFragmentManager();

        final SaleHelper saleHelper = new SaleHelper(getApplicationContext());
        editTextSaleDescription = (EditText)findViewById(R.id.edit_text_saleDescription);
        editTextSaleExpirationDate = (EditText)findViewById(R.id.edit_text_expirationDate);
        spinnerCategory = (Spinner)findViewById(R.id.spinner_category);
        btnAddSale = (Button)findViewById(R.id.btn_addSale);

        btnAddSale.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saleDescription = editTextSaleDescription.getText().toString();
                saleExpirationDate = editTextSaleExpirationDate.getText().toString();
                category= "Test";
                if(saleDescription.length() > 0 && saleExpirationDate.length() > 0){
                    saleHelper.addSale(
                            BusinessHomeActivity.currentBusiness.getBusinessId(),
                            category, saleDescription,
                            saleExpirationDate);
                    Intent data = new Intent();
                    data.putExtra( "SUCCESS","Success." );
                    setResult(RESULT_OK,data);
                    finish();
                } else {
                    SimpleDialog emptyFieldsDialog =
                            new SimpleDialog("Fill up all the fields before adding sale.", "Ok");
                    emptyFieldsDialog.show(fm, "Alert Dialog Fragment");
                }
            }
        } );
    }
}
