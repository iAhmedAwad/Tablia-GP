package com.awad.tablia.ChefHome.TabBar.Oreder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awad.tablia.R;

import java.util.List;

import Models.OrderPojo;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

  private List<OrderPojo> data;
  Context context;

  public OrderAdapter(List<OrderPojo> data, Context context) {
    this.data = data;
    this.context = context;
  }

  @NonNull
  @Override
  public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    View v = inflater.inflate(R.layout.row_order, parent, false);

    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

    holder.txtOrderID.setText(data.get(position).getOrderID());
    holder.txtOrderTime.setText(data.get(position).getOrderTime());
    holder.txtCustomerID.setText(data.get(position).getCustomerID());
    holder.txtPrice.setText(data.get(position).getPrice());
    if (data.get(position).isCheck() == true) {
      holder.imgCheck.setImageResource(R.drawable.ic_check_circle_true_black_24dp);
    } else {
      holder.imgCheck.setImageResource(R.drawable.ic_cancel_black_24dp);
    }
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    TextView txtCustomerID, txtOrderID, txtOrderTime, txtPrice;
    ImageView imgCheck;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      txtCustomerID = itemView.findViewById(R.id.customerID);
      txtOrderID = itemView.findViewById(R.id.orderID);
      txtOrderTime = itemView.findViewById(R.id.orderTime);
      txtPrice = itemView.findViewById(R.id.price);
      imgCheck = itemView.findViewById(R.id.imgCheck);
    }
  }
}


