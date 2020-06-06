package iti.team.tablia.Customer.ChefProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.Models.Others.ChefReviews;
import iti.team.tablia.R;

public class ReviewsActivity extends AppCompatActivity {

    private ReviewsRecyclerAdapter adapter;
    private ReviewViewModel rViewModel;
    public static final String ChefId = "ChefId";
    RecyclerView myrecyler;
    String string;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
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
        rViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        Intent intent = getIntent();
        string = intent.getStringExtra(ChefId);
//        itemID = ReviewsRecyclerAdapter.itemIDD;
        myrecyler = findViewById(R.id.myRecycle);
        myrecyler.setLayoutManager(new LinearLayoutManager(this));

        rViewModel.retrieveChefReviews(string).observe(this, new Observer<ArrayList<ChefReviews>>() {
            @Override
            public void onChanged(ArrayList<ChefReviews> chefReviews) {

//                chefArray = chefReviews;
                progressBar.setVisibility(View.GONE);
                adapter = new ReviewsRecyclerAdapter(ReviewsActivity.this,chefReviews);
                myrecyler.setAdapter(adapter);
                adapter.notifyDataSetChanged();



            }
        });



    }
}