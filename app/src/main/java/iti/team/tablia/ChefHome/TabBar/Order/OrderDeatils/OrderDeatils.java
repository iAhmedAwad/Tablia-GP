package iti.team.tablia.ChefHome.TabBar.Order.OrderDeatils;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.R;


public class OrderDeatils extends AppCompatActivity {

  OrederDeatailsAdapter myAdapter;
  RecyclerView recyclerView;
  List<ItemOrderPojo> list = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order_deatils);


    recyclerView = findViewById(R.id.listItemOrderDeatils);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    myAdapter = new OrederDeatailsAdapter(list, this);
    recyclerView.setAdapter(myAdapter);
  }
}
