package iti.team.tablia.CustomerAccount.Chat;

import android.app.Activity;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.others.Repository;
import iti.team.tablia.Models.ChatUser;

public class CustomerChatViewModel extends ViewModel {

    private MutableLiveData<List<ChatUser>> chatList;
    private Repository repository;

    public CustomerChatViewModel() {
        chatList = new MutableLiveData<>();
        repository = new Repository();
    }

    public MutableLiveData<List<ChatUser>> getCustChatList() {
        return repository.getCustChatList();
    }

    public void updateToken(Activity activity) {
        repository.updateToken(activity);
    }

    public void lastMsg(String userId, TextView last_msg) {
        repository.LastMsg(userId, last_msg);
    }

    public void status(String status) {
        repository.status(status);
    }
}
