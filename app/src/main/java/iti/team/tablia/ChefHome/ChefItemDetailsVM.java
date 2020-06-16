package iti.team.tablia.ChefHome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.others.Repository;


public class ChefItemDetailsVM extends ViewModel {
    private Repository repository;
    public ChefItemDetailsVM() {
        repository = new Repository();
    }

    public MutableLiveData<MenuPojo> getMenuItemDetails(String chefId, String itemId) {
        return repository.getMenuItemDetails(chefId,itemId);
    }
    public MutableLiveData<MenuPojo> getDisItemDetails(String chefId, String itemId) {
        return repository.getDisItemDetails(chefId,itemId);
    }
    public void deleteMenuItem(String chefId, String itemId) {
        repository.deleteMenuItem( chefId,  itemId);
    }


    public MutableLiveData<List<Review>> getItemReviewsCountAndRating(String itemId) {
        return repository.getReviewsCountAndRating(itemId);

    }
}
