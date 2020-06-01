package iti.team.tablia.CustomerAccount.CustomerOrder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;

public class CompleteOrderViewModel extends ViewModel {
    private ChatRepository chatRepository;

    public CompleteOrderViewModel() {
        chatRepository = new ChatRepository();
    }

    public MutableLiveData<CustomerAccountSettings> getCustInfo() {
        return chatRepository.getCustInfo();
    }

    public void saveOrder(OrderPojo orderPojo, CompleteOrder completeOrder) {
        chatRepository.saveOrder(orderPojo, completeOrder);
    }

    public MutableLiveData<Boolean> checkOrderItemsInMenu(OrderPojo orderPojo, CompleteOrder completeOrder) {
        return chatRepository.checkOrderItemsInMenu(orderPojo, completeOrder);
    }

    public void updateMenuQty(OrderPojo orderPojo, CompleteOrder completeOrder) {
        chatRepository.updateMenuQty(orderPojo, completeOrder);
    }

    public void removeCartOrder(OrderPojo orderPojo, CompleteOrder completeOrder) {
        chatRepository.removeCartOrder(orderPojo, completeOrder);
    }

    public void saveCustOrder(OrderPojo orderPojo, CompleteOrder completeOrder) {
        chatRepository.saveCustOrder(orderPojo, completeOrder);
    }
}
