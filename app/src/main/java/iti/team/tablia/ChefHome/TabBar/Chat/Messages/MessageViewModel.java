package iti.team.tablia.ChefHome.TabBar.Chat.Messages;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Chat.ChatRepository;
import iti.team.tablia.Models.Chat;
import iti.team.tablia.Models.ChatUser;


public class MessageViewModel extends ViewModel {

  private ChatRepository chatRepository;
  private MutableLiveData<ChatUser> userMutableLiveData;
  private MutableLiveData<List<Chat>> chatList;

  public MessageViewModel() {
    chatRepository = new ChatRepository();
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
    chatRepository.sendMessage(sender, receiver, message);
  }

  public void notifyUser(String receiver, String message, Context context, boolean notify) {
    chatRepository.notifyUser(receiver, message, context, notify);
  }

  public void openChatMessage(String userId) {
    userMutableLiveData = chatRepository.openChatMessage(userId);

  }

  public void readMessage(String uid, String userId) {
    chatList = chatRepository.readMessage(uid, userId);
  }

  public void seenMessage(String userId, boolean isPaused) {
    chatRepository.seenMessage(userId, isPaused);
  }
}
