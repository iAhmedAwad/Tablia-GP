package iti.team.tablia.CustomerAccount.MyOrders.CustOrderDetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.ChefHome.TabBar.Chat.Repository;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;

public class CustOrderDetailsVM extends ViewModel {
    private Repository repository;
    public CustOrderDetailsVM() {
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

    public MutableLiveData<Boolean> checkItemExist(String itemID, String chefID) {
        return repository.checkItemExist(itemID,chefID);
    }

    public void updateOrderCustConfirm(String orderID, String chefID, String custID) {
        repository.updateOrderCustConfirm(orderID, chefID, custID);
    }
}
