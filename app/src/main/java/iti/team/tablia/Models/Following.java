package iti.team.tablia.Models;

public class Following {

  private String chefId;
  private String profilePhoto;
  private String bio;
  private String fullName;
  private String userName;


  public Following() {
  }

  public Following(String chefId, String profilePhoto,
                   String bio, String fullName, String userName) {
    this.chefId = chefId;
    this.profilePhoto = profilePhoto;
    this.bio = bio;
    this.fullName = fullName;
    this.userName = userName;
  }

  public String getChefId() {
    return chefId;
  }

  public void setChefId(String chefId) {
    this.chefId = chefId;
  }

  public String getProfilePhoto() {
    return profilePhoto;
  }

  public void setProfilePhoto(String profilePhoto) {
    this.profilePhoto = profilePhoto;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
