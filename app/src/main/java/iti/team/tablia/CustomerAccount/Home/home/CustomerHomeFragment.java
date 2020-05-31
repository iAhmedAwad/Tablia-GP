package iti.team.tablia.CustomerAccount.Home.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
  //FollowingRecyclerAdaptor adapter3;
  TextView see1, see2, see3;
  CustomerHomeViewModel viewModel;
  // ChefYouFollowViewModel followViewModel;
  List<Integer> list = new ArrayList<>();

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    super.onCreate(savedInstanceState);

    viewModel = new ViewModelProvider(getActivity()).get(CustomerHomeViewModel.class);
    //  followViewModel = new ViewModelProvider(getActivity()).get(ChefYouFollowViewModel.class);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
    // viewModel.getitems();
    list.add(R.drawable.pasta);
    list.add(R.drawable.pasta);
    list.add(R.drawable.pasta);
    list.add(R.drawable.pasta);
    list.add(R.drawable.pasta);
    list.add(R.drawable.pasta);
    list.add(R.drawable.pasta);
    list.add(R.drawable.pasta);


    final RecyclerView recycler = view.findViewById(R.id.id_recycle1);
    final RecyclerView recycler2 = view.findViewById(R.id.id_recycle2);
//        final RecyclerView recycler3 = view.findViewById(R.id.id_recycle3);

    see1 = view.findViewById(R.id.seeAllchefs);
    see2 = view.findViewById(R.id.seeAllCategories);
//        see3 = view.findViewById(R.id.seeAllFollowings);


    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);


    viewModel.getChefList().observe(requireActivity(), new Observer<List<ChatUser>>() {
      @Override
      public void onChanged(List<ChatUser> chefList) {
        adapter = new TopChefRecyclerAdaptor(getContext(), chefList);
        recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

      }
    });
//        followViewModel.getFollwing().observe(getActivity(), new Observer<ArrayList<Following>>() {
//            @Override
//            public void onChanged(ArrayList<Following> followings) {
//                adapter3 = new FollowingRecyclerAdaptor(getContext(), followings);
////                recycler3.setAdapter(adapter3);
//                adapter3.notifyDataSetChanged();
//            }
//
//        });


// TODO ViewModel Category

    viewModel.getitemsFollwedByCust().observe(getActivity(), new Observer<ArrayList<MenuPojo>>() {
      @Override
      public void onChanged(ArrayList<MenuPojo> menuPojos) {
        adapter2 = new CategoryRecyclerAdaptor(getActivity(), menuPojos);
        recycler2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
      }
    });
//        adapter2 = new CategoryRecyclerAdaptor(getContext(), list);


    recycler.setLayoutManager(layoutManager);
    recycler2.setLayoutManager(gridLayoutManager);
    //seperator
    //recycler2.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//        recycler3.setLayoutManager(layoutManager3);


    see1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(getActivity(), CustomerChefListActivity.class);
        startActivity(intent);
      }
    });
//        see2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getActivity(), seeAllCategories.class);
//                startActivity(intent);
//            }
//        });
//        see3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getActivity(), FollowingActivity.class);
//                startActivity(intent);
//            }
//        });


    return view;
  }


}
