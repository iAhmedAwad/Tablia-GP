package iti.team.tablia.CustomerAccount.Home.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.CustomerAccount.CustomerChefListActivity;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerHomeFragment extends Fragment {

  public CustomerHomeFragment() {
    // Required empty public constructor
  }

  TopChefRecyclerAdaptor adapter;
  CategoryRecyclerAdaptor adapter2;
  TextView see1, see2;
  CustomerHomeViewModel viewModel;
  ProgressBar progressBar;
  ProgressBar progressBar2;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    super.onCreate(savedInstanceState);

    viewModel = new ViewModelProvider(getActivity()).get(CustomerHomeViewModel.class);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.home));
    progressBar = view.findViewById(R.id.progressBar);
    progressBar.setVisibility(View.VISIBLE);
    progressBar2 = view.findViewById(R.id.progressBar1);
    progressBar2.setVisibility(View.VISIBLE);
    final RecyclerView recycler = view.findViewById(R.id.id_recycle1);
    final RecyclerView recycler2 = view.findViewById(R.id.id_recycle2);

    see1 = view.findViewById(R.id.seeAllchefs);
    see2 = view.findViewById(R.id.seeAllCategories);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);


    viewModel.getChefList().observe(requireActivity(), new Observer<List<ChatUser>>() {
      @Override
      public void onChanged(List<ChatUser> chefList) {
        progressBar.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);
        adapter = new TopChefRecyclerAdaptor(getContext(), chefList);
        recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

      }
    });

    viewModel.getitemsFollwedByCust().observe(getActivity(), new Observer<ArrayList<MenuPojo>>() {
      @Override
      public void onChanged(ArrayList<MenuPojo> menuPojos) {
        adapter2 = new CategoryRecyclerAdaptor(getActivity(), menuPojos);
        recycler2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
      }
    });

    recycler.setLayoutManager(layoutManager);
    recycler2.setLayoutManager(gridLayoutManager);

    see1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(getActivity(), CustomerChefListActivity.class);
        startActivity(intent);
      }
    });

    return view;
  }


}
