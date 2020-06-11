package iti.team.tablia.CustomerAccount.MyOrders.CustOrderDetails;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import iti.team.tablia.CustomerAccount.Items.ItemDetails;
import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.R;

public class CustOrderItemAdapter extends RecyclerView.Adapter<CustOrderItemAdapter.ViewHolder> {
    private String priceUnit;
    private List<CartPojo> cartPojos;
    Context context;

    public CustOrderItemAdapter(List<CartPojo> cartPojos, Context context) {
        this.cartPojos = cartPojos;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.row_order_deatils, parent, false);

        return new CustOrderItemAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CartPojo cartPojo = cartPojos.get(position);
        Bitmap bitmap = stringToBitMap(cartPojo.getImg());
        holder.itemImg.setImageBitmap(bitmap);
        holder.txtNameItem.setText(cartPojo.getItemName());
        holder.txtQuantity.setText(cartPojo.getQuantity() + "");
        holder.txtSubTotal.setText((cartPojo.getItemPrice() * cartPojo.getQuantity()) + priceUnit);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetails.class);
                intent.putExtra("chefId", cartPojo.getChefID());
                intent.putExtra("itemId", cartPojo.getItemID());
                intent.putExtra("itemName", cartPojo.getItemName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameItem, txtQuantity, txtSubTotal;
        ImageView itemImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.itemImg);
            txtNameItem = itemView.findViewById(R.id.txtNameItem);
            txtQuantity = itemView.findViewById(R.id.qty);
            txtSubTotal = itemView.findViewById(R.id.Subtotal);


        }
    }

    public Bitmap stringToBitMap(String encodedString) {
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
