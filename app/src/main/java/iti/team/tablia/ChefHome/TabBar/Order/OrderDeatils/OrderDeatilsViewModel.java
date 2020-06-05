package iti.team.tablia.ChefHome.TabBar.Order.OrderDeatils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.others.Repository;

public class OrderDeatilsViewModel extends ViewModel {
    private Repository repository;
    public OrderDeatilsViewModel() {
        repository = new Repository();
    }

    public MutableLiveData<OrderPojo> getOrder(String orderID, String chefID, String custID) {
        return repository.getOrder(orderID,chefID,custID);
    }

    public MutableLiveData<CustomerAccountSettings> getCustInfo(String custID) {
        return repository.getCustInfo(custID);
    }

    public MutableLiveData<ChefAccountSettings> getChefInfo(String chefID) {
        return repository.getChefInfo(chefID);
    }

    public void updateOrder(String orderID, String chefID, String custID,double newShippingFee) {
        repository.updateOrder(orderID,chefID,custID,newShippingFee);
    }

    public void updateOrderDelTime(String orderID, String chefID, String custID, String deliveryTime) {
        repository.updateOrderDelTime(orderID,chefID,custID,deliveryTime);
    }

    public void updateOrderChefConfirm(String orderID, String chefID, String custID) {
        repository.updateOrderChefConfirm(orderID, chefID, custID);
    }

}
