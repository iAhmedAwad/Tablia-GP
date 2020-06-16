package iti.team.tablia.ChefHome.TabBar.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Locale;

import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.R;


public class HomeFragment extends Fragment {
  private RatingBar ratingBar;
  private TextView rating;
  private TextView sales;
  private TextView followers_num;
  private TextView top_item;
  private TextView avg_orders;
  private HomeViewModel homeViewModel;
  private ProgressBar progressBar;
  private String priceUnit;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    String lang = Locale.getDefault().getLanguage();
    if (lang.equals("ar")) {
      priceUnit = " ج.م";

    } else {
      priceUnit = " EGP";
    }
    ratingBar = view.findViewById(R.id.chef_rate);
    rating = view.findViewById(R.id.rate);
    sales = view.findViewById(R.id.sales_amount);
    followers_num = view.findViewById(R.id.followers_num);
    top_item = view.findViewById(R.id.top_item);
    avg_orders = view.findViewById(R.id.avg_orders);
    progressBar = view.findViewById(R.id.progressBar);
    progressBar.setVisibility(View.VISIBLE);
    homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    homeViewModel.getChefData().observe(getViewLifecycleOwner(), new Observer<ChefAccountSettings>() {
      @Override
      public void onChanged(ChefAccountSettings settings) {
        followers_num.setText(settings.getFollowers()+"");
        avg_orders.setText(settings.getOrders()+"");
        ratingBar.setRating(settings.getRating());
        rating.setText(settings.getRating() + "");

      }
    });
    homeViewModel.getOrdersAmount().observe(getViewLifecycleOwner(), new Observer<Double>() {
      @Override
      public void onChanged(Double amount) {
        progressBar.setVisibility(View.GONE);
        sales.setText(amount + priceUnit);
      }
    });
    homeViewModel.getTodysOrders().observe(getViewLifecycleOwner(), new Observer<Integer>() {
      @Override
      public void onChanged(Integer num) {
        top_item.setText(num + "");
      }
    });

    return view;
  }
}
