package mx.cetys.jorgepayan.whatsonsale.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import mx.cetys.jorgepayan.whatsonsale.Controllers.MainActivity;
import mx.cetys.jorgepayan.whatsonsale.R;

/**
 * Created by jorge.payan on 11/20/17.
 */

public class SimpleDialog extends DialogFragment {
    private String dialogText;
    private String positiveButtonText;
    private String negativeButtonText;
    private Context applicationContext;
    private Class<?> positiveClass;
    private String[] extraKeys;
    private String[] extraValues;

    public SimpleDialog() {

    }

    public SimpleDialog(String dialogText, String positiveButtonText, String negativeButtonText,
                             Context applicationContext, Class<?> positiveClass, String[] extraKeys,
                             String[] extraValues) {
        this.dialogText = dialogText;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
        this.applicationContext = applicationContext;
        this.positiveClass = positiveClass;
        this.extraKeys = extraKeys;
        this.extraValues = extraValues;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (negativeButtonText == null || negativeButtonText.length() == 0) {
            builder.setMessage(dialogText)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToIntent(applicationContext, positiveClass, extraKeys, extraValues);
                    }
                });
        } else {
            builder.setMessage(dialogText)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToIntent(applicationContext, positiveClass, extraKeys, extraValues);
                    }
                })
                .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        }
        return builder.create();
    }

    private void goToIntent(Context context, Class<?> cls, String[] keys, String[] values) {
        Intent intent = new Intent(context, cls);
        for (int i = 0; i < keys.length; i++) {
            intent.putExtra(keys[i], values[i]);
        }
        startActivity(intent);
    }
}
