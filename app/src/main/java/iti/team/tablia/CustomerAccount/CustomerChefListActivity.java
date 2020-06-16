package iti.team.tablia.CustomerAccount;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.CustomerAccount.Home.seeAll.TopChefAdapter;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.R;


public class CustomerChefListActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private TopChefAdapter userAdapter;
  private List<ChatUser> users;
  EditText search;
  CustomerChefListViewModel viewModel;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer_chef_list);
    Toolbar toolbar = findViewById(R.id.toolbar);
    progressBar = findViewById(R.id.progressBar);
    progressBar.setVisibility(View.VISIBLE);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        finish();
      }
    });
    recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    users = new ArrayList<>();
    viewModel = ViewModelProviders.of(this).get(CustomerChefListViewModel.class);
    viewModel.getChefList().observe(this, new Observer<List<ChatUser>>() {
      @Override
      public void onChanged(List<ChatUser> chefList) {
        progressBar.setVisibility(View.GONE);
        userAdapter = new TopChefAdapter(CustomerChefListActivity.this, chefList);
        recyclerView.setAdapter(userAdapter);


      }
    });
//        readUsers();
    search = findViewById(R.id.search);

    search.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        viewModel.searchChef(s.toString()).observe(CustomerChefListActivity.this, new Observer<List<ChatUser>>() {
          @Override
          public void onChanged(List<ChatUser> chefList) {
            userAdapter = new TopChefAdapter(CustomerChefListActivity.this, chefList);
            recyclerView.setAdapter(userAdapter);
          }
        });
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  private void searchUsers(String s) {

    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    Query query = FirebaseDatabase.getInstance().getReference("chef_account_settings").orderByChild("userName")
        .startAt(s)
        .endAt(s + "\uf8ff");

    query.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (!search.getText().toString().equals("")) {
          users.clear();
          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            ChefAccountSettings user = snapshot.getValue(ChefAccountSettings.class);
            String chefId = snapshot.getKey();
            ChatUser chatUser = new ChatUser(chefId, user.getUserName(), user.getProfilePhoto(), user.getStatus(),"none","none");
            users.add(chatUser);

          }
          userAdapter = new TopChefAdapter(CustomerChefListActivity.this, users);
          recyclerView.setAdapter(userAdapter);
        } else {
          readUsers();
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  private void readUsers() {
    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        users.clear();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
          ChefAccountSettings user = snapshot.getValue(ChefAccountSettings.class);
          String chefId = snapshot.getKey();
          ChatUser chatUser = new ChatUser(chefId, user.getUserName(), user.getProfilePhoto(), user.getStatus(),"none","none");
          users.add(chatUser);
        }
        userAdapter = new TopChefAdapter(CustomerChefListActivity.this, users);
        recyclerView.setAdapter(userAdapter);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }
}
