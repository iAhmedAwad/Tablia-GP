package iti.team.tablia.Models;


import java.io.Serializable;

public class CartPojo implements Serializable {
  private String chefID;
  private String itemID;
  private String chefName;
  private String itemName;
  private int quantity;
  private double itemPrice;
  private String img;

  public CartPojo() {
  }

  public CartPojo(String chefID, String itemID, String chefName, String itemName, int quantity, double itemPrice, String img) {
    this.chefID = chefID;
    this.itemID = itemID;
    this.chefName = chefName;
    this.itemName = itemName;
    this.quantity = quantity;
    this.itemPrice = itemPrice;
    this.img = img;
  }

  public String getChefID() {
    return chefID;
  }

  public void setChefID(String chefID) {
    this.chefID = chefID;
  }

  public String getItemID() {
    return itemID;
  }

  public void setItemID(String itemID) {
    this.itemID = itemID;
  }

  public String getChefName() {
    return chefName;
  }

  public void setChefName(String chefName) {
    this.chefName = chefName;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(double itemPrice) {
    this.itemPrice = itemPrice;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }
}
