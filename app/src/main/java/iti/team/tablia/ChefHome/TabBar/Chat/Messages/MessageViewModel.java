package iti.team.tablia.ChefHome.TabBar.Chat.Messages;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.others.Repository;
import iti.team.tablia.Models.Chat;
import iti.team.tablia.Models.ChatUser;


public class MessageViewModel extends ViewModel {

  private Repository repository;
  private MutableLiveData<ChatUser> userMutableLiveData;
  private MutableLiveData<List<Chat>> chatList;

  public MessageViewModel() {
    repository = new Repository();
    userMutableLiveData = new MutableLiveData<>();
    chatList = new MutableLiveData<>();
  }

  public MutableLiveData<ChatUser> getUserMutableLiveData() {
    return userMutableLiveData;
  }

  public MutableLiveData<List<Chat>> getChatList() {
    return chatList;
  }

  public void sendMessage(String sender, String receiver, String message) {
    repository.sendMessage(sender, receiver, message);
  }

  public void notifyUser(String receiver, String message, Context context, boolean notify) {
    repository.notifyUser(receiver, message, context, notify);
  }

  public void openChatMessage(String userId) {
    userMutableLiveData = repository.openChatMessage(userId);

  }

  public void readMessage(String uid, String userId) {
    chatList = repository.readMessage(uid, userId);
  }

  public void seenMessage(String userId, boolean isPaused) {
    repository.seenMessage(userId, isPaused);
  }

    public void setStatus(String status) {
    repository.setStatusFromMsg(status);
    }
}
