package iti.team.tablia.ChefHome.TabBar.Profile.Reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import iti.team.tablia.Customer.ChefProfile.ReviewsRecyclerAdapter;
import iti.team.tablia.Models.Others.ChefReviews;
import iti.team.tablia.R;

public class ChefReviewChefAcc extends AppCompatActivity {
    RecyclerView myrecyler;
    ChefReviewViewModel chefReviewViewModel;
    ReviewsRecyclerAdapter adaptor;
    String chefId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_reviews);
        Intent intent = getIntent();
        chefId = intent.getStringExtra("chefId");
        myrecyler = findViewById(R.id.myRecycle);
        myrecyler.setLayoutManager(new LinearLayoutManager(ChefReviewChefAcc.this));
        chefReviewViewModel = new ViewModelProvider(this).get(ChefReviewViewModel.class);
        chefReviewViewModel.retrieveChefReviews(chefId).observe(this, new Observer<ArrayList<ChefReviews>>() {
            @Override
            public void onChanged(ArrayList<ChefReviews> chefReviews) {
                adaptor = new ReviewsRecyclerAdapter(ChefReviewChefAcc.this, chefReviews);
                myrecyler.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            }
        });
    }
}
