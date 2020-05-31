package iti.team.tablia.CustomerAccount.CustomerOrder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.ChefHome.TabBar.Oreder.OrderPojo;
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
}
