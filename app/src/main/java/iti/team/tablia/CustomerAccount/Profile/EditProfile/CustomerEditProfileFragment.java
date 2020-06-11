package iti.team.tablia.CustomerAccount.Profile.EditProfile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import iti.team.tablia.ChefHome.TabBar.Profile.EditChiefActivity;
import iti.team.tablia.CustomerAccount.CustomerOrder.EditCustProfile;
import iti.team.tablia.CustomerAccount.Profile.CustomerProfileFragment;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.Models.Customer.CustomerSettings;
import iti.team.tablia.Models.User;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;
import iti.team.tablia.others.PlaceApi;
import iti.team.tablia.others.PlacesAutoSuggestAdapter;
import iti.team.tablia.util.GlobalImageLoader;
import iti.team.tablia.util.Permissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerEditProfileFragment extends Fragment {
    public static final String TAG = "EditProfileFragment";
    private static final int CAMERA_REQUEST_CODE = 3;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    Uri img;
    String mypic;
    Bitmap bitmap;
    private String camera;
    private String gal;
    private String can;
    private String title;
    //PlacesAPI
    private final String mAPIKey = "AIzaSyDFhO6SEcewKE7jQUjyE-XwqbhlODfObEA";
    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Database database;
    //Widgets
    private CircleImageView xProfileImage;
    ImageView cam;
    private AutoCompleteTextView xCusomerAddress;
    private CustomerSettings mCustomerSettings;
    private EditText xCustomerName, xCustomerPhone, xCustomerDescription;
    private Button xSaveChanges;
    private CustomerEditProfileViewModel model;
    //vars


    public CustomerEditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(getActivity()).get(CustomerEditProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_edit_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.edit_profile));


        xProfileImage = view.findViewById(R.id.xprofile_image);
        xCusomerAddress = view.findViewById(R.id.xCustomerAddress);
        xCustomerName = view.findViewById(R.id.xCustomerName);
        xCustomerPhone = view.findViewById(R.id.xCustomerPhone);
        xCustomerDescription = view.findViewById(R.id.xCustomerDescription);
        xSaveChanges = view.findViewById(R.id.xEditData);
        cam = view.findViewById(R.id.id_edit_cam);
        database = new Database(getActivity());
        model.getCustomerSettings().observe(getViewLifecycleOwner(), new Observer<CustomerSettings>() {
            @Override
            public void onChanged(CustomerSettings customerSettings) {
//        setProfileWidgets(customerSettings);

                mCustomerSettings = new CustomerSettings();
                mCustomerSettings.setUser(customerSettings.getUser());
                mCustomerSettings.setCustomerAccountSettings(customerSettings.getCustomerAccountSettings());
            }
        });
        setProfileWidgets(CustomerProfileFragment.customer);

        //setupFirebaseAuth();
        initComponents();
//    initProfileImage(xProfileImage);
        return view;
    }


    /**
     * sets thee fragment widgets with the data received from database
     *
     * @param customerSettings
     */
    private void setProfileWidgets(CustomerSettings customerSettings) {

        Log.d(TAG, "Setting widgets with data retrieved from Firebase");
        User user = customerSettings.getUser();
        CustomerAccountSettings settings = customerSettings.getCustomerAccountSettings();
//    GlobalImageLoader.setImage(getActivity(), xProfileImage, settings.getProfilePhoto());


        xCustomerName.setText(user.getFullName());
        xCustomerPhone.setText(settings.getPhoneNumber());
        xCusomerAddress.setText(settings.getAddress());
        xCustomerDescription.setText(settings.getBio());
        mypic = settings.getProfilePhoto();
        xProfileImage.setImageBitmap(GlobalImageLoader.StringToBitMap(settings.getProfilePhoto()));


    }

    /**
     * initializes the Address AutoCompleteTextView
     * initializes the Save Changes button
     */

    private void initComponents() {
        PlaceApi placeApi = new PlaceApi();

        if (!Places.isInitialized()) {
            // Initialize the SDK
            Places.initialize(getActivity(), mAPIKey);
        }
        // Create a new Places client instance
        PlacesClient mPlacesClient = Places.createClient(getActivity());
        xCusomerAddress.setAdapter(new PlacesAutoSuggestAdapter(getActivity(), android.R.layout.simple_list_item_1));

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();

            }
        });

        xSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCustomer(mCustomerSettings);

