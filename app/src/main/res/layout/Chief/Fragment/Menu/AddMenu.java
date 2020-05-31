package com.awad.tablia.ChefHome.TabBar.Menu;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.awad.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import com.awad.tablia.R;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;

public class AddMenu extends AppCompatActivity {

  ImageView imgItem;
  Button addPic, addItem;
  Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
  Bitmap bmp;
  Uri selectedImageUri;
  EditText editPriceItem, editNameItem;
  MenuPojo Item;
  //    TestPojo Item ;
  DatabaseReference ref;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_menu);

    imgItem = findViewById(R.id.imgAdd);
    addPic = findViewById(R.id.btnAddPic);
    addItem = findViewById(R.id.btnAddItem);
    editNameItem = findViewById(R.id.editNameItem);
    editPriceItem = findViewById(R.id.editNameItem);

    if (ContextCompat.checkSelfPermission(com.awad.tablia.ChefHome.TabBar.Menu.AddMenu.AddMenu.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(com.awad.tablia.ChefHome.TabBar.Menu.AddMenu.AddMenu.this, new String[]{
          Manifest.permission.CAMERA
      }, REQUEST_CAMERA);
    }

    addPic.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        SelectImage();
      }
    });

    addItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if ((bmp != null) && !editNameItem.getText().toString().isEmpty() && !editPriceItem.getText().toString().isEmpty()) {

          Item = new MenuPojo(editNameItem.getText().toString(), editPriceItem.getText().toString(), bmp);

//                    Item = new TestPojo(editNameItem.getText().toString(),editPriceItem.getText().toString());

          DataOfMenu data = DataOfMenu.getInstance();
          data.setData(Item);


//                    ref = FirebaseDatabase.getInstance().getReference().child("MenuItem").push();
//                    ref.setValue(Item);

          finish();

        } else {
          Toast.makeText(com.awad.tablia.ChefHome.TabBar.Menu.AddMenu.AddMenu.this, "Thire Data Not Complete", Toast.LENGTH_LONG).show();
        }

      }
    });

  }

  private void SelectImage() {

    final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
    AlertDialog.Builder builder = new AlertDialog.Builder(com.awad.tablia.ChefHome.TabBar.Menu.AddMenu.AddMenu.this);
    builder.setTitle("Add Image");
    builder.setItems(items, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

        if (items[which].equals("Camera")) {

          Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          startActivityForResult(intentCamera, REQUEST_CAMERA);

        } else if (items[which].equals("Gallery")) {

          Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
          intentGallery.setType("image/*");
          startActivityForResult(intentGallery.createChooser(intentGallery, "SelectFile"), SELECT_FILE);

        } else {
          dialog.dismiss();
        }
      }
    });
    builder.show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK) {

      if (requestCode == REQUEST_CAMERA) {

//                Bundle bundle = data.getExtras();
        bmp = (Bitmap) data.getExtras().get("data");
        imgItem.setImageBitmap(bmp);

      } else if (requestCode == SELECT_FILE) {

//                bmp = (Bitmap) data.getExtras().get("data");
        selectedImageUri = data.getData();
        try {
          bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
        } catch (IOException e) {
          e.printStackTrace();
        }
        imgItem.setImageBitmap(bmp);
      }

    }

  }
}
