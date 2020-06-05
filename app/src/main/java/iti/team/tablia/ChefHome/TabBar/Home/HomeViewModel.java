package iti.team.tablia.ChefHome.TabBar.Home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.others.Repository;
import iti.team.tablia.Models.Chef.ChefAccountSettings;


public class HomeViewModel extends ViewModel {
  private Repository repository;

  public HomeViewModel() {
    repository = new Repository();
  }

  public MutableLiveData<Double> getOrdersAmount() {
    return repository.getOrdersAmount();
  }

    public MutableLiveData<ChefAccountSettings> getChefData() {
    return repository.getChefData();
    }

  public MutableLiveData<Integer> getTodysOrders() {
    return repository.getTodysOrders();
  }

}
