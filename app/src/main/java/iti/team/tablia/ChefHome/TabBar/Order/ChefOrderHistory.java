package iti.team.tablia.ChefHome.TabBar.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.R;

public class ChefOrderHistory extends AppCompatActivity {

    private OrderViewModel orderViewModel;
    private OrderAdapter myAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_order_history);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        recyclerView = findViewById(R.id.listOfOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderViewModel.getChefOrdersHistory().observe(this, new Observer<List<OrderPojo>>() {
            @Override
            public void onChanged(List<OrderPojo> orderPojos) {
                progressBar.setVisibility(View.GONE);
                myAdapter = new OrderAdapter(orderPojos,ChefOrderHistory.this,orderViewModel);
                recyclerView.setAdapter(myAdapter);
            }
        });
    }
}
