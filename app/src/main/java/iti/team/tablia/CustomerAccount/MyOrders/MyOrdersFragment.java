package iti.team.tablia.CustomerAccount.MyOrders;

import android.content.Intent;
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
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {

    private MyOrdersViewModel orderViewModel;
    private FloatingActionButton fab;
    private MyOrdersAdapter myAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);
        fab = root.findViewById(R.id.fab_history);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.orders));
        orderViewModel = ViewModelProviders.of(this).get(MyOrdersViewModel.class);
        progressBar=root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = root.findViewById(R.id.listOfOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderViewModel.getCustOrders().observe(getViewLifecycleOwner(), new Observer<List<OrderPojo>>() {
            @Override
            public void onChanged(List<OrderPojo> orderPojos) {
                progressBar.setVisibility(View.GONE);
                myAdapter = new MyOrdersAdapter(orderPojos, getContext());
                recyclerView.setAdapter(myAdapter);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), OrderHistory.class);
                startActivity(i);
            }
        });

        return root;
    }
}
