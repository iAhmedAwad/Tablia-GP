package com.awad.tablia.ChefHome.TabBar.Menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.awad.tablia.ChefHome.TabBar.Menu.AddMenu.AddMenu;
import com.awad.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import com.awad.tablia.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MenuFragment extends Fragment {

  private MenuViewModel menuViewModel;

  MenuAdapter myAdapter;
  RecyclerView recycleMenu;
  //    ImageView img ;
  FloatingActionButton fab;
  Bitmap bitmap = null;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
    View root = inflater.inflate(R.layout.fragment_menu, container, false);

    DataOfMenu data = DataOfMenu.getInstance();

    fab = root.findViewById(R.id.fab);

    recycleMenu = root.findViewById(R.id.recycleMenu);
    recycleMenu.setLayoutManager(new GridLayoutManager(getContext(), 2));


    data.setData(new MenuPojo("Cake", "200 EGP", bitmap));
    menuViewModel.getList();


//        menuViewModel.setList(testList);

    menuViewModel.deatilsMutableLiveData.observe(this, new Observer<List<MenuPojo>>() {
      @Override
      public void onChanged(List<MenuPojo> menuPojos) {
        myAdapter = new MenuAdapter(menuPojos, getContext());
        recycleMenu.setAdapter(myAdapter);
      }
    });


    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent goToAddMenue = new Intent(getContext(), AddMenu.class);
        startActivity(goToAddMenue);
      }
    });

    return root;
  }
}
