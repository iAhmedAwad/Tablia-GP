package iti.team.tablia.ChefHome.TabBar.Menu.AddMenu;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;

public class AddMenu extends AppCompatActivity {

    Button addItem;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Bitmap bmp;
    Uri selectedImageUri;
    EditText editNameItem, editPriceItem, editDescrption, editIngredients, qty;
    MenuPojo Item;
    String picture;
    ImageView imgAdd;
    Spinner spinner;
    Database db;
    RecyclerView myrecycle;
    AddMenuAdapter myAdapter;
    MenuItemPojo picItem;
    List<MenuItemPojo> list;
    private String category;
    private String cam;
    private String gal;
    private String can;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        editNameItem = findViewById(R.id.editNameItem);
        editPriceItem = findViewById(R.id.editPriceItem);
        editDescrption = findViewById(R.id.editDescrption);
        editIngredients = findViewById(R.id.editIngredients);
        qty = findViewById(R.id.editItemqty);
        addItem = findViewById(R.id.btnAddItem);


        myrecycle = findViewById(R.id.recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myrecycle.setLayoutManager(layoutManager);
        list = new ArrayList<>();

        myAdapter = new AddMenuAdapter(list, this);
        myrecycle.setAdapter(myAdapter);


        imgAdd = findViewById(R.id.imgAdd);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImage();

            }
        });


        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(AddMenu.this, parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                category = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        db = new Database(this);

        if (ContextCompat.checkSelfPermission(AddMenu.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddMenu.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA);
        }


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((bmp != null) && !editNameItem.getText().toString().isEmpty() && !editPriceItem.getText().toString().isEmpty() &&
                        !editDescrption.getText().toString().isEmpty() && !editIngredients.getText().toString().isEmpty() && !qty.getText().toString().isEmpty()) {
                    picture = BitMapToString(bmp);

                    Item = new MenuPojo("itemID", FirebaseAuth.getInstance().getCurrentUser().getUid()
                            , editNameItem.getText().toString(), Double.parseDouble(editPriceItem.getText().toString()), list,
                            category, editDescrption.getText().toString(), editIngredients.getText().toString(), Integer.parseInt(qty.getText().toString()));

                    db.addMenuItemToDatabase(Item);


                    Log.i("MM", picture);

                    finish();
                } else {
                    if (Locale.getDefault().getLanguage().equals("ar")) {
                        Toast.makeText(AddMenu.this, "أكمل البيانات من فضلك", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddMenu.this, "There Data Not Complete", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }


    private void SelectImage() {

        if (Locale.getDefault().getLanguage().equals("ar")) {
            cam = "كاميرا";
            gal = "المعرض";
            can = "إلغاء";
            title ="إضافة صورة";
        } else {
            cam = "Camera";
            gal = "Gallery";
            can = "Cancel";
            title = "Add Image";
        }
        final CharSequence[] items = {cam, gal, can};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddMenu.this);
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (items[which].equals(cam)) {

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

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {

//                Bundle bundle = data.getExtras();
                bmp = (Bitmap) data.getExtras().get("data");
//                imgAdd.setImageBitmap(bmp);
                AddPic(bmp);

            } else if (requestCode == SELECT_FILE) {

//                bmp = (Bitmap) data.getExtras().get("data");
                selectedImageUri = data.getData();
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                imgAdd.setImageBitmap(bmp);
                AddPic(bmp);
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


    private void AddPic(Bitmap bmp) {

        picture = BitMapToString(bmp);
        picItem = new MenuItemPojo(picture);
        list.add(picItem);
        myAdapter.notifyDataSetChanged();


    }
}