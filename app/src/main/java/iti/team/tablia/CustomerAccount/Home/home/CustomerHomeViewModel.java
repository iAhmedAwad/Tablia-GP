package iti.team.tablia.CustomerAccount.Home.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import iti.team.tablia.others.Repository;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.others.Database;

public class CustomerHomeViewModel extends ViewModel {

  Repository repository = new Repository();
  Database database = new Database();
  public MutableLiveData<ChefAccountSettings> myData = new MutableLiveData<>();
  public MutableLiveData<HashSet<MenuPojo>> myData2 = new MutableLiveData<>();

  public MutableLiveData<List<ChatUser>> getChefList() {
    return repository.getChefList();
  }

  public void getChefInfo(String userId) {

    myData = database.getChefInfoById(userId);
  }

  public MutableLiveData<ArrayList<MenuPojo>> getitemsFollwedByCust() {
    return database.getFollowedChefsMenuItems();
  }
}




