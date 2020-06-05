package iti.team.tablia.ChefHome.TabBar.Order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.others.Database;
import iti.team.tablia.others.Repository;

public class OrderViewModel extends ViewModel {

  public MutableLiveData<List<OrderPojo>> deatilsMutableLiveData = new MutableLiveData<>();

  Database db = new Database();
  Repository repository = new Repository();
  public void getList() {

    deatilsMutableLiveData = db.getChefOrder();

  }
  public MutableLiveData<CustomerAccountSettings> getCustInfo(String custId){
    return repository.getCustInfo(custId);
  }


  public MutableLiveData<List<OrderPojo>> getChefOrdersHistory() {
    return repository.getChefOrdersHistory();
  }
}
