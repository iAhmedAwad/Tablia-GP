//package iti.team.tablia.ChefHome.TabBar.Profile.ChefFollowers;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelStoreOwner;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.ArrayList;
//import de.hdodenhof.circleimageview.CircleImageView;
//import iti.team.tablia.CustomerAccount.FollowingActivity.FollowingActivityViewModel;
//import iti.team.tablia.Models.Following;
//import iti.team.tablia.R;
//import iti.team.tablia.others.Database;
//import iti.team.tablia.util.GlobalImageLoader;
//
//
//public class ChefFollowersListAdaptor extends RecyclerView.Adapter<ChefFollowersListAdaptor.ViewHolder> {
//
//    private ArrayList<Following> mList;
//    private Context mContext;
//    private Database mDatabase;
//    private FollowingActivityViewModel mViewModel;
//
//    public ChefFollowersListAdaptor(ArrayList<Following> list, Context context) {
//        mList = list;
//        mContext = context;
//        mDatabase = new Database();
//    }
//
//    @NonNull
//    @Override
//    public ChefFollowersListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        mViewModel = new ViewModelProvider((ViewModelStoreOwner) mContext)
//                .get(FollowingActivityViewModel.class);
//
//        return new ChefFollowersListAdaptor.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.follower_row, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ChefFollowersListAdaptor.ViewHolder holder, int position) {
//
//        final String id = mList.get(position).getChefId();
//        holder.xFollowerRowFullName.setText(mList.get(position).getFullName());
//        holder.xFollowerRowUsername.setText(mList.get(position).getUserName());
//        holder.xFollowerRowBio.setText(mList.get(position).getBio());
//        Bitmap b = GlobalImageLoader.StringToBitMap(mList.get(position).getProfilePhoto());
//        holder.xFollowerRowImageView.setImageBitmap(b);
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private CircleImageView xFollowerRowImageView;
//        private TextView xFollowerRowFullName, xFollowerRowUsername, xFollowerRowBio;
//
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            xFollowerRowImageView = itemView.findViewById(R.id.xFollowerRowImageView);
//            xFollowerRowFullName = itemView.findViewById(R.id.xFollowerRowFullName);
//            xFollowerRowUsername = itemView.findViewById(R.id.xFollowerRowUsername);
//            xFollowerRowBio = itemView.findViewById(R.id.xFollowerRowBio);
//
//        }
//    }
//
//
//}
