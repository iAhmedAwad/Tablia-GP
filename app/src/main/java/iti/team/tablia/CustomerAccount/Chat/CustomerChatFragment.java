package iti.team.tablia.CustomerAccount.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Chat.UserAdapter;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerChatFragment extends Fragment {

    private CustomerChatViewModel customerChatViewModel;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        /** Inflate the layout for this fragment
         *
         */
        View view = inflater.inflate(R.layout.fragment_customer_chat, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.chat));
        customerChatViewModel = ViewModelProviders.of(this).get(CustomerChatViewModel.class);

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
        customerChatViewModel.getCustChatList().observe(getViewLifecycleOwner(), new Observer<List<ChatUser>>() {
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
        customerChatViewModel.updateToken(getActivity());
        return view;
    }
}
