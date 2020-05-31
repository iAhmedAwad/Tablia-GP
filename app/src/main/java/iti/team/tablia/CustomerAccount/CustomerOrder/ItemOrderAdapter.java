package iti.team.tablia.CustomerAccount.CustomerOrder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.R;


public class ItemOrderAdapter extends RecyclerView.Adapter<ItemOrderAdapter.ViewHolder> {
  private List<CartPojo> items;
  private Context context;
  private TextView subTotal;
  private Cart cartActivity;
  private int _qty = 0;

  public ItemOrderAdapter(List<CartPojo> items, Context context, TextView subTotal) {
    this.items = items;
    this.context = context;
    this.subTotal = subTotal;
    cartActivity = (Cart) context;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_order_row, parent, false);
    return new ItemOrderAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
    final CartPojo item = items.get(position);
    Bitmap bitmap = stringToBitMap(item.getImg());
    holder.itemImg.setImageBitmap(bitmap);
    holder.itemName.setText(item.getItemName());
    holder.price.setText(item.getItemPrice() + " EGP");
    holder.qty.setText(item.getQuantity() + "");
    holder.minusQty.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        _qty = Integer.parseInt(holder.qty.getText().toString());
        if (_qty > 1) {
          _qty--;
          String[] itemPrice = subTotal.getText().toString().split(" ");
          double price = Double.parseDouble(itemPrice[0]);
          price -= item.getItemPrice();
          subTotal.setText(price + " EGP");
        }
        item.setQuantity(_qty);
        holder.qty.setText(_qty + "");
        cartActivity.cartViewModel.updateCart(item);
      }
    });
    holder.plusQty.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        _qty = Integer.parseInt(holder.qty.getText().toString());
        _qty++;
        holder.qty.setText(_qty + "");
        String[] itemPrice = subTotal.getText().toString().split(" ");
        double price = Double.parseDouble(itemPrice[0]);
        price += item.getItemPrice();
        subTotal.setText(price + " EGP");

        cartActivity.cartViewModel.updateCart(item).observe(cartActivity, new Observer<Boolean>() {
          @Override
          public void onChanged(Boolean aBoolean) {
            if (!aBoolean) {
              _qty--;
              Toast.makeText(context, "no more items available", Toast.LENGTH_SHORT).show();
            } else {
              item.setQuantity(_qty);
            }
          }
        });

      }
    });
    holder.deleteItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        double itemTotal = item.getItemPrice() * item.getQuantity();
        String[] itemPrice = subTotal.getText().toString().split(" ");
        double price = Double.parseDouble(itemPrice[0]);
        price -= itemTotal;
        subTotal.setText(price + " EGP");
        items.remove(item);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
        holder.itemView.setVisibility(View.GONE);
        cartActivity.cartViewModel.removeCartItem(item);
      }
    });

  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView itemImg;
    private TextView itemName;
    private Button minusQty;
    private Button plusQty;
    private TextView qty;
    private TextView price;
    private LinearLayout deleteItem;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      itemImg = itemView.findViewById(R.id.itemImg);
      itemName = itemView.findViewById(R.id.itemTitle);
      minusQty = itemView.findViewById(R.id.minus);
      plusQty = itemView.findViewById(R.id.plus);
      qty = itemView.findViewById(R.id.qty);
      price = itemView.findViewById(R.id.itemPrice);
      deleteItem = itemView.findViewById(R.id.delete_item);
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
