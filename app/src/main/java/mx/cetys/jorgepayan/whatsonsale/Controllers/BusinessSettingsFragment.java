package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import mx.cetys.jorgepayan.whatsonsale.Models.Business;
import mx.cetys.jorgepayan.whatsonsale.R;
import mx.cetys.jorgepayan.whatsonsale.Utils.BusinessHelper;
import mx.cetys.jorgepayan.whatsonsale.Utils.SimpleDialog;

public class BusinessSettingsFragment extends Fragment {

    EditText businessEmail;
    EditText businessName;
    EditText businessHqAddress;
    Button btn_changePassword;
    Button btn_updateBusiness;
    BusinessHelper businessHelper;
    Business business;

    public static BusinessSettingsFragment newInstance() {
        BusinessSettingsFragment fragment = new BusinessSettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate( savedInstanceState );}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_business_settings, container,
                false );
        btn_changePassword = (Button) view.findViewById(R.id.btn_updatePasword);
        btn_updateBusiness = (Button) view.findViewById(R.id.btn_updateBusiness);

        businessHelper = new BusinessHelper(getContext().getApplicationContext());
        business = businessHelper.getBusiness(BusinessHomeActivity.currentBusiness.getBusinessId());
        businessEmail = (EditText) view.findViewById(R.id.edit_text_updateBusinessemail);
        businessEmail.setText(business.getuserEmail());
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
                final EditText oldPassword = (EditText) viewInflated.findViewById(R.id.oldPassword);
                final EditText newPassword = (EditText) viewInflated.findViewById(R.id.newPassword);
                builder.setView(viewInflated);

                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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
        btn_updateBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                business.setuserEmail(businessEmail.getText().toString());
                business.setBusinessName(businessName.getText().toString() );
                business.setHqAddress(businessHqAddress.getText().toString());

                businessHelper.updateBusiness(business);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SimpleDialog successDialog =
                        new SimpleDialog("Changes where updates successfully", "Ok" );
                successDialog.show( fm,"Alert Dialog Fragment");
            }
        } );
        return view;
    }

}
