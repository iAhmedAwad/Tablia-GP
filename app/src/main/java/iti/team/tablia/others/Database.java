package iti.team.tablia.others;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.Models.Chef.ChefSettings;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.Models.Customer.CustomerSettings;
import iti.team.tablia.Models.Following;
import iti.team.tablia.Models.Others.ChefReviews;
import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.Models.User;
import iti.team.tablia.R;
import iti.team.tablia.util.Constants;
import iti.team.tablia.util.ImageManager;
import iti.team.tablia.util.TM;

public class Database {

    private static final String TAG = "Database";
    private MutableLiveData<List<MenuPojo>> menuList = new MutableLiveData<>();
    private MutableLiveData<List<OrderPojo>> order = new MutableLiveData<>();
    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userId;
    private Context mContext;
    private StorageReference mStorageReference;
    //vars
    private double mPhotoUploadProgress;


    public Database() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userId = mUser.getUid();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mPhotoUploadProgress = 0;
    }

    public Database(Context context) {
        this.mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userId = mUser.getUid();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mPhotoUploadProgress = 0;
    }

    /**
     * Adds a customer to database on two steps
     * a) Add to users node
     * b) Add to customer_settings_node
     *
     * @param customerSettings
     */
    public void addCustomerToDatabase(CustomerSettings customerSettings) {

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        //add data to the "customers" node
        reference.child("users")
                .child(userid)
                .setValue(customerSettings.getUser());

        //add data to the "customer settings" node

        reference.child(Constants.customerAccountSettingsNode)
                .child(userid)
                .setValue(customerSettings.getCustomerAccountSettings());
    }

    /**
     * retrieves customer account settings from database
     *
     * @param dataSnapshot
     * @return
     */
    public CustomerSettings getCustomerSettings(DataSnapshot dataSnapshot) {
        User user = new User();
        CustomerAccountSettings customerAccountSettings = new CustomerAccountSettings();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            //CustomerAccountSettingsNode

            if (ds.getKey().equals(mContext.getString(R.string.customerAccountSettingsNode))) {

                try {

                    customerAccountSettings.setUserName(ds.child(userId)
                            .getValue(CustomerAccountSettings.class)
                            .getUserName());

                    customerAccountSettings.setAddress(ds.child(userId)
                            .getValue(CustomerAccountSettings.class)
                            .getAddress());

                    customerAccountSettings.setBio(ds.child(userId)
                            .getValue(CustomerAccountSettings.class)
                            .getBio());

                    customerAccountSettings.setDisplayName(ds.child(userId)
                            .getValue(CustomerAccountSettings.class)
                            .getDisplayName());

                    customerAccountSettings.setFollowing(ds.child(userId)
                            .getValue(CustomerAccountSettings.class)
                            .getFollowing());

                    customerAccountSettings.setOrders(ds.child(userId)
                            .getValue(CustomerAccountSettings.class)
                            .getOrders());
                    customerAccountSettings.setProfilePhoto(ds.child(userId)
                            .getValue(CustomerAccountSettings.class)
                            .getProfilePhoto());

                } catch (NullPointerException e) {
                }
            }

            //Users Node

            if (ds.getKey().equals(mContext.getString(R.string.usersNode))) {

                try {
                    user.setUser_id(ds.child(userId).getValue(User.class).getUser_id());
                    user.setEmail(ds.child(userId).getValue(User.class).getEmail());
                    user.setFullName(ds.child(userId).getValue(User.class).getFullName());
                    user.setUsername(ds.child(userId).getValue(User.class).getUsername());
                    user.setType(ds.child(userId).getValue(User.class).getType());

                } catch (NullPointerException e) {
                }

            }
        }
        return new CustomerSettings(user, customerAccountSettings);


    }

    /**
     * retrieves chef from database as a MutableLiveData
     *
     * @return
     */
    public MutableLiveData<CustomerSettings> getCustomerSettings() {

        final MutableLiveData<CustomerSettings> customerSettingsMutableLiveData = new MutableLiveData<>();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//Edited from addValue to addSingle
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = null;
                CustomerAccountSettings customerAccountSettings = null;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals("users")) {
                        user = ds.child(userId).getValue(User.class);
                    }

                    if (ds.getKey().equals("customer_account_settings")) {
                        customerAccountSettings = ds.child(userId).getValue(CustomerAccountSettings.class);
                    }
                }

                customerSettingsMutableLiveData.setValue(new CustomerSettings(user, customerAccountSettings));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return customerSettingsMutableLiveData;
    }

    /**
     * Add a chef to database on two steps
     * a) Add to users node
     * b) Add to chef_account_settings node
     *
     * @param chefSettings
     */
    public void addChefToDatabase(ChefSettings chefSettings) {

        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        //add data to the "users" node
        reference.child(Constants.usersNode)
                .child(userid)
                .setValue(chefSettings.getUser());


        //add data to the "customer settings" node

        reference.child(mContext.getString(R.string.chefAccountSettingsNode))
                .child(userid)
                .setValue(chefSettings.getChefAccountSettings());
        if (!chefSettings.getChefAccountSettings().isAvailable()) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("menu").child(userid);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        FirebaseDatabase.getInstance().getReference("Disable").child(userid)
                                .setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    dataSnapshot.getRef().removeValue();
                                }
                            }

                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    /**
     * retrieves chef data from database
     *
     * @param dataSnapshot
     * @return
     */
    public ChefSettings getChefSettings(DataSnapshot dataSnapshot) {
        User user = new User();
        ChefAccountSettings chefAccountSettings = new ChefAccountSettings();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            //ChefAccountSettingsNode

            if (ds.getKey().equals(mContext.getString(R.string.chefAccountSettingsNode))) {
                chefAccountSettings = ds.child(userId)
                        .getValue(ChefAccountSettings.class);
//                try {
//                    chefAccountSettings.setUserName(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .getUserName());
//
//                    chefAccountSettings.setAddress(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .getAddress());
//
//                    chefAccountSettings.setBio(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .getBio());
//
//                    chefAccountSettings.setDisplayName(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .getDisplayName());
//
//
//                    chefAccountSettings.setOrders(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .getOrders());
//                    chefAccountSettings.setProfilePhoto(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .getProfilePhoto());
//
//                    chefAccountSettings.setFollowers(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .getFollowers());
//
//                    //Edittedd
//                    chefAccountSettings.setFollowers(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .getFollowers());
//
//                    chefAccountSettings.setStart_order_time(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .getStart_order_time());
//                    chefAccountSettings.setEnd_order_time(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .getEnd_order_time());
//
//                    chefAccountSettings.setAvailable(ds.child(userId)
//                            .getValue(ChefAccountSettings.class)
//                            .isAvailable());
//
//
//                } catch (NullPointerException e) {
//                }
            }

            //Users Node

            if (ds.getKey().equals(mContext.getString(R.string.usersNode))) {
                user = ds.child(userId).getValue(User.class);
//                try {
//                    user.setUser_id(ds.child(userId).getValue(User.class).getUser_id());
//                    user.setEmail(ds.child(userId).getValue(User.class).getEmail());
//                    user.setFullName(ds.child(userId).getValue(User.class).getFullName());
//                    user.setUsername(ds.child(userId).getValue(User.class).getUsername());
//                    user.setType(ds.child(userId).getValue(User.class).getType());
//
//                } catch (NullPointerException e) {
//                }

            }
        }
        return new ChefSettings(user, chefAccountSettings);


    }

    public User retrieveUserFromDatabase(DataSnapshot dataSnapshot) {

        User user = new User();


        try {

            user = dataSnapshot.getValue(User.class);


        } catch (NullPointerException e) {
        }


        return user;
    }

    /**
     * Adds  menu item to database
     *
     * @param menuPojo
     */
    public void addMenuItemToDatabase(MenuPojo menuPojo) {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(mContext.getString(R.string.menuNode));

        //add data to the "customers" node
        String key = reference.push().getKey();
        menuPojo.setItemID(key);
        reference.child(userid)
                .child(key)
                .setValue(menuPojo);

    }


    public void updateMenuItemToDatabase(MenuPojo menuPojo) {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(mContext.getString(R.string.menuNode));

        //add data to the "customers" node
//    String key = reference.push().getKey();
//    menuPojo.setItemID(key);
        reference.child(userid)
                .child(menuPojo.getItemID())
                .setValue(menuPojo);

    }


    /**
     * retrieves chef's menu items
     *
     * @return
     */

    public MutableLiveData<List<MenuPojo>> getMenuItems() {

        final List<MenuPojo> list = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("menu").child(userId);

        //ref.child(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    MenuPojo pojo = data.getValue(MenuPojo.class);

                    list.add(pojo);
                }

                menuList.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return menuList;
    }


    public void addMenuDisableItemToDatabase(MenuPojo menuPojo) {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Disable");

        //add data to the "customers" node
        reference.child(userid)
                .child(menuPojo.getItemID())
                .setValue(menuPojo);
        FirebaseDatabase.getInstance().getReference("menu")
                .child(userid)
                .child(menuPojo.getItemID()).removeValue();

    }


    public MutableLiveData<List<MenuPojo>> getMenuItemsDisable() {

        final List<MenuPojo> list = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Disable").child(userId);

        //ref.child(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    MenuPojo pojo = data.getValue(MenuPojo.class);

                    list.add(pojo);
                }

                menuList.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return menuList;
    }


    /**
     * retrieve chef menu by chefId
     */

    public MutableLiveData<List<MenuPojo>> getMenuItems(String chefId) {

        final List<MenuPojo> list = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("menu").child(chefId);

        /**
         * retrieves chef's menu items
         *
         * @return
         */

        //ref.child(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    MenuPojo pojo = data.getValue(MenuPojo.class);

                    list.add(pojo);
                }

                menuList.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return menuList;
    }


    public void addOrderToDatabase(OrderPojo orderPojo) {

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /**
         * retrieve chef menu by chefId
         */

        //add data to the "customers" node
        reference.child(mContext.getString(R.string.Order))
                .child(userid)
                .push()
                .setValue(orderPojo);
    }

    public MutableLiveData<List<OrderPojo>> getOrder() {

        final List<OrderPojo> list = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order").child(userId);

        //ref.child(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    OrderPojo pojo = data.getValue(OrderPojo.class);

                    list.add(pojo);
                }

                order.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return order;
    }


    public MutableLiveData<List<OrderPojo>> getChefOrder() {

        final List<OrderPojo> list = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders").child(userId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    for (DataSnapshot dataSnapshot1 : data.getChildren()) {


                        OrderPojo pojo = dataSnapshot1.getValue(OrderPojo.class);
                        if (!pojo.isChefConfirm() || !pojo.isCustConfirm()) {
                            list.add(pojo);
                        }
                    }

                }

                order.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return order;
    }


    /**
     * This uploads a photo to database
     * And also adds the link to this photo to the node using the updateProfilePhotoInDatabase method
     *
     * @param bitmap
     */
    public void uploadProfilePhoto(Bitmap bitmap) {
        final StorageReference storageReference = mStorageReference
                .child("ProfilePhotosNode" + userId + "/profilePhoto");
        //Convert image url to a bitmap
        //Bitmap bitmap = ImageManager.getBitmap(mImageUrl);
        byte[] bytes = ImageManager.getBytesFromBitmap(bitmap, 50);
        UploadTask uploadTask = null;
        uploadTask = storageReference.putBytes(bytes);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference
                        .getDownloadUrl()
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                updateProfilePhotoInDatabase(task.getResult().toString());
                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                TM.toast(mContext, "Photo upload failed!");

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                if (progress - 15 > mPhotoUploadProgress) {
                    //   TM.toast(mContext, "Photo upload progress" + String.format("%.0f", progress));
                    mPhotoUploadProgress = progress;
                }
            }
        });

    }

    /**
     * Adds a profile photo link to the data base in the settings node
     *
     * @param imageUrl
     */
    private void updateProfilePhotoInDatabase(String imageUrl) {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        //add data to the "customer settings" node

        reference.child("customer_account_settings")
                .child(userid)
                .child("profilePhoto")
                .setValue(imageUrl);
    }

    /**
     * Categories .. this is gonna be a long journey!
     */

    public MutableLiveData<ArrayList<MenuPojo>> getCategoryList(final String category, final String category_ar) {
        final MutableLiveData<ArrayList<MenuPojo>> listMutableLiveData =
                new MutableLiveData<ArrayList<MenuPojo>>();

        final ArrayList<MenuPojo> list = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("menu");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dsx : ds.getChildren()) {
                        MenuPojo menuPojo = dsx.getValue(MenuPojo.class);
                        if (menuPojo.getCategory().equals(category)
                                || menuPojo.getCategory().equals(category_ar)) {
                            list.add(menuPojo);
                        }
                    }
                }

                listMutableLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return listMutableLiveData;
    }


    /**
     * Filtered data
     */

    public MutableLiveData<ArrayList<MenuPojo>> getFilteredItems(final ArrayList<String> categoriesList,
                                                                 final double min, final double max) {
        final MutableLiveData<ArrayList<MenuPojo>> listMutableLiveData = new MutableLiveData<>();

        final ArrayList<MenuPojo> list = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(Constants.MenuNode);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    // Log.d("filterx", "first loop");

                    for (DataSnapshot dsx : ds.getChildren()) {
                        //  Log.d("filterx", "second loop");
                        MenuPojo menuPojo = dsx.getValue(MenuPojo.class);
                        if (categoriesList.contains(menuPojo.getCategory())
                                && menuPojo.getPriceItem() >= min
                                && menuPojo.getPriceItem() <= max
                        ) {
                            Log.d("filterx", "Item name is: " + menuPojo.getItemName());
                            Log.d("filterx", "Item price is: " + menuPojo.getPriceItem());
                            Log.d("filterx", "min and max are: " + min + " & " + max);
                            list.add(menuPojo);
                        }
                    }
                }

                listMutableLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return listMutableLiveData;
    }

    /**
     * return all the menu items
     */

    public MutableLiveData<ArrayList<MenuPojo>> getAllMenuItemsInRange(final double min, final double max) {
        final MutableLiveData<ArrayList<MenuPojo>> listMutableLiveData =
                new MutableLiveData<ArrayList<MenuPojo>>();

        final ArrayList<MenuPojo> list = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("menu");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dsx : ds.getChildren()) {
                        MenuPojo menuPojo = dsx.getValue(MenuPojo.class);

                        if (menuPojo.getPriceItem() >= min
                                && menuPojo.getPriceItem() <= max
                        ) {

                            list.add(menuPojo);
                        }

                    }
                }

                listMutableLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return listMutableLiveData;
    }

    public MutableLiveData<ChefAccountSettings> getChefInfoById(String userId) {

        final MutableLiveData<ChefAccountSettings> chefLiveData = new MutableLiveData<>();
        DatabaseReference refernce = FirebaseDatabase.getInstance().getReference("chef_account_settings").child(userId);
        refernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChefAccountSettings chef = dataSnapshot.getValue(ChefAccountSettings.class);
                chefLiveData.setValue(chef);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return chefLiveData;
    }

    /**
     * retrieves the review from database by using the itemID and comparing the customer ID to userID
     *
     * @param itemID
     * @return
     */
    public MutableLiveData<Review> getReview(String itemID) {
        Log.d("Reviewx", "From DB getReview method ");


        final MutableLiveData<Review> reviewMutableLiveData = new MutableLiveData<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(Constants.reviewsNode).child(itemID);
        Log.d("Reviewx", "From DB itemId:" + itemID);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Review review = ds.getValue(Review.class);

                    Log.d("Reviewx", "From DB userID: " + userId);
                    Log.d("Reviewx", "From DB customerId: " + review.getCustomerId());
                    if (review.getCustomerId().equals(userId)) {
                        reviewMutableLiveData.setValue(review);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return reviewMutableLiveData;
    }

    /**
     * Adds a review to the database
     *
     * @param review
     */

    public void addReview(Review review) {
        String reviewId;
        if (review.getReviewId() == null) {

            reviewId = FirebaseDatabase.getInstance().getReference().push().getKey();
            review.setReviewId(reviewId);
        } else {
            reviewId = review.getReviewId();
        }

        review.setCustomerId(userId);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(Constants.reviewsNode).child(review.getItemId());

        ref.child(reviewId)
                .setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (mContext != null) {
                    Toast.makeText(mContext, "Thanks for your review!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * The following system
     *
     * @return
     */


    /**
     * A customer follows a certain chef
     *
     * @param chefId
     */
    public void followChef(String chefId) {

        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.customerAccountSettingsNode)
                .child(userId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CustomerAccountSettings settings = dataSnapshot.getValue(CustomerAccountSettings.class);
                settings.setFollowing(settings.getFollowing() + 1);
                ref.setValue(settings);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child(Constants.FOLLOWING_NODE)
                .child(userId).child(chefId).setValue(chefId);

        addFollower(chefId);

    }

    /**
     * A customer unfollows a certain chef
     *
     * @param chefId
     */

    public void unfollowChef(String chefId) {


        final DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(Constants.customerAccountSettingsNode)
                .child(userId);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CustomerAccountSettings settings = dataSnapshot.getValue(CustomerAccountSettings.class);
                settings.setFollowing(settings.getFollowing() - 1);
                reference.setValue(settings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(Constants.FOLLOWING_NODE).child(userId).child(chefId);
        ref.removeValue();
        deleteFollower(chefId);

    }

    /**
     * get ALL the followed chefs
     *
     * @return
     */

    public MutableLiveData<ArrayList<Following>> getFollowing() {
        final MutableLiveData<ArrayList<Following>> followingMutableLiveData = new MutableLiveData<>();
        final ArrayList<String> chefIDLis = new ArrayList<>();
        final ArrayList<Following> followingArrayList = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference();

        ref.child(Constants.FOLLOWING_NODE).child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        chefIDLis.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            chefIDLis.add(ds.getValue(String.class));
                        }
                        ref.child(Constants.chefAccountSettingsNode)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        followingArrayList.clear();
                                        for (DataSnapshot dsx : dataSnapshot.getChildren()) {

                                            if (chefIDLis.contains(dsx.getKey())) {
                                                Following following = new Following();
                                                ChefAccountSettings settings = dsx.getValue(ChefAccountSettings.class);
                                                following.setBio(settings.getBio());
                                                following.setChefId(dsx.getKey());
                                                following.setProfilePhoto(settings.getProfilePhoto());
                                                following.setFullName(settings.getDisplayName());
                                                following.setUserName(settings.getUserName());
                                                followingArrayList.add(following);
                                            }
                                        }
                                        followingMutableLiveData.setValue(followingArrayList);
                                        ref.child(Constants.chefAccountSettingsNode).removeEventListener(this);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        return followingMutableLiveData;
    }

    /**
     * get the nth followed chefs
     *
     * @param numberOfChefs
     * @return
     */

    public MutableLiveData<ArrayList<Following>> getFollowing(int numberOfChefs) {
        final MutableLiveData<ArrayList<Following>> followingMutableLiveData = new MutableLiveData<>();
        final ArrayList<String> chefIDLis = new ArrayList<>();
        final ArrayList<Following> followingArrayList = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference();

        Query query = FirebaseDatabase.getInstance().getReference(Constants.FOLLOWING_NODE).child(userId).orderByKey();

        //ref.child(Constants.FOLLOWING_NODE).child(userId)
        query.limitToFirst(numberOfChefs).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chefIDLis.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    chefIDLis.add(ds.getValue(String.class));
                }
                ref.child(Constants.chefAccountSettingsNode)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                followingArrayList.clear();
                                for (DataSnapshot dsx : dataSnapshot.getChildren()) {

                                    if (chefIDLis.contains(dsx.getKey())) {
                                        Following following = new Following();
                                        ChefAccountSettings settings = dsx.getValue(ChefAccountSettings.class);
                                        following.setBio(settings.getBio());
                                        following.setChefId(dsx.getKey());
                                        following.setProfilePhoto(settings.getProfilePhoto());
                                        following.setFullName(settings.getDisplayName());
                                        following.setUserName(settings.getUserName());
                                        followingArrayList.add(following);
                                    }
                                }
                                followingMutableLiveData.setValue(followingArrayList);
                                ref.child(Constants.chefAccountSettingsNode).removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return followingMutableLiveData;
    }

    /**
     * Increment number of chef followers when Follow
     *
     * @param chefID
     */

    private void addFollower(String chefID) {
        final DatabaseReference refrence = FirebaseDatabase.getInstance().getReference(Constants.chefAccountSettingsNode).child(chefID);
        refrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChefAccountSettings chef = dataSnapshot.getValue(ChefAccountSettings.class);
                chef.setFollowers(chef.getFollowers() + 1);
                refrence.setValue(chef);
                refrence.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * Decrement number of chef followers when UnFollow
     *
     * @param chefID
     */
    private void deleteFollower(String chefID) {
        final DatabaseReference refrence = FirebaseDatabase.getInstance().getReference(Constants.chefAccountSettingsNode).child(chefID);
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChefAccountSettings chef = dataSnapshot.getValue(ChefAccountSettings.class);
                chef.setFollowers(chef.getFollowers() - 1);
                refrence.setValue(chef);
                refrence.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * a method to return true if a customer follows a certain chef
     */

    public MutableLiveData<Boolean> isFollowing(final String chefId) {

        final MutableLiveData<Boolean> isFollowing = new MutableLiveData<>();

        final ArrayList<Boolean> list = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(Constants.FOLLOWING_NODE).child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    if (chefId.equals(ds.getKey())) {

                        isFollowing.setValue(true);

                        break;
                    } else {
                        isFollowing.setValue(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return isFollowing;

    }

    /**
     * populate customer's home fragment
     */

    public MutableLiveData<ArrayList<MenuPojo>> getFollowedChefsMenuItems() {

        final MutableLiveData<ArrayList<MenuPojo>> ArrayListMutableLiveData = new MutableLiveData<>();
        final ArrayList<String> chefIDLis = new ArrayList<>();
        final ArrayList<MenuPojo> menuPojoArrayList = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child(Constants.FOLLOWING_NODE).child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        chefIDLis.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            chefIDLis.add(ds.getKey());
                        }
                        ref.child(Constants.MenuNode)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        menuPojoArrayList.clear();
                                        for (DataSnapshot dsx : dataSnapshot.getChildren()) {

                                            if (chefIDLis.contains(dsx.getKey())) {

                                                for (DataSnapshot dsItems : dsx.getChildren()) {

                                                    MenuPojo menuPojo = dsItems.getValue(MenuPojo.class);

                                                    menuPojoArrayList.add(menuPojo);
                                                }

                                            }
                                        }
                                        ArrayListMutableLiveData.setValue(menuPojoArrayList);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        return ArrayListMutableLiveData;
    }

    public MutableLiveData<ArrayList<ChefReviews>> retrieveChefReviews(final String chefId) {
        Log.w("bored", "Hi from retrieveChefReviews");

        final ArrayList<Review> reviewArrayList = new ArrayList<>();
        final ArrayList<ChefReviews> chefReviewsArrayList = new ArrayList<>();
        final MutableLiveData<ArrayList<ChefReviews>> mutableLiveData = new MutableLiveData<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child(Constants.reviewsNode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Getting the chef's items
                //These are the reviews *grouped* by ItemId
                for (DataSnapshot dsGroupedByItem : dataSnapshot.getChildren()) {
                    for (DataSnapshot dsReview : dsGroupedByItem.getChildren()) {
                        Review review = dsReview.getValue(Review.class);

                        if (review.getChefId().equals(chefId)) {
                            reviewArrayList.add(review);
                        }
                    }
                }
                Log.wtf("bored", "Size: " + reviewArrayList.size());
                ref.child(Constants.usersNode).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (Review rv : reviewArrayList) {
                            ChefReviews chefReviews = new ChefReviews();
                            chefReviews.setReview(rv);
                            for (DataSnapshot usersSnapshot : dataSnapshot.getChildren()) {
                                User user = usersSnapshot.getValue(User.class);

                                if (usersSnapshot.getKey().equals(rv.getChefId())) {
                                    chefReviews.setChefName(user.getFullName());
                                    //Log.wtf("blabla", "chef is true, name is: " + chefReviews.getChefName());
                                }
                                if (usersSnapshot.getKey().equals(rv.getCustomerId())) {
                                    chefReviews.setCustomerName(user.getFullName());
                                    //Log.wtf("blabla", "customer is true, name is: " + user.getFullName());
                                }
                            }
                            chefReviewsArrayList.add(chefReviews);
                        }

                        for (ChefReviews cr : chefReviewsArrayList) {

                            Log.wtf("blabla", "Customer: " + cr.getCustomerName());
                            Log.wtf("blabla", "Chef: " + cr.getChefName());
                        }

                        mutableLiveData.setValue(chefReviewsArrayList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mutableLiveData;
    }

    /**
     * retreives Reviews By itemID
     *
     * @param itemID
     * @return
     */
    public MutableLiveData<ArrayList<Review>> retriveReviewByItemId(final String itemID) {
        Log.i("test", "inside method");
        final MutableLiveData<ArrayList<Review>> ArrayListMutableLiveData = new MutableLiveData<>();
        final ArrayList<Review> list = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child(Constants.reviewsNode).child(itemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i("test", String.valueOf(ds.getChildrenCount()));
                    Review review = ds.getValue(Review.class);
                    if (review != null && review.getItemId().equals(itemID)) {
                        list.add(review);
                    }
                }
                Log.i("test", "list size " + list.size());
                ArrayListMutableLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return ArrayListMutableLiveData;
    }

    /**
     * retrieve CustName from custId
     */
    public MutableLiveData<String> getCustName(String custId) {
        final DatabaseReference reference;
        final MutableLiveData<String> list = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("customer_account_settings").child(custId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CustomerAccountSettings settings = dataSnapshot.getValue(CustomerAccountSettings.class);
                list.setValue(settings.getDisplayName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return list;
    }
}
