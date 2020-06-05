package iti.team.tablia.ChefHome.TabBar.Order.OrderDeatils;

public class ItemOrderPojo {
  private String orderId;
  private String itemName;
  private int Quantity;
  private double itemPrice;
  private String itemId;
  private String img;

  public ItemOrderPojo() {
  }

  public ItemOrderPojo(String orderId, String itemName, int quantity, double itemPrice, String itemId, String img) {
    this.orderId = orderId;
    this.itemName = itemName;
    Quantity = quantity;
    this.itemPrice = itemPrice;
    this.itemId = itemId;
    this.img = img;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public int getQuantity() {
    return Quantity;
  }

  public void setQuantity(int quantity) {
    Quantity = quantity;
  }

  public double getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(double itemPrice) {
    this.itemPrice = itemPrice;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }


}

