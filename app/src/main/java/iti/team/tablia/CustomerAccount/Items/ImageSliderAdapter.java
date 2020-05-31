package iti.team.tablia.CustomerAccount.Items;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import iti.team.tablia.ChefHome.TabBar.Menu.AddMenu.MenuItemPojo;

public class ImageSliderAdapter extends PagerAdapter {
  private Context mContext;
  private List<MenuItemPojo> mImgIds;

  public ImageSliderAdapter(Context mContext, List<MenuItemPojo> mImgIds) {
    this.mContext = mContext;
    this.mImgIds = mImgIds;
  }

  @Override
  public int getCount() {
    return mImgIds.size();
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    ImageView imageView = new ImageView(mContext);
    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    Bitmap photo = stringToBitMap(mImgIds.get(position).getImgaeItem());
    imageView.setImageBitmap(photo);
    container.addView(imageView, 0);
    return imageView;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((ImageView) object);
  }

  public Bitmap stringToBitMap(String encodedString) {
    try {
      byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
      Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
          encodeByte.length);
      return bitmap;
    } catch (Exception e) {
      e.getMessage();
      return null;
    }
  }

}
