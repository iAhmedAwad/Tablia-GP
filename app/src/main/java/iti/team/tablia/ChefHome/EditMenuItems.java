package iti.team.tablia.ChefHome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.AddMenu;
import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.AddMenuAdapter;
import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.MenuItemPojo;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;
import iti.team.tablia.util.Constants;

public class EditMenuItems extends AppCompatActivity {


    Button addItem;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Bitmap bmp;
    Uri selectedImageUri;
    EditText editNameItem, editPriceItem, editDescrption, editIngredients,qty;
    MenuPojo Item;
    String picture;
    ImageView imgAdd;
    Spinner spinner;
    Database db;
    RecyclerView myrecycle;
    AddMenuAdapter myAdapter;
    MenuItemPojo picItem;
    List<MenuItemPojo> list =  new ArrayList<>();
    String category;
    public static MenuPojo menuPojo ;
    private String cam;
    private String gal;
    private String can;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_items);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
//        Intent intent = getIntent();

//        menuPojo =  intent.getParcelableExtra("itemEdit");

        editNameItem = findViewById(R.id.editNameItem);
        editPriceItem = findViewById(R.id.editPriceItem);
        editDescrption = findViewById(R.id.editDescrption);
        editIngredients = findViewById(R.id.editIngredients);
        qty = findViewById(R.id.editItemqty);


        addItem = findViewById(R.id.btnAddItem);


        editNameItem.setText(menuPojo.getItemName());
        editPriceItem.setText(String.valueOf(menuPojo.getPriceItem()));
        editDescrption.setText(menuPojo.getDescription());
        editIngredients.setText(menuPojo.getIngredients());
        qty.setText(String.valueOf(menuPojo.getItemQuantity()));


        myrecycle = findViewById(R.id.recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myrecycle.setLayoutManager(layoutManager);



        list.addAll(menuPojo.getImgItem());


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
        String cat = getCategory(menuPojo.getCategory());
        String[]catList=getResources().getStringArray(R.array.Spinner_Item);
        List<String> catClone=Arrays.asList(catList);
        int x = catClone.indexOf(cat);
        spinner.setSelection(x);
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

                if ((bmp != null || !list.isEmpty()) && !editNameItem.getText().toString().isEmpty() && !editPriceItem.getText().toString().isEmpty() &&
                        !editDescrption.getText().toString().isEmpty() && !editIngredients.getText().toString().isEmpty()&&!qty.getText().toString().isEmpty()) {
                    if(bmp != null) {
                        picture = BitMapToString(bmp);
                    }
                    Item = new MenuPojo(menuPojo.getItemID(), FirebaseAuth.getInstance().getCurrentUser().getUid()
                            , editNameItem.getText().toString(), Double.parseDouble(editPriceItem.getText().toString()), list,
                            category, editDescrption.getText().toString(), editIngredients.getText().toString(),  Integer.parseInt(qty.getText().toString()));

                    db.updateMenuItemToDatabase(Item);



                    finish();
                } else {
                    if (Locale.getDefault().getLanguage().equals("ar")) {
                        Toast.makeText(EditMenuItems.this, "أكمل البيانات من فضلك", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EditMenuItems.this, "There Data Not Complete", Toast.LENGTH_LONG).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditMenuItems.this);
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

                bmp = (Bitmap) data.getExtras().get("data");
                AddPic(bmp);

            } else if (requestCode == SELECT_FILE) {

                selectedImageUri = data.getData();
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private String getCategory(String category) {
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("ar")) {
            if (category.equals(Constants.BACKING)) {
                category = Constants.BACKING_AR;
            } else if (category.equals(Constants.DESSERT)) {
                category = Constants.DESSERT_AR;
            } else if (category.equals(Constants.GRILLED)) {
                category = Constants.GRILLED_AR;
            } else if (category.equals(Constants.JUICE)) {
                category = Constants.JUICE_AR;
            } else if (category.equals(Constants.MACARONI)) {
                category = Constants.MACARONI_AR;
            } else if (category.equals(Constants.MAHASHY)) {
                category = Constants.MAHASHY_AR;
            } else if (category.equals(Constants.MAIN_DISHES)) {
                category = Constants.MAIN_DISHES_AR;
            } else if (category.equals(Constants.SALAD)) {
                category = Constants.SALAD_AR;
            } else if (category.equals(Constants.SEAFOOD)) {
                category = Constants.SEAFOOD_AR;
            } else if (category.equals(Constants.SIDE_DISHES)) {
                category = Constants.SIDE_DISHES_AR;
            } else if (category.equals(Constants.SOUPS)) {
                category = Constants.SOUPS_AR;
            }
        } else {
            if (category.equals(Constants.BACKING_AR)) {
                category = Constants.BACKING;
            } else if (category.equals(Constants.DESSERT_AR)) {
                category = Constants.DESSERT;
            } else if (category.equals(Constants.GRILLED_AR)) {
                category = Constants.GRILLED;
            } else if (category.equals(Constants.JUICE_AR)) {
                category = Constants.JUICE;
            } else if (category.equals(Constants.MACARONI_AR)) {
                category = Constants.MACARONI;
            } else if (category.equals(Constants.MAHASHY_AR)) {
                category = Constants.MAHASHY;
            } else if (category.equals(Constants.MAIN_DISHES_AR)) {
                category = Constants.MAIN_DISHES;
            } else if (category.equals(Constants.SALAD_AR)) {
                category = Constants.SALAD;
            } else if (category.equals(Constants.SEAFOOD_AR)) {
                category = Constants.SEAFOOD;
            } else if (category.equals(Constants.SIDE_DISHES_AR)) {
                category = Constants.SIDE_DISHES;
            } else if (category.equals(Constants.SOUPS_AR)) {
                category = Constants.SOUPS;
            }
        }
        return category;
    }

}

