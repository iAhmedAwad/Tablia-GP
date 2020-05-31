package com.awad.tablia.ChefHome.TabBar.Menu;

import com.awad.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;

import java.util.ArrayList;
import java.util.List;

public class DataOfMenu {

  private static DataOfMenu INSTANCE = null;
  List<MenuPojo> data = new ArrayList<>();

  private DataOfMenu() {
  }

  ;

  public static DataOfMenu getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new DataOfMenu();
    }
    return (INSTANCE);
  }

  public List<MenuPojo> getData() {
    return data;
  }

  public void setData(MenuPojo dataa) {
    this.data.add(dataa);
  }
}

