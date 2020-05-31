package iti.team.tablia.ChefHome.TabBar.Home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;


public class HomeViewModel extends ViewModel {
  private ChatRepository chatRepository;

  public HomeViewModel() {
    chatRepository = new ChatRepository();
  }

  public MutableLiveData<Float> getChefRate() {
    return chatRepository.getChefRate();
  }

  public MutableLiveData<Double> getOrdersAmount() {
    return chatRepository.getOrdersAmount();
  }
}
