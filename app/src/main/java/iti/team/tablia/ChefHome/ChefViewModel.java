package iti.team.tablia.ChefHome;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.Models.Chef.ChefAccountSettings;

public class ChefViewModel extends ViewModel {
  private StatusRepository statusRepository;

  public ChefViewModel() {
    statusRepository = new StatusRepository();
  }

  public void status(String status) {
    statusRepository.status(status);
  }

  public MutableLiveData<ChefAccountSettings> getCurrentUser() {
    return statusRepository.getCurrentUser();
  }
}
