package iti.team.tablia.ChefHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.CustomerAccount.CustomerActivity.CustomerActivity;
import iti.team.tablia.CustomerAccount.CustomerOrder.Cart;
import iti.team.tablia.CustomerAccount.Filter.FilterActivity;
import iti.team.tablia.CustomerAccount.ItemReview.ItemReview;
import iti.team.tablia.CustomerAccount.Items.ImageSliderAdapter;
import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;

public class DisabledItemDetails extends AppCompatActivity {
    private TextView toolbarTitle;
    private TextView itemName;
    private TextView itemPrice;
    private TextView category;
    private TextView ingredients;
    private TextView description;
    private DisableMenuItemVM detailsViewModel;
    private TextView qty;
    private String chefId;
    private String itemId;
    private ProgressBar progressBar;
    public static MenuPojo menuPojo ;
    Database db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disabled_item_details);

        db = new Database(this);

        toolbarTitle = findViewById(R.id.toolbar_title);
        Intent intent = getIntent();
        chefId = intent.getStringExtra("chefId");
        itemId = intent.getStringExtra("itemId");
        String item_name = intent.getStringExtra("itemName");
//        menuPojo =  intent.getParcelableExtra("pojoItem");
        toolbarTitle.setText(item_name);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(item_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        itemName = findViewById(R.id.item_title);
        itemPrice = findViewById(R.id.price);
        category = findViewById(R.id.cat_name);
        ingredients = findViewById(R.id.ing);
        description = findViewById(R.id.desc);
        qty = findViewById(R.id.item_qty);
        final ViewPager viewPager = findViewById(R.id.viewPager);


        detailsViewModel = ViewModelProviders.of(this).get(DisableMenuItemVM.class);

        detailsViewModel.getDisItemDetails(chefId, itemId).observe(this, new Observer<MenuPojo>() {
            @Override
            public void onChanged(MenuPojo menuPojo) {
                if(menuPojo!=null) {
                    DisabledItemDetails.menuPojo = menuPojo;
                    progressBar.setVisibility(View.GONE);
                    getSupportActionBar().setTitle(menuPojo.getItemName());
                    itemName.setText(menuPojo.getItemName());
                    itemPrice.setText(menuPojo.getPriceItem() + " EGP");
                    qty.setText(menuPojo.getItemQuantity() + " Items");
                    category.setText(menuPojo.getCategory());
                    category.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(DisabledItemDetails.this, "nav to category activity", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ingredients.setText(menuPojo.getIngredients());
                    description.setText(menuPojo.getDescription());
                    ImageSliderAdapter adapter = new ImageSliderAdapter(DisabledItemDetails.this, menuPojo.getImgItem());
                    viewPager.setAdapter(adapter);
                }else {
                    Toast.makeText(DisabledItemDetails.this, "item is no longer exist", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.disabled_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.id_delete:
                detailsViewModel.deleteDisabledItem(chefId, itemId);
                Toast.makeText(this, "delete item", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case R.id.id_enable:
                detailsViewModel.addDisabledToMenu(menuPojo);
                Toast.makeText(this, "enable item", Toast.LENGTH_SHORT).show();
                finish();
                return true;
        }
        return false;

    }
}
