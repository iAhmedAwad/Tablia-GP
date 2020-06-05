package iti.team.tablia.CustomerAccount;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.others.Repository;
import iti.team.tablia.Models.ChatUser;

public class CustomerChefListViewModel extends ViewModel {
  private Repository repository;

  public CustomerChefListViewModel() {
    repository = new Repository();
  }

  public MutableLiveData<List<ChatUser>> getChefList() {
    return repository.getChefList();
  }


  public MutableLiveData<List<ChatUser>> searchChef(String s) {
    return repository.getChefSearchList(s);
  }
}
