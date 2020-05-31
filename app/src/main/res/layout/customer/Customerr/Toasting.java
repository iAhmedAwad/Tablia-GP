package com.awad.tablia.Customer.Customerr;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class Toasting {

  public static void toastAnywhere(final Context context, final String text, final int length) {
    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(new Runnable() {
      public void run() {

        if (length == 0) {

          Toast.makeText(context, text,
              Toast.LENGTH_SHORT).show();
        } else {

          Toast.makeText(context, text,
              Toast.LENGTH_LONG).show();
        }
      }
    });
  }
}
