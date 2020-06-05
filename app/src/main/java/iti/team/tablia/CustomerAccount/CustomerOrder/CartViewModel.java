package iti.team.tablia.CustomerAccount.CustomerOrder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.others.Repository;
import iti.team.tablia.Models.CartGroupPojo;
import iti.team.tablia.Models.CartPojo;

public class CartViewModel extends ViewModel {
    private Repository repository;

    public CartViewModel() {
        this.repository = new Repository();
    }

    public MutableLiveData<List<CartGroupPojo>> getCartListGroups() {
        return repository.getCartListGroups();
    }

    public void removeCartItem(CartPojo cartPojo) {
        repository.removeCartItem(cartPojo);
    }

    public MutableLiveData<Boolean> updateCart(CartPojo cartPojo) {
        return repository.updateCartItem(cartPojo);
    }
}
