package iti.team.tablia.ChefHome.TabBar.Menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.team.tablia.ChefHome.ChefItemDetails;
import iti.team.tablia.ChefHome.EditMenuItems;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.R;

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
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, final int position) {

        final MenuPojo menuPojo = data.get(position);
        Bitmap bitmap = StringToBitMap(data.get(position).getImgItem().get(0).getImgaeItem());
        holder.imgItem.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.imgItem.setImageBitmap(bitmap);
        holder.txtNameItem.setText(data.get(position).getItemName());
        holder.txtPriceItem.setText(String.valueOf(data.get(position).getPriceItem()+" EGP"));
        holder.txtCategory.setText(data.get(position).getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditMenuItems.menuPojo = menuPojo;
                ChefItemDetails.menuPojo=menuPojo;
                Intent intent = new Intent(context, ChefItemDetails.class);
        intent.putExtra("chefId", menuPojo.getChefID());
        intent.putExtra("itemId", menuPojo.getItemID());
        intent.putExtra("itemName", menuPojo.getItemName());
//        intent.putExtra("pojoItem",menuPojo);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgItem;
        TextView txtNameItem, txtPriceItem, txtCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItem);
            txtNameItem = itemView.findViewById(R.id.txtNameItem);
            txtPriceItem = itemView.findViewById(R.id.txtPriceItem);
            txtCategory = itemView.findViewById(R.id.txtCategory);

        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
