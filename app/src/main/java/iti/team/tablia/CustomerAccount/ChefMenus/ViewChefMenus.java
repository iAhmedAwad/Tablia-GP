package iti.team.tablia.CustomerAccount.ChefMenus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.MenuAdapter;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.CustomerAccount.Home.home.CustomerHomeViewModel;
import iti.team.tablia.CustomerAccount.Home.home.TopChefRecyclerAdaptor;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.R;

public class ViewChefMenus extends AppCompatActivity {

    MenuAdapter myAdapter;
    RecyclerView recycleMenu;
    ViewChefMenuViewModel myViewModel;
    String chefId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chef_menus);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recycleMenu = findViewById(R.id.recycleMenu);
        myViewModel = new ViewModelProvider(this).get(ViewChefMenuViewModel.class);

      //  String chefId = get
        final Intent intent = getIntent();
        chefId = intent.getStringExtra("userid");

myViewModel.getList(chefId).observe(this, new Observer<List<MenuPojo>>() {
    @Override
    public void onChanged(List<MenuPojo> menuPojos) {
        myAdapter = new MenuAdapter( menuPojos, ViewChefMenus.this);
        recycleMenu.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }
});
        recycleMenu.setLayoutManager(gridLayoutManager);
    }
}
