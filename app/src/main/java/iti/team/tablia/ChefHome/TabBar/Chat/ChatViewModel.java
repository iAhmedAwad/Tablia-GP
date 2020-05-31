package iti.team.tablia.ChefHome.TabBar.Chat;

import android.app.Activity;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.team.tablia.Models.ChatUser;


public class ChatViewModel extends ViewModel {
  private MutableLiveData<List<ChatUser>> chatList;
  private ChatRepository chatRepository;

  public ChatViewModel() {
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