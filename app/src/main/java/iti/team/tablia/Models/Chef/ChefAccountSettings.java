package iti.team.tablia.Models.Chef;

public class ChefAccountSettings {
  private String displayName;
  private String bio;
  private String profilePhoto;
  private String userName;
  private String address;
  private String phoneNumber;
  private String start_order_time;
  private String end_order_time;
  private String status;
  private float rating;
  private int orders;
  private int followers;
  private boolean isAvailable;

  public ChefAccountSettings() {
    displayName = "John Doe";
    bio = "Here is the description";
    profilePhoto = "some profile";
    userName = "unique user name";
    address = "address";
    phoneNumber = "123456789";
    start_order_time = "00:00";
    end_order_time = "00:00";
    status = "offline";
    rating = 0;
    orders = 0;
    followers = 0;
    isAvailable = false;

  }

  public ChefAccountSettings(String displayName, String bio,
                             String profilePhoto, String userName, String address,
                             String phoneNumber, String start_order_time, String end_order_time, String status,
                             float rating, int orders, int followers,
                             boolean isAvailable) {
    this.displayName = displayName;
    this.bio = bio;
    this.profilePhoto = profilePhoto;
    this.userName = userName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.start_order_time = start_order_time;
    this.end_order_time = end_order_time;
    this.status = status;
    this.rating = rating;
    this.orders = orders;
    this.followers = followers;
    this.isAvailable = isAvailable;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getProfilePhoto() {
    return profilePhoto;
  }

  public void setProfilePhoto(String profilePhoto) {
    this.profilePhoto = profilePhoto;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getStart_order_time() {
    return start_order_time;
  }

  public void setStart_order_time(String start_order_time) {
    this.start_order_time = start_order_time;
  }

  public String getEnd_order_time() {
    return end_order_time;
  }

  public void setEnd_order_time(String end_order_time) {
    this.end_order_time = end_order_time;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public int getOrders() {
    return orders;
  }

  public void setOrders(int orders) {
    this.orders = orders;
  }

  public int getFollowers() {
    return followers;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  @Override
  public String toString() {
    return "ChefAccountSettings{" +
        "displayName='" + displayName + '\'' +
        ", description='" + bio + '\'' +
        ", profilePhoto='" + profilePhoto + '\'' +
        ", userName='" + userName + '\'' +
        ", address='" + address + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", start_order_time='" + start_order_time + '\'' +
        ", end_order_time='" + end_order_time + '\'' +
        ", status='" + status + '\'' +
        ", rating=" + rating +
        ", orders=" + orders +
        ", followers=" + followers +
        ", isAvailable=" + isAvailable +
        '}';
  }
}
