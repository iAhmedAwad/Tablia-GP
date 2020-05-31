package com.awad.tablia.Customer.Customerr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.awad.tablia.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

  static final int GALLERY_INTENT = 1;
  private final String mAPIKey = "AIzaSyDFhO6SEcewKE7jQUjyE-XwqbhlODfObEA";
  private CircleImageView xProfileImage;
  private AutoCompleteTextView xCusomerAddress;
  private StorageReference mStorage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    xProfileImage = findViewById(R.id.xprofile_image);
    xCusomerAddress = findViewById(R.id.xCustomerAddress);
    xProfileImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dispatchTakePictureIntent();
      }
    });
    PlaceApi placeApi = new PlaceApi();

    if (!Places.isInitialized()) {
      // Initialize the SDK
      Places.initialize(getApplicationContext(), mAPIKey);
    }
    // Create a new Places client instance
    PlacesClient mPlacesClient = Places.createClient(this);
    xCusomerAddress.setAdapter(new PlacesAutoSuggestAdapter(this, android.R.layout.simple_list_item_1));
    mStorage = FirebaseStorage.getInstance().getReference();
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
      try {
        final Uri imageUri = data.getData();
        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);


        xProfileImage.setImageBitmap(selectedImage);
        StorageReference filePath = mStorage.child("ProfilePhoto").child(imageUri.getLastPathSegment());
        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Toast.makeText(MainActivity.this, "Upload Done", Toast.LENGTH_LONG).show();
          }
        }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Toast.makeText(MainActivity.this, "Upload failed", Toast.LENGTH_LONG).show();

          }
        });
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
      }

    } else {
      Toast.makeText(MainActivity.this, "You haven't picked an image", Toast.LENGTH_LONG).show();
    }
  }

  private void dispatchTakePictureIntent() {

    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
    photoPickerIntent.setType("image/*");
    startActivityForResult(photoPickerIntent, GALLERY_INTENT);
  }


}
