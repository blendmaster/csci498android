package csci498.strupper.munchlist;

import android.os.Bundle;

import com.google.android.maps.MapActivity;

public class RestaurantMap extends MapActivity {
  public static final String EXTRA_NAME = "munchlist.EXTRA_NAME";
  public static final String EXTRA_LON = "munchlist.EXTRA_LON";
  public static final String EXTRA_LAT = "munchlist.EXTRA_LAT";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.map);
  }

  @Override
  protected boolean isRouteDisplayed() {
    // TODO Auto-generated method stub
    return false;
  }

}
