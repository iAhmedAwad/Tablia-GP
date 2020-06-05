package iti.team.tablia.ChefHome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import iti.team.tablia.Authentication.LoginActivity;
import iti.team.tablia.ChefHome.TabBar.Chat.ChatFragment;
import iti.team.tablia.ChefHome.TabBar.Home.HomeFragment;
import iti.team.tablia.ChefHome.TabBar.Menu.MenuFragment;
import iti.team.tablia.ChefHome.TabBar.Order.OrderFragment;
import iti.team.tablia.ChefHome.TabBar.Profile.profileFragment;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.R;
import iti.team.tablia.util.Constants;


public class ChiefActivity extends AppCompatActivity {

  private BottomNavigationView bottomNavigationView;
  private TextView username;
  private ImageView profile_image;
  private ChefViewModel chefViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chief);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("");

    username = findViewById(R.id.username);
    profile_image = findViewById(R.id.profile_image);


    chefViewModel = ViewModelProviders.of(this).get(ChefViewModel.class);
    chefViewModel.getCurrentUser().observe(ChiefActivity.this, new Observer<ChefAccountSettings>() {
      @Override
      public void onChanged(ChefAccountSettings user) {
        username.setText(user.getDisplayName());
        if (user.getProfilePhoto().contains("https")) {
          Glide.with(getApplicationContext()).load(user.getProfilePhoto()).into(profile_image);
        } else {
          Bitmap bitmap = StringToBitMap(user.getProfilePhoto());
          profile_image.setImageBitmap(bitmap);
        }
      }
    });
    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    bottomNavigationView = findViewById(R.id.bottomNav);
    bottomNavigationView.setSelectedItemId(R.id.home);

    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()) {
          case R.id.chat:
            fragment = new ChatFragment();
            break;

          case R.id.profile:
            fragment = new profileFragment();
            break;

          case R.id.home:
            fragment = new HomeFragment();
            break;

          case R.id.order:
            fragment = new OrderFragment();
            break;

          case R.id.menu:
            fragment = new MenuFragment();
            break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        return true;
      }
    });

  }

  /**
   * render menu
   *
   * @param menu
   * @return
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.doted_menu, menu);
    return true;
  }

  /**
   * choose menu item to logout
   *
   * @param item
   * @return
   */
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.logout:
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedPref = getSharedPreferences(Constants.isLogged, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constants.isLogged, false);
        editor.commit();
        startActivity(new Intent(ChiefActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
        return true;


    }
    return false;
  }

  public Bitmap StringToBitMap(String encodedString) {
    try {
      byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
      Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
          encodeByte.length);
      return bitmap;
    } catch (Exception e) {
      e.getMessage();
      return null;
    }
  }

  /**
   * change current status of users online offline
   *
   * @param status
   */
  private void status(String status) {
    chefViewModel.status(status);
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
}
