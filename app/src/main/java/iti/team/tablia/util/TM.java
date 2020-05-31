package iti.team.tablia.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TM {

  public static void log(String message) {
    Log.d("Debug", message);
  }

  public static void toast(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }
}
