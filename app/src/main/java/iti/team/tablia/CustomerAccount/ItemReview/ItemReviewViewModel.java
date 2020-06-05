package iti.team.tablia.CustomerAccount.ItemReview;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.others.Database;

public class ItemReviewViewModel extends ViewModel {
    private Database database;

    public ItemReviewViewModel() {
        database = new Database();
    }

    public MutableLiveData<ArrayList<Review>> getReviews(String itemId) {

      return  database.retriveReviewByItemId(itemId);
    }

}
