package iti.team.tablia.CustomerAccount.CustomerOrder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.others.Repository;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;

public class CompleteOrderViewModel extends ViewModel {
    private Repository repository;

    public CompleteOrderViewModel() {
        repository = new Repository();
    }

    public MutableLiveData<CustomerAccountSettings> getCustInfo() {
        return repository.getCustInfo();
    }

    public void saveOrder(OrderPojo orderPojo, CompleteOrder completeOrder) {
        repository.saveOrder(orderPojo, completeOrder);
    }

    public MutableLiveData<Boolean> checkOrderItemsInMenu(OrderPojo orderPojo, CompleteOrder completeOrder) {
        return repository.checkOrderItemsInMenu(orderPojo, completeOrder);
    }

    public void updateMenuQty(OrderPojo orderPojo, CompleteOrder completeOrder) {
        repository.updateMenuQty(orderPojo, completeOrder);
    }

    public void removeCartOrder(OrderPojo orderPojo, CompleteOrder completeOrder) {
        repository.removeCartOrder(orderPojo, completeOrder);
    }

    public void saveCustOrder(OrderPojo orderPojo, CompleteOrder completeOrder) {
        repository.saveCustOrder(orderPojo, completeOrder);
    }
}
