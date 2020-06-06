package iti.team.tablia.CustomerAccount.FollowingActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.Customer.ChefProfile.ViewChiefProfileActivity;
import iti.team.tablia.Models.Following;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;
import iti.team.tablia.util.GlobalImageLoader;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {

  private ArrayList<Following> mList;
  private Context mContext;
  private Database mDatabase;
  private FollowingActivityViewModel mViewModel;

  public FollowingAdapter(ArrayList<Following> list, Context context) {
    mList = list;
    mContext = context;
    mDatabase = new Database();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    mViewModel = new ViewModelProvider((ViewModelStoreOwner) mContext)
        .get(FollowingActivityViewModel.class);

    return new ViewHolder(LayoutInflater.from(mContext)
        .inflate(R.layout.following_row, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

    final String id = mList.get(position).getChefId();
    //TODO make sure button responds right
    holder.xFollowingRowFullName.setText(mList.get(position).getFullName());
//    holder.xFollowingRowUsername.setText(mList.get(position).getUserName());
    holder.xFollowingRowBio.setText(mList.get(position).getBio());
    Bitmap b = GlobalImageLoader.StringToBitMap(mList.get(position).getProfilePhoto());
    holder.xFollowingRowImageView.setImageBitmap(b);

    mViewModel.isFollowing(id).observe((LifecycleOwner) mContext, new Observer<Boolean>() {
      @Override
      public void onChanged(Boolean aBoolean) {

        if (!aBoolean) {
          holder.xFollowingRowButton.setChecked(false);
          Log.d("Toggle", "I am true");
        } else {
          holder.xFollowingRowButton.setChecked(true);
          Log.d("Toggle", "I am false");
        }
      }
    });


    holder.xFollowingRowButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!buttonView.isChecked()) {
          mDatabase.unfollowChef(id);

        } else {
          Log.d("Toggle", "followed");
          mDatabase.followChef(id);
        }
      }
    });


    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(mContext, ViewChiefProfileActivity.class);
        Toast.makeText(mContext, id, Toast.LENGTH_SHORT).show();
        intent.putExtra("userid", id);
        mContext.startActivity(intent);
      }
    });

  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private CircleImageView xFollowingRowImageView;
    private TextView xFollowingRowFullName, xFollowingRowBio;
    private ToggleButton xFollowingRowButton;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      xFollowingRowImageView = itemView.findViewById(R.id.xFollowerRowImageView);
      xFollowingRowFullName = itemView.findViewById(R.id.xFollowerRowFullName);
//      xFollowingRowUsername = itemView.findViewById(R.id.xFollowerRowUsername);
      xFollowingRowBio = itemView.findViewById(R.id.xFollowerRowBio);
      xFollowingRowButton = itemView.findViewById(R.id.xFollowingRowButton);
    }
  }


}
