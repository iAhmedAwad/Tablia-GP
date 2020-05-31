package iti.team.tablia.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import iti.team.tablia.ChefHome.TabBar.Chat.Messages.MessageActivity;
import iti.team.tablia.others.Token;

public class MyFirebaseIdService extends FirebaseMessagingService {
  private static final String TAG = "msg";

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    String sent = remoteMessage.getData().get("sent");
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    if (firebaseUser != null && sent.equals(firebaseUser.getUid())) {
      sendNotification(remoteMessage);
    }
  }

  private void sendNotification(RemoteMessage remoteMessage) {
    String user = remoteMessage.getData().get("user");
    String icon = remoteMessage.getData().get("icon");
    String title = remoteMessage.getData().get("title");
    String body = remoteMessage.getData().get("body");
    RemoteMessage.Notification notification = remoteMessage.getNotification();
    int j = Integer.parseInt(user.replaceAll("[\\D]", ""));
    Intent intent = new Intent(this, MessageActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString("userid", user);
    intent.putExtras(bundle);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);
    Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    String NOTIFICATION_CHANNEL_ID = "com.huda.chapp.test";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "notification", NotificationManager.IMPORTANCE_HIGH);
      notificationChannel.setDescription("ChatApp channel");
      notificationChannel.enableLights(true);
      notificationChannel.setLightColor(Color.BLUE);
      notificationChannel.setVibrationPattern(new long[]{0, 100, 500, 1000});
      notificationManager.createNotificationChannel(notificationChannel);
    }
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        .setDefaults(Notification.DEFAULT_ALL)
        .setWhen(System.currentTimeMillis())
        .setContentInfo("Info")
        .setSmallIcon(Integer.parseInt(icon))
        .setContentTitle(title)
        .setContentText(body)
        .setAutoCancel(true)
        .setSound(defaultSound)
        .setContentIntent(pendingIntent);
    int i = 0;
    if (j > 0) {
      i = j;
    }
    notificationManager.notify(i, builder.build());
  }

  @Override
  public void onNewToken(String token) {
    Log.d(TAG, "Refreshed token: " + token);

    sendRegistrationToServer(token);
  }

  private void sendRegistrationToServer(String token) {
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    if (firebaseUser != null) {
      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tokens");
      Token token1 = new Token(token);
      reference.child(firebaseUser.getUid()).setValue(token1);
    }
  }
}
