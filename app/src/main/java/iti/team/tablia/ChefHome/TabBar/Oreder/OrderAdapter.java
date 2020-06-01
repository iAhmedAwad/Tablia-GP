package iti.team.tablia.ChefHome.TabBar.Oreder;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Order.OrderDeatils.OrderDeatils;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.ChefHome.TabBar.Order.OrderViewModel;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

  private List<OrderPojo> data;
  private Context context;
  private OrderViewModel orderViewModel;

  public OrderAdapter(List<OrderPojo> data, Context context, OrderViewModel orderViewModel) {
    this.data = data;
    this.context = context;
    this.orderViewModel = orderViewModel;
  }

  @NonNull
  @Override
  public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    View v = inflater.inflate(R.layout.row_order, parent, false);

    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull final OrderAdapter.ViewHolder holder, final int position) {

    holder.txtOrderTime.setText(data.get(position).getOrderTime());
    orderViewModel.getCustInfo(data.get(position).getCustomerID()).observe((LifecycleOwner) context, new Observer<CustomerAccountSettings>() {
      @Override
      public void onChanged(CustomerAccountSettings customerAccountSettings) {
        holder.txtCustomerID.setText(customerAccountSettings.getDisplayName());
      }
    });
    holder.txtPrice.setText(String.valueOf(data.get(position).getTotal()));
    holder.con.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent goToOrderDeatils = new Intent(context, OrderDeatils.class);
        context.startActivity(goToOrderDeatils);

      }
    });

  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    TextView txtCustomerID, txtOrderTime, txtPrice;
    ConstraintLayout con;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      txtCustomerID = itemView.findViewById(R.id.customerID);
      txtOrderTime = itemView.findViewById(R.id.orderTime);
      txtPrice = itemView.findViewById(R.id.price);
      con = itemView.findViewById(R.id.conID);
    }
  }
}

