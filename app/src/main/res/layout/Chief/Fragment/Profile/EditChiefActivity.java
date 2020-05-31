package com.awad.tablia.ChefHome.TabBar.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.awad.tablia.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import Models.ChiefPojo;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditChiefActivity extends AppCompatActivity {
  EditText name, address, start, end, orders;
  Button save;
  ImageView cam;
  CircleImageView prof;
  ChiefPojo editedChief;
  int SELECT_FILE = 0;
  Uri img;
  String mypic;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_chief);
    name = findViewById(R.id.id_name);
    address = findViewById(R.id.id_address);
    start = findViewById(R.id.start_time);
    end = findViewById(R.id.end_time);
    orders = findViewById(R.id.num_orders);
    save = findViewById(R.id.id_save_changes);
    cam = findViewById(R.id.id_edit_cam);
    prof = findViewById(R.id.id_profile_pic);
    cam.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentGallery.setType("image/*");
        startActivityForResult(intentGallery.createChooser(intentGallery, "SelectFile"), SELECT_FILE);
      }
    });
    save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        editedChief = new ChiefPojo();
        // editedChief.setProfile_Pic(mypic);
        // Log.i( "mm",mypic);
        editedChief.setChief_name(name.getText().toString());
        editedChief.setChief_address(address.getText().toString());
        editedChief.setStart_order_time(start.getText().toString());
        editedChief.setNumber_orders(Integer.parseInt(orders.getText().toString()));
        editedChief.setEnd_order_time(end.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("keyName", editedChief);

        setResult(RESULT_OK, intent);
        finish();
      }
    });
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null) {
      img = data.getData();
      try {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), img);
        mypic = BitMapToString(bitmap);

      } catch (IOException e) {
        e.printStackTrace();
      }
      prof.setImageURI(img);
    }

  }

  public String BitMapToString(Bitmap bitmap) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    byte[] b = baos.toByteArray();
    String temp = Base64.encodeToString(b, Base64.DEFAULT);
    return temp;
  }


}
