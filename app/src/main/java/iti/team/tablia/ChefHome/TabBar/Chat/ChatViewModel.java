package iti.team.tablia.ChefHome.TabBar.Chat;

import android.app.Activity;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.Models.ChatList;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.others.Repository;


public class ChatViewModel extends ViewModel {
  private MutableLiveData<List<ChatUser>> chatList;
  private Repository repository;

  public ChatViewModel() {
    chatList = new MutableLiveData<>();
    repository = new Repository();
  }
  public MutableLiveData<List<ChatUser>> getChefChatList(){
    return repository.getChefChatList();
  }

//  public MutableLiveData<List<ChatUser>> getChatList() {
//    chatList = repository.getChatList();
//    return chatList;
//  }

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