package com.awad.tablia.ChefHome.TabBar.Chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public ChatViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is HHHHHHHHHHH fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }
}