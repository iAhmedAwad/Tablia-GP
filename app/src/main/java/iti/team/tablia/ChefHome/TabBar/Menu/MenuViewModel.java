package iti.team.tablia.ChefHome.TabBar.Menu;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.others.Database;

public class MenuViewModel extends ViewModel {

  public MutableLiveData<List<MenuPojo>> deatilsMutableLiveData = new MutableLiveData<>();
//    DataOfMenu data = DataOfMenu.getInstance();

  Database db = new Database();


  public void getList() {

    deatilsMutableLiveData = db.getMenuItems();

  }


}
