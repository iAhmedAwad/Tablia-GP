package iti.team.tablia.CustomerAccount.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.CustomerAccount.Filter.FilterActivity;
import iti.team.tablia.CustomerAccount.Profile.EditProfile.CustomerEditProfileFragment;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.Models.Customer.CustomerSettings;
import iti.team.tablia.Models.User;
import iti.team.tablia.R;
import iti.team.tablia.util.GlobalImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerProfileFragment extends Fragment {

    //Other members
    private static final String TAG = "CustomerProfileActivity";
    //Firebase
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    private FirebaseAuth mAuth;
//    private FirebaseDatabase mFirebaseDatabase;
//    private DatabaseReference mDatabaseReference;
//    private Database database;
    //Widgets
    private TextView xOrders, xFollowing, xCustomerName,
            xCustomerPhone, xCustomerAddress, xCustomerDescription;
    private CircleImageView xProfileImage;
    private Button xEditProfile, xTryFilters;

    public CustomerProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_profile, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");

        //database = new Database(getActivity());
        xOrders = view.findViewById(R.id.xOrders);
        xFollowing = view.findViewById(R.id.xFollowing);
        xCustomerName = view.findViewById(R.id.xCustomerName);
        xCustomerPhone = view.findViewById(R.id.xCustomerPhone);
        xCustomerAddress = view.findViewById(R.id.xCustomerAddress);
        xCustomerDescription = view.findViewById(R.id.xCustomerDescription);
        xProfileImage = view.findViewById(R.id.xprofile_image);
        xEditProfile = view.findViewById(R.id.xEditProfile);
        xTryFilters = view.findViewById(R.id.xTryFilters);
        //TM.log("Hello from" + TAG);
        initButton();
        //setupFirebaseAuth();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CustomerProfileViewModel model =
                ViewModelProviders.of(this).get(CustomerProfileViewModel.class);

        model.getCustomerSettings().observe(getViewLifecycleOwner(), new Observer<CustomerSettings>() {
            @Override
            public void onChanged(CustomerSettings customerSettings) {
                setProfileWidgets(customerSettings);
                if (customerSettings != null) {
                   // Log.d("HelloV", customerSettings.toString());
                }
            }
        });
    }

    private void initButton() {
        xEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToEditProfileFragment();
            }
        });

        xTryFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setProfileWidgets(CustomerSettings customerSettings) {
        Log.d(TAG, "Setting widgets with data retrieved from Firebase");
        User user = customerSettings.getUser();
        CustomerAccountSettings settings = customerSettings.getCustomerAccountSettings();
        GlobalImageLoader.setImage(getContext(), xProfileImage, settings.getProfilePhoto());
        xCustomerName.setText(user.getFullName());
        xOrders.setText(String.valueOf(settings.getOrders()));
        xFollowing.setText(String.valueOf(settings.getFollowing()));
        xCustomerPhone.setText(settings.getPhoneNumber());
        xCustomerAddress.setText(settings.getAddress());
        xCustomerDescription.setText(settings.getBio());

    }

    private void navigateToEditProfileFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new CustomerEditProfileFragment()).addToBackStack("Edit Fragment").commit();
        //
    }

    /*
   ----------------------------- Firebase setup ---------------------------------
*/

    /*
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser muser = firebaseAuth.getCurrentUser();
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
                setProfileWidgets(database.getCustomerSettings(dataSnapshot));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

     */
}
