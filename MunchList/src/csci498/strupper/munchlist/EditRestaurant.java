package csci498.strupper.munchlist;

import android.app.Activity;
import android.os.Bundle;

public class EditRestaurant extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.detail_activity);
  }

  @Override
  public void onResume() {
    super.onResume();
    String restaurantId = getIntent().getStringExtra(MunchList.ID_EXTRA);
    if (restaurantId != null) {
      EditFragment details =
          (EditFragment)getFragmentManager()
                                            .findFragmentById(R.id.details);
      if (details != null) {
        details.loadRestaurant(restaurantId);
      }
    }
  }

}
