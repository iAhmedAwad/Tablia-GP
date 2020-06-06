package iti.team.tablia.Models;

public class ChatList {
    private String chefId;
    private String custId;
    private String lastMsgTime;
    private String lastMsg;

    public ChatList() {
    }

    public ChatList(String chefId, String custId, String lastMsgTime, String lastMsg) {
        this.chefId = chefId;
        this.custId = custId;
        this.lastMsgTime = lastMsgTime;
        this.lastMsg = lastMsg;
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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
