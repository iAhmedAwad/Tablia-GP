package iti.team.tablia.CustomerAccount.Categories.SideDishes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.CustomerAccount.Categories.General.GeneralAdapter;
import iti.team.tablia.CustomerAccount.Categories.General.GeneralViewModel;
import iti.team.tablia.R;
import iti.team.tablia.util.Constants;

  public class SideDishesFragment extends Fragment {

    private GeneralViewModel mViewModel;
    private RecyclerView xRecyclerView;
    private GeneralAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_category, container, false);
      String lang = Locale.getDefault().getLanguage();
      if(lang.equals("ar")) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Constants.SIDE_DISHES_AR);
      }else {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Constants.SIDE_DISHES);
      }
      mViewModel = new ViewModelProvider(this).get(GeneralViewModel.class);
      xRecyclerView = view.findViewById(R.id.xCategoryRecycle);
      progressBar = view.findViewById(R.id.progressBar);
      layoutManager = new LinearLayoutManager(getContext());
      xRecyclerView.setHasFixedSize(true);
      xRecyclerView.setLayoutManager(layoutManager);


      return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      mViewModel.getCategoryItems(Constants.SIDE_DISHES, Constants.SIDE_DISHES_AR)
          .observe(getViewLifecycleOwner(), new Observer<ArrayList<MenuPojo>>() {
            @Override
            public void onChanged(ArrayList<MenuPojo> menuPojos) {
              progressBar.setVisibility(View.GONE);
              mAdapter = new GeneralAdapter(getContext(), menuPojos);
              xRecyclerView.setAdapter(mAdapter);
              mAdapter.notifyDataSetChanged();
            }
          });
    }

  }
