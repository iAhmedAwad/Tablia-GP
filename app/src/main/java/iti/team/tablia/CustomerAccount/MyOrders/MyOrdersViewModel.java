package iti.team.tablia.CustomerAccount.MyOrders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;

public class MyOrdersViewModel extends ViewModel {
    private ChatRepository chatRepository;
    public MyOrdersViewModel() {
        chatRepository = new ChatRepository();
    }
    public MutableLiveData<List<OrderPojo>> getCustOrders(){
        return chatRepository.getCustOrders();
    }

}
