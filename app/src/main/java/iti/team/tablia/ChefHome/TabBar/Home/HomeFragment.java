package iti.team.tablia.ChefHome.TabBar.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import iti.team.tablia.R;


public class HomeFragment extends Fragment {
  private RatingBar ratingBar;
  private TextView rating;
  private TextView sales;
  private TextView followers_num;
  private TextView top_item;
  private TextView avg_orders;
  private HomeViewModel homeViewModel;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    ratingBar = view.findViewById(R.id.chef_rate);
    rating = view.findViewById(R.id.rate);
    sales = view.findViewById(R.id.sales_amount);
    followers_num = view.findViewById(R.id.followers_num);
    top_item = view.findViewById(R.id.top_item);
    avg_orders = view.findViewById(R.id.avg_orders);
    homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    homeViewModel.getChefRate().observe(getViewLifecycleOwner(), new Observer<Float>() {
      @Override
      public void onChanged(Float rate) {
        ratingBar.setRating(rate);
        rating.setText(rate + "");

      }
    });
    homeViewModel.getOrdersAmount().observe(getViewLifecycleOwner(), new Observer<Double>() {
      @Override
      public void onChanged(Double amount) {
        sales.setText(amount + " EGP");
      }
    });
    return view;
  }
}
