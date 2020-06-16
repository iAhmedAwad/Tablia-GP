package iti.team.tablia.CustomerAccount.Categories.General;


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
import java.util.Locale;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.CustomerAccount.Items.ItemDetails;
import iti.team.tablia.R;
import iti.team.tablia.util.Constants;
import iti.team.tablia.util.GlobalImageLoader;

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {
  private Context mContext;
  private ArrayList<MenuPojo> menuPojos;

  public GeneralAdapter(Context context, ArrayList<MenuPojo> menuPojos) {
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
    String cat = getCategory(menuPojos.get(position).getCategory());
    holder.xCatItemCategory.setText(cat);
    String lang = Locale.getDefault().getLanguage();
    if(lang.equals("ar")){
      holder.xCatItemPrice.setText(String.valueOf(menuPojos.get(position).getPriceItem())+" ج.م");

    }else {
      holder.xCatItemPrice.setText(String.valueOf(menuPojos.get(position).getPriceItem())+" EGP");
    }
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
  private String getCategory(String category) {
    String lang = Locale.getDefault().getLanguage();
    if (lang.equals("ar")) {
      if (category.equals(Constants.BACKING)) {
        category = Constants.BACKING_AR;
      } else if (category.equals(Constants.DESSERT)) {
        category = Constants.DESSERT_AR;
      } else if (category.equals(Constants.GRILLED)) {
        category = Constants.GRILLED_AR;
      } else if (category.equals(Constants.JUICE)) {
        category = Constants.JUICE_AR;
      } else if (category.equals(Constants.MACARONI)) {
        category = Constants.MACARONI_AR;
      } else if (category.equals(Constants.MAHASHY)) {
        category = Constants.MAHASHY_AR;
      } else if (category.equals(Constants.MAIN_DISHES)) {
        category = Constants.MAIN_DISHES_AR;
      } else if (category.equals(Constants.SALAD)) {
        category = Constants.SALAD_AR;
      } else if (category.equals(Constants.SEAFOOD)) {
        category = Constants.SEAFOOD_AR;
      } else if (category.equals(Constants.SIDE_DISHES)) {
        category = Constants.SIDE_DISHES_AR;
      } else if (category.equals(Constants.SOUPS)) {
        category = Constants.SOUPS_AR;
      }
    } else {
      if (category.equals(Constants.BACKING_AR)) {
        category = Constants.BACKING;
      } else if (category.equals(Constants.DESSERT_AR)) {
        category = Constants.DESSERT;
      } else if (category.equals(Constants.GRILLED_AR)) {
        category = Constants.GRILLED;
      } else if (category.equals(Constants.JUICE_AR)) {
        category = Constants.JUICE;
      } else if (category.equals(Constants.MACARONI_AR)) {
        category = Constants.MACARONI;
      } else if (category.equals(Constants.MAHASHY_AR)) {
        category = Constants.MAHASHY;
      } else if (category.equals(Constants.MAIN_DISHES_AR)) {
        category = Constants.MAIN_DISHES;
      } else if (category.equals(Constants.SALAD_AR)) {
        category = Constants.SALAD;
      } else if (category.equals(Constants.SEAFOOD_AR)) {
        category = Constants.SEAFOOD;
      } else if (category.equals(Constants.SIDE_DISHES_AR)) {
        category = Constants.SIDE_DISHES;
      } else if (category.equals(Constants.SOUPS_AR)) {
        category = Constants.SOUPS;
      }
    }
    return category;
  }

}
