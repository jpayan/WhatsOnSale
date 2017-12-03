package mx.cetys.jorgepayan.whatsonsale.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mx.cetys.jorgepayan.whatsonsale.Models.Location;
import mx.cetys.jorgepayan.whatsonsale.R;

/**
 * Created by fidel on 12/2/2017.
 */

public class LocationAdapter extends ArrayAdapter<Location> {
    public LocationAdapter(Context context){
        super(context, R.layout.location_row,
                R.id.text_view_locationName);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View oView = super.getView(position, convertView, parent);

        TextView locationName = (TextView) oView.findViewById(R.id.text_view_locationName);
        TextView locationAddress = (TextView) oView.findViewById(R.id.text_view_locationAddress);

        Location location = this.getItem(position);
        locationName.setText("Location Name: " + location.getName());
        locationAddress.setText("Location Address: " + location.getAddress());

        return oView;
    }

}
