package iti.team.tablia.CustomerAccount.MyOrders;

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
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Order.OrderAdapter;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.CustomerAccount.MyOrders.CustOrderDetails.CustOrderDetails;
import iti.team.tablia.R;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {
    private String priceUnit;
    private List<OrderPojo> orderPojos;
    private Context context;

    public MyOrdersAdapter(List<OrderPojo> orderPojos, Context context) {
        this.orderPojos = orderPojos;
        this.context = context;
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("ar")) {
            priceUnit = " ج.م";

        } else {
            priceUnit = " EGP";
        }
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
        final OrderPojo orderPojo = orderPojos.get(position);
        holder.txtOrderTime.setText(orderPojo.getOrderTime());
        holder.txtChefName.setText(orderPojo.getChefName());
        holder.txtPrice.setText(String.valueOf(orderPojo.getTotal()+priceUnit));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToOrderDeatils = new Intent(context, CustOrderDetails.class);
                goToOrderDeatils.putExtra("orderID",orderPojo.getOrderID());
                goToOrderDeatils.putExtra("chefID",orderPojo.getChefID());
                goToOrderDeatils.putExtra("custID",orderPojo.getCustomerID());
                context.startActivity(goToOrderDeatils);

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
