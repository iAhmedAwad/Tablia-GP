package iti.team.tablia.CustomerAccount.FollowingActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import iti.team.tablia.Models.Following;
import iti.team.tablia.R;


public class FollowingActivity extends AppCompatActivity {

  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;
  private FollowingActivityViewModel mModel;
  private FollowingAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_following);
    mRecyclerView = findViewById(R.id.xRecyclerFollowing);
    mRecyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mModel = new ViewModelProvider(this).get(FollowingActivityViewModel.class);
    mModel.getFollowing().observe(this, new Observer<ArrayList<Following>>() {
      @Override
      public void onChanged(ArrayList<Following> followings) {
        mAdapter = new FollowingAdapter(followings, FollowingActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
      }
    });
  }
}
