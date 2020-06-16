package iti.team.tablia.ChefHome.TabBar.Order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Order.OrderDeatils.OrderDeatils;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private String priceUnit;
    private List<OrderPojo> orderPojos;
    private Context context;
    private OrderViewModel orderViewModel;

    public OrderAdapter(List<OrderPojo> orderPojos, Context context, OrderViewModel orderViewModel) {
        this.orderPojos = orderPojos;
        this.context = context;
        this.orderViewModel = orderViewModel;
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("ar")) {
            priceUnit = " ج.م";

        } else {
            priceUnit = " EGP";
        }
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
        final OrderPojo orderPojo = orderPojos.get(position);
        holder.txtOrderTime.setText(orderPojo.getOrderTime());
        orderViewModel.getCustInfo(orderPojo.getCustomerID()).observe((LifecycleOwner) context, new Observer<CustomerAccountSettings>() {
            @Override
            public void onChanged(CustomerAccountSettings customerAccountSettings) {
                holder.txtCustomerID.setText(customerAccountSettings.getDisplayName());
            }
        });
        holder.txtPrice.setText(String.valueOf(orderPojos.get(position).getTotal() + priceUnit));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToOrderDeatils = new Intent(context, OrderDeatils.class);
                goToOrderDeatils.putExtra("orderID", orderPojo.getOrderID());
                goToOrderDeatils.putExtra("chefID", orderPojo.getChefID());
                goToOrderDeatils.putExtra("custID", orderPojo.getCustomerID());
                context.startActivity(goToOrderDeatils);

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCustomerID, txtOrderTime, txtPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCustomerID = itemView.findViewById(R.id.customerID);
            txtOrderTime = itemView.findViewById(R.id.orderTime);
            txtPrice = itemView.findViewById(R.id.price);
        }
    }
}


