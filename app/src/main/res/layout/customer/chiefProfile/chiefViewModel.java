package com.awad.tablia.Customer.chiefProfile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Models.ChiefPojo;


public class chiefViewModel extends ViewModel {
  MutableLiveData<ChiefPojo> myData = new MutableLiveData<ChiefPojo>();

  public void setDataFromDataBase(ChiefPojo objj) {
    myData.setValue(objj);
  }

}


