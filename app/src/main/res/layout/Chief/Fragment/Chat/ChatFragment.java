package com.awad.tablia.ChefHome.TabBar.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.awad.tablia.R;


public class ChatFragment extends Fragment {

  private ChatViewModel chatViewModel;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

    View v = inflater.inflate(R.layout.fragment_chat, container, false);
    final TextView test = v.findViewById(R.id.test);

    chatViewModel.getText().observe(this, new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        test.setText(s);
      }
    });
    return v;
  }
}
