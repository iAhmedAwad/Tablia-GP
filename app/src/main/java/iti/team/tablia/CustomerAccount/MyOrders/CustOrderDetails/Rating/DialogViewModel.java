package iti.team.tablia.CustomerAccount.MyOrders.CustOrderDetails.Rating;

import androidx.lifecycle.ViewModel;

import iti.team.tablia.others.Repository;

public class DialogViewModel extends ViewModel {
    private Repository repository;
    public DialogViewModel() {
        repository = new Repository();
    }
    public void saveCustRating(String chefId, String custId, float rating) {
        repository.saveCustRating(chefId,custId,rating);
    }
}
