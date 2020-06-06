package iti.team.tablia.ChefHome.TabBar.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.ChefHome.TabBar.Profile.Reviews.ChefReviewChefAcc;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.Models.Chef.ChefSettings;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;
import iti.team.tablia.util.GlobalImageLoader;


public class profileFragment extends Fragment {
    TextView name, nb_followers, nb_orders, address, reviews, menus, bio, phone;
    RatingBar rating;
    CircleImageView prfo_img;
    ImageView edit;
    CircleImageView isAavaliable;
    Bitmap bitmap;
    ProgressBar progressBar;
    private static final String TAG = "profileFragment";
    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Database database;
  FirebaseUser muser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_chief, container, false);
        progressBar =view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        name = view.findViewById(R.id.id_name);
        prfo_img = view.findViewById(R.id.id_profile_pic);
        bio = view.findViewById(R.id.id_description);
        phone = view.findViewById(R.id.id_phone);
        nb_followers = view.findViewById(R.id.id_nb_followers);
        nb_orders = view.findViewById(R.id.id_nb_orders);
        rating = view.findViewById(R.id.id_rating);
        address = view.findViewById(R.id.id_address);
        isAavaliable = view.findViewById(R.id.online_status);
        edit = view.findViewById(R.id.id_edit);
        menus = view.findViewById(R.id.id_visit_menu);
        reviews = view.findViewById(R.id.id_view_reviews);

//        nb_followers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ChefFollowersList.class);
//                intent.putExtra("chefId",muser.getUid());
//                startActivity(intent);
//            }
//        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(getContext(), ChefReviewChefAcc.class);
              intent.putExtra("chefId",muser.getUid());
              startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditChiefActivity.class);
                startActivity(intent);
            }
        });
        database = new Database(getActivity());
        setupFirebaseAuth();

        return view;

    }

    /*
       ----------------------------- Firebase setup ---------------------------------
    */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
             muser = firebaseAuth.getCurrentUser();
                if (muser != null) {
                    //user is signed in
                    Log.d(TAG, "onAuthStateChanged: signed_in" + muser.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
                // ...
            }
        };

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //retrieve user information from database
                setProfileWidgets(database.getChefSettings(dataSnapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setProfileWidgets(ChefSettings chefSettings) {
        progressBar.setVisibility(View.GONE);

        ChefAccountSettings settings = chefSettings.getChefAccountSettings();
        address.setText(settings.getAddress());
        name.setText(settings.getDisplayName());
        nb_orders.setText(String.valueOf(settings.getOrders()));
        bio.setText(String.valueOf(settings.getBio()));
        phone.setText(String.valueOf(settings.getPhoneNumber()));
        nb_followers.setText(String.valueOf(settings.getFollowers()));
        bitmap = GlobalImageLoader.StringToBitMap(settings.getProfilePhoto());
        prfo_img.setImageBitmap(bitmap);
        rating.setRating(settings.getRating());

        if (settings.isAvailable() == true) {
            isAavaliable.setImageResource(R.drawable.online);
        } else {
            isAavaliable.setImageResource(R.drawable.offlinee);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}
