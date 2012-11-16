package csci498.strupper.munchlist;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EditFragment extends Fragment {

  private RestaurantHelper helper;
  private String restaurantId;
  private final LocationListener onLocationChange = new LocationListener() {
    public void onLocationChanged(Location fix) {
      helper.updateLocation(restaurantId,
                            fix.getLatitude(),
                            fix.getLongitude());
      ((TextView)getActivity().findViewById(R.id.location))
                                                           .setText(String.valueOf(
                                                                          fix.getLatitude())
                                                               + ", "
                                                               + String.valueOf(fix.getLongitude()));
      getActivity();
      ((LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE))
                                                                                 .removeUpdates(onLocationChange);
      Toast
           .makeText(getActivity(), "Location saved",
                     Toast.LENGTH_LONG)
           .show();
    }

    public void onProviderDisabled(String provider) {
      // required for interface, not used
    }

    public void onProviderEnabled(String provider) {
      // required for interface, not used
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
      // required for interface, not used
    }
  };

  private Double lat = 0d;
  private Double lon = 0d;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return (inflater.inflate(R.layout.edit_restaurant, container, false));
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);


  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    helper = new RestaurantHelper(getActivity());

    restaurantId = getActivity().getIntent().getStringExtra(MunchList.ID_EXTRA);

    // restore form
    if (savedInstanceState != null) {
      String s;
      if (restaurantId == null
          && (s = savedInstanceState.getString("id")) != null) {
        restaurantId = s;
      }
      if ((s = savedInstanceState.getString("name")) != null) {
        ((EditText)getView().findViewById(R.id.name)).setText(s);
      }
      if ((s = savedInstanceState.getString("address")) != null) {
        ((EditText)getView().findViewById(R.id.address)).setText(s);
      }
      if ((s = savedInstanceState.getString("feed")) != null) {
        ((EditText)getView().findViewById(R.id.feed)).setText(s);
      }
      if ((s = savedInstanceState.getString("type")) != null) {
        if (s.equals("sit_down")) {
          ((RadioButton)getView().findViewById(R.id.sit_down)).setChecked(true);
        } else if (s.equals("take_out")) {
          ((RadioButton)getView().findViewById(R.id.take_out)).setChecked(true);
        } else if (s.equals("delivery")) {
          ((RadioButton)getView().findViewById(R.id.delivery)).setChecked(true);
        }
      }
    }

    if (restaurantId != null) {
      load();
    }
  }

  @Override
  public void onPrepareOptionsMenu(Menu menu) {
    if (restaurantId == null) {
      menu.findItem(R.id.setLocation).setEnabled(false);
      menu.findItem(R.id.map).setEnabled(false);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    helper = new RestaurantHelper(getActivity());
    restaurantId = getActivity().getIntent().getStringExtra(MunchList.ID_EXTRA);
    if (restaurantId != null) {
      load();
    }
  }

  @Override
  public void onPause() {
    save();
    ((LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE))
                                                         .removeUpdates(onLocationChange);
    helper.close();
    super.onPause();
  };

  private void setFrom(Restaurant r) {
    ((EditText)getView().findViewById(R.id.name)).setText(r.getName());
    ((EditText)getView().findViewById(R.id.address)).setText(r.getAddress());
    ((EditText)getView().findViewById(R.id.feed)).setText(r.getFeed());
    RadioGroup types = (RadioGroup)getView().findViewById(R.id.types);
    if (r.getType().equals("sit_down")) {
      types.check(R.id.sit_down);
    }
    else if (r.getType().equals("take_out")) {
      types.check(R.id.take_out);
    }
    else {
      types.check(R.id.delivery);
    }
    ((TextView)getView().findViewById(R.id.location))
                                           .setText(r.getLat() + ", "
                                               + r.getLon());

    lat = r.getLat();
    lon = r.getLon();
  }

  private void load() {
    Cursor c = helper.getById(restaurantId);
    c.moveToFirst();
    setFrom(RestaurantHelper.restaurantOf(c));
    c.close();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    if (restaurantId != null) {
      outState.putString("id", restaurantId);
    }
    outState
            .putString("name",
                       ((EditText)getView().findViewById(R.id.name)).getText().toString());
    outState
            .putString("address",
                       ((EditText)getView().findViewById(R.id.address)).getText()
                                                             .toString());
    outState
            .putString("feed",
                       ((EditText)getView().findViewById(R.id.feed)).getText().toString());
    RadioGroup types = (RadioGroup)getView().findViewById(R.id.types);
    switch (types.getCheckedRadioButtonId()) {
    case R.id.sit_down:
      outState.putString("type", "sit_down");
      break;
    case R.id.take_out:
      outState.putString("type", "take_out");
      break;
    case R.id.delivery:
      outState.putString("type", "delivery");
      break;
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.activity_edit_restaurant, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.viewFeed:
      if (isNetworkAvailable()) {
        Intent i = new Intent(getActivity(), FeedActivity.class);
        i.putExtra(FeedActivity.FEED_URL,
                   ((EditText)getView().findViewById(R.id.feed)).getText().toString());
        startActivity(i);
      }
      else {
        Toast
             .makeText(getActivity(),
                       "Sorry, the Internet is not available",
                       Toast.LENGTH_LONG)
             .show();
      }
      return true;
    case R.id.setLocation:
      ((LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE))
                                                           .requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                                                                   0,
                                                                                   0,
                                                                                   onLocationChange);
      return true;
    case R.id.map:
      Intent i = new Intent(getActivity(), RestaurantMap.class);
      i.putExtra(RestaurantMap.EXTRA_LAT, lat);
      i.putExtra(RestaurantMap.EXTRA_LON, lon);
      i.putExtra(RestaurantMap.EXTRA_NAME,
                 ((EditText)getView().findViewById(R.id.name)).getText().toString());
      startActivity(i);
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
  }

  private boolean isNetworkAvailable() {
    return ((ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE))
                                                                        .getActiveNetworkInfo() != null;
  }

  private void save() {
    Restaurant r = new Restaurant();
    r.setName(((EditText)getView().findViewById(R.id.name))
                                                 .getText()
                                                 .toString());
    r.setAddress(((EditText)getView().findViewById(R.id.address))
                                                       .getText()
                                                       .toString());
    r.setFeed(((EditText)getView().findViewById(R.id.feed))
                                                 .getText()
                                                 .toString());

    RadioGroup types = (RadioGroup)getView().findViewById(R.id.types);
    switch (types.getCheckedRadioButtonId()) {
    case R.id.sit_down:
      r.setType("sit_down");
      break;
    case R.id.take_out:
      r.setType("take_out");
      break;
    case R.id.delivery:
      r.setType("delivery");
      break;
    }

    if (restaurantId == null) {
      helper.insert(r);
    } else {
      helper.update(restaurantId, r);
    }
  }

}
