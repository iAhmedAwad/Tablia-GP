package iti.team.tablia.ChefHome.TabBar.Order.OrderDeatils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Chat.Messages.MessageActivity;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.R;


public class OrderDeatils extends AppCompatActivity {

    private OrderDetailsAdapter myAdapter;
    private RecyclerView recyclerView;
    public OrderDeatilsViewModel model;
    private List<CartPojo> list = new ArrayList<>();
    private TextView orderID;
    private TextView orderTime;
    private TextView itemNum;
    private TextView total;
    private TextView deliveryType;
    private TextView delAddress;
    private TextView shippingFee;
    private EditText shippingFeeEditor;
    private ImageView editShipping;
    private ImageView doneShipping;
    private TextView custName;
    private TextView custPhone;
    private LinearLayout message;
    private EditText delTimeEditor;
    private ImageView editDelivery;
    private ImageView doneDelivery;
    private TextView delTime;
    private TextView delStatus;
    private Button confirm;
    private String orderIDStr;
    private String chefID;
    private String custID;
    private CustomerAccountSettings cust;
    private ChefAccountSettings chef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_deatils);
        recyclerView = findViewById(R.id.listItemOrderDeatils);
        orderID = findViewById(R.id.orderID);
        orderTime = findViewById(R.id.orderTime);
        itemNum = findViewById(R.id.item_num);
        total = findViewById(R.id.total_amount);
        deliveryType = findViewById(R.id.shipping_method);
        delAddress = findViewById(R.id.del_address);
        shippingFee = findViewById(R.id.shipping_fee);
        shippingFeeEditor = findViewById(R.id.shipping_fee_Editor);
        editShipping = findViewById(R.id.edit_shipping_fee);
        doneShipping = findViewById(R.id.done_shipping_fee);
        custName = findViewById(R.id.cust_name);
        custPhone = findViewById(R.id.cust_phone);
        message = findViewById(R.id.message);
        delTimeEditor = findViewById(R.id.delivery_timeEditor);
        editDelivery = findViewById(R.id.edit_del_time);
        doneDelivery = findViewById(R.id.done_del_time);
        delTime = findViewById(R.id.delivery_time);
        delStatus = findViewById(R.id.delivery_status);
        confirm = findViewById(R.id.confirm_delivery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        model = ViewModelProviders.of(this).get(OrderDeatilsViewModel.class);
        final Intent intent = getIntent();
        orderIDStr = intent.getStringExtra("orderID");
        chefID = intent.getStringExtra("chefID");
        custID = intent.getStringExtra("custID");
        orderID.setText(orderIDStr);
        model.getCustInfo(custID).observe(OrderDeatils.this, new Observer<CustomerAccountSettings>() {
            @Override
            public void onChanged(CustomerAccountSettings settings) {
                cust = settings;
                custName.setText(settings.getDisplayName());
                custPhone.setText(settings.getPhoneNumber());
            }
        });
        model.getChefInfo(chefID).observe(OrderDeatils.this, new Observer<ChefAccountSettings>() {
            @Override
            public void onChanged(ChefAccountSettings settings) {
                chef = settings;

            }
        });
        model.getOrder(orderIDStr, chefID, custID).observe(this, new Observer<OrderPojo>() {
            @Override
            public void onChanged(OrderPojo orderPojo) {


                orderTime.setText(orderPojo.getOrderTime());
                itemNum.setText(orderPojo.getItems().size() + " items");
                total.setText("EGP " + orderPojo.getTotal());
                if (orderPojo.isDeliveryType()) {
                    deliveryType.setText("Door Delivery");
                    if (cust != null) {
                        delAddress.setText(cust.getAddress());
                    }
                } else {
                    if (chef != null) {
                        delAddress.setText(chef.getAddress());
                    }
                    deliveryType.setText("Pickup");
                }
                shippingFee.setText(orderPojo.getDeliveryFee() + " EGP");
                if(!orderPojo.isCustConfirm()&&!orderPojo.isChefConfirm()) {
                  delStatus.setText("Order not delivered yet");
                }else if(orderPojo.isChefConfirm()&&!orderPojo.isCustConfirm()){
                  delStatus.setText("Order on the way to be delivered");
                }else if(orderPojo.isCustConfirm()&&orderPojo.isChefConfirm()){
                  delStatus.setText("Order was deliverd on" + orderPojo.getDeliveryTime());
                }
                if(orderPojo.isChefConfirm()){
                  confirm.setEnabled(false);
                  confirm.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                  confirm.setBackground(null);
                  confirm.setText("Delivery Confirmed");
                  confirm.setTextColor(Color.GREEN);
                  editDelivery.setVisibility(View.INVISIBLE);
                  editShipping.setVisibility(View.INVISIBLE);
                }
                delTime.setText(orderPojo.getDeliveryTime());
                myAdapter = new OrderDetailsAdapter(orderPojo.getItems(), OrderDeatils.this);
                recyclerView.setAdapter(myAdapter);
            }
        });
        editShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shippingFeeEditor.setVisibility(View.VISIBLE);
                shippingFee.setVisibility(View.INVISIBLE);
                doneShipping.setVisibility(View.VISIBLE);
                editShipping.setVisibility(View.INVISIBLE);
                double shipping = Double.parseDouble(shippingFee.getText().toString().split(" ")[0]);
                shippingFeeEditor.setText(shipping+"");
            }
        });
        doneShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shippingFeeEditor.getText().toString().equals("")) {
                    double newShippingFee = Double.parseDouble(shippingFeeEditor.getText().toString());
                    shippingFee.setText(newShippingFee + " EGP");
                    shippingFeeEditor.setVisibility(View.INVISIBLE);
                    shippingFee.setVisibility(View.VISIBLE);
                    doneShipping.setVisibility(View.INVISIBLE);
                    editShipping.setVisibility(View.VISIBLE);
                    model.updateOrder(orderIDStr, chefID, custID, newShippingFee);

                }
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent goToMsg = new Intent(OrderDeatils.this, MessageActivity.class);
            goToMsg.putExtra("userid",custID);
            startActivity(goToMsg);
          }
        });
      editDelivery.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          delTimeEditor.setVisibility(View.VISIBLE);
          delTime.setVisibility(View.INVISIBLE);
          doneDelivery.setVisibility(View.VISIBLE);
          editDelivery.setVisibility(View.INVISIBLE);
          delTimeEditor.setText(delTime.getText().toString());
        }
      });
      doneDelivery.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (!delTimeEditor.getText().toString().equals("")) {
            String deliveryTime = delTimeEditor.getText().toString();
            delTime.setText(deliveryTime);
            delTimeEditor.setVisibility(View.INVISIBLE);
            delTime.setVisibility(View.VISIBLE);
            doneDelivery.setVisibility(View.INVISIBLE);
            editDelivery.setVisibility(View.VISIBLE);
            model.updateOrderDelTime(orderIDStr, chefID, custID, deliveryTime);

          }
        }
      });
      confirm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          confirm.setEnabled(false);
          confirm.setBackgroundColor(Color.parseColor("#00FFFFFF"));
          confirm.setBackground(null);
          confirm.setText("Delivery Confirmed");
          confirm.setTextColor(Color.GREEN);
          editDelivery.setVisibility(View.INVISIBLE);
          editShipping.setVisibility(View.INVISIBLE);
          model.updateOrderChefConfirm(orderIDStr, chefID, custID);
        }
      });

    }
}
