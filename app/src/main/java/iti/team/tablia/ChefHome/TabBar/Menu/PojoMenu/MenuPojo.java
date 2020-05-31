package iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu;


import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.MenuItemPojo;

public class MenuPojo {
    private String itemID;
    private String chefID;
    private String itemName;
    private double priceItem;
    private List<MenuItemPojo> imgItem;
    private String category;
    private  String description ;
    private String ingredients ;
    private int itemQuantity;

    public MenuPojo() {
    }

    public MenuPojo(String itemID, String chefID, String itemName, double priceItem, List<MenuItemPojo> imgItem, String category, String description, String ingredients, int itemQuantity) {
        this.itemID = itemID;
        this.chefID = chefID;
        this.itemName = itemName;
        this.priceItem = priceItem;
        this.imgItem = imgItem;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(double priceItem) {
        this.priceItem = priceItem;
    }

    public List<MenuItemPojo> getImgItem() {
        return imgItem;
    }

    public void setImgItem(List<MenuItemPojo> imgItem) {
        this.imgItem = imgItem;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getChefID() {
        return chefID;
    }

    public void setChefID(String chefID) {
        this.chefID = chefID;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
