package com.awad.tablia.Customer.Customerr;

import android.content.Context;
import android.util.Log;

import com.awad.tablia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Models.Customer;
import Models.CustomerAccountSettings;
import Models.CustomerSettings;


public class Database {

  private FirebaseAuth mAuth;
  private FirebaseUser mUser;
  private static final String TAG = "Database";
  private String userId;
  private Context mContext;


  public Database(Context context) {
    this.mContext = context;
    mAuth = FirebaseAuth.getInstance();
    mUser = mAuth.getCurrentUser();
    userId = mUser.getUid();
  }

  /**
   * retrieves customer account settings from database
   *
   * @param dataSnapshot
   * @return
   */
  public CustomerSettings getCustomerSettings(DataSnapshot dataSnapshot) {
    Log.d(TAG, "Retrieving customer account settings from database");
    Customer customer = new Customer();
    CustomerAccountSettings customerAccountSettings = new CustomerAccountSettings();
    for (DataSnapshot ds : dataSnapshot.getChildren()) {

      //CustomerAccountSettingsNode

      if (ds.getKey().equals(mContext.getString(R.string.customerAccountSettingsNode))) {
        Log.d(TAG, "Looking inside the customer settings node");

        try {

          customerAccountSettings.setUserName(ds.child(userId)
              .getValue(CustomerAccountSettings.class)
              .getUserName());

          customerAccountSettings.setAddress(ds.child(userId)
              .getValue(CustomerAccountSettings.class)
              .getAddress());

          customerAccountSettings.setDescription(ds.child(userId)
              .getValue(CustomerAccountSettings.class)
              .getDescription());

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
          Log.d(TAG, "NullPointerException " + e.getMessage());
        } finally {
          Log.d(TAG, customerAccountSettings.toString());
        }
      }

      //Customer Node

      if (ds.getKey().equals(mContext.getString(R.string.customersNode))) {
        Log.d(TAG, "Looking inside the customers node");

        try {
          customer.setCustomer_id(ds.child(userId).getValue(Customer.class).getCustomer_id());
          customer.setEmail(ds.child(userId).getValue(Customer.class).getEmail());
          customer.setFullName(ds.child(userId).getValue(Customer.class).getFullName());
          customer.setUsername(ds.child(userId).getValue(Customer.class).getUsername());

        } catch (NullPointerException e) {
          Log.d(TAG, "NullPointerException" + e.getMessage());
        } finally {
          Log.d(TAG, customer.toString());
        }

      }
    }
    return new CustomerSettings(customer, customerAccountSettings);


  }

  public void addCustomerToDatabase(CustomerSettings customerSettings) {

    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Log.d(TAG, "Database class: addNewUser: Adding new User: \n user_id:" + userid);
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    //add data to the "customers" node
    reference.child(mContext.getString(R.string.customersNode))
        .child(userid)
        .setValue(customerSettings.getCustomer());

    //add data to the "customer settings" node

    reference.child(mContext.getString(R.string.customerAccountSettingsNode))
        .child(userid)
        .setValue(customerSettings.getCustomerAccountSettings());
  }
}
