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
import mx.cetys.jorgepayan.whatsonsale.Utils.CustomerHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.Utils.UserHelper;

public class RegisterCustomerActivity extends AppCompatActivity {
    public static final String REGISTER_SUCCESS = "SUCCESSFULLY REGISTERED";

    UserHelper userHelper;
    CustomerHelper customerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        final EditText editTextEmail = (EditText) findViewById(R.id.edit_text_cr_email);
        final EditText editTextPassword = (EditText) findViewById(R.id.edit_text_cr_password);
        final EditText editTextCustomerName = (EditText) findViewById(R.id.edit_text_customer_name);
        final EditText editTextCustomerAge = (EditText) findViewById(R.id.edit_text_customer_age);
        final EditText editTextCustomerGender = (EditText) findViewById(R.id.edit_text_customer_gender);
        final Button btnRegister = (Button) findViewById(R.id.btn_cr);

        Intent fromMain = getIntent();
        String email = fromMain.getStringExtra(MainActivity.MAIN_EMAIL);
        String password = fromMain.getStringExtra(MainActivity.MAIN_PASS);

        userHelper = new UserHelper(getApplicationContext());
        customerHelper = new CustomerHelper(getApplicationContext());

        editTextEmail.setText(email);
        editTextPassword.setText(password);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalEmail = editTextEmail.getText().toString();
                String finalPass = editTextPassword.getText().toString();
                String name = editTextCustomerName.getText().toString();
                int age = Integer.parseInt(editTextCustomerAge.getText().toString());
                String gender = editTextCustomerGender.getText().toString();

                long userId = userHelper.addUser(finalEmail, finalPass, "Customer");
                try {
                    long customerId = customerHelper.addCustomer(name, age, gender);
                    SimpleDialog registerDialog =
                            new SimpleDialog("You have successfully registered your" +
                                    R.string.customer + " user.",
                                    "Ok", null, getApplicationContext(), BusinessHomeActivity.class,
                                    new String[]{REGISTER_SUCCESS},
                                    new String[]{String.valueOf(customerId)});
                    FragmentManager fm = getSupportFragmentManager();
                    registerDialog.show(fm, "Alert Dialog Fragment");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
