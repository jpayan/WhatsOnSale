package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import mx.cetys.jorgepayan.whatsonsale.Models.User;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_EMAIL = "MAIN EMAIL";
    public static final String MAIN_PASS = "MAIN PASSWORD";
    public static User currentUser;

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

        final FragmentManager fm = getSupportFragmentManager();

        Utils.synchronizeDB(this);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.logIn(getApplicationContext(), editTextEmail.getText().toString(),
                    editTextPassword.getText().toString(), fm, radioButtonBusiness.isChecked());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.register(getApplicationContext(), editTextEmail.getText().toString(),
                    editTextPassword.getText().toString(), fm, radioButtonBusiness.isChecked());

            }
        });
    }
}
