package mx.cetys.jorgepayan.whatsonsale.Controllers.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import mx.cetys.jorgepayan.whatsonsale.Models.SaleLocation;
import mx.cetys.jorgepayan.whatsonsale.R;

/**
 * Created by fidel on 12/5/2017.
 */

public class SaleLocationAdapter extends ArrayAdapter<SaleLocation> {

    public SaleLocationAdapter(Context context) {
        super( context, R.layout.sale_location_row,
                R.id.text_view_saleDescription);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View oView = super.getView(position, convertView, parent);

        SaleLocation saleLocation = this.getItem(position);

        return oView;

    }
}
