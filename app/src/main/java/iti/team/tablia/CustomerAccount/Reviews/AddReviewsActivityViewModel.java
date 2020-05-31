package iti.team.tablia.CustomerAccount.Reviews;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.others.Database;


public class AddReviewsActivityViewModel extends ViewModel {

  private Database mDatabase;

  public AddReviewsActivityViewModel() {
    mDatabase = new Database();
  }

  public MutableLiveData<Review> getReview(String itemID) {

    return mDatabase.getReview(itemID);
  }

  public void addReview(Review review) {
    mDatabase.addReview(review);
  }
}
