package iti.team.tablia.CustomerAccount.MyOrders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Order.OrderAdapter;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.R;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {
    private List<OrderPojo> orderPojos;
    private Context context;

    public MyOrdersAdapter(List<OrderPojo> orderPojos, Context context) {
        this.orderPojos = orderPojos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cust_row_order, parent, false);
        return new MyOrdersAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtOrderTime.setText(orderPojos.get(position).getOrderTime());
        holder.txtChefName.setText(orderPojos.get(position).getChefName());
        holder.txtPrice.setText(String.valueOf(orderPojos.get(position).getTotal()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent goToOrderDeatils = new Intent(context, OrderDeatils.class);
//                context.startActivity(goToOrderDeatils);

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderPojos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtChefName, txtOrderTime, txtPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtChefName = itemView.findViewById(R.id.customerID);
            txtOrderTime = itemView.findViewById(R.id.orderTime);
            txtPrice = itemView.findViewById(R.id.price);
        }
    }
}
