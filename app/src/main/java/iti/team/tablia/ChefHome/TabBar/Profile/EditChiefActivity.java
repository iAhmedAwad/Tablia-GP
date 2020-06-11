package iti.team.tablia.ChefHome.TabBar.Profile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.libraries.places.api.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.Models.Chef.ChefSettings;
import iti.team.tablia.Models.User;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;
import iti.team.tablia.others.PlacesAutoSuggestAdapter;


public class EditChiefActivity extends AppCompatActivity {
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    EditText name,  phone, desc;
    Button save;
    ImageView cam;
    CircleImageView prof;
    private AutoCompleteTextView address;
    private final String mAPIKey = "AIzaSyDFhO6SEcewKE7jQUjyE-XwqbhlODfObEA";
    ProgressBar progressBar;
    Uri img;
    String mypic;
    Bitmap bitmap;
    Switch swicth;
    Boolean status;
    private static final String TAG = "EditChiefActivity";

    ChefSettings chefSettings;

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Database database;
    private String camera;
    private String gal;
    private String can;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chief);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        name = findViewById(R.id.id_name);
        address = findViewById(R.id.id_address);

        if (!Places.isInitialized()) {
            // Initialize the SDK
            Places.initialize(this, mAPIKey);
        }
        address.setAdapter(new PlacesAutoSuggestAdapter(this, android.R.layout.simple_list_item_1));
        phone = findViewById(R.id.phone);
        desc = findViewById(R.id.desc);
//        orders = findViewById(R.id.num_orders);
        save = findViewById(R.id.id_save_changes);
        cam = findViewById(R.id.id_edit_cam);
        prof = findViewById(R.id.id_profile_pic);
        swicth = findViewById(R.id.id_avaliable);
        status = swicth.isChecked();


        database = new Database(getApplicationContext());
        setupFirebaseAuth();

        if (ContextCompat.checkSelfPermission(EditChiefActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditChiefActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA);
        }

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = chefSettings.getUser();
                ChefAccountSettings settings = chefSettings.getChefAccountSettings();
                user.setFullName(name.getText().toString());
                settings.setAddress(address.getText().toString());
                settings.setDisplayName(name.getText().toString());
                settings.setBio(desc.getText().toString());
                settings.setPhoneNumber(phone.getText().toString());
//                settings.setOrders(Integer.parseInt(orders.getText().toString()));
                settings.setAvailable(swicth.isChecked());
                settings.setProfilePhoto(mypic);

                database.addChefToDatabase(new ChefSettings(user, settings));
                finish();
            }
        });
        setupFirebaseAuth();
    }


    private void SelectImage() {
        if (Locale.getDefault().getLanguage().equals("ar")) {
            camera = "كاميرا";
            gal = "المعرض";
            can = "إلغاء";
            title ="إضافة صورة";
        } else {
            camera = "Camera";
            gal = "Gallery";
            can = "Cancel";
            title = "Add Image";
        }
        final CharSequence[] items = {camera, gal, can};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditChiefActivity.this);
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (items[which].equals(camera)) {

                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentCamera, REQUEST_CAMERA);

                } else if (items[which].equals(gal)) {

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
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CAMERA) {
                bitmap = (Bitmap) data.getExtras().get("data");
                prof.setImageBitmap(bitmap);
                mypic = BitMapToString(bitmap);

            } else if (requestCode == SELECT_FILE) {

                img = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), img);
                    mypic = BitMapToString(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //prof.setImageURI(img);
                prof.setImageBitmap(bitmap);
            }
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    /*
       ----------------------------- Firebase setup ---------------------------------
    */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser muser = firebaseAuth.getCurrentUser();
                if (muser != null) {
                    //user is signed in
                    Log.d(TAG, "onAuthStateChanged: signed_in" + muser.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
                // ...
            }
        };
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //retrieve user information from database
                chefSettings = database.getChefSettings(dataSnapshot);
                setProfileWidgets(chefSettings);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setProfileWidgets(ChefSettings chefSettings) {
        progressBar.setVisibility(View.GONE);
        name.setText(chefSettings.getUser().getFullName());
        address.setText(chefSettings.getChefAccountSettings().getAddress());
        desc.setText(String.valueOf(chefSettings.getChefAccountSettings().getBio()));
        phone.setText(String.valueOf(chefSettings.getChefAccountSettings().getPhoneNumber()));
//        orders.setText(String.valueOf(chefSettings.getChefAccountSettings().getOrders()));
        swicth.setChecked(chefSettings.getChefAccountSettings().isAvailable());
        mypic = chefSettings.getChefAccountSettings().getProfilePhoto();
        Bitmap bitmapload = StringToBitMap(chefSettings.getChefAccountSettings().getProfilePhoto());
        prof.setImageBitmap(bitmapload);
        //   prof.setImageBitmap(bitmap);


    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }


}
