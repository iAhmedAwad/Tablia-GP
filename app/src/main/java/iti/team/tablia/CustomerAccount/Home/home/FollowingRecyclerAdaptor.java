package iti.team.tablia.CustomerAccount.Home.home;

//public class FollowingRecyclerAdaptor extends RecyclerView.Adapter<FollowingRecyclerAdaptor.ViewHolder> {
//    Context context;
//    ArrayList<Following> arrName;
//    Bitmap bitmap;
//
//    public FollowingRecyclerAdaptor(Context context, ArrayList<Following> arrName) {
//        this.context = context;
//        this.arrName = arrName;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_chef_row, viewGroup, false));
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int i) {
//
//        final String id = arrName.get(i).getChefId();
//        viewHolder.image.setImageBitmap(GlobalImageLoader.StringToBitMap(arrName.get(i).getProfilePhoto()));
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ViewChiefProfileActivity.class);
//                intent.putExtra("userid", id);
//                context.startActivity(intent);
//            }
//        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//       return arrName.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        CircleImageView image;
//
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.id_profile_pic);
//
//        }
//    }
//}
