package iti.team.tablia.ChefHome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.others.Database;
import iti.team.tablia.others.Repository;

public class DisableMenuItemVM extends ViewModel {

    public MutableLiveData<List<MenuPojo>> deatilsMutableLiveData = new MutableLiveData<>();
//    DataOfMenu data = DataOfMenu.getInstance();

    private Database db = new Database();
    private Repository repository = new Repository();

    public void getList() {

        deatilsMutableLiveData = db.getMenuItemsDisable();

    }


    public MutableLiveData<MenuPojo> getDisItemDetails(String chefId, String itemId) {
        return repository.getDisItemDetails(chefId,itemId);
    }

    public void deleteDisabledItem(String chefId, String itemId) {
        repository.deleteDisabledItem(chefId,itemId);
    }

    public void addDisabledToMenu(MenuPojo menuPojo) {
        repository.addDisabledToMenu(menuPojo);
    }
}
