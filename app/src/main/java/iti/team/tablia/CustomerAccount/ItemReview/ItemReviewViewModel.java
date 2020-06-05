package iti.team.tablia.CustomerAccount.ItemReview;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.others.Repository;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.others.Database;

public class ItemReviewViewModel extends ViewModel {
    private Database database;
    private Repository repository;

    public ItemReviewViewModel() {
        repository = new Repository();
        database = new Database();
    }

    public MutableLiveData<ArrayList<Review>> getReviews(String itemId) {

      return  database.retriveReviewByItemId(itemId);
    }

    public MutableLiveData<CustomerAccountSettings> getCustInfo(String custId){
        return repository.getCustInfo(custId);
    }
}
