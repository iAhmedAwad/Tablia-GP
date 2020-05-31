package com.awad.tablia.ChefHome.TabBar.Oreder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import Models.OrderPojo;

public class OrderViewModel extends ViewModel {

  public MutableLiveData<List<OrderPojo>> deatilsMutableLiveData = new MutableLiveData<>();


  public void setList(List<OrderPojo> list) {
    deatilsMutableLiveData.setValue(list);
  }


}
