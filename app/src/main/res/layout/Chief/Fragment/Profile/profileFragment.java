package com.awad.tablia.ChefHome.TabBar.Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.awad.tablia.R;

import Models.ChiefPojo;

public class profileFragment extends Fragment {

  TextView name, start_time, end_time, nb_followers, nb_orders, address, reviews, menus;
  RatingBar rating;
  ImageView prfo_img, edit;
  Switch isAavaliable;
  ChiefPojo cheif;
  ToggleButton isfollow;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_profile_chief, container, false);
    name = view.findViewById(R.id.id_name);
    prfo_img = view.findViewById(R.id.id_profile_pic);
    start_time = view.findViewById(R.id.id_start_time);
    end_time = view.findViewById(R.id.id_end_time);
    nb_followers = view.findViewById(R.id.id_nb_followers);
    nb_orders = view.findViewById(R.id.id_nb_orders);
    rating = view.findViewById(R.id.id_rating);
    address = view.findViewById(R.id.id_address);
    isfollow = view.findViewById(R.id.xFollow);
    isAavaliable = view.findViewById(R.id.id_avaliable);
    edit = view.findViewById(R.id.id_edit);
    menus = view.findViewById(R.id.id_visit_menu);
    reviews = view.findViewById(R.id.id_view_reviews);
    edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getContext(), "Edit Chief Profile", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), EditChiefActivity.class);
        startActivityForResult(intent, 9);
      }
    });


    return view;

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 9 && data != null && resultCode == Activity.RESULT_OK) {
      ChiefPojo pojo = (ChiefPojo) data.getSerializableExtra("keyName");
      //Bitmap b =  StringToBitMap(pojo.getProfile_Pic());
      //prfo_img.setImageBitmap(b);
      //Log.i("mm",pojo.getProfile_Pic());
      address.setText(pojo.getChief_address());
      name.setText(pojo.getChief_name());
      nb_orders.setText(String.valueOf(pojo.getNumber_orders()));
      start_time.setText(String.valueOf(pojo.getStart_order_time()));
      end_time.setText(pojo.getEnd_order_time());

    }
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
}
