package iti.team.tablia.CustomerAccount.Items;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.MenuItemPojo;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.Customer.ChefProfile.ViewChiefProfileActivity;
import iti.team.tablia.CustomerAccount.CustomerOrder.Cart;
import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.R;

public class ItemDetails extends AppCompatActivity {
    private ImageView cart;
    private TextView itemName;
    private TextView itemPrice;
    private TextView reviews;
    private RatingBar itemRating;
    private TextView category;
    private TextView chefName;
    private TextView ingredients;
    private TextView description;
    private CardView writeReview;
    private FloatingActionButton fab;
    private ItemDetailsViewModel detailsViewModel;
    private List<MenuItemPojo> imgList;
    private double price;
    private MenuPojo menuPojo;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        toolbarTitle = findViewById(R.id.toolbar_title);
        Intent intent = getIntent();
        final String chefId = intent.getStringExtra("chefId");
        final String itemId = intent.getStringExtra("itemId");
        String item_name = intent.getStringExtra("itemName");
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
        cart = findViewById(R.id.shopping_cart);
        fab = findViewById(R.id.add_cart);
        itemName = findViewById(R.id.item_title);
        itemPrice = findViewById(R.id.price);
        itemRating = findViewById(R.id.item_rating);
        reviews = findViewById(R.id.item_reviews);
        category = findViewById(R.id.cat_name);
        chefName = findViewById(R.id.chef_name);
        ingredients = findViewById(R.id.ing);
        description = findViewById(R.id.desc);
        writeReview = findViewById(R.id.cont4);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        //review node
        itemRating.setRating(3.5f);
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemDetails.this, "to reviews activity", Toast.LENGTH_SHORT).show();
            }
        });
        //end

        detailsViewModel = ViewModelProviders.of(this).get(ItemDetailsViewModel.class);
        detailsViewModel.checkItemExistInCart(chefId, itemId).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(final Boolean aBoolean) {
                if (aBoolean) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#71068F")));
                    fab.setImageResource(R.drawable.ic_remove_cart);
                } else {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
                    fab.setImageResource(R.drawable.ic_add_cart);
                }
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imgList != null) {

                            CartPojo cartPojo = new CartPojo(chefId, itemId, chefName.getText().toString()
                                    , itemName.getText().toString(), 1, price
                                    , imgList.get(0).getImgaeItem());

                            if (aBoolean) {
                                detailsViewModel.removeCartItem(cartPojo);
                                Toast.makeText(ItemDetails.this, "remove item from cart", Toast.LENGTH_SHORT).show();
                            } else {

                                detailsViewModel.saveCartItem(cartPojo).observe(ItemDetails.this, new Observer<Boolean>() {
                                    @Override
                                    public void onChanged(Boolean aBoolean) {
                                        if (!aBoolean) {
                                            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
                                            fab.setImageResource(R.drawable.ic_add_cart);
                                            Toast.makeText(ItemDetails.this, "no more items available", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ItemDetails.this, "item added to cart", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }
                    }
                });
            }
        });
        //get chef info
        detailsViewModel.getChefInfo(chefId).observe(this, new Observer<ChefAccountSettings>() {
            @Override
            public void onChanged(ChefAccountSettings chefAccountSettings) {
                chefName.setText(chefAccountSettings.getUserName());
                chefName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ItemDetails.this, ViewChiefProfileActivity.class);
                        intent.putExtra("userid", chefId);
                        startActivity(intent);
                        Toast.makeText(ItemDetails.this, "chef profile", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        //end

        detailsViewModel.getMenuItemDetails(chefId, itemId).observe(this, new Observer<MenuPojo>() {
            @Override
            public void onChanged(MenuPojo menuPojo) {
                ItemDetails.this.menuPojo = menuPojo;
                getSupportActionBar().setTitle(menuPojo.getItemName());
                itemName.setText(menuPojo.getItemName());
                price = menuPojo.getPriceItem();
                itemPrice.setText(menuPojo.getPriceItem() + " EGP");
                category.setText(menuPojo.getCategory());
                category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ItemDetails.this, "nav to category activity", Toast.LENGTH_SHORT).show();
                    }
                });
                ingredients.setText(menuPojo.getIngredients());
                description.setText(menuPojo.getDescription());
                imgList = menuPojo.getImgItem();
                ImageSliderAdapter adapter = new ImageSliderAdapter(ItemDetails.this, menuPojo.getImgItem());
                viewPager.setAdapter(adapter);
            }
        });
        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemDetails.this, "writeReview", Toast.LENGTH_SHORT).show();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetails.this, Cart.class);
                startActivity(intent);
            }
        });

    }
}
