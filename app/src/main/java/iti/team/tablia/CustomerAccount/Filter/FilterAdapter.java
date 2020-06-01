package iti.team.tablia.CustomerAccount.Filter;

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

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
  private ArrayList<MenuPojo> mList;
  private Context mContext;

  public FilterAdapter(Context context, ArrayList<MenuPojo> filteredDataList) {
    this.mContext = context;
    this.mList = filteredDataList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(mContext)
        .inflate(R.layout.cat_row, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    final MenuPojo menuPojo = mList.get(position);

    holder.xCatItemName.setText(menuPojo.getItemName());
    holder.xCatItemCategory.setText(menuPojo.getCategory());
    holder.xCatItemPrice.setText(String.valueOf(menuPojo.getPriceItem())+"EGP");
    holder.cat_imageView.setImageBitmap(
        GlobalImageLoader.StringToBitMap(
            menuPojo.getImgItem().get(0).getImgaeItem()));

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
    return mList.size();
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
