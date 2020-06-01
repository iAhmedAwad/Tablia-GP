package iti.team.tablia.ChefHome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.AddMenu;
import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.AddMenuAdapter;
import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.MenuItemPojo;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;

public class EditMenuItems extends AppCompatActivity {


    Button addItem;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Bitmap bmp;
    Uri selectedImageUri;
    EditText editNameItem, editPriceItem, editDescrption, editIngredients;
    MenuPojo Item;
    String picture;
    ImageView imgAdd;
    Spinner spinner;
    Database db;
    RecyclerView myrecycle;
    AddMenuAdapter myAdapter;
    MenuItemPojo picItem;
    List<MenuItemPojo> list;
    String category;
    private MenuPojo menuPojo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_items);

        Intent intent = getIntent();

        menuPojo =  intent.getParcelableExtra("itemEdit");

        editNameItem = findViewById(R.id.editNameItem);
        editPriceItem = findViewById(R.id.editPriceItem);
        editDescrption = findViewById(R.id.editDescrption);
        editIngredients = findViewById(R.id.editIngredients);


        addItem = findViewById(R.id.btnAddItem);



        editNameItem.setText(menuPojo.getItemName());
        editPriceItem.setText(String.valueOf(menuPojo.getPriceItem()));
        editDescrption.setText(menuPojo.getDescription());
        editIngredients.setText(menuPojo.getIngredients());


        myrecycle = findViewById(R.id.recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myrecycle.setLayoutManager(layoutManager);

        list = new ArrayList<>();
//        list.addAll(menuPojo.getImgItem());



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

                Toast.makeText(EditMenuItems.this, parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                category = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        db = new Database(this);

        if (ContextCompat.checkSelfPermission(EditMenuItems.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditMenuItems.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA);
        }


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((bmp != null) && !editNameItem.getText().toString().isEmpty() && !editPriceItem.getText().toString().isEmpty() &&
                        !editDescrption.getText().toString().isEmpty() && !editIngredients.getText().toString().isEmpty()) {
                    picture = BitMapToString(bmp);

                    Item = new MenuPojo("itemID", FirebaseAuth.getInstance().getCurrentUser().getUid()
                            , editNameItem.getText().toString(), Double.parseDouble(editPriceItem.getText().toString()), list,
                            category, editDescrption.getText().toString(), editIngredients.getText().toString(), 10);

                    db.addMenuItemToDatabase(Item);


                    Log.i("MM", picture);

                    finish();
                } else {
                    Toast.makeText(EditMenuItems.this, "There Data Not Complete", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    private void SelectImage() {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditMenuItems.this);
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