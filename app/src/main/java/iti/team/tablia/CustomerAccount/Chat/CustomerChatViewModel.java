package iti.team.tablia.CustomerAccount.Chat;

import android.app.Activity;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.Models.ChatUser;

public class CustomerChatViewModel extends ViewModel {

  private MutableLiveData<List<ChatUser>> chatList;
  private ChatRepository chatRepository;

  public CustomerChatViewModel() {
    chatList = new MutableLiveData<>();
    chatRepository = new ChatRepository();
  }

  public MutableLiveData<List<ChatUser>> getChatList() {
    chatList = chatRepository.getChatList();
    return chatList;
  }

  public void updateToken(Activity activity) {
    chatRepository.updateToken(activity);
  }

  public void lastMsg(String userId, TextView last_msg) {
    chatRepository.LastMsg(userId, last_msg);
  }

  public void status(String status) {
    chatRepository.status(status);
  }
}
