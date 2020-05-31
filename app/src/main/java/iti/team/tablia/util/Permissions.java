package iti.team.tablia.util;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class Permissions {

  public static final String[] PERMISSIONS = {
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.CAMERA,
      Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.READ_EXTERNAL_STORAGE
  };

  public static final String CAMERA_PERMISSIONS = Manifest.permission.CAMERA;
  public static final String ACCESS_FINE_LOCATION_PERMISSIONS = Manifest.permission.ACCESS_FINE_LOCATION;
  public static final String WRITE_STORAGE_PERMISSIONS = Manifest.permission.WRITE_EXTERNAL_STORAGE;
  public static final int VERIFY_PERMISSIONS_REQUEST = 1;

  /**
   * checking an array of permissions
   *
   * @param permissions
   * @return
   */
  public static boolean checkPermissionsArray(Context context, String[] permissions) {
    TM.log("checkPermissionsArray: checking permissions array");
    for (int i = 0; i < permissions.length; i++) {
      String check = permissions[i];
      if (!checkPermission(context, check)) {
        return false;
      }
    }
    return true;
  }

  /**
   * check a single permission has it been verified or not
   *
   * @param permission
   * @return
   */
  public static boolean checkPermission(Context context, String permission) {
    TM.log("checkPermissionsArray: checking permission" + permission);

    int permissionRequest = ActivityCompat.checkSelfPermission(context, permission);
    if (permissionRequest != PackageManager.PERMISSION_GRANTED) {
      TM.log("checkPermissionsArray: Permission isn't granted for" + permission);
      return false;

    }
    TM.log("checkPermissionsArray: Permission is granted for" + permission);

    return true;
  }

  public static void verifyPermissions(Context context, String[] permissions) {
    TM.log("checkPermissionsArray:verifying permissons" + permissions);
    ActivityCompat.requestPermissions((Activity) context,
        permissions,
        VERIFY_PERMISSIONS_REQUEST);
  }
}
