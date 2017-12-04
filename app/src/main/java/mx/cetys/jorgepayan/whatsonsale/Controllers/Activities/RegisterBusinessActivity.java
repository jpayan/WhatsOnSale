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
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.BusinessHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.UserHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;

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

        final String businessVal =
            getApplicationContext().getResources().getString(R.string.business);

        editTextEmail.setText(email);
        editTextPassword.setText(password);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String finalEmail = editTextEmail.getText().toString();
                final String finalPass = editTextPassword.getText().toString();
                final String businessName = editTextBusinessName.getText().toString();
                final String hqAddress = editTextHQAddress.getText().toString();

                if (userHelper.userExists(finalEmail, "business")) {
                    final SimpleDialog existingUserDialog =
                        new SimpleDialog("A user with this email has already been registered." +
                            "\nIf the user registered with that email belongs to you, press the " +
                            "login button.", "Ok");

                    FragmentManager fm = getSupportFragmentManager();

                    existingUserDialog.show(fm, "Alert Dialog Fragment");
                } else {
                    userHelper.addUser(finalEmail, finalPass, "business");

                    MainActivity.currentUser = new User(finalEmail, finalPass, "business");

                    Utils.post("user", getApplicationContext(), new HashMap<String, String>() {{
                        put("email", finalEmail); put("password", finalPass);
                        put("type", "business");
                    }});

                    try {
                        final String businessId = businessHelper.addBusiness("", finalEmail,
                            businessName, hqAddress);

                        Utils.post("business", getApplicationContext(),
                            new HashMap<String, String>() {{ put("business_id", businessId);
                            put("business_name", businessName); put("hq_address", hqAddress);
                            put("user_email", finalEmail);
                        }});

                        SimpleDialog registerDialog =
                            new SimpleDialog("You have successfully registered your "
                                + businessVal + " user.",
                                "Ok", null, getApplicationContext(), BusinessHomeActivity.class,
                                new String[]{MainActivity.MAIN_EMAIL},
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
