package iti.team.tablia.ChefHome.TabBar.Oreder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.R;


public class OrderFragment extends Fragment {

  private OrderViewModel orderViewModel;

  OrderAdapter myAdapter;
  RecyclerView recyclerView;
  List<OrderPojo> list = new ArrayList<>();
  List<OrderPojo> testList = new ArrayList<>();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);


    View root = inflater.inflate(R.layout.fragment_order, container, false);

    recyclerView = root.findViewById(R.id.listOfOrder);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    orderViewModel.getList();

    orderViewModel.deatilsMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<OrderPojo>>() {
      @Override
      public void onChanged(List<OrderPojo> orderPojos) {
        list = orderPojos;
        myAdapter = new OrderAdapter(list, getContext());
        recyclerView.setAdapter(myAdapter);
      }
    });

    return root;
  }
}
