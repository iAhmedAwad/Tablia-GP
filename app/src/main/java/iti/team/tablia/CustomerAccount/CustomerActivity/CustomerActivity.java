package iti.team.tablia.CustomerAccount.CustomerActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.Authentication.LoginActivity;
import iti.team.tablia.CustomerAccount.Categories.Baking.BackingFragment;
import iti.team.tablia.CustomerAccount.Categories.Dessert.DessertFragment;
import iti.team.tablia.CustomerAccount.Categories.Grilled.GrilledFragment;
import iti.team.tablia.CustomerAccount.Categories.Juice.JuiceFragment;
import iti.team.tablia.CustomerAccount.Categories.Macaroni.MacaroniFragment;
import iti.team.tablia.CustomerAccount.Categories.Mahashy.MahashyFragment;
import iti.team.tablia.CustomerAccount.Categories.MainDishes.MainDishesFragment;
import iti.team.tablia.CustomerAccount.Categories.Salad.SaladFragment;
import iti.team.tablia.CustomerAccount.Categories.Seafood.SeafoodFragment;
import iti.team.tablia.CustomerAccount.Categories.SideDishes.SideDishesFragment;
import iti.team.tablia.CustomerAccount.Categories.Soups.SoupsFragment;
import iti.team.tablia.CustomerAccount.Chat.CustomerChatFragment;
import iti.team.tablia.CustomerAccount.CustomerChefListActivity;
import iti.team.tablia.CustomerAccount.CustomerOrder.Cart;
import iti.team.tablia.CustomerAccount.Filter.FilterActivity;
import iti.team.tablia.CustomerAccount.Home.home.CustomerHomeFragment;
import iti.team.tablia.CustomerAccount.MyOrders.MyOrdersFragment;
import iti.team.tablia.CustomerAccount.Profile.CustomerProfileFragment;
import iti.team.tablia.Models.Customer.CustomerSettings;
import iti.team.tablia.R;
import iti.team.tablia.util.Constants;
import iti.team.tablia.util.GlobalImageLoader;


public class CustomerActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

  private DrawerLayout drawer;
  private NavigationView navigationView;
  private CircleImageView xNavHeaderImageView;
  private TextView xNavBarName;
  private CustomerActivityViewModel model;
  private FragmentManager mManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer);
    mManager = getSupportFragmentManager();
    mManager.addOnBackStackChangedListener(this);
    model = ViewModelProviders.of(this).get(CustomerActivityViewModel.class);
    model.getCustomerSettings().observe(this, new Observer<CustomerSettings>() {
      @Override
      public void onChanged(CustomerSettings customerSettings) {
        //Update UI
        setWidgets(customerSettings);

      }
    });

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    drawer = findViewById(R.id.drawer_layout);


    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    navigationView = findViewById(R.id.nav_view);
    View header = navigationView.getHeaderView(0);
    xNavHeaderImageView = header.findViewById(R.id.xNavHeaderImageView);
    xNavBarName = header.findViewById(R.id.xNavHeaderName);
    xNavHeaderImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigateToProfileFragment();
      }
    });
    //open home as default
    init();
    if (savedInstanceState == null) {

      mManager.beginTransaction().replace(R.id.fragment_container,
          new CustomerHomeFragment()).addToBackStack("home").commit();
      navigationView.setCheckedItem(R.id.nav_home);

    }
  }

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  private void init() {
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
          case R.id.nav_home:
            mManager.beginTransaction().replace(R.id.fragment_container,
                new CustomerHomeFragment()).commit();

            break;

          case R.id.nav_profile:
            mManager.beginTransaction().replace(R.id.fragment_container,
                new CustomerProfileFragment()).commit();
            break;


          case R.id.nav_chat:
            mManager.beginTransaction().replace(R.id.fragment_container,
                new CustomerChatFragment()).commit();
            break;

          case R.id.nav_fish:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new SeafoodFragment()).commit();
            break;

          case R.id.nav_grilled:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new GrilledFragment()).commit();
            break;

          case R.id.nav_mainDishes:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new MainDishesFragment()).commit();
            break;

          case R.id.nav_baking:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new BackingFragment()).commit();
            break;

          case R.id.nav_sideDishes:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new SideDishesFragment()).commit();
            break;

          case R.id.nav_macaroni:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new MacaroniFragment()).commit();
            break;

          case R.id.nav_mahashy:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new MahashyFragment()).commit();
            break;

          case R.id.nav_salad:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new SaladFragment()).commit();
            break;

          case R.id.nav_soups:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new SoupsFragment()).commit();
            break;

          case R.id.nav_dessert:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new DessertFragment()).commit();
            break;

          case R.id.nav_juice:

            mManager.beginTransaction().replace(R.id.fragment_container,
                new JuiceFragment()).commit();
            break;

          case R.id.nav_signout:
            FirebaseAuth.getInstance().signOut();
            SharedPreferences sharedPref = getSharedPreferences(Constants.isLogged, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(Constants.isLogged, false);
            editor.commit();
            Toast.makeText(CustomerActivity.this, "You're signed out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CustomerActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();

            break;
          case R.id.chef_list:
            Intent intent = new Intent(CustomerActivity.this, CustomerChefListActivity.class);
            startActivity(intent);
            break;
          case R.id.nav_orders:
            mManager.beginTransaction().replace(R.id.fragment_container,
                new MyOrdersFragment()).commit();
            break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
      }
    });
  }

  private void setWidgets(CustomerSettings customerSettings) {
//    GlobalImageLoader.setImage(this, xNavHeaderImageView, customerSettings.getCustomerAccountSettings().getProfilePhoto());
    xNavHeaderImageView.setImageBitmap(GlobalImageLoader.StringToBitMap(customerSettings.getCustomerAccountSettings().getProfilePhoto()));
    xNavBarName.setText(customerSettings.getUser().getFullName());

  }



  private void navigateToProfileFragment() {

   this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            new CustomerProfileFragment()).commit();
    drawer.closeDrawer(GravityCompat.START);
  }
  @Override
  public void onBackStackChanged() {
        /*
        FragmentManager supportFragmentManager = mManager;
        int count = supportFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            //Log.d("myStack", "Hi backstack!!");
            Log.d("myStack", supportFragmentManager.getBackStackEntryAt(i).getName());
        }

         */
  }

  /**
   * change current status of users online offline
   *
   * @param status
   */
  private void status(String status) {
    model.status(status);
  }

  @Override
  protected void onResume() {
    super.onResume();
    status("online");
  }

  @Override
  protected void onPause() {
    super.onPause();
    status("offline");
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_toolbar, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {

      case R.id.filter:
        Intent filterIntent = new Intent(CustomerActivity.this, FilterActivity.class);
        startActivity(filterIntent);
        return true;
      case R.id.cart:
        Intent intent = new Intent(CustomerActivity.this, Cart.class);
        startActivity(intent);
        //Toast.makeText(this, "Go To Cart Activity ", Toast.LENGTH_SHORT).show();
        return true;
    }
    return false;

  }
}
