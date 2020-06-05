package iti.team.tablia.CustomerAccount.ItemReview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.R;

public class ItemReview extends AppCompatActivity {
    RecyclerView myrecyler;
    ItemReviewViewModel reviewViewModel;
    ItemReviewAdaptor adaptor;
    String itemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_review);
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemID");
        reviewViewModel = new ViewModelProvider(this).get(ItemReviewViewModel.class);
        myrecyler = findViewById(R.id.myRecycle);
        myrecyler.setLayoutManager(new LinearLayoutManager(this));

        reviewViewModel.getReviews(itemId).observe(this, new Observer<ArrayList<Review>>() {
            @Override
            public void onChanged(ArrayList<Review> reviews) {
                adaptor = new ItemReviewAdaptor(ItemReview.this,reviews);
                myrecyler.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            }
        });
    }
}
;