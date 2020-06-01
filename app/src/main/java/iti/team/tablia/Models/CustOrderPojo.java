package iti.team.tablia.Models;

public class CustOrderPojo {
    private String chefId;
    private String orderId;
    private boolean confirmed;

    public CustOrderPojo() {
        confirmed = false;
    }

    public CustOrderPojo(String chefId, String orderId) {
        this.chefId = chefId;
        this.orderId = orderId;
        confirmed = false;
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
