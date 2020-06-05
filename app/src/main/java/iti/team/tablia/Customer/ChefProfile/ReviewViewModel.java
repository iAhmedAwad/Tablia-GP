package iti.team.tablia.Customer.ChefProfile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.others.Repository;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.Models.Others.ChefReviews;
import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.others.Database;

public class ReviewViewModel extends ViewModel {
    private Database database;
    private Repository Repository;
    private MutableLiveData<Review> myData;

    public ReviewViewModel() {
        database = new Database();
        Repository = new Repository();
        myData = new MutableLiveData<>();
    }


    public void getReviews(String id) {

        myData = database.getReview(id);
    }


    public MutableLiveData<ArrayList<ChefReviews>> retrieveChefReviews(String chefId) {

        return database.retrieveChefReviews(chefId);
    }
    public MutableLiveData<MenuPojo> getMenuItemDetails(String chefId, String itemId) {

        return Repository.getMenuItemDetails(chefId, itemId);
    }


}
