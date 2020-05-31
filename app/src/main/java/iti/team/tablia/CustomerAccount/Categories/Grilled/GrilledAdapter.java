package iti.team.tablia.CustomerAccount.Categories.Grilled;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.CustomerAccount.Items.ItemDetails;
import iti.team.tablia.R;

public class GrilledAdapter extends RecyclerView.Adapter<GrilledAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MenuPojo> list;
    public GrilledAdapter(Context context, ArrayList<MenuPojo> menuPojos) {
        this.mContext = context;
        this.list = menuPojos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.cat_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuPojo menuPojo = list.get(position);
        holder.xTextView.setText(menuPojo.getItemName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ItemDetails.class);
                intent.putExtra("chefId",menuPojo.getChefID());
                intent.putExtra("itemId",menuPojo.getItemID());
                intent.putExtra("itemName",menuPojo.getItemName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView xTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            xTextView = itemView.findViewById(R.id.xTextView);

        }
    }
}
