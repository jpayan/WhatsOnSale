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

import mx.cetys.jorgepayan.whatsonsale.Models.Customer;
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

        final String customerVal =
                getApplicationContext().getResources().getString(R.string.customer);

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

            if (userHelper.userExists(finalEmail, "Customer")) {
                final SimpleDialog existingUserDialog =
                    new SimpleDialog("A user with this email has already been registered." +
                        "\nIf the user registered with that email belongs to you, press the " +
                        "login button.", "Ok");
                FragmentManager fm = getSupportFragmentManager();
                existingUserDialog.show(fm, "Alert Dialog Fragment");
            } else {
                userHelper.addUser(finalEmail, finalPass, "Customer");
                try {
                    customerHelper.addCustomer(finalEmail, name, age, gender);
                    SimpleDialog registerDialog =
                        new SimpleDialog("You have successfully registered your " +
                            customerVal + " user.",
                            "Ok", null, getApplicationContext(), CustomerHomeActivity.class,
                            new String[]{REGISTER_SUCCESS},
                            new String[]{finalEmail});
                    FragmentManager fm = getSupportFragmentManager();
                    registerDialog.show(fm, "Alert Dialog Fragment");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            }
        });
    }
}
