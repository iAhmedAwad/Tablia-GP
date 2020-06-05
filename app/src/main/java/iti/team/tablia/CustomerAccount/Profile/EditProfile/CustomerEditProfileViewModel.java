package iti.team.tablia.CustomerAccount.Profile.EditProfile;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.Models.Customer.CustomerSettings;
import iti.team.tablia.others.Database;

public class CustomerEditProfileViewModel extends ViewModel {
  private MutableLiveData<CustomerSettings> customerSettingsMutableLiveData;
  private Database database;

  public CustomerEditProfileViewModel() {

    database = new Database();
  }

  public MutableLiveData<CustomerSettings> getCustomerSettings() {

    customerSettingsMutableLiveData = database.getCustomerSettings();
    // Log.d("HelloX", customerSettingsMutableLiveData.getValue().getUser().getFullName());
    return customerSettingsMutableLiveData;
  }

  public void editCustomer(CustomerSettings customerSettings) {
    database.addCustomerToDatabase(customerSettings);
  }

  public void uploadProfilePhoto(Bitmap bitmap) {
    database.uploadProfilePhoto(bitmap);
  }


}
