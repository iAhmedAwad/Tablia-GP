package iti.team.tablia.Customer.ChefProfile;

import android.content.Context;
import android.media.Rating;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.Models.Others.ChefReviews;
import iti.team.tablia.R;

public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsRecyclerAdapter.ViewHolder> {
  Context context;
  ArrayList<ChefReviews> arrName;

  public ReviewsRecyclerAdapter(Context context,ArrayList<ChefReviews> arrName) {
    this.context = context;
    this.arrName = arrName;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.review_row, viewGroup, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int i) {
    viewHolder.review.setText(arrName.get(i).getReview().getReviewText());
    viewHolder.itemName.setText(arrName.get(i).getItemName());
    viewHolder.custName.setText(arrName.get(i).getCustomerName());
    viewHolder.rating.setRating(arrName.get(i).getReview().getRating());
  }

  @Override
  public int getItemCount() {
    return arrName.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView review,custName,itemName;
    RatingBar rating;

    public ViewHolder(View itemView) {
      super(itemView);
      custName = itemView.findViewById(R.id.id_txtCustomr_name);
      itemName = itemView.findViewById(R.id.id_txtItem_name);
      review = itemView.findViewById(R.id.id_txtReview);
      rating = itemView.findViewById(R.id.id_ratingBar);
    }
  }
}
