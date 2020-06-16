package iti.team.tablia.CustomerAccount.Items;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.MenuItemPojo;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.Customer.ChefProfile.ViewChiefProfileActivity;
import iti.team.tablia.CustomerAccount.CustomerOrder.Cart;
import iti.team.tablia.CustomerAccount.ItemReview.ItemReview;
import iti.team.tablia.CustomerAccount.Reviews.AddReviewsActivity;
import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.R;
import iti.team.tablia.util.Constants;

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
    private TextView toolbarTitle;
    private String chefId;
    private String itemId;
    private ProgressBar progressBar;
    private String priceUnit;
    private String reviewTxt;
    private String more;
    private String warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        String lang = Locale.getDefault().getLanguage();

        if (lang.equals("ar")) {
            priceUnit = " ج.م";
            reviewTxt=" تعليقات";
            warning="هذا المنتج قد نفذ";
            more="لا يوجد المزيد";

        } else {
            priceUnit = " EGP";
            reviewTxt=" reviews";
            warning = "item is no longer exist";
            more="no more items available";
        }

        toolbarTitle = findViewById(R.id.toolbar_title);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        chefId = intent.getStringExtra("chefId");
        itemId = intent.getStringExtra("itemId");
        final String item_name = intent.getStringExtra("itemName");
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


        detailsViewModel = ViewModelProviders.of(this).get(ItemDetailsViewModel.class);
        detailsViewModel.getItemReviewsCountAndRating(itemId).observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviewsList) {
                double totalRating = 0;
                for (Review review : reviewsList) {
                    totalRating+=review.getRating();
                }
                itemRating.setRating((float) (totalRating/(float)reviewsList.size()));
                reviews.setText(reviewsList.size() + reviewTxt);
            }
        });


        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemDetails.this, ItemReview.class);
                i.putExtra("itemID",itemId);
                startActivity(i);

            }
        });
        //end
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
                                            Toast.makeText(ItemDetails.this, more, Toast.LENGTH_SHORT).show();
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
                if (menuPojo != null) {
                    progressBar.setVisibility(View.GONE);
                    getSupportActionBar().setTitle(menuPojo.getItemName());
                    itemName.setText(menuPojo.getItemName());
                    price = menuPojo.getPriceItem();
                    itemPrice.setText(menuPojo.getPriceItem() + priceUnit);
                    String cat = getCategory(menuPojo.getCategory());
                    category.setText(cat);
                    ingredients.setText(menuPojo.getIngredients());
                    description.setText(menuPojo.getDescription());
                    imgList = menuPojo.getImgItem();
                    ImageSliderAdapter adapter = new ImageSliderAdapter(ItemDetails.this, menuPojo.getImgItem());
                    viewPager.setAdapter(adapter);
                } else {
                    Toast.makeText(ItemDetails.this, warning, Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Review", "itemId is: "+itemId+"ChefId is: "+ chefId);
                Intent intent1 = new Intent(ItemDetails.this, AddReviewsActivity.class);
                intent1.putExtra(AddReviewsActivity.INCOMING_ITEM_ID, itemId);
                intent1.putExtra(AddReviewsActivity.INCOMING_CHEF_ID, chefId);
                intent1.putExtra(AddReviewsActivity.INCOMING_ITEM_NAME, item_name);
                startActivity(intent1);
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
