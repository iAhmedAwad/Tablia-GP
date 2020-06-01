package iti.team.tablia.Customer.ChefProfile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.others.Database;

public class ReviewViewModel extends ViewModel {

  Database database = new Database();
  public MutableLiveData<Review> myData = new MutableLiveData<>();

  public void getReviews(String id) {

    myData = database.getReview(id);
  }

}
