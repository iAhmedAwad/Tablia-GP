package iti.team.tablia.CustomerAccount.Categories.Seafood;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.CustomerAccount.Items.ItemDetails;
import iti.team.tablia.R;
import iti.team.tablia.util.GlobalImageLoader;

public class SeafoodAdapter extends RecyclerView.Adapter<SeafoodAdapter.ViewHolder> {
  private Context mContext;
  private ArrayList<MenuPojo> menuPojos;

  public SeafoodAdapter(Context context, ArrayList<MenuPojo> menuPojos) {
    this.mContext = context;
    this.menuPojos = menuPojos;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cat_row, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final MenuPojo menuPojo = menuPojos.get(position);

    holder.xCatItemName.setText(menuPojos.get(position).getItemName());
    holder.xCatItemCategory.setText(menuPojos.get(position).getCategory());
    holder.xCatItemPrice.setText(String.valueOf(menuPojos.get(position).getPriceItem())+" EGP");
    holder.cat_imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    holder.cat_imageView.setImageBitmap(
        GlobalImageLoader.StringToBitMap(
            menuPojos.get(position).getImgItem().get(0).getImgaeItem()));

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(mContext, ItemDetails.class);
        intent.putExtra("chefId", menuPojo.getChefID());
        intent.putExtra("itemId", menuPojo.getItemID());
        intent.putExtra("itemName", menuPojo.getItemName());
        mContext.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {

    return menuPojos.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView xCatItemName, xCatItemPrice, xCatItemCategory;
    private ImageView cat_imageView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      xCatItemName = itemView.findViewById(R.id.xCatItemName);
      xCatItemCategory = itemView.findViewById(R.id.xCatItemCategory);
      xCatItemPrice = itemView.findViewById(R.id.xCatItemPrice);
      cat_imageView = itemView.findViewById(R.id.cat_imageView);
    }
  }
}
