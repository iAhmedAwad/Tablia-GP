package iti.team.tablia.CustomerAccount.CustomerOrder;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.Models.CartGroupPojo;
import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
  private Context context;
  private List<CartGroupPojo> orders;
  private double subTotal = 0;
  private Cart cartActivity;

  public CartAdapter(Context context, List<CartGroupPojo> orders) {
    this.context = context;
    this.orders = orders;
    cartActivity = (Cart) context;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.cart_row, parent, false);
    return new CartAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
    final CartGroupPojo order = orders.get(position);
    final String chefName = order.getCartPojos().get(0).getChefName();
    if (Locale.getDefault().getLanguage().equals("ar")){
      holder.title.setText("طلباتك من الشيف " + chefName);
    }else {
      holder.title.setText("Your Order from chef " + chefName);
    }
    holder.itemRecyclerView.setHasFixedSize(true);
    holder.itemRecyclerView.setLayoutManager(new LinearLayoutManager(context));


    for (CartPojo pojo : order.getCartPojos()) {
      subTotal += (pojo.getQuantity() * pojo.getItemPrice());
    }
    String lang = Locale.getDefault().getLanguage();
    if(lang.equals("ar")){
      holder.subTotal.setText(subTotal+" ج.م");

    }else {
      holder.subTotal.setText(subTotal+" EGP");
    }
    ItemOrderAdapter itemOrderAdapter = new ItemOrderAdapter(order.getCartPojos(), context, holder.subTotal);
    holder.itemRecyclerView.setAdapter(itemOrderAdapter);

    holder.confirm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String str = holder.subTotal.getText().toString();
        String[] priceArr = str.split(" ");
        double total = Double.parseDouble(priceArr[0]);
        OrderPojo orderPojo = new OrderPojo(order.getChefId(), chefName, total, order.getCartPojos());
        CompleteOrder.orderPojo = orderPojo;
        Intent intent = new Intent(context, CompleteOrder.class);
        context.startActivity(intent);
        Toast.makeText(context, "confirm order", Toast.LENGTH_SHORT).show();
      }
    });
    holder.subTotal.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str = s.toString();
        String[] priceArr = str.split(" ");
        double price = Double.parseDouble(priceArr[0]);
        if (price <= 0) {
          orders.remove(order);
          notifyItemRemoved(position);
          notifyItemRangeChanged(position, orders.size());
          holder.itemView.setVisibility(View.GONE);
        }

      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });


  }


  @Override
  public int getItemCount() {
    return orders.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public RecyclerView itemRecyclerView;
    public TextView subTotal;
    public Button confirm;
    public ImageView expand;
    public CardView expandedArea;
    public CardView titleArea;
    public Animation animationUp, animationDown;
    private final int COUNTDOWN_RUNNING_TIME = 500;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.titleTextView);
      itemRecyclerView = itemView.findViewById(R.id.item_order_recyclerView);
      subTotal = itemView.findViewById(R.id.sub_total);
      confirm = itemView.findViewById(R.id.complete_order);
      expand = itemView.findViewById(R.id.expand_more);
      expandedArea = itemView.findViewById(R.id.expandable_layout);
      titleArea = itemView.findViewById(R.id.title);
      animationUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
      animationDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
      titleArea.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (expandedArea.isShown()) {
            expandedArea.startAnimation(animationUp);
            CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
              @Override
              public void onTick(long millisUntilFinished) {
              }

              @Override
              public void onFinish() {
                expandedArea.setVisibility(View.GONE);
                expand.setImageResource(R.drawable.ic_expand_more);
              }
            };
            countDownTimerStatic.start();

          } else {

            expandedArea.setVisibility(View.VISIBLE);
            expandedArea.startAnimation(animationDown);
            expand.setImageResource(R.drawable.ic_collaps);

          }

        }
      });
    }
  }
}
