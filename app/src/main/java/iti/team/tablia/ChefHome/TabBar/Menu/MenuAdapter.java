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
import java.util.Locale;

import iti.team.tablia.ChefHome.ChefItemDetails;
import iti.team.tablia.ChefHome.EditMenuItems;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.R;
import iti.team.tablia.util.Constants;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private String priceUnit;
    private List<MenuPojo> data;
    Context context;

    public MenuAdapter(List<MenuPojo> data, Context context) {
        this.data = data;
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
        holder.txtPriceItem.setText(String.valueOf(data.get(position).getPriceItem() + priceUnit));

        String cat = getCategory(data.get(position).getCategory());
        holder.txtCategory.setText(cat);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditMenuItems.menuPojo = menuPojo;
                ChefItemDetails.menuPojo = menuPojo;
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
