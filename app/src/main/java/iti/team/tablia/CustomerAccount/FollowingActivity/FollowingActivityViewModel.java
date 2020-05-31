package iti.team.tablia.CustomerAccount.FollowingActivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.Models.Following;
import iti.team.tablia.others.Database;

public class FollowingActivityViewModel extends ViewModel {
  private Database mDatabase;

  public FollowingActivityViewModel() {
    mDatabase = new Database();
  }

  public MutableLiveData<ArrayList<Following>> getFollowing() {

    return mDatabase.getFollowing();
  }

  public MutableLiveData<Boolean> isFollowing(String chefId) {

    return mDatabase.isFollowing(chefId);
  }
}
