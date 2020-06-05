package iti.team.tablia.CustomerAccount.ItemReview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import iti.team.tablia.Models.Others.ChefReviews;
import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.R;




public class ItemReviewAdaptor extends RecyclerView.Adapter<ItemReviewAdaptor.ViewHolder> {
    Context context;
    ArrayList<Review> arrName;


    public ItemReviewAdaptor(Context context, ArrayList<Review> arrName) {
        this.context = context;
        this.arrName = arrName;

    }

    @Override
    public ItemReviewAdaptor.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ItemReviewAdaptor.ViewHolder(LayoutInflater.from(context).inflate(R.layout.review_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ItemReviewAdaptor.ViewHolder viewHolder, int i) {
        viewHolder.review.setText(arrName.get(i).getReviewText());
        viewHolder.itemName.setText(arrName.get(i).getItemName());
        viewHolder.rating.setRating(arrName.get(i).getRating());


    }

    @Override
    public int getItemCount() {
        return arrName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView review,custName,itemName;
        RatingBar rating ;

        public ViewHolder(View itemView) {
            super(itemView);
            custName = itemView.findViewById(R.id.id_txtCustomr_name);
            itemName = itemView.findViewById(R.id.id_txtItem_name);
            review = itemView.findViewById(R.id.id_txtReview);
            rating = itemView.findViewById(R.id.id_ratingBar);
        }
    }
}