//        if (bitmap != null) {
//          model.uploadProfilePhoto(bitmap);
//        }
                navigateToProfileFragment();

            }
        });

    }

    public void SelectImage() {
        if (Locale.getDefault().getLanguage().equals("ar")) {
            camera = "كاميرا";
            gal = "المعرض";
            can = "إلغاء";
            title ="إضافة صورة";
        } else {
            camera = "Camera";
            gal = "Gallery";
            can = "Cancel";
            title = "Add Image";
        }
        final CharSequence[] items = {camera, gal, can};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (items[which].equals(camera)) {

                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentCamera, REQUEST_CAMERA);

                } else if (items[which].equals(gal)) {

                    Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intentGallery.setType("image/*");
                    startActivityForResult(intentGallery.createChooser(intentGallery, "SelectFile"), SELECT_FILE);

                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void initProfileImage(CircleImageView circleImageView) {
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Permissions.checkPermission(getActivity(),
                        Permissions.CAMERA_PERMISSIONS)) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
            }
        });
        //database.uploadProfilePhoto(mImageUrl);
    }

    public void editCustomer(CustomerSettings customerSettings) {

        String name = xCustomerName.getText().toString();
        String phone = xCustomerPhone.getText().toString();
        String address = xCusomerAddress.getText().toString();
        String description = xCustomerDescription.getText().toString();


        customerSettings.getUser().setFullName(name);
        customerSettings.getCustomerAccountSettings().setDisplayName(name);
        customerSettings.getCustomerAccountSettings().setPhoneNumber(phone);
        customerSettings.getCustomerAccountSettings().setAddress(address);
        customerSettings.getCustomerAccountSettings().setBio(description);
        customerSettings.getCustomerAccountSettings().setProfilePhoto(mypic);
//customerSettings.getCustomerAccountSettings().setProfilePhoto(mypic);
        model.editCustomer(customerSettings);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == REQUEST_CAMERA) {
                bitmap = (Bitmap) data.getExtras().get("data");
                Log.i("nopic", GlobalImageLoader.BitMapToString(bitmap));

                xProfileImage.setImageBitmap(bitmap);
                mypic = GlobalImageLoader.BitMapToString(bitmap);

            } else if (requestCode == SELECT_FILE) {

                img = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), img);
                    xProfileImage.setImageBitmap(bitmap);
                    mypic = GlobalImageLoader.BitMapToString(bitmap);

//        xProfileImage.setImageBitmap(bitmap);
                    Log.i("nopic", bitmap.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }


//        Log.i("nopic", bitmap.toString());
//        Log.i("nopic",GlobalImageLoader.BitMapToString(bitmap));
            }
        }


//    if (requestCode == CAMERA_REQUEST_CODE) {
//      if (data != null) {
//        bitmap = (Bitmap) data.getExtras().get("data");
//        xProfileImage.setImageBitmap(bitmap);
//
//      }
////            database.uploadProfilePhoto(bitmap);
//
//            /*
//            if (isRootTask()) {
//
//            } else {
//                try{
////                    TM.log("Received a new bitmap from camera "+ bitmap);
////                    Intent intent = new Intent(getActivity(),
////                            CustomerProfileActivityOld.class);
////                    intent.putExtra(getString(R.string.selectedBitmap), bitmap);
////                    intent.putExtra(getString(R.string.intentSource), getString(R.string.customerProfileActivity));
////                    startActivity(intent);
////                    finish();
//                    database.uploadProfilePhoto(bitmap);
//                }catch (NullPointerException e){
//                    TM.log("NullPointerException" + e.getMessage());
//                }
//
//            }
//*/
//    } else {
//    }
    }

    private boolean isRootTask() {
        if (getActivity().getTaskId() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void navigateToProfileFragment() {
        if (getActivity().getClass().equals(EditCustProfile.class)) {
            getActivity().finish();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CustomerProfileFragment()).commit();
        }
    }

//  @Override
//  public void onDestroyView() {
//    super.onDestroyView();
//    model.getCustomerSettings().removeObservers(getViewLifecycleOwner());
//  }

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
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    //user is signed in
                    Log.d(TAG, "onAuthStateChanged: signed_in" + mUser.getUid());

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
                customerSettings = database.getCustomerSettings(dataSnapshot);
                setProfileWidgets(customerSettings);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

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

    /*
    private void init(){
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentTransaction fragmentTransaction = EditProfileActivity.this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, profileFragment);
        fragmentTransaction.addToBackStack("Profile Fragment");
        fragmentTransaction.commit();
    }

     */
}
