package iti.team.tablia.ChefHome.TabBar.Order;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.others.Database;

public class OrderViewModel extends ViewModel {

  public MutableLiveData<List<OrderPojo>> deatilsMutableLiveData = new MutableLiveData<>();

  Database db = new Database();
  ChatRepository chatRepository = new ChatRepository();
  public void getList() {

    deatilsMutableLiveData = db.getChefOrder();

  }
  public MutableLiveData<CustomerAccountSettings> getCustInfo(String custId){
    return chatRepository.getCustInfo(custId);
  }


}
