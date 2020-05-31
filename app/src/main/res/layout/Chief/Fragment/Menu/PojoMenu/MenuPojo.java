package com.awad.tablia.ChefHome.TabBar.Menu.PojoMenu;

import android.graphics.Bitmap;

public class MenuPojo {

  private String nameItem;
  private String priceItem;
  private Bitmap imgItem;

  public MenuPojo(String nameItem, String priceItem, Bitmap imgItem) {
    this.nameItem = nameItem;
    this.priceItem = priceItem;
    this.imgItem = imgItem;
  }

  public MenuPojo(String nameItem, String priceItem) {
    this.nameItem = nameItem;
    this.priceItem = priceItem;
  }

  public String getNameItem() {
    return nameItem;
  }

  public void setNameItem(String nameItem) {
    this.nameItem = nameItem;
  }

  public String getPriceItem() {
    return priceItem;
  }

  public void setPriceItem(String priceItem) {
    this.priceItem = priceItem;
  }

  public Bitmap getImgItem() {
    return imgItem;
  }

  public void setImgItem(Bitmap imgItem) {
    this.imgItem = imgItem;
  }
}
