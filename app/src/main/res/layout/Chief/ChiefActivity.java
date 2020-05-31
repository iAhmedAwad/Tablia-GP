package com.awad.tablia.ChefHome;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.awad.tablia.ChefHome.TabBar.Chat.ChatFragment;
import com.awad.tablia.ChefHome.TabBar.Home.HomeFragment;
import com.awad.tablia.ChefHome.TabBar.Menu.MenuFragment;
import com.awad.tablia.ChefHome.TabBar.Oreder.OrderFragment;
import com.awad.tablia.ChefHome.TabBar.Profile.profileFragment;
import com.awad.tablia.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChiefActivity extends AppCompatActivity {

  private BottomNavigationView bottomNavigationView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chief);
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
}
