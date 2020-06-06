package iti.team.tablia.ChefHome.TabBar.Profile.Reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import iti.team.tablia.Customer.ChefProfile.ReviewsRecyclerAdapter;
import iti.team.tablia.Models.Others.ChefReviews;
import iti.team.tablia.R;

public class ChefReviewChefAcc extends AppCompatActivity {
    RecyclerView myrecyler;
    ChefReviewViewModel chefReviewViewModel;
    ReviewsRecyclerAdapter adaptor;
    String chefId;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_reviews);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        chefId = intent.getStringExtra("chefId");
        myrecyler = findViewById(R.id.myRecycle);
        myrecyler.setLayoutManager(new LinearLayoutManager(ChefReviewChefAcc.this));
        chefReviewViewModel = new ViewModelProvider(this).get(ChefReviewViewModel.class);
        chefReviewViewModel.retrieveChefReviews(chefId).observe(this, new Observer<ArrayList<ChefReviews>>() {
            @Override
            public void onChanged(ArrayList<ChefReviews> chefReviews) {
                progressBar.setVisibility(View.GONE);
                adaptor = new ReviewsRecyclerAdapter(ChefReviewChefAcc.this, chefReviews);
                myrecyler.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            }
        });
    }
}
