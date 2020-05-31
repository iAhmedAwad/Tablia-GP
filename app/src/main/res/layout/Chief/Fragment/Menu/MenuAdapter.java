package com.awad.tablia.ChefHome.TabBar.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awad.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import com.awad.tablia.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

  private List<MenuPojo> data;
  Context context;

  public MenuAdapter(List<MenuPojo> data, Context context) {
    this.data = data;
    this.context = context;
  }

  @NonNull
  @Override
  public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    View v = inflater.inflate(R.layout.row_menu, parent, false);

    return new ViewHolder(v);

  }

  @Override
  public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {

    holder.imgItem.setImageBitmap(data.get(position).getImgItem());
    holder.txtNameItem.setText(data.get(position).getNameItem());
    holder.txtPriceItem.setText(data.get(position).getPriceItem());

  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imgItem;
    TextView txtNameItem, txtPriceItem;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      imgItem = itemView.findViewById(R.id.imgItem);
      txtNameItem = itemView.findViewById(R.id.txtNameItem);
      txtPriceItem = itemView.findViewById(R.id.txtPriceItem);

    }
  }
}
