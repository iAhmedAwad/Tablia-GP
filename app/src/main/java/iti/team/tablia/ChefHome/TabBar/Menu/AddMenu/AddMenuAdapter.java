package iti.team.tablia.ChefHome.TabBar.Menu.AddMenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.R;

public class AddMenuAdapter extends RecyclerView.Adapter<AddMenuAdapter.ViewHolder> {

  private List<MenuItemPojo> data;
  Context context;

  public AddMenuAdapter(List<MenuItemPojo> data, Context context) {
    this.data = data;
    this.context = context;
  }

  @NonNull
  @Override
  public AddMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    View v = inflater.inflate(R.layout.row_images, parent, false);

    return new ViewHolder(v);

  }

  @Override
  public void onBindViewHolder(@NonNull AddMenuAdapter.ViewHolder holder, final int position) {

    Bitmap bitmap = StringToBitMap(data.get(position).getImgaeItem());

    holder.imgItem.setImageBitmap(bitmap);
    holder.btnClose.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(context,"Remove",Toast.LENGTH_LONG).show();
        data.remove(data.get(position));
        notifyDataSetChanged();

      }
    });

  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    CircleImageView imgItem , btnClose;


    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      imgItem = itemView.findViewById(R.id.imgItem);
      btnClose = itemView.findViewById(R.id.btnClose);


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
