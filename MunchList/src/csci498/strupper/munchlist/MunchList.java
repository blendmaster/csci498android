package csci498.strupper.munchlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MunchList extends Activity implements
    MunchFragment.OnRestaurantListener {
  public final static String ID_EXTRA = "apt.tutorial._ID";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    MunchFragment lunch =
        (MunchFragment)(getFragmentManager()
                                            .findFragmentById(R.id.lunch));

    lunch.setOnRestaurantListener(this);
  }

  public void onRestaurantSelected(long id) {
    Intent i = new Intent(this, EditRestaurant.class);
    i.putExtra(ID_EXTRA, String.valueOf(id));
    startActivity(i);
  }

}
