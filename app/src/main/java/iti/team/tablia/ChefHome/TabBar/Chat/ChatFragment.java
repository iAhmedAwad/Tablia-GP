package iti.team.tablia.ChefHome.TabBar.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.team.tablia.Models.ChatList;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.R;

public class ChatFragment extends Fragment {

    private ChatViewModel chatViewModel;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        /** Inflate the layout for this fragment
         *
         */
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        /**render recyclerview and set its attribute
         *
         */
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        /**observe viewmodel to get chat list
         *
         */
        chatViewModel.getChefChatList().observe(getViewLifecycleOwner(), new Observer<List<ChatUser>>() {
            @Override
            public void onChanged(List<ChatUser> chatUsers) {
                progressBar.setVisibility(View.GONE);
                userAdapter = new UserAdapter(getContext(), chatUsers, true);
                recyclerView.setAdapter(userAdapter);
            }
        });


        /**
         *update token for push notification
         */
        chatViewModel.updateToken(getActivity());
        return view;
    }
}
