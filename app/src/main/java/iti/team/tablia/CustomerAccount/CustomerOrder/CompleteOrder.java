package iti.team.tablia.CustomerAccount.CustomerOrder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import iti.team.tablia.ChefHome.TabBar.Oreder.OrderPojo;
import iti.team.tablia.Customer.chiefProfile.ViewChiefProfileActivity;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.R;


public class CompleteOrder extends AppCompatActivity {
  private TextView changeAddress;
  private TextView custName;
  private TextView custAddress;
  private TextView custPhone;
  private RadioButton doorDelivery;
  private RadioButton pickup;
  private TextView shippingFee;
  private TextView chefName;
  private TextView subTotal;
  private TextView shippingFee2;
  private TextView total;
  private Button confirm;
  private Button cancel;
  private CompleteOrderViewModel model;
  public static OrderPojo orderPojo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_complete_order);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("Complete Order");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    changeAddress = findViewById(R.id.change_address);
    custName = findViewById(R.id.cust_name);
    custAddress = findViewById(R.id.cust_address);
    custPhone = findViewById(R.id.cust_phone);
    doorDelivery = findViewById(R.id.door);
    pickup = findViewById(R.id.pickup);
    shippingFee = findViewById(R.id.shipping_fee);
    shippingFee2 = findViewById(R.id.shippingFee);
    total = findViewById(R.id.total);
    subTotal = findViewById(R.id.subtotal);
    chefName = findViewById(R.id.chef_name);
    confirm = findViewById(R.id.confirm);
    cancel = findViewById(R.id.cancel);
    changeAddress.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(CompleteOrder.this, "CHANGE YOUR ADDRESS", Toast.LENGTH_SHORT).show();
      }
    });
    model = ViewModelProviders.of(this).get(CompleteOrderViewModel.class);
    model.getCustInfo().observe(this, new Observer<CustomerAccountSettings>() {
      @Override
      public void onChanged(CustomerAccountSettings settings) {
        custName.setText(settings.getDisplayName());
        custAddress.setText(settings.getAddress());
        custPhone.setText(settings.getPhoneNumber());
      }
    });
    final double shipping = orderPojo.getDeliveryFee();
    chefName.setText(orderPojo.getChefName());
    chefName.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent1 = new Intent(CompleteOrder.this, ViewChiefProfileActivity.class);
        intent1.putExtra("userid", orderPojo.getChefID());
        startActivity(intent1);
      }
    });
    subTotal.setText("EGP " + orderPojo.getSubTotal());

    shippingFee.setText(shipping + " EGP");
    doorDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (doorDelivery.isChecked()) {
          shippingFee2.setText("EGP " + shipping);
          shippingFee2.setTextColor(Color.parseColor("#71068F"));
          double totalD = orderPojo.getSubTotal() + shipping;
          total.setText("EGP " + totalD);
          orderPojo.setDeliveryType(true);
        }
      }
    });

    pickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (pickup.isChecked()) {
          shippingFee2.setText("EGP " + 0.0);
          shippingFee2.setTextColor(Color.GREEN);
          total.setText("EGP " + orderPojo.getSubTotal());
          orderPojo.setDeliveryType(false);
        }
      }
    });
    confirm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        orderPojo.setOrderTime(dateFormat.format(date));
        model.saveOrder(orderPojo);
      }
    });
    cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });


  }
}
