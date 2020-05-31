package iti.team.tablia.CustomerAccount.Categories.Seafood;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.others.Database;


public class SeafoodViewModel extends ViewModel {
private Database database;
private MutableLiveData<ArrayList<MenuPojo>> mutableLiveData;

    public SeafoodViewModel() {
        this.database = new Database();

    }

    public MutableLiveData<ArrayList<MenuPojo>> getFishItems(String category){

        mutableLiveData = database.getCategoryList(category);

        return mutableLiveData;
    }
}
