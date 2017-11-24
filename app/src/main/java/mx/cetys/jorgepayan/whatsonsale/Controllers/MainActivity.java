package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.Utils.UserHelper;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_EMAIL = "MAIN EMAIL";
    public static final String MAIN_PASS = "MAIN PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnLogIn = (Button) findViewById(R.id.btn_log_in);
        final Button btnRegister = (Button) findViewById(R.id.btn_register);
        final EditText editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        final EditText editTextPassword = (EditText) findViewById(R.id.edit_text_password);
        final RadioButton radioButtonBusiness =
                (RadioButton) findViewById(R.id.radio_button_business);

        final UserHelper userHelper = new UserHelper(this);

        final SimpleDialog emptyFieldsDialog =
            new SimpleDialog("Fill up all the fields before logging in or registering.", "Ok");
        final SimpleDialog incorrectCredentialsDialog =
            new SimpleDialog("Incorrect email or password.", "Ok");
        final SimpleDialog unexistingUserDialog =
            new SimpleDialog("No user with the specified email is registered.\nVerify the type " +
                "of user selected.\nIf this is your first time using the app, press the " +
                "register button.", "Ok");
        final SimpleDialog existingUserDialog =
            new SimpleDialog("A user with this email has already been registered.\nIf the user " +
                "registered with that email belongs to you, press the login button.", "Ok");

        final FragmentManager fm = getSupportFragmentManager();

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.length() == 0 || password.length() == 0) {
                    emptyFieldsDialog.show(fm, "Alert Dialog Fragment");
                } else {
                    if (radioButtonBusiness.isChecked()) {
                        if (userHelper.userExists(email, "Business")) {
                            if (userHelper.validateCredentials(email, password)) {
                                goToIntent(getApplicationContext(), BusinessHomeActivity.class,
                                    new String[]{email, password});
                            } else {
                                incorrectCredentialsDialog.show(fm, "Alert Dialog Fragment");
                            }
                        } else {
                            unexistingUserDialog.show(fm, "Alert Dialog Fragment");
                        }
                    } else {
                        if (userHelper.userExists(email, "Customer")) {
                            if (userHelper.validateCredentials(email, password)) {
                                goToIntent(getApplicationContext(), CustomerHomeActivity.class,
                                        new String[]{email, password});
                            } else {
                                incorrectCredentialsDialog.show(fm, "Alert Dialog Fragment");
                            }
                        } else {
                            unexistingUserDialog.show(fm, "Alert Dialog Fragment");
                        }
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.length() == 0 || password.length() == 0) {
                    emptyFieldsDialog.show(fm, "Alert Dialog Fragment");
                } else {
                    if (radioButtonBusiness.isChecked()) {
                        if (userHelper.userExists(email, "Business")) {
                            existingUserDialog.show(fm, "Alert Dialog Fragment");
                        } else {
                            goToIntent(getApplicationContext(), RegisterBusinessActivity.class,
                                new String[]{editTextEmail.getText().toString(),
                                    editTextPassword.getText().toString()});
                        }
                    } else {
                        if (userHelper.userExists(email, "Customer")) {
                            existingUserDialog.show(fm, "Alert Dialog Fragment");
                        } else {
                            goToIntent(getApplicationContext(), RegisterCustomerActivity.class,
                                new String[]{editTextEmail.getText().toString(),
                                    editTextPassword.getText().toString()});
                        }
                    }
                }
            }
        });

    }

    private void goToIntent(Context context, Class<?> cls, String[] values) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(MAIN_EMAIL, values[0]);
        intent.putExtra(MAIN_PASS, values[1]);
        startActivity(intent);
    }
}
