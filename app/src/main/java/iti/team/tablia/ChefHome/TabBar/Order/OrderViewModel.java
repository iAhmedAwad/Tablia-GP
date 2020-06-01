package iti.team.tablia.ChefHome.TabBar.Order;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.others.Database;

public class OrderViewModel extends ViewModel {

  public MutableLiveData<List<OrderPojo>> deatilsMutableLiveData = new MutableLiveData<>();

  Database db = new Database();

  public void getList() {

    deatilsMutableLiveData = db.getChefOrder();

  }


}
