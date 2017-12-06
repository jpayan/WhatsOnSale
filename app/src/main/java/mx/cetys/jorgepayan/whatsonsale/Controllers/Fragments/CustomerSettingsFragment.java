package mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.CustomerHomeActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.MainActivity;
import mx.cetys.jorgepayan.whatsonsale.Models.Business;
import mx.cetys.jorgepayan.whatsonsale.Models.Customer;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.BusinessHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.CustomerHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.UserHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;

public class CustomerSettingsFragment extends Fragment {

    EditText customerEmail;
    EditText customerName;
    EditText customerAge;
    EditText customerGender;
    ImageButton btn_logOut;

    Button btn_changePassword;
    Button btn_updateCustomer;

    CustomerHelper customerHelper;
    UserHelper userHelper;
    Customer customer;

    public static CustomerSettingsFragment newInstance() {
        return new CustomerSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate( savedInstanceState ); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_customer_settings, container, false);
        btn_changePassword = (Button) view.findViewById(R.id.btn_updateCustomerPasword);
        btn_updateCustomer = (Button) view.findViewById(R.id.btn_updateCustomer);
        btn_logOut = (ImageButton) view.findViewById(R.id.btn_customerLogout);

        customerHelper = new CustomerHelper(getContext().getApplicationContext());

        customer = CustomerHomeActivity.currentCustomer;

        customerEmail = (EditText)view.findViewById(R.id.edit_text_updateCustomerEmail);
        customerEmail.setText(customer.getUserEmail());

        customerName = (EditText)view.findViewById(R.id.edit_text_updateCustomerName);
        customerName.setText(customer.getName());

        customerAge = (EditText)view.findViewById(R.id.edit_text_updateCustomerAge);
        customerAge.setText(String.valueOf(customer.getAge()));

        customerGender = (EditText)view.findViewById(R.id.edit_text_updateCustomerGender);
        customerGender.setText(customer.getGender());

        btn_changePassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Update Password");
                View viewInflated = LayoutInflater.from(getContext()).inflate(
                        R.layout.update_business_password, (ViewGroup) getView(), false);
                final EditText editTextOldPassword = (EditText) viewInflated.findViewById(R.id.oldPassword);
                final EditText editTextNewPassword = (EditText) viewInflated.findViewById(R.id.newPassword);
                builder.setView(viewInflated);

                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String oldPassword =  editTextOldPassword.getText().toString();
                                String newPassword =  editTextNewPassword.getText().toString();

                                FragmentManager fm = getFragmentManager();

                                if(!oldPassword.isEmpty() && !newPassword.isEmpty()) {
                                    if(MainActivity.currentUser.getPassword().equals(oldPassword)) {
                                        MainActivity.currentUser.setPassword(newPassword);
                                        userHelper = new UserHelper(getContext().getApplicationContext());

                                        userHelper.updateUser(MainActivity.currentUser);

                                        Utils.post("user", getContext().getApplicationContext(),
                                                new HashMap<String, String>() {{
                                                    put("email", MainActivity.currentUser.getEmail());
                                                    put("password", MainActivity.currentUser.getPassword());
                                                    put("type", MainActivity.currentUser.getType());
                                                }});

                                        new SimpleDialog("Password updated successfully.", "Ok")
                                                .show(fm, "Alert Dialog");

                                    } else {
                                        new SimpleDialog("Old password is incorrect.","Ok")
                                                .show(fm,"Alert Dialog");
                                    }
                                } else {
                                    new SimpleDialog("Fill up all the fields before updating your password.",
                                            "Ok").show(fm, "Alert Dialog");
                                }
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        } );

        btn_updateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer.setName(customerName.getText().toString() );
                customer.setAge(Integer.parseInt(customerAge.getText().toString()));
                customer.setGender(customerGender.getText().toString());

                customerHelper.updateCustomer(customer);

                Utils.post("customer", getContext().getApplicationContext(),
                        new HashMap<String, String>() {{
                            put("customer_id", customer.getCustomerId());
                            put("user_email", customer.getUserEmail());
                            put("name", customer.getName());
                            put("age", String.valueOf(customer.getAge()));
                            put("gender",customer.getGender());
                        }});

                FragmentManager fm = getActivity().getSupportFragmentManager();
                SimpleDialog successDialog =
                        new SimpleDialog("Changes where updates successfully", "Ok" );
                successDialog.show( fm,"Alert Dialog Fragment");
            }
        });

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Log Out");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentManager fm = getFragmentManager();
                                Intent intent = new Intent(getContext().getApplicationContext(),
                                        MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        return view;
    }
}
