package iti.team.tablia.ChefHome.TabBar.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.Models.Chef.ChefAccountSettings;


public class HomeViewModel extends ViewModel {
  private ChatRepository chatRepository;

  public HomeViewModel() {
    chatRepository = new ChatRepository();
  }

  public MutableLiveData<Double> getOrdersAmount() {
    return chatRepository.getOrdersAmount();
  }

    public MutableLiveData<ChefAccountSettings> getChefData() {
    return chatRepository.getChefData();
    }

  public MutableLiveData<Integer> getTodysOrders() {
    return chatRepository.getTodysOrders();
  }

}
