package iti.team.tablia.ChefHome.TabBar.Chat.Messages;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Locale;

import iti.team.tablia.Models.Chat;
import iti.team.tablia.R;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
  public static final int MSG_TYPE_LEFT = 0;
  public static final int MSG_TYPE_RIGHT = 1;
  private Context context;
  private List<Chat> chats;
  private String imageurl;
  FirebaseUser firebaseUser;


  public MessageAdapter(Context context, List<Chat> chats, String imageurl) {
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    this.context = context;
    this.chats = chats;
    this.imageurl = imageurl;

  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    if (viewType == MSG_TYPE_RIGHT) {
      View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
      return new ViewHolder(view);
    } else {
      View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
      return new ViewHolder(view);
    }

  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Chat chat = chats.get(position);
    holder.show_message.setText(chat.getMessage());
    if (imageurl.contains("https")) {
      Glide.with(context).load(imageurl).into(holder.profile_image);
    } else {
      Bitmap bitmap = StringToBitMap(imageurl);
      holder.profile_image.setImageBitmap(bitmap);
    }
    if (position == chats.size() - 1) {
      if (chat.isSeen()) {
        if(Locale.getDefault().getLanguage().equals("ar")){
          holder.seen.setText("تمت المشاهدة");
        }else{
          holder.seen.setText("Seen");
        }
      } else {
        if(Locale.getDefault().getLanguage().equals("ar")){
          holder.seen.setText("تم التوصيل");
        }else {
          holder.seen.setText("Delivered");
        }
      }
    } else {
      holder.seen.setVisibility(View.GONE);
    }

  }


  @Override
  public int getItemCount() {
    return chats.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView show_message;
    public ImageView profile_image;
    public TextView seen;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      show_message = itemView.findViewById(R.id.show_message);
      profile_image = itemView.findViewById(R.id.profile_image);
      seen = itemView.findViewById(R.id.seen);
    }

  }

  @Override
  public int getItemViewType(int position) {
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    if (chats.get(position).getSender().equals(firebaseUser.getUid())) {
      return MSG_TYPE_RIGHT;
    } else {
      return MSG_TYPE_LEFT;
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
