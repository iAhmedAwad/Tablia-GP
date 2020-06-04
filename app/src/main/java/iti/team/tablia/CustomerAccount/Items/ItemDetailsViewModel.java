package iti.team.tablia.CustomerAccount.Items;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.ChefHome.TabBar.Chat.Repository;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.Models.Chef.ChefAccountSettings;

public class ItemDetailsViewModel extends ViewModel {
  private Repository repository;

  public ItemDetailsViewModel() {
    this.repository = new Repository();
  }

  public MutableLiveData<MenuPojo> getMenuItemDetails(String chefId, String itemId) {

    return repository.getMenuItemDetails(chefId, itemId);
  }

  public MutableLiveData<ChefAccountSettings> getChefInfo(String chefId) {

    return repository.getChefInfo(chefId);
  }

  public MutableLiveData<Boolean> checkItemExistInCart(String chefId, String itemId) {
    return repository.checkItemExistInCart(chefId, itemId);
  }

  public MutableLiveData<Boolean> saveCartItem(CartPojo cartPojo) {
    return repository.saveCartItem(cartPojo);
  }

  public void removeCartItem(CartPojo cartPojo) {
    repository.removeCartItem(cartPojo);
  }
}
