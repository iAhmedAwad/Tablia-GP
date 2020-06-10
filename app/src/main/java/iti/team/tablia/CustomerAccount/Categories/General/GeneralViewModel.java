package iti.team.tablia.CustomerAccount.Categories.General;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.others.Database;

public class GeneralViewModel  extends ViewModel {
  private MutableLiveData<ArrayList<MenuPojo>> arrayListMutableLiveData;
  private Database database;

  public GeneralViewModel() {
    this.database = new Database();
  }

  public MutableLiveData<ArrayList<MenuPojo>> getCategoryItems(String category, String category_ar) {
    arrayListMutableLiveData = database.getCategoryList(category,category_ar);
    return arrayListMutableLiveData;
  }
}
