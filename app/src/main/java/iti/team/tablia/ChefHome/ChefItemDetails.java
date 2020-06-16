package iti.team.tablia.ChefHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.CustomerAccount.ItemReview.ItemReview;
import iti.team.tablia.CustomerAccount.Items.ImageSliderAdapter;
import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;
import iti.team.tablia.util.Constants;

public class ChefItemDetails extends AppCompatActivity {
    private TextView toolbarTitle;
    private TextView itemName;
    private TextView itemPrice;
    private TextView reviews;
    private RatingBar itemRating;
    private TextView category;
    private TextView ingredients;
    private TextView description;
    private ChefItemDetailsVM detailsViewModel;
    private TextView qty;
    private String chefId;
    private String itemId;
    private ProgressBar progressBar;
    public static MenuPojo menuPojo;
    Database db;
    private String priceUnit;
    private String reviewTxt;
    private String item;
    private String warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_item_details);

        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("ar")) {
            priceUnit = " ج.م";
            reviewTxt=" تعليقات";
            item=" وجبات";
            warning="هذا المنتج قد نفذ";

        } else {
            item=" items";
            priceUnit = " EGP";
            reviewTxt=" reviews";
            warning = "item is no longer exist";
        }
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
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        itemName = findViewById(R.id.item_title);
        itemPrice = findViewById(R.id.price);
        itemRating = findViewById(R.id.item_rating);
        reviews = findViewById(R.id.item_reviews);
        category = findViewById(R.id.cat_name);
        ingredients = findViewById(R.id.ing);
        description = findViewById(R.id.desc);
        qty = findViewById(R.id.item_qty);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        //review node

        //end

        detailsViewModel = ViewModelProviders.of(this).get(ChefItemDetailsVM.class);
        detailsViewModel.getItemReviewsCountAndRating(itemId).observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviewsList) {
                double totalRating = 0;
                for (Review review : reviewsList) {
                    totalRating += review.getRating();
                }
                itemRating.setRating((float) (totalRating / (float) reviewsList.size()));
                reviews.setText(reviewsList.size() + reviewTxt);
            }
        });
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChefItemDetails.this, ItemReview.class);
                i.putExtra("itemID", itemId);
                startActivity(i);
            }
        });
        detailsViewModel.getMenuItemDetails(chefId, itemId).observe(this, new Observer<MenuPojo>() {
            @Override
            public void onChanged(MenuPojo menuPojo) {
                if (menuPojo != null) {
                    progressBar.setVisibility(View.GONE);
                    getSupportActionBar().setTitle(menuPojo.getItemName());
                    itemName.setText(menuPojo.getItemName());
                    itemPrice.setText(menuPojo.getPriceItem() + priceUnit);
                    qty.setText(menuPojo.getItemQuantity() + item);
                    String cat = getCategory(menuPojo.getCategory());
                    category.setText(cat);
                    ingredients.setText(menuPojo.getIngredients());
                    description.setText(menuPojo.getDescription());
                    ImageSliderAdapter adapter = new ImageSliderAdapter(ChefItemDetails.this, menuPojo.getImgItem());
                    viewPager.setAdapter(adapter);
                } else {
                    Toast.makeText(ChefItemDetails.this, warning, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                detailsViewModel.deleteMenuItem(chefId, itemId);
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case R.id.edit:
                Intent intent = new Intent(this, EditMenuItems.class);
                intent.putExtra("chefId", chefId);
                intent.putExtra("itemId", itemId);
//                intent.putExtra("itemEdit",menuPojo);
                startActivity(intent);
                Toast.makeText(this, "EDIT", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.disable:
                Toast.makeText(this, "disable", Toast.LENGTH_SHORT).show();
                db.addMenuDisableItemToDatabase(menuPojo);
                finish();
                return true;


        }
        return false;
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
