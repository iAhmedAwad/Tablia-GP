package iti.team.tablia.CustomerAccount.Items;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.Models.Chef.ChefAccountSettings;

public class ItemDetailsViewModel extends ViewModel {
  private ChatRepository chatRepository;

  public ItemDetailsViewModel() {
    this.chatRepository = new ChatRepository();
  }

  public MutableLiveData<MenuPojo> getMenuItemDetails(String chefId, String itemId) {

    return chatRepository.getMenuItemDetails(chefId, itemId);
  }

  public MutableLiveData<ChefAccountSettings> getChefInfo(String chefId) {

    return chatRepository.getChefInfo(chefId);
  }

  public MutableLiveData<Boolean> checkItemExistInCart(String chefId, String itemId) {
    return chatRepository.checkItemExistInCart(chefId, itemId);
  }

  public MutableLiveData<Boolean> saveCartItem(CartPojo cartPojo) {
    return chatRepository.saveCartItem(cartPojo);
  }

  public void removeCartItem(CartPojo cartPojo) {
    chatRepository.removeCartItem(cartPojo);
  }
}
