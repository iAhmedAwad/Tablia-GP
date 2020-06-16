package iti.team.tablia.Models;

public class ChatUser {
    private String id;
    private String username;
    private String imageURL;
    private String status;
    private String lastMsgTime;
    private String lastMsg;

    public ChatUser() {
    }

    public ChatUser(String id, String username, String imageURL, String status, String lastMsgTime, String lastMsg) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.status = status;
      this.lastMsgTime = lastMsgTime;
      this.lastMsg = lastMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

  public String getLastMsgTime() {
    return lastMsgTime;
  }

  public void setLastMsgTime(String lastMsgTime) {
    this.lastMsgTime = lastMsgTime;
  }

  public String getLastMsg() {
    return lastMsg;
  }

  public void setLastMsg(String lastMsg) {
    this.lastMsg = lastMsg;
  }
}
