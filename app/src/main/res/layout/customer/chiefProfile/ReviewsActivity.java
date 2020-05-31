package com.awad.tablia.Customer.chiefProfile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.awad.tablia.R;

import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

  ReviewsRecyclerAdapter adapter;
  List<String> list;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reviews);
    list = (List<String>) getIntent().getSerializableExtra("List");
    RecyclerView myrecyler = findViewById(R.id.myRecycle);
    adapter = new ReviewsRecyclerAdapter(ReviewsActivity.this, list);
    myrecyler.setLayoutManager(new LinearLayoutManager(this));
    myrecyler.setAdapter(adapter);
  }
}
