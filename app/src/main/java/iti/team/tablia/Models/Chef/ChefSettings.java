package iti.team.tablia.Models.Chef;

import iti.team.tablia.Models.User;

public class ChefSettings {
  private User user;
  private ChefAccountSettings chefAccountSettings;

  public ChefSettings() {
  }

  public ChefSettings(User user, ChefAccountSettings chefAccountSettings) {
    this.user = user;
    this.chefAccountSettings = chefAccountSettings;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public ChefAccountSettings getChefAccountSettings() {
    return chefAccountSettings;
  }

  public void setChefAccountSettings(ChefAccountSettings chefAccountSettings) {
    this.chefAccountSettings = chefAccountSettings;
  }

  @Override
  public String toString() {
    return "ChefSettings{" +
        "user=" + user +
        ", chefAccountSettings=" + chefAccountSettings +
        '}';
  }
}
