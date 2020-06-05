package iti.team.tablia.ChefHome.TabBar.Profile.Reviews;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.Models.Others.ChefReviews;
import iti.team.tablia.others.Database;

public class ChefReviewViewModel extends ViewModel {
    Database database ;
    public ChefReviewViewModel() {
        database = new Database();
    }
    public MutableLiveData<ArrayList<ChefReviews>> retrieveChefReviews(String chefId) {

        return database.retrieveChefReviews(chefId);
    }
}
