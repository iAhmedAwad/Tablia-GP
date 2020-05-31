package iti.team.tablia.ChefHome;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import iti.team.tablia.Models.Chef.ChefAccountSettings;


public class StatusRepository {
  private FirebaseUser firebaseUser;
  private DatabaseReference reference;

  public StatusRepository() {
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
  }

  /**
   * update user current status if they are offline or online for chat
   *
   * @param status
   */
  public void status(String status) {
    FirebaseDatabase.getInstance().getReference("chef_account_settings").child(firebaseUser.getUid()).child("status").setValue(status);
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("status", status);
//        reference.updateChildren(hashMap);
  }

  public MutableLiveData<ChefAccountSettings> getCurrentUser() {
    final MutableLiveData<ChefAccountSettings> liveData = new MutableLiveData<>();
    reference = FirebaseDatabase.getInstance().getReference("chef_account_settings").child(firebaseUser.getUid());
    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        ChefAccountSettings settings = dataSnapshot.getValue(ChefAccountSettings.class);
        liveData.setValue(settings);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
    return liveData;
  }
}
