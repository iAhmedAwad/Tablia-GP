package iti.team.tablia.Models.Customer;


import iti.team.tablia.Models.User;

public class CustomerSettings {
  private User user;
  private CustomerAccountSettings customerAccountSettings;

  public CustomerSettings() {
  }

  public CustomerSettings(User user, CustomerAccountSettings customerAccountSettings) {
    this.user = user;
    this.customerAccountSettings = customerAccountSettings;
  }


  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public CustomerAccountSettings getCustomerAccountSettings() {
    return customerAccountSettings;
  }

  public void setCustomerAccountSettings(CustomerAccountSettings customerAccountSettings) {
    this.customerAccountSettings = customerAccountSettings;
  }

  @Override
  public String toString() {
    return "CustomerSettings{" +
        "customer=" + user +
        ", customerAccountSettings=" + customerAccountSettings +
        '}';
  }
}
