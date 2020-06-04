package iti.team.tablia.Customer.ChefProfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
  }

  @Override
  public int getItemCount() {
    return arrName.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView review;

    public ViewHolder(View itemView) {
      super(itemView);
      review = itemView.findViewById(R.id.id_txtReview);
    }
  }
}
