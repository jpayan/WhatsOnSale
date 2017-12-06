package mx.cetys.jorgepayan.whatsonsale.Controllers.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;

import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.BusinessHomeActivity;
import mx.cetys.jorgepayan.whatsonsale.Controllers.Activities.MainActivity;
import mx.cetys.jorgepayan.whatsonsale.Models.Business;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.BusinessHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.DB.Helpers.UserHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;
import mx.cetys.jorgepayan.whatsonsale.Utils.Utils;

public class BusinessSettingsFragment extends Fragment {

    EditText businessEmail;
    EditText businessName;
    EditText businessHqAddress;
    Button btn_changePassword;
    Button btn_updateBusiness;
    ImageButton btn_logOut;

    BusinessHelper businessHelper;
    UserHelper userHelper;
    Business business;

    public static BusinessSettingsFragment newInstance() {
        return new BusinessSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_business_settings, container, false);
        btn_changePassword = (Button) view.findViewById(R.id.btn_updatePasword);
        btn_updateBusiness = (Button) view.findViewById(R.id.btn_updateBusiness);
        btn_logOut = (ImageButton) view.findViewById(R.id.btn_logout);

        businessHelper = new BusinessHelper(getContext().getApplicationContext());

        business = BusinessHomeActivity.currentBusiness;

        businessEmail = (EditText) view.findViewById(R.id.edit_text_updateBusinessSettings);
        businessEmail.setText(business.getUserEmail());

        businessName = (EditText) view.findViewById(R.id.edit_text_updateBusinessName);
        businessName.setText(business.getBusinessName());

        businessHqAddress = (EditText) view.findViewById(R.id.edit_text_updateHqAddress);
        businessHqAddress.setText(business.getHqAddress());

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
        });

        btn_updateBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                business.setBusinessName(businessName.getText().toString() );
                business.setHqAddress(businessHqAddress.getText().toString());

                businessHelper.updateBusiness(business);

                Utils.post("business", getContext().getApplicationContext(),
                    new HashMap<String, String>() {{
                        put("business_id", business.getBusinessId());
                        put("user_email", business.getUserEmail());
                        put("business_name", business.getBusinessName());
                        put("hq_address", business.getHqAddress());
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
