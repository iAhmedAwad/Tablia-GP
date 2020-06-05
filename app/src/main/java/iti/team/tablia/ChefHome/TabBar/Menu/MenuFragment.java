package iti.team.tablia.ChefHome.TabBar.Menu;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.AddMenu;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.R;


public class MenuFragment extends Fragment {

  private MenuViewModel menuViewModel;

  MenuAdapter myAdapter;
  RecyclerView recycleMenu;
  FloatingActionButton fab;
  Bitmap bitmap = null;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);

    View root = inflater.inflate(R.layout.fragment_menu, container, false);


    fab = root.findViewById(R.id.fab);

    recycleMenu = root.findViewById(R.id.recycleMenu);
    recycleMenu.setLayoutManager(new GridLayoutManager(getContext(), 2));


    menuViewModel.getList();



    menuViewModel.deatilsMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<MenuPojo>>() {
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
