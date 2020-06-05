package iti.team.tablia.ChefHome.TabBar.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.R;


public class OrderFragment extends Fragment {

    private OrderViewModel orderViewModel;
    private OrderAdapter myAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        fab = root.findViewById(R.id.fab_history);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = root.findViewById(R.id.listOfOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderViewModel.getList();

        orderViewModel.deatilsMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<OrderPojo>>() {
            @Override
            public void onChanged(List<OrderPojo> orderPojos) {
                progressBar.setVisibility(View.GONE);
                myAdapter = new OrderAdapter(orderPojos, getContext(), orderViewModel);
                recyclerView.setAdapter(myAdapter);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChefOrderHistory.class);
                startActivity(i);
            }
        });

        return root;
    }
}
