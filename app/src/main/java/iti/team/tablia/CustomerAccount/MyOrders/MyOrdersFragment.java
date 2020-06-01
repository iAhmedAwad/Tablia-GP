package iti.team.tablia.CustomerAccount.MyOrders;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {

    private MyOrdersViewModel orderViewModel;

   private MyOrdersAdapter myAdapter;
    private RecyclerView recyclerView;

    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_my_orders, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Orders");
        orderViewModel = ViewModelProviders.of(this).get(MyOrdersViewModel.class);

        recyclerView = root.findViewById(R.id.listOfOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderViewModel.getCustOrders().observe(getViewLifecycleOwner(), new Observer<List<OrderPojo>>() {
            @Override
            public void onChanged(List<OrderPojo> orderPojos) {
                myAdapter = new MyOrdersAdapter(orderPojos, getContext());
                recyclerView.setAdapter(myAdapter);
            }
        });

        return root;
    }
}
