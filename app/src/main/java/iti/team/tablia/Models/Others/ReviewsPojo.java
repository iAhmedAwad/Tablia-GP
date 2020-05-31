package iti.team.tablia.Models.Others;

public class ReviewsPojo {
  private int cust_id;
  private int chief_id;
  private int item_id;
  private String reviews;

  public ReviewsPojo() {
  }


  public ReviewsPojo(int cust_id, int chief_id, int item_id, String reviews) {
    this.cust_id = cust_id;
    this.chief_id = chief_id;
    this.item_id = item_id;

  }

  public int getCust_id() {
    return cust_id;
  }

  public void setCust_id(int cust_id) {
    this.cust_id = cust_id;
  }

  public int getChief_id() {
    return chief_id;
  }

  public void setChief_id(int chief_id) {
    this.chief_id = chief_id;
  }

  public int getItem_id() {
    return item_id;
  }

  public void setItem_id(int item_id) {
    this.item_id = item_id;
  }

  public String getReviews() {
    return reviews;
  }

  public void setReviews(String reviews) {
    this.reviews = reviews;
  }

}
