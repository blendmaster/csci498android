package csci498.strupper.munchlist;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class Preferences extends PreferenceActivity {
  private final OnSharedPreferenceChangeListener onChange =
      new OnSharedPreferenceChangeListener() {

        public void onSharedPreferenceChanged(SharedPreferences prefs,
                                              String key) {
          if ("alarm".equals(key)) {
            boolean enabled = prefs.getBoolean(key, false);
            int flag = (enabled ?
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
            ComponentName component = new ComponentName(Preferences.this,
                                                        OnBoot.class);
            getPackageManager()
                               .setComponentEnabledSetting(component,
                                                           flag,
                                                           PackageManager.DONT_KILL_APP);
            if (enabled) {
              OnBoot.setAlarm(Preferences.this);
            }
            else {
              OnBoot.cancelAlarm(Preferences.this);
            }
          }
          else if ("alarm_time".equals(key)) {
            OnBoot.cancelAlarm(Preferences.this);
            OnBoot.setAlarm(Preferences.this);
          }

        }
      };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Display the fragment as the main content.
    getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new PrefsFragment())
                        .commit();
  }

  public static class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Load the preferences from an XML resource
      addPreferencesFromResource(R.xml.preferences);
    }
  }

  @Override
  public void onPause() {
    PreferenceManager.getDefaultSharedPreferences(this)
                     .unregisterOnSharedPreferenceChangeListener(onChange);
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();

    PreferenceManager.getDefaultSharedPreferences(this)
                     .registerOnSharedPreferenceChangeListener(onChange);
  }
}
