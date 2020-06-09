package iti.team.tablia.CustomerAccount.Categories.Baking;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.CustomerAccount.Categories.Grilled.GrilledAdapter;
import iti.team.tablia.R;
import iti.team.tablia.util.Constants;

public class BackingFragment extends Fragment {

  private BackingViewModel mViewModel;
  private RecyclerView xRecyclerView;
  private GrilledAdapter mAdapter;
  private RecyclerView.LayoutManager layoutManager;
  private ProgressBar progressBar;
  public BackingFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_category, container, false);
    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Backing");
    mViewModel = new ViewModelProvider(this).get(BackingViewModel.class);
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

    mViewModel.getBackingItems(Constants.BACKING)
        .observe(getViewLifecycleOwner(), new Observer<ArrayList<MenuPojo>>() {
          @Override
          public void onChanged(ArrayList<MenuPojo> menuPojos) {
            progressBar.setVisibility(View.GONE);
            mAdapter = new GrilledAdapter(getContext(), menuPojos);
            xRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
          }
        });
  }
}