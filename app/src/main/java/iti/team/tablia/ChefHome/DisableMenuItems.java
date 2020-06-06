package iti.team.tablia.ChefHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.MenuAdapter;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.R;

public class DisableMenuItems extends AppCompatActivity {


    private DisableMenuItemVM disableMenuItemVM;
    DisabledAdapter myAdapter;
    RecyclerView recycleMenu;
    FloatingActionButton fab;
    Bitmap bitmap = null;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disable_menu_items);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        disableMenuItemVM = ViewModelProviders.of(this).get(DisableMenuItemVM.class);

        recycleMenu = findViewById(R.id.recycleMenu);
        recycleMenu.setLayoutManager(new GridLayoutManager(this, 2));


        disableMenuItemVM.getList();


        disableMenuItemVM.deatilsMutableLiveData.observe(this, new Observer<List<MenuPojo>>() {
            @Override
            public void onChanged(List<MenuPojo> menuPojos) {
                progressBar.setVisibility(View.GONE);
                myAdapter = new DisabledAdapter(menuPojos, DisableMenuItems.this);
                recycleMenu.setAdapter(myAdapter);

            }
        });

    }
}
