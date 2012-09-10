package csci498.strupper.munchlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MunchList extends Activity implements EditRestaurant.SaveRestaurantListener {


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_munch_list);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_munch_list, menu);
    return true;
  }

  public void onRestaurantSave(Restaurant r) {
        ((RestaurantsList)getFragmentManager()
                         .findFragmentById(R.id.restaurants))
                         .add(r);
  }
}
