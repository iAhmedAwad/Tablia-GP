//package iti.team.tablia.ChefHome.TabBar.Profile.ChefFollowers;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import iti.team.tablia.R;
//
//public class ChefFollowersList extends AppCompatActivity {
//RecyclerView myRecycler ;
//String chefId;
//ChefFollowersListAdaptor adaptor;
//ChefFollowersViewModel chefFollowersViewModel;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chef_followers_list);
//        myRecycler = findViewById(R.id.xRecyclerFollowers);
//        Intent intent = getIntent();
//        chefId = intent.getStringExtra("chefId");
//        myRecycler.setHasFixedSize(true);
//        myRecycler.setLayoutManager(new LinearLayoutManager(this));
//    }
//}
