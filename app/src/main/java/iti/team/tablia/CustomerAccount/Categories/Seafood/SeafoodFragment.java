package iti.team.tablia.CustomerAccount.Categories.Seafood;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.R;
import iti.team.tablia.util.Constants;


public class SeafoodFragment extends Fragment {

  private SeafoodViewModel mViewModel;
  private RecyclerView recyclerView;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager layoutManager;
  private ProgressBar progressBar;

  public static SeafoodFragment newInstance() {
    return new SeafoodFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fish_fragment, container, false);
    String lang = Locale.getDefault().getLanguage();
    if(lang.equals("ar")) {
      ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Constants.SEAFOOD_AR);
    }else {
      ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Constants.SEAFOOD);

    }
    recyclerView = view.findViewById(R.id.xFishRecycler);
    progressBar=view.findViewById(R.id.progressBar);
    progressBar.setVisibility(View.VISIBLE);
    recyclerView.setHasFixedSize(true);

    layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    mViewModel = ViewModelProviders.of(this).get(SeafoodViewModel.class);
    mViewModel.getFishItems(Constants.SEAFOOD,Constants.SEAFOOD_AR).observe(getViewLifecycleOwner(), new Observer<ArrayList<MenuPojo>>() {
      @Override
      public void onChanged(ArrayList<MenuPojo> menuPojos) {
        progressBar.setVisibility(View.GONE);
        mAdapter = new SeafoodAdapter(getContext(), menuPojos);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
      }
    });
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    // TODO: Use the ViewModel
  }

}
