package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.BusinessHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.Utils.UserHelper;

public class RegisterBusinessActivity extends AppCompatActivity {
    public static final String REGISTER_SUCCESS = "SUCCESSFULLY REGISTERED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_business);

        final EditText editTextEmail = (EditText) findViewById(R.id.edit_text_br_email);
        final EditText editTextPassword = (EditText) findViewById(R.id.edit_text_br_password);
        final EditText editTextBusinessName = (EditText) findViewById(R.id.edit_text_business_name);
        final EditText editTextHQAddress = (EditText) findViewById(R.id.edit_text_hq_address);
        final Button btnRegister = (Button) findViewById(R.id.btn_br);

        final UserHelper userHelper = new UserHelper(this);
        final BusinessHelper businessHelper = new BusinessHelper(getApplicationContext());

        Intent fromMain = getIntent();
        String email = fromMain.getStringExtra(MainActivity.MAIN_EMAIL);
        String password = fromMain.getStringExtra(MainActivity.MAIN_PASS);

        editTextEmail.setText(email);
        editTextPassword.setText(password);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalEmail = editTextEmail.getText().toString();
                String finalPass = editTextPassword.getText().toString();
                String businessName = editTextBusinessName.getText().toString();
                String hqAddress = editTextHQAddress.getText().toString();

                long userId = userHelper.addUser(finalEmail, finalPass, "Business");
                try {
                    long bussinessId = businessHelper.addBusiness(userId, businessName, hqAddress);
                    SimpleDialog registerDialog =
                            new SimpleDialog("You have successfully registered your "
                                    + R.string.business + " user.",
                                    "Ok", null, getApplicationContext(), BusinessHomeActivity.class,
                                    new String[]{REGISTER_SUCCESS},
                                    new String[]{String.valueOf(bussinessId)});
                    FragmentManager fm = getSupportFragmentManager();
                    registerDialog.show(fm, "Alert Dialog Fragment");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
