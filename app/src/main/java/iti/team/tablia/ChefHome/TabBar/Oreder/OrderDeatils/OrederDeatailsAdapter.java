package iti.team.tablia.ChefHome.TabBar.Oreder.OrderDeatils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.team.tablia.R;

public class OrederDeatailsAdapter extends RecyclerView.Adapter<OrederDeatailsAdapter.ViewHolder> {

  private List<ItemOrderPojo> data;
  Context context;

  public OrederDeatailsAdapter(List<ItemOrderPojo> data, Context context) {
    this.data = data;
    this.context = context;
  }

  @NonNull
  @Override
  public OrederDeatailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    View v = inflater.inflate(R.layout.row_order_deatils, parent, false);

    return new OrederDeatailsAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull OrederDeatailsAdapter.ViewHolder holder, int position) {

    holder.txtNameItem.setText(data.get(position).getItemName());
    holder.txtQuantity.setText(data.get(position).getQuantity());
    holder.txtSubTotal.setText(data.get(position).getItemPrice() + " EGP");

  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    TextView txtNameItem, txtQuantity, txtSubTotal;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      txtNameItem = itemView.findViewById(R.id.txtNameItem);
      txtQuantity = itemView.findViewById(R.id.txtQuantity);
      txtSubTotal = itemView.findViewById(R.id.txtSubTotal);


    }
  }
}


