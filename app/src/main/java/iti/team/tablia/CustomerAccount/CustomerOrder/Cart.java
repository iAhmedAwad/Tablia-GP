package iti.team.tablia.CustomerAccount.CustomerOrder;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.team.tablia.Models.CartGroupPojo;
import iti.team.tablia.R;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    public CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        cartViewModel.getCartListGroups().observe(this, new Observer<List<CartGroupPojo>>() {
            @Override
            public void onChanged(List<CartGroupPojo> cartGroupPojos) {
                cartAdapter = new CartAdapter(Cart.this, cartGroupPojos);
                recyclerView.setAdapter(cartAdapter);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cartViewModel.getCartListGroups().observe(this, new Observer<List<CartGroupPojo>>() {
            @Override
            public void onChanged(List<CartGroupPojo> cartGroupPojos) {
                cartAdapter = new CartAdapter(Cart.this, cartGroupPojos);
                recyclerView.setAdapter(cartAdapter);
            }
        });
    }
}
