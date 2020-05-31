package com.awad.tablia.ChefHome.TabBar.Oreder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.awad.tablia.R;

import java.util.ArrayList;
import java.util.List;

import Models.OrderPojo;


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

    testList.add(new OrderPojo("Order ID : 353", "Order Time : 04 : 20 AM 20/20/2020",
        "Customer ID : 324", "Total : 200 EGP", true));
    testList.add(new OrderPojo("Order ID : 353", "Order Time : 04 : 20 AM 20/20/2020",
        "Customer ID : 324", "Total : 200 EGP", false));
    orderViewModel.setList(testList);

    orderViewModel.deatilsMutableLiveData.observe(this, new Observer<List<OrderPojo>>() {
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
