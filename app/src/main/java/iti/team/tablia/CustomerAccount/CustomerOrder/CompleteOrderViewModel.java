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

  public void saveOrder(OrderPojo orderPojo) {
    chatRepository.saveOrder(orderPojo);
  }

  public MutableLiveData<Boolean> checkOrderItemsInMenu(OrderPojo orderPojo) {
    return chatRepository.checkOrderItemsInMenu(orderPojo);
  }

  public void updateMenuQty(OrderPojo orderPojo) {
    chatRepository.updateMenuQty(orderPojo);
  }

  public void removeCartOrder(OrderPojo orderPojo) {
    chatRepository.removeCartOrder(orderPojo);
  }
}
