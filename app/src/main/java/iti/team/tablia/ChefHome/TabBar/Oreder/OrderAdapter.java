package iti.team.tablia.ChefHome.TabBar.Oreder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Oreder.OrderDeatils.OrderDeatils;
import iti.team.tablia.R;

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
    holder.txtPrice.setText(String.valueOf(data.get(position).getSubTotal()));
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

    TextView txtCustomerID, txtOrderID, txtOrderTime, txtPrice;
    ConstraintLayout con;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      txtCustomerID = itemView.findViewById(R.id.customerID);
      txtOrderID = itemView.findViewById(R.id.orderID);
      txtOrderTime = itemView.findViewById(R.id.orderTime);
      txtPrice = itemView.findViewById(R.id.price);
      con = itemView.findViewById(R.id.conID);
    }
  }
}


