package iti.team.tablia.CustomerAccount.Filter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.others.Database;

public class FilteredDataActivityViewModel extends ViewModel {
  private MutableLiveData<ArrayList<MenuPojo>> listMutableLiveData;
  private Database database;

  public FilteredDataActivityViewModel() {
    database = new Database();
  }

  public MutableLiveData<ArrayList<MenuPojo>> getFilteredData(ArrayList<String> categoryList, double min, double max) {

    if (categoryList != null) {
      listMutableLiveData = database.getFilteredItems(categoryList, min, max);
    } else {
      listMutableLiveData = database.getAllMenuItemsInRange(min, max);
    }

    return listMutableLiveData;
  }


}
