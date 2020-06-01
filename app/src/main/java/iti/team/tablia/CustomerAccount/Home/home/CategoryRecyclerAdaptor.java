package iti.team.tablia.CustomerAccount.Home.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.CustomerAccount.Items.ItemDetails;
import iti.team.tablia.R;
import iti.team.tablia.util.GlobalImageLoader;


public class CategoryRecyclerAdaptor extends RecyclerView.Adapter<CategoryRecyclerAdaptor.ViewHolder> {
    Context context;
    ArrayList<MenuPojo> arrName;


    public CategoryRecyclerAdaptor(Context context, ArrayList<MenuPojo> arrName) {
        this.context = context;
        this.arrName = arrName;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item_row, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final MenuPojo menuPojo = arrName.get(position);
        Log.d("why", arrName.get(position).getImgItem().get(0).getImgaeItem());
        viewHolder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.image.setImageBitmap(GlobalImageLoader.StringToBitMap(arrName.get(position).getImgItem().get(0).getImgaeItem()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetails.class);
                intent.putExtra("chefId", menuPojo.getChefID());
                intent.putExtra("itemId", menuPojo.getItemID());
                intent.putExtra("itemName", menuPojo.getItemName());
                context.startActivity(intent);
//                Toast.makeText(context, "Go to item", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrName.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.id_profile_pic);

        }
    }
}
