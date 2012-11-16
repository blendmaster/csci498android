package csci498.strupper.munchlist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.preference.PreferenceManager;

public class OnAlarm extends BroadcastReceiver {
  private static final int NOTIFY_ME_ID = 1337;

  @Override
  public void onReceive(Context context, Intent intent) {
    SharedPreferences prefs =
        PreferenceManager.getDefaultSharedPreferences(context);
    boolean useNotification = prefs.getBoolean("use_notification",
                                               true);
    if (useNotification) {
      ((NotificationManager)
        context.getSystemService(Context.NOTIFICATION_SERVICE))
        .notify(NOTIFY_ME_ID,
                new Notification.Builder(context)
                  .setContentTitle("It's time for lunch!")
                  .setSmallIcon(R.drawable.lunchtime)
                  .setAutoCancel(true)
                  .setSound(prefs.getString("alarm_ringtone", null) != null
                            ? Uri.parse(prefs.getString("alarm_ringtone", null))
                            : null, AudioManager.STREAM_ALARM)
                  .setContentIntent(
                     PendingIntent.getActivity(context,
                                               0,
                                               new Intent(context,
                                                  AlarmActivity.class),
                                                  0))
                  .setContentText("It's time for lunch! Aren't you hungry?")
                  .build());
    } else {
      Intent i = new Intent(context, AlarmActivity.class);
      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

      context.startActivity(i);
    }
  }

}
