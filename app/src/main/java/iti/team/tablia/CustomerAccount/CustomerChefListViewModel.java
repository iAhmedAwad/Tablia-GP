package iti.team.tablia.CustomerAccount;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.Models.ChatUser;

public class CustomerChefListViewModel extends ViewModel {
  private ChatRepository chatRepository;

  public CustomerChefListViewModel() {
    chatRepository = new ChatRepository();
  }

  public MutableLiveData<List<ChatUser>> getChefList() {
    return chatRepository.getChefList();
  }


  public MutableLiveData<List<ChatUser>> searchChef(String s) {
    return chatRepository.getChefSearchList(s);
  }
}
