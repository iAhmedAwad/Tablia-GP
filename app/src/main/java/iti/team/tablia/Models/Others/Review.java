package iti.team.tablia.Models.Others;

public class Review {

  private String reviewText;
  private float rating;
  private String chefId;
  private String customerId;
  private String itemId;
  private String itemName;
  private String reviewId;

  public Review() {
  }


  public Review(String reviewText, float rating,
                String chefId, String customerId,
                String itemId, String reviewId,String itemName) {
    this.reviewText = reviewText;
    this.rating = rating;
    this.chefId = chefId;
    this.customerId = customerId;
    this.itemId = itemId;
    this.reviewId = reviewId;
    this.itemName = itemName;
  }

  public Review(Review review) {
    this.reviewText = review.getReviewText();
    this.rating = review.getRating();
    this.chefId = review.getChefId();
    this.customerId = review.getCustomerId();
    this.itemId = review.getItemId();
    this.reviewId = review.getReviewId();
  }

  public String getReviewText() {
    return reviewText;
  }

  public void setReviewText(String reviewText) {
    this.reviewText = reviewText;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public String getChefId() {
    return chefId;
  }

  public void setChefId(String chefId) {
    this.chefId = chefId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getReviewId() {
    return reviewId;
  }

  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  @Override
  public String toString() {
    return "Review{" +
        "reviewText='" + reviewText + '\'' +
        ", rating=" + rating +
        ", chefId='" + chefId + '\'' +
        ", customerId='" + customerId + '\'' +
        ", itemId='" + itemId + '\'' +
        ", reviewId='" + reviewId + '\'' +
        '}';
  }
}
