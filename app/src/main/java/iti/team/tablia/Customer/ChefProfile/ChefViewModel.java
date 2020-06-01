package iti.team.tablia.Customer.ChefProfile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.others.Database;


public class ChefViewModel extends ViewModel {
  private Database database = new Database();
  MutableLiveData<ChefAccountSettings> myData = new MutableLiveData<>();

  public void getDataFromDataBase(ChefAccountSettings objj) {
    myData.setValue(objj);
  }

  public void getChefInfo(String userId) {

    myData = database.getChefInfoById(userId);
  }

  public MutableLiveData<Boolean> isFollowing(String chefId) {

    return database.isFollowing(chefId);
  }
}


