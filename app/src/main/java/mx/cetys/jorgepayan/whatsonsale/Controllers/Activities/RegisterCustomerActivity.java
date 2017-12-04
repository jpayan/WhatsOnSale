package mx.cetys.jorgepayan.whatsonsale.Controllers.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import mx.cetys.jorgepayan.whatsonsale.Models.User;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.CustomerHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.UserHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;

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
            final String finalEmail = editTextEmail.getText().toString();
            final String finalPass = editTextPassword.getText().toString();
            final String name = editTextCustomerName.getText().toString();
            final int age = Integer.parseInt(editTextCustomerAge.getText().toString());
            final String gender = editTextCustomerGender.getText().toString();

            if (userHelper.userExists(finalEmail, "customer")) {
                final SimpleDialog existingUserDialog =
                    new SimpleDialog("A user with this email has already been registered." +
                        "\nIf the user registered with that email belongs to you, press the " +
                        "login button.", "Ok");

                FragmentManager fm = getSupportFragmentManager();

                existingUserDialog.show(fm, "Alert Dialog Fragment");
            } else {
                userHelper.addUser(finalEmail, finalPass, "customer");

                MainActivity.currentUser = new User(finalEmail, finalPass, "customer");

                Utils.post("user", getApplicationContext(), new HashMap<String, String>() {{
                    put("email", finalEmail); put("password", finalPass);
                    put("type", "customer");
                }});

                try {
                    final String customerId = customerHelper.addCustomer("", finalEmail, name, age,
                        gender);

                    Utils.post("business", getApplicationContext(),
                            new HashMap<String, String>() {{ put("customer_id", customerId);
                                put("name", name); put("age", String.valueOf(age));
                                put("gender", gender); put("user_email", finalEmail);
                            }});

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

    @Override
    public void onBackPressed() {
        finish();
    }
}
