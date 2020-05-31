package iti.team.tablia.CustomerAccount.Home.seeAll;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.Customer.ChefProfile.ViewChiefProfileActivity;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.R;


public class TopChefAdapter extends RecyclerView.Adapter<TopChefAdapter.ViewHolder> {
  private Context context;
  private List<ChatUser> users;
  Bitmap bitmap;

  public TopChefAdapter(Context context, List<ChatUser> users) {
    this.context = context;
    this.users = users;

  }

  @NonNull
  @Override
  public TopChefAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.top_chef_row, parent, false);
    return new TopChefAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final TopChefAdapter.ViewHolder holder, final int position) {

    final ChatUser user = users.get(position);
    holder.username.setText(user.getUsername());
    String strRetrieved = user.getImageURL();
    Bitmap display = StringToBitMap(strRetrieved);
    holder.image.setImageBitmap(display);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
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
    return users.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView username;
    public CircleImageView image;


    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      username = itemView.findViewById(R.id.id_Name);
      image = itemView.findViewById(R.id.id_profile_pic);


    }
  }

}
