package iti.team.tablia.Customer.ChefProfile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.ChefHome.TabBar.Chat.Messages.MessageActivity;
import iti.team.tablia.CustomerAccount.ChefMenus.ViewChefMenus;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;
import iti.team.tablia.util.GlobalImageLoader;

public class ViewChiefProfileActivity extends AppCompatActivity {

  TextView name, bio, phone, nb_followers, nb_orders, address, reviews, menus;
  String userId;
  RatingBar rating;
  LinearLayout chat;
  Database db;
  CircleImageView prfo_img, avaliablity;
  ChefAccountSettings cheif;
  ToggleButton isfollow;
  ArrayList<String> reviewlist = new ArrayList<>();
  ChefViewModel myViewModel;
  FirebaseUser cust;

  protected void onCreate(Bundle savedInstanceState) {
    db = new Database();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_chief_profile);
    reviewlist.add("  when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");
    reviewlist.add("Lorem Ipsum is simply dummy text of the prins been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");
    reviewlist.add("Lorem since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");
    reviewlist.add("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");
    reviewlist.add("Lo since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");
    reviewlist.add("Lorem Ipustry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");

    name = findViewById(R.id.id_name);
    prfo_img = findViewById(R.id.id_profile_pic);
    nb_followers = findViewById(R.id.id_nb_followers);
    phone = findViewById(R.id.id_phone);
    bio = findViewById(R.id.id_descp);
    nb_orders = findViewById(R.id.id_nb_orders);
    rating = findViewById(R.id.id_rating);
    address = findViewById(R.id.id_address);
    isfollow = findViewById(R.id.id_follow);
    chat = findViewById(R.id.id_chat);
    menus = findViewById(R.id.id_visit_menu);
    reviews = findViewById(R.id.id_view_reviews);
    avaliablity = findViewById(R.id.online_status);

    final Intent intent = getIntent();
    userId = intent.getStringExtra("userid");


    // Tells the activity which ViewModel to observe
    myViewModel = new ViewModelProvider(this).get(ChefViewModel.class);
    cheif = new ChefAccountSettings();
    myViewModel.getChefInfo(userId);

    //Activity observe on the  specific --> MutableLiveData of the ViewModelClass


    myViewModel.isFollowing(userId).observe(ViewChiefProfileActivity.this, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean aBoolean) {
        if (aBoolean) {
          Log.d("xfollow", "I am in the isFollowing check - true");
          isfollow.setChecked(true);
        } else {
          Log.d("xfollow", "I am in the isFollowing check - false ");
          isfollow.setChecked(false);
        }
      }
    });


    myViewModel.myData.observe(this, new Observer<ChefAccountSettings>() {
      @Override
      public void onChanged(ChefAccountSettings chiefPojo) {
        name.setText(chiefPojo.getDisplayName());
        address.setText(chiefPojo.getAddress());
        bio.setText(String.valueOf(chiefPojo.getBio()));
        phone.setText(String.valueOf(chiefPojo.getPhoneNumber()));
        nb_followers.setText(String.valueOf(chiefPojo.getFollowers()));
        nb_orders.setText(String.valueOf(chiefPojo.getOrders()));
        rating.setRating(chiefPojo.getRating());
        prfo_img.setImageBitmap(GlobalImageLoader.StringToBitMap(chiefPojo.getProfilePhoto()));
        reviews.setText("View Reviews On " + chiefPojo.getDisplayName());
        menus.setText("Vist " + chiefPojo.getDisplayName() + "'s " + "Menus");
        if (chiefPojo.isAvailable() == true) {
          avaliablity.setImageResource(R.drawable.online);
        } else {
          avaliablity.setImageResource(R.drawable.offlinee);
        }
        db = new Database();
      }
    });


    chat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(ViewChiefProfileActivity.this, MessageActivity.class);
        i.putExtra("userid", userId);
        startActivity(i);
        Toast.makeText(ViewChiefProfileActivity.this, "Chat", Toast.LENGTH_LONG).show();
      }
    });
    menus.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent1 = new Intent(ViewChiefProfileActivity.this, ViewChefMenus.class);
        intent1.putExtra("userid", userId);
        startActivity(intent1);
//        Toast.makeText(ViewChiefProfileActivity.this, "Menus", Toast.LENGTH_LONG).show();
      }
    });

    reviews.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ViewChiefProfileActivity.this, "Go to Reviews", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ViewChiefProfileActivity.this, ReviewsActivity.class);
        intent.putExtra(ReviewsActivity.ChefId,userId);
//        intent.putExtra("List", reviewlist);
//
        startActivity(intent);
      }
    });

    isfollow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Firebase
        cust = FirebaseAuth.getInstance().getCurrentUser();


        if (isfollow.isChecked()) {
          db.followChef(userId);
        } else {
          db.unfollowChef(userId);
        }

      }
    });


  }


}