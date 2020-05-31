package iti.team.tablia.CustomerAccount.Profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.Models.Customer.CustomerSettings;
import iti.team.tablia.others.Database;

public class CustomerProfileViewModel extends ViewModel {

  private MutableLiveData<CustomerSettings> customerSettingsMutableLiveData;
  private Database database;

  public CustomerProfileViewModel() {

    database = new Database();
  }

  public MutableLiveData<CustomerSettings> getCustomerSettings() {

    customerSettingsMutableLiveData = database.getCustomerSettings();
    // Log.d("HelloX", customerSettingsMutableLiveData.getValue().getUser().getFullName());
    return customerSettingsMutableLiveData;
  }
}
