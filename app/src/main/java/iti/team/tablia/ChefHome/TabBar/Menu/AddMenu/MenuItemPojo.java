package iti.team.tablia.ChefHome.TabBar.Menu.AddMenu;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItemPojo implements Parcelable {

  String imgaeItem;

  public MenuItemPojo() {

  }

  public MenuItemPojo(String imgaeItem) {
    this.imgaeItem = imgaeItem;
  }

    protected MenuItemPojo(Parcel in) {
        imgaeItem = in.readString();
    }

    public static final Creator<MenuItemPojo> CREATOR = new Creator<MenuItemPojo>() {
        @Override
        public MenuItemPojo createFromParcel(Parcel in) {
            return new MenuItemPojo(in);
        }

        @Override
        public MenuItemPojo[] newArray(int size) {
            return new MenuItemPojo[size];
        }
    };

    public String getImgaeItem() {
    return imgaeItem;
  }

  public void setImgaeItem(String imgaeItem) {
    this.imgaeItem = imgaeItem;
  }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgaeItem);
    }
}
