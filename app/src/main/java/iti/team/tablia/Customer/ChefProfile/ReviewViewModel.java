package iti.team.tablia.Customer.ChefProfile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.Models.Others.ChefReviews;
import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.others.Database;

public class ReviewViewModel extends ViewModel {
    private Database database;
    private MutableLiveData<Review> myData;

    public ReviewViewModel() {
        database = new Database();
        myData = new MutableLiveData<>();
    }


    public void getReviews(String id) {

        myData = database.getReview(id);
    }


    public MutableLiveData<ArrayList<ChefReviews>> retrieveChefReviews(String chefId) {

        return database.retrieveChefReviews(chefId);
    }


}
