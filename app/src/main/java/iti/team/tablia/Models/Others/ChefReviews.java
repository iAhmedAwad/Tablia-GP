package iti.team.tablia.Models.Others;

public class ChefReviews {

  private Review mReview;
  private String itemName;
  private String customerName;
  private String chefName;

  public ChefReviews() {
  }

  public ChefReviews(Review review, String itemName,
                     String customerName, String chefName) {
    mReview = review;
    this.itemName = itemName;
    this.customerName = customerName;
    this.chefName = chefName;
  }

  public Review getReview() {
    return mReview;
  }

  public void setReview(Review review) {
    mReview = review;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getChefName() {
    return chefName;
  }

  public void setChefName(String chefName) {
    this.chefName = chefName;
  }
}
