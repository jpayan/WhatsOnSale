package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mx.cetys.jorgepayan.whatsonsale.Models.Location;
import mx.cetys.jorgepayan.whatsonsale.Models.Sale;
import mx.cetys.jorgepayan.whatsonsale.R;

/**
 * Created by fidel on 12/2/2017.
 */

public class SaleAdapter extends ArrayAdapter<Sale> {
    public SaleAdapter(Context context){
        super(context, R.layout.sale_row,
                R.id.text_view_saleDescription);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View oView = super.getView(position, convertView, parent);

        TextView saleDescription = (TextView) oView.findViewById(R.id.text_view_saleDescription);
        TextView saleExporationDate = (TextView) oView.findViewById(R.id.text_view_saleExpirationDate);
        TextView saleCategoryName = (TextView) oView.findViewById(R.id.text_view_saleCategoryName);

        Sale sale = this.getItem(position);
        saleDescription.setText("Sale Description: " + sale.getDescription());
        saleExporationDate.setText("Expiration Date: " + sale.getExpirationDate());
        saleCategoryName.setText("Category Name: " + sale.getcategoryName());

        return oView;
    }
}
