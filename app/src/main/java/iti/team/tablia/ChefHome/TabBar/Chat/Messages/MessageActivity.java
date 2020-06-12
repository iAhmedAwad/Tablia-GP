package iti.team.tablia.ChefHome.TabBar.Chat.Messages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.Authentication.LoginActivity;
import iti.team.tablia.Models.Chat;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.R;

public class MessageActivity extends AppCompatActivity {
  private CircleImageView profile_image;
  private TextView username;
  private FirebaseUser firebaseUser;
  private DatabaseReference reference;
  private Intent intent;
  private ImageButton btn_send;
  private EditText txt_send;
  private MessageAdapter messageAdapter;
  private RecyclerView recyclerView;
  private String userId;
  private String imageURL;
  private boolean notify = false;
  private MessageViewModel messageViewModel;
  private boolean isPaused = false;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_message);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        finish();
      }
    });
    profile_image = findViewById(R.id.profile_image);
    username = findViewById(R.id.username);
    btn_send = findViewById(R.id.btn_send);
    txt_send = findViewById(R.id.txt_send);
    recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
    layoutManager.setStackFromEnd(true);
    recyclerView.setLayoutManager(layoutManager);
    intent = getIntent();
    userId = intent.getStringExtra("userid");
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    //Done
    btn_send.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        notify = true;
        String msg = txt_send.getText().toString();
        if (!msg.equals("")) {
          sendMessage(firebaseUser.getUid(), userId, msg);
        } else {
          if (Locale.getDefault().getLanguage().equals("ar")){
            Toast.makeText(MessageActivity.this, "لا يمكن إرسال رسالة فارغة", Toast.LENGTH_SHORT).show();

          }else {
            Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
          }
        }
        txt_send.setText("");
      }
    });


    messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
    messageViewModel.openChatMessage(userId);
    messageViewModel.getUserMutableLiveData().observe(this, new Observer<ChatUser>() {
      @Override
      public void onChanged(ChatUser user) {
        username.setText(user.getUsername());
        imageURL = user.getImageURL();
        if (user.getImageURL().contains("https")) {
          Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
        } else {
          Bitmap bitmap = StringToBitMap(user.getImageURL());
          profile_image.setImageBitmap(bitmap);
        }
        messageViewModel.readMessage(firebaseUser.getUid(), userId);
        messageViewModel.getChatList().observe(MessageActivity.this, new Observer<List<Chat>>() {
          @Override
          public void onChanged(List<Chat> chats) {
            messageAdapter = new MessageAdapter(MessageActivity.this, chats, imageURL);
            recyclerView.setAdapter(messageAdapter);
          }
        });
      }
    });

//    seenMessage(userId);

  }
//
//  private void seenMessage(final String userId) {
//    messageViewModel.seenMessage(userId, isPaused);
//  }

  private void sendMessage(final String sender, final String receiver, String message) {
    messageViewModel.sendMessage(sender, receiver, message);
    messageViewModel.notifyUser(receiver, message, MessageActivity.this, notify);
  }

  public Bitmap StringToBitMap(String encodedString) {
    try {
      byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
      Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
          encodeByte.length);
      return bitmap;
    } catch (Exception e) {
      e.getMessage();
      return null;
    }
  }

  private void status(String status) {
    messageViewModel.setStatus(status);
  }

  @Override
  protected void onResume() {
    super.onResume();
    messageViewModel.seenMessage(userId, false);
    status("online");
  }

  @Override
  protected void onPause() {
    super.onPause();
    messageViewModel.seenMessage(userId, true);
    status("offline");
  }

}
