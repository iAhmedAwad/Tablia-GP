package iti.team.tablia.CustomerAccount.CustomerOrder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.Models.CartGroupPojo;
import iti.team.tablia.Models.CartPojo;

public class CartViewModel extends ViewModel {
    private ChatRepository chatRepository;

    public CartViewModel() {
        this.chatRepository = new ChatRepository();
    }

    public MutableLiveData<List<CartGroupPojo>> getCartListGroups() {
        return chatRepository.getCartListGroups();
    }

    public void removeCartItem(CartPojo cartPojo) {
        chatRepository.removeCartItem(cartPojo);
    }

    public MutableLiveData<Boolean> updateCart(CartPojo cartPojo) {
        return chatRepository.updateCartItem(cartPojo);
    }
}
