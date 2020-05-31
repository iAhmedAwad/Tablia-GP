package iti.team.tablia.CustomerAccount.Home.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
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
//        ArrayList<MenuPojo> namesList = new ArrayList<>(arrName);
//
////        viewHolder.image.setImageBitmap(GlobalImageLoader.StringToBitMap(namesList.get(i).getImgItem(0));
////
//        Iterator iterator = arrName.iterator();
//
//        while (iterator.hasNext()){
//
////            viewHolder.image.setImageBitmap(GlobalImageLoader.StringToBitMap(arrName.iterator().next().getImgItem());
//        }

        Log.d("why",arrName.get(position).getImgItem().get(0).getImgaeItem());
        viewHolder.image.setImageBitmap(GlobalImageLoader.StringToBitMap(arrName.get(position).getImgItem().get(0).getImgaeItem()));
    }



    @Override
    public int getItemCount() {
     return    arrName.size();
//        if(arrName.size() > 5){
//            return 5 ;
//        }else{
//            return  arrName.size();
//        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.id_profile_pic);

        }
    }
}
