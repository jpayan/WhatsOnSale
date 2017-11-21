package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import mx.cetys.jorgepayan.whatsonsale.R;

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

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButtonBusiness.isChecked()) {
                    goToIntent(getApplicationContext(), BusinessHomeActivity.class,
                               new String[]{editTextEmail.getText().toString(),
                                            editTextPassword.getText().toString()});
                } else {
                    goToIntent(getApplicationContext(), CustomerHomeActivity.class,
                            new String[]{editTextEmail.getText().toString(),
                                    editTextPassword.getText().toString()});
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButtonBusiness.isChecked()) {
                    goToIntent(getApplicationContext(), RegisterBusinessActivity.class,
                            new String[]{editTextEmail.getText().toString(),
                                    editTextPassword.getText().toString()});
                } else {
                    goToIntent(getApplicationContext(), RegisterCustomerActivity.class,
                            new String[]{editTextEmail.getText().toString(),
                                    editTextPassword.getText().toString()});
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
