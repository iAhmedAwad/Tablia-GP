package iti.team.tablia.CustomerAccount.MyOrders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.others.Repository;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;

public class MyOrdersViewModel extends ViewModel {
    private Repository repository;
    public MyOrdersViewModel() {
        repository = new Repository();
    }
    public MutableLiveData<List<OrderPojo>> getCustOrders(){
        return repository.getCustOrders();
    }

    public MutableLiveData<List<OrderPojo>> getCustOrdersHistory() {
        return repository.getCustOrdersHistory();
    }

}
