package iti.team.tablia.Models;

import java.util.List;

public class CartGroupPojo {
  private String chefId;
  private List<CartPojo> cartPojos;

  public CartGroupPojo() {
  }

  public CartGroupPojo(String chefId, List<CartPojo> cartPojos) {
    this.chefId = chefId;
    this.cartPojos = cartPojos;
  }

  public String getChefId() {
    return chefId;
  }

  public void setChefId(String chefId) {
    this.chefId = chefId;
  }

  public List<CartPojo> getCartPojos() {
    return cartPojos;
  }

  public void setCartPojos(List<CartPojo> cartPojos) {
    this.cartPojos = cartPojos;
  }
}
