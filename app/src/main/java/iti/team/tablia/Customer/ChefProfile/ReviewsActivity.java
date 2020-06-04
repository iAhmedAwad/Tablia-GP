package iti.team.tablia.Customer.ChefProfile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import iti.team.tablia.Models.Others.ChefReviews;
import iti.team.tablia.R;

public class ReviewsActivity extends AppCompatActivity {

    private ReviewsRecyclerAdapter adapter;
    private List<String> list;
    private ReviewViewModel rViewModel;
    public static final String ChefId = "ChefId";
    RecyclerView myrecyler;
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        rViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        Intent intent = getIntent();
        string = intent.getStringExtra(ChefId);
//        list = (List<String>) getIntent().getSerializableExtra("List");
        myrecyler = findViewById(R.id.myRecycle);
        myrecyler.setLayoutManager(new LinearLayoutManager(this));

        rViewModel.retrieveChefReviews(string).observe(this, new Observer<ArrayList<ChefReviews>>() {
            @Override
            public void onChanged(ArrayList<ChefReviews> chefReviews) {
                adapter = new ReviewsRecyclerAdapter(ReviewsActivity.this, chefReviews);
                myrecyler.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });


    }
}
