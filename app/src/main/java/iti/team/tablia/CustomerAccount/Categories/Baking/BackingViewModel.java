package iti.team.tablia.CustomerAccount.Categories.Baking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.others.Database;

class BackingViewModel extends ViewModel {
  private MutableLiveData<ArrayList<MenuPojo>> arrayListMutableLiveData;
  private Database database;

  public BackingViewModel() {
    this.database = new Database();
  }
//
//  public MutableLiveData<ArrayList<MenuPojo>> getBackingItems(String category) {
//    arrayListMutableLiveData = database.getCategoryList(category, category_ar);
//    return arrayListMutableLiveData;
//  }
}
