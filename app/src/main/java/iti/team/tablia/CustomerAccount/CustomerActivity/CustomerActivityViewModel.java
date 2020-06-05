package iti.team.tablia.CustomerAccount.CustomerActivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.others.Repository;
import iti.team.tablia.Models.Customer.CustomerSettings;
import iti.team.tablia.others.Database;


public class CustomerActivityViewModel extends ViewModel {
  MutableLiveData<CustomerSettings> settingsMutableLiveData;

  private MutableLiveData<CustomerSettings> customerSettingsMutableLiveData;
  private Database database;
  private Repository repository;

  public CustomerActivityViewModel() {
    database = new Database();
    repository = new Repository();
  }

  public MutableLiveData<CustomerSettings> getCustomerSettings() {
    settingsMutableLiveData = database.getCustomerSettings();
    // Log.d("HelloX", customerSettingsMutableLiveData.getValue().getUser().getFullName());
    return settingsMutableLiveData;
  }

  public void status(String status) {
    repository.custStatus(status);
  }
}
