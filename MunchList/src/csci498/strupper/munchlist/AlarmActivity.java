package csci498.strupper.munchlist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class AlarmActivity extends Activity implements OnPreparedListener {
  MediaPlayer player = new MediaPlayer();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.alarm);

    SharedPreferences prefs =
        PreferenceManager.getDefaultSharedPreferences(this);
    String sound = prefs.getString("alarm_ringtone", null);
    if (sound != null) {
      player.setAudioStreamType(AudioManager.STREAM_ALARM);
    }
    try {
      player.setDataSource(sound);
      player.setOnPreparedListener(this);
      player.prepareAsync();
    } catch (Exception e) {
      Log.e("LunchList", "Exception in playing ringtone", e);
    }

  }

  public void onPrepared(MediaPlayer mp) {
    player.start();
  }

 @Override
  protected void onPause() {
    if (player.isPlaying()) {
      player.stop();
    }
    super.onPause();
  }

}
