package iti.team.tablia.CustomerAccount.Categories.Grilled;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.others.Database;

public class GrilledViewModel extends ViewModel {
  private MutableLiveData<ArrayList<MenuPojo>> arrayListMutableLiveData;
  private Database database;

  public GrilledViewModel() {
    this.database = new Database();
  }

  public MutableLiveData<ArrayList<MenuPojo>> getGrilledItems(String category, String category_ar) {
    arrayListMutableLiveData = database.getCategoryList(category, category_ar);
    return arrayListMutableLiveData;
  }
}
