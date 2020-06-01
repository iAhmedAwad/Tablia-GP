package iti.team.tablia.CustomerAccount.Home.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.Customer.ChefProfile.ViewChiefProfileActivity;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.R;


public class TopChefRecyclerAdaptor extends RecyclerView.Adapter<TopChefRecyclerAdaptor.ViewHolder> {
  Context context;
  // List<ChatUser> arrName;
  Bitmap bitmap;
  private List<ChatUser> users;

  public TopChefRecyclerAdaptor(Context context, List<ChatUser> users) {
    this.context = context;
    this.users = users;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_chef_row, viewGroup, false));
  }


  @Override
  public void onBindViewHolder(TopChefRecyclerAdaptor.ViewHolder viewHolder, int i) {
    final ChatUser user = users.get(i);
    String strToConvert = users.get(i).getImageURL();

    viewHolder.image.setImageBitmap(StringToBitMap(strToConvert));
    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(context, ViewChiefProfileActivity.class);
        intent.putExtra("userid", user.getId());
        context.startActivity(intent);
      }
    });
  }

  public Bitmap StringToBitMap(String encodedString) {
    try {
      byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
      bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
          encodeByte.length);
      return bitmap;
    } catch (Exception e) {
      e.getMessage();
      return null;
    }
  }

  @Override
  public int getItemCount() {
    if (users.size() > 5) {
      return 5;
    } else {
      return users.size();
    }

  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    CircleImageView image;


    public ViewHolder(View itemView) {
      super(itemView);
      image = itemView.findViewById(R.id.id_profile_pic);

    }
  }
}
