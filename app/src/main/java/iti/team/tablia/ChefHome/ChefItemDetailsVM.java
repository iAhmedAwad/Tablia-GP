package iti.team.tablia.ChefHome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;


public class ChefItemDetailsVM extends ViewModel {
    private ChatRepository chatRepository;
    public ChefItemDetailsVM() {
        chatRepository = new ChatRepository();
    }

    public MutableLiveData<MenuPojo> getMenuItemDetails(String chefId, String itemId) {
        return chatRepository.getMenuItemDetails(chefId,itemId);
    }

    public void deleteMenuItem(String chefId, String itemId) {
        chatRepository.deleteMenuItem( chefId,  itemId);
    }

    public void disableMenuItem(String chefId, String itemId) {
        chatRepository.disableMenuItem(chefId,itemId);
    }
}
