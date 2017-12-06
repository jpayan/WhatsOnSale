package mx.cetys.jorgepayan.whatsonsale.Controllers.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mx.cetys.jorgepayan.whatsonsale.Models.Sale;
import mx.cetys.jorgepayan.whatsonsale.Models.SaleReview;
import mx.cetys.jorgepayan.whatsonsale.R;

/**
 * Created by fidel on 12/5/2017.
 */

public class SaleReviewAdapter extends ArrayAdapter<SaleReview> {
    public SaleReviewAdapter(Context context){
        super(context, R.layout.sale_review_row,
                R.id.text_view_saleReviewLikes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View oView = super.getView(position, convertView, parent);

        TextView reviewLikes = (TextView) oView.findViewById(R.id.text_view_saleReviewLikes);
        TextView reviewDate = (TextView) oView.findViewById(R.id.text_view_reviewDate);

        SaleReview saleReview = this.getItem(position);
        reviewLikes.setText(saleReview.getLiked());
        reviewDate.setText(saleReview.getDate());

        return oView;
    }
}
