package iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.MenuItemPojo;

public class MenuPojo implements Parcelable {

  private String itemID;
  private String chefID;
  private String itemName;
  private double priceItem;
  private List<MenuItemPojo> imgItem;
  private String category;
  private String description;
  private String ingredients;
  private int itemQuantity;
  private boolean disabled;

  public MenuPojo() {
    disabled = false;
  }

  public MenuPojo(String itemID, String chefID, String itemName, double priceItem, List<MenuItemPojo> imgItem, String category, String description, String ingredients, int itemQuantity) {
    this.itemID = itemID;
    this.chefID = chefID;
    this.itemName = itemName;
    this.priceItem = priceItem;
    this.imgItem = imgItem;
    this.category = category;
    this.description = description;
    this.ingredients = ingredients;
    this.itemQuantity = itemQuantity;
    disabled = false;
  }

  protected MenuPojo(Parcel in) {
    itemID = in.readString();
    chefID = in.readString();
    itemName = in.readString();
    priceItem = in.readDouble();
    category = in.readString();
    description = in.readString();
    ingredients = in.readString();
    itemQuantity = in.readInt();
    disabled = in.readByte() != 0;
  }

  public static final Creator<MenuPojo> CREATOR = new Creator<MenuPojo>() {
    @Override
    public MenuPojo createFromParcel(Parcel in) {
      return new MenuPojo(in);
    }

    @Override
    public MenuPojo[] newArray(int size) {
      return new MenuPojo[size];
    }
  };

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public double getPriceItem() {
    return priceItem;
  }

  public void setPriceItem(double priceItem) {
    this.priceItem = priceItem;
  }

  public List<MenuItemPojo> getImgItem() {
    return imgItem;
  }

  public void setImgItem(List<MenuItemPojo> imgItem) {
    this.imgItem = imgItem;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIngredients() {
    return ingredients;
  }

  public void setIngredients(String ingredients) {
    this.ingredients = ingredients;
  }

  public String getItemID() {
    return itemID;
  }

  public void setItemID(String itemID) {
    this.itemID = itemID;
  }

  public String getChefID() {
    return chefID;
  }

  public void setChefID(String chefID) {
    this.chefID = chefID;
  }

  public int getItemQuantity() {
    return itemQuantity;
  }

  public void setItemQuantity(int itemQuantity) {
    this.itemQuantity = itemQuantity;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(itemID);
    dest.writeString(chefID);
    dest.writeString(itemName);
    dest.writeDouble(priceItem);
    dest.writeString(category);
    dest.writeString(description);
    dest.writeString(ingredients);
    dest.writeInt(itemQuantity);
    dest.writeByte((byte) (disabled ? 1 : 0));
  }
}
