package iti.team.tablia.ChefHome.TabBar.Order;

import java.io.Serializable;
import java.util.List;

import iti.team.tablia.Models.CartPojo;


public class OrderPojo implements Serializable {

  private String orderID;
  private String customerID;
  private String OrderTime;
  private String chefID;
  private String chefName;
  private boolean deliveryType;
  private double subTotal;
  private double deliveryFee;
  private String deliveryTime;
  private List<CartPojo> items;
  private boolean chefConfirm;
  private boolean custConfirm;
  private double total;

  public OrderPojo(String chefID, String chefName, double subTotal, List<CartPojo> items) {
    this.chefID = chefID;
    this.chefName = chefName;
    this.subTotal = subTotal;
    this.items = items;
    ////////////////////
    deliveryFee = 0;
    deliveryTime = "_";
    chefConfirm = false;
    custConfirm = false;
    total = 0.0;
  }

  public OrderPojo() {
    deliveryFee = 0;
    deliveryTime = "_";
    chefConfirm = false;
    custConfirm = false;
    total = 0.0;
  }

  public String getOrderID() {
    return orderID;
  }

  public void setOrderID(String ordeerID) {
    this.orderID = ordeerID;
  }

  public String getCustomerID() {
    return customerID;
  }

  public void setCustomerID(String customerID) {
    this.customerID = customerID;
  }

  public String getOrderTime() {
    return OrderTime;
  }

  public void setOrderTime(String orderTime) {
    OrderTime = orderTime;
  }

  public boolean isDeliveryType() {
    return deliveryType;
  }

  public void setDeliveryType(boolean deliveryType) {
    this.deliveryType = deliveryType;
  }

  public String getChefID() {
    return chefID;
  }

  public void setChefID(String chefID) {
    this.chefID = chefID;
  }

  public String getChefName() {
    return chefName;
  }

  public void setChefName(String chefName) {
    this.chefName = chefName;
  }

  public double getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(double subTotal) {
    this.subTotal = subTotal;
  }

  public double getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(double deliveryFee) {
    this.deliveryFee = deliveryFee;
  }

  public String getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(String deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public List<CartPojo> getItems() {
    return items;
  }

  public void setItems(List<CartPojo> items) {
    this.items = items;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public boolean isCustConfirm() {
    return custConfirm;
  }

  public void setCustConfirm(boolean custConfirm) {
    this.custConfirm = custConfirm;
  }

  public boolean isChefConfirm() {
    return chefConfirm;
  }

  public void setChefConfirm(boolean chefConfirm) {
    this.chefConfirm = chefConfirm;
  }

}
