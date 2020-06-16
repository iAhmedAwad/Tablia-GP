package iti.team.tablia.CustomerAccount.ChefMenus;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.others.Database;

public class ViewChefMenuViewModel extends ViewModel {

    Database db = new Database();

    public MutableLiveData<List<MenuPojo>> getList(String chefId) {

        return db.getMenuItems(chefId);

    }

}
