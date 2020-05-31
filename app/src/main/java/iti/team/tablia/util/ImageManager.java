package iti.team.tablia.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageManager {

  private static final String TAG = "ImageManager";

  public static Bitmap getBitmap(String imageUrl) {

    File imageFile = new File(imageUrl);
    FileInputStream fis = null;
    Bitmap bitmap = null;

    try {
      fis = new FileInputStream(imageFile);
      bitmap = BitmapFactory.decodeStream(fis);
    } catch (FileNotFoundException e) {

      TM.log("FileNotFoundException" + e.getMessage());
    } finally {
      try {
        fis.close();
      } catch (IOException e) {

        TM.log("IOException" + e.getMessage());
      }
    }
    return bitmap;
  }

  /**
   * returns a byte array from a bit map
   * quality is greater than 0 and greater than 100
   *
   * @param bitmap
   * @param quality
   * @return
   */

  public static byte[] getBytesFromBitmap(Bitmap bitmap, int quality) {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
    return stream.toByteArray();
  }
}
