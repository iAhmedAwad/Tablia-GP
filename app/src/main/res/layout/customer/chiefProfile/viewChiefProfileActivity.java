package com.awad.tablia.Customer.chiefProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.awad.tablia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import Models.ChiefPojo;

public class viewChiefProfileActivity extends AppCompatActivity {

  TextView name, start_time, end_time, nb_followers, nb_orders, address, reviews, menus;
  RatingBar rating;
  ImageView prfo_img, chat;
  Switch isAavaliable;
  ChiefPojo cheif;
  ToggleButton isfollow;
  ArrayList<String> reviewlist = new ArrayList<>();
  ChefViewModel myViewModel;
  DatabaseReference ref;
  FirebaseUser cust;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_chief_profile);
    reviewlist.add("  when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");
    reviewlist.add("Lorem Ipsum is simply dummy text of the prins been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");
    reviewlist.add("Lorem since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");
    reviewlist.add("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");
    reviewlist.add("Lo since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");
    reviewlist.add("Lorem Ipustry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ");

    // Tells the activity which ViewModel to observe
    myViewModel = ViewModelProviders.of(this).get(ChefViewModel.class);
    String img = "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/p960x960/" +
        "93821865_2902076849888497_5406484177707073536_o.jpg?_nc_cat=101" +
        "&_nc_sid=85a577&_nc_oc=AQlj-eVDRBwwy9R29oF6_tAFkeETi5woam0F9CU6biVd9uvc210SgPYDPlgllflNX9E" +
        "&_nc_ht=scontent-hbe1-1.xx&_nc_tp=6&oh=2c60b072ef5edb4fc64b5d0fea2c11e4&oe=5EE1935F";
    cheif = new ChiefPojo(null, "Ahmed Chief", "Louran", "1:00PM", "3:00PM", 15, false, 2.5f, 144);
    myViewModel.setDataFromDataBase(cheif);

    //Activity observe on the  specific --> MutableLiveData of the ViewModelClass
    name = findViewById(R.id.id_name);
    prfo_img = findViewById(R.id.id_profile_pic);
    start_time = findViewById(R.id.id_start_time);
    end_time = findViewById(R.id.id_end_time);
    nb_followers = findViewById(R.id.id_nb_followers);
    nb_orders = findViewById(R.id.id_nb_orders);
    rating = findViewById(R.id.id_rating);
    address = findViewById(R.id.id_address);
    isfollow = findViewById(R.id.xFollow);
    isAavaliable = findViewById(R.id.id_avaliable);
    chat = findViewById(R.id.id_chat);
    menus = findViewById(R.id.id_visit_menu);
    reviews = findViewById(R.id.id_view_reviews);

    myViewModel.myData.observe(this, new Observer<ChiefPojo>() {
      @Override
      public void onChanged(ChiefPojo chiefPojo) {
        name.setText(cheif.getChief_name());
        address.setText(cheif.getChief_address());
        start_time.setText(String.valueOf(cheif.getStart_order_time()));
        end_time.setText(String.valueOf(cheif.getEnd_order_time()));
        nb_followers.setText(String.valueOf(cheif.getFollowers()));
        nb_orders.setText(String.valueOf(cheif.getNumber_orders()));
        rating.setRating(cheif.getRating());
        isAavaliable.setChecked(cheif.isAvaliable());

      }
    });

    chat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ViewChiefProfileActivity.this, "Chat", Toast.LENGTH_LONG).show();
      }
    });
    menus.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ViewChiefProfileActivity.this, "Menus", Toast.LENGTH_LONG).show();
      }
    });

    reviews.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ViewChiefProfileActivity.this, "Go to Reviews", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ViewChiefProfileActivity.this, ReviewsActivity.class);
        intent.putExtra("List", reviewlist);
        startActivity(intent);
      }
    });

    isfollow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Firebase
        cust = FirebaseAuth.getInstance().getCurrentUser();
        if (isfollow.isChecked()) {
          // ref = FirebaseDatabase.getInstance().getReference("following").child(cust.getUid());
          // ref.setValue(cheif.getChief_name());

          Toast.makeText(ViewChiefProfileActivity.this, "Follow", Toast.LENGTH_SHORT).show();
          int fllwr = cheif.getFollowers();
          fllwr++;
          nb_followers.setText(String.valueOf(fllwr));
          cheif.setFollowers(fllwr);

        } else {
          Toast.makeText(ViewChiefProfileActivity.this, "UnFollow", Toast.LENGTH_SHORT).show();
          //Firebase
          //ref = FirebaseDatabase.getInstance().getReference("following").child(cust.getUid()).child(cheif.getChief_name());
          //  ref.removeValue();
          int fllwr = cheif.getFollowers();
          fllwr--;
          nb_followers.setText(String.valueOf(fllwr));
          cheif.setFollowers(fllwr);
        }
      }
    });
    isAavaliable.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isAavaliable.isChecked()) {
          Toast.makeText(ViewChiefProfileActivity.this, "Avaliable", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(ViewChiefProfileActivity.this, "Not Avaliable", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
}
