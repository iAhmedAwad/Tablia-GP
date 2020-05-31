package com.awad.tablia.ChefHome.TabBar.Menu;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.awad.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MenuViewModel extends ViewModel {

  public MutableLiveData<List<MenuPojo>> deatilsMutableLiveData = new MutableLiveData<>();
  DataOfMenu data = DataOfMenu.getInstance();
  DatabaseReference ref;


//    public void getList() {
//
//
//        ref = FirebaseDatabase.getInstance().getReference("MenuItem");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot data : dataSnapshot.getChildren()){
//                    TestPojo item = data.getValue(TestPojo.class);
//                    list.add(item);
//                }
//
//                deatilsMutableLiveData.setValue(list);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }


  public void getList() {


    deatilsMutableLiveData.setValue(data.getData());

  }


}
