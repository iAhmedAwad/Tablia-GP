package iti.team.tablia.Models;


public class User {

  private String user_id;
  private String fullName;
  private String username;
  private String email;
  private String type;

  public User() {
  }

  public User(String user_id, String fullName, String username, String email, String type) {
    this.user_id = user_id;
    this.fullName = fullName;
    this.username = username;
    this.email = email;
    this.type = type;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "User{" +
        "user_id='" + user_id + '\'' +
        ", fullName='" + fullName + '\'' +
        ", username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
