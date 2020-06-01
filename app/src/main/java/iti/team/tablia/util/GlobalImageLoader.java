package iti.team.tablia.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

import iti.team.tablia.R;

public class GlobalImageLoader {

  private static GlobalImageLoader globalImageLoader = null;

  private GlobalImageLoader() {
  }

  static GlobalImageLoader getInstance() {
    if (globalImageLoader == null) {

      globalImageLoader = new GlobalImageLoader();
      return globalImageLoader;
    } else {
      return globalImageLoader;
    }
  }

  public static void setImage(Context context, ImageView imageView, String url) {
    TM.log("context is " + context);
    if (context != null) {

      Glide
          .with(context)
          .load(url)
          .centerCrop()
          .placeholder(R.drawable.image_placeholder)
          .error(R.drawable.image_placeholder)
          .into(imageView);
    }
  }

  public static Bitmap StringToBitMap(String encodedString) {
    try {
      Bitmap bitmap;
      byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
      bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
          encodeByte.length);
      return bitmap;
    } catch (Exception e) {
      e.getMessage();
      return null;
    }
  }

  public static String BitMapToString(Bitmap bitmap) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    byte[] b = baos.toByteArray();
    String temp = Base64.encodeToString(b, Base64.DEFAULT);
    return temp;
  }
}
