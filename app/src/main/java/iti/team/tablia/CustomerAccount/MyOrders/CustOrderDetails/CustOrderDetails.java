package iti.team.tablia.CustomerAccount.MyOrders.CustOrderDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Chat.Messages.MessageActivity;
import iti.team.tablia.ChefHome.TabBar.Order.OrderDeatils.OrderDeatils;
import iti.team.tablia.ChefHome.TabBar.Order.OrderDeatils.OrderDeatilsViewModel;
import iti.team.tablia.ChefHome.TabBar.Order.OrderDeatils.OrderDetailsAdapter;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.CustomerAccount.MyOrders.CustOrderDetails.Rating.RatingDialog;
import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.R;

public class CustOrderDetails extends AppCompatActivity {


    private CustOrderItemAdapter myAdapter;
    private RecyclerView recyclerView;
    public CustOrderDetailsVM model;
    private TextView orderID;
    private TextView orderTime;
    private TextView itemNum;
    private TextView total;
    private TextView deliveryType;
    private TextView delAddress;
    private TextView shippingFee;
    private TextView chefName;
    private TextView chefPhone;
    private LinearLayout message;
    private TextView delTime;
    private TextView delStatus;
    private Button confirm;
    private String orderIDStr;
    private String chefID;
    private String custID;
    private CustomerAccountSettings cust;
    private ChefAccountSettings chef;
    private ProgressBar progressBar;
    private String priceUnit;
    private String itemLang;
    private String status_del;
    private String status_on_way;
    private String status_not_del;
    private String confirmDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_order_details);
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("ar")) {
            priceUnit = " ج.م";
            itemLang = " أصناف";
            status_del="تم توصيل الطلبية في";
            status_not_del="لم يتم توصيل الطلبية بعد";
            status_on_way="الطلبية في الطريق ليتم توصيلها";
            confirmDel="تم تسليم الطلبية";
        } else {
            itemLang = " items";
            priceUnit = " EGP";
            status_del="Order was deliverd on";
            status_not_del="Order not delivered yet";
            status_on_way="Order on the way to be delivered";
            confirmDel = "Delivery Confirmed";
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.listItemOrderDeatils);
        orderID = findViewById(R.id.orderID);
        orderTime = findViewById(R.id.orderTime);
        itemNum = findViewById(R.id.item_num);
        total = findViewById(R.id.total_amount);
        deliveryType = findViewById(R.id.shipping_method);
        delAddress = findViewById(R.id.del_address);
        shippingFee = findViewById(R.id.shipping_fee);
        chefName = findViewById(R.id.chef_name);
        chefPhone = findViewById(R.id.chef_phone);
        message = findViewById(R.id.message);
        delTime = findViewById(R.id.delivery_time);
        delStatus = findViewById(R.id.delivery_status);
        confirm = findViewById(R.id.confirm_delivery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        model = ViewModelProviders.of(this).get(CustOrderDetailsVM.class);
        final Intent intent = getIntent();
        orderIDStr = intent.getStringExtra("orderID");
        chefID = intent.getStringExtra("chefID");
        custID = intent.getStringExtra("custID");
        orderID.setText(orderIDStr);
        model.getCustInfo(custID).observe(CustOrderDetails.this, new Observer<CustomerAccountSettings>() {
            @Override
            public void onChanged(CustomerAccountSettings settings) {
                cust = settings;
                chefName.setText(settings.getDisplayName());
                chefPhone.setText(settings.getPhoneNumber());
            }
        });
        model.getChefInfo(chefID).observe(CustOrderDetails.this, new Observer<ChefAccountSettings>() {
            @Override
            public void onChanged(ChefAccountSettings settings) {
                chef = settings;
            }
        });
        model.getOrder(orderIDStr, chefID, custID).observe(this, new Observer<OrderPojo>() {
            @Override
            public void onChanged(OrderPojo orderPojo) {

                progressBar.setVisibility(View.GONE);
                orderTime.setText(orderPojo.getOrderTime());
                itemNum.setText(orderPojo.getItems().size() + itemLang);
                total.setText(orderPojo.getTotal()+priceUnit);
                if (orderPojo.isDeliveryType()) {
                    deliveryType.setText(getString(R.string.door_delivery));
                    if (cust != null) {
                        delAddress.setText(cust.getAddress());
                    }
                } else {
                    if (chef != null) {
                        delAddress.setText(chef.getAddress());
                    }
                    deliveryType.setText(getString(R.string.pickup_order));
                }
                if (!orderPojo.isChefConfirm()) {
                    confirm.setEnabled(false);
                    confirm.setBackgroundResource(R.drawable.rect_gray);
                } else {
                    confirm.setEnabled(true);
                    confirm.setBackgroundResource(R.drawable.rect);
                }
                shippingFee.setText(orderPojo.getDeliveryFee() + priceUnit);
                if (!orderPojo.isCustConfirm() && !orderPojo.isChefConfirm()) {
                    delStatus.setText(status_not_del);
                } else if (orderPojo.isChefConfirm() && !orderPojo.isCustConfirm()) {
                    delStatus.setText(status_on_way);
                } else if (orderPojo.isCustConfirm() && orderPojo.isChefConfirm()) {
                    delStatus.setText(status_del + orderPojo.getDeliveryTime());
                }
                if (orderPojo.isCustConfirm()) {
                    confirm.setEnabled(false);
                    confirm.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    confirm.setBackground(null);
                    confirm.setText(confirmDel);
                    confirm.setTextColor(Color.GREEN);

                }
                delTime.setText(orderPojo.getDeliveryTime());
                myAdapter = new CustOrderItemAdapter(orderPojo.getItems(), CustOrderDetails.this);
                recyclerView.setAdapter(myAdapter);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMsg = new Intent(CustOrderDetails.this, MessageActivity.class);
                goToMsg.putExtra("userid", chefID);
                startActivity(goToMsg);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.setEnabled(false);
                confirm.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                confirm.setBackground(null);
                confirm.setText(confirmDel);
                confirm.setTextColor(Color.GREEN);
                model.updateOrderCustConfirm(orderIDStr, chefID, custID);
                RatingDialog ratingDialog = new RatingDialog(chefID,custID);
                ratingDialog.show(getSupportFragmentManager(),"Rating Dialog");
            }
        });

    }
}
