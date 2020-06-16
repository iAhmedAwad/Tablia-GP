package iti.team.tablia.Models.Customer;

public class CustomerAccountSettings {

  private String displayName;
  private String bio;
  private String profilePhoto;
  private String userName;
  private String address;
  private String phoneNumber;
  private String status;
  private int orders;
  private int following;

  public CustomerAccountSettings() {
    displayName = "John doe";
    bio = "A description";
    profilePhoto = "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/p960x960/" +
        "93821865_2902076849888497_5406484177707073536_o.jpg?_nc_cat=101" +
        "&_nc_sid=85a577&_nc_oc=AQlj-eVDRBwwy9R29oF6_tAFkeETi5woam0F9CU6biVd9uvc210SgPYDPlgllflNX9E" +
        "&_nc_ht=scontent-hbe1-1.xx&_nc_tp=6&oh=2c60b072ef5edb4fc64b5d0fea2c11e4&oe=5EE1935F";
    userName = "JohnDoe";
    address = "Please, edit profile so you can enter your address";
    phoneNumber = "0123456789";
    orders = 0;
    following = 0;
    status = "offline";
  }

  public CustomerAccountSettings(String displayName, String bio,
                                 String profilePhoto, String userName,
                                 String address, String phoneNumber, String status, int orders, int following) {
    this.displayName = displayName;
    this.bio = bio;
    this.profilePhoto = profilePhoto;
    this.userName = userName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.status = status;
    this.orders = orders;
    this.following = following;
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

  public int getOrders() {
    return orders;
  }

  public void setOrders(int orders) {
    this.orders = orders;
  }

  public int getFollowing() {
    return following;
  }

  public void setFollowing(int following) {
    this.following = following;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "CustomerAccountSettings{" +
        "displayName='" + displayName + '\'' +
        ", description='" + bio + '\'' +
        ", profilePhoto='" + profilePhoto + '\'' +
        ", userName='" + userName + '\'' +
        ", address='" + address + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", orders=" + orders +
        ", follwing=" + following +
        '}';
  }
}
