package iti.team.tablia.Customer.ChefProfile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.team.tablia.R;

public class ReviewsActivity extends AppCompatActivity {

  ReviewsRecyclerAdapter adapter;
  List<String> list;
  ReviewViewModel rViewModel;

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
