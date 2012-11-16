package csci498.strupper.munchlist;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
    if (findViewById(R.id.details) == null) {
      Intent i = new Intent(this, EditRestaurant.class);
      i.putExtra(ID_EXTRA, String.valueOf(id));
      startActivity(i);
    }
    else {
      FragmentManager fragMgr = getFragmentManager();
      EditFragment details =
          (EditFragment)fragMgr.findFragmentById(R.id.details);
      if (details == null) {
        details = EditFragment.newInstance(id);
        FragmentTransaction xaction = fragMgr.beginTransaction();
        xaction
               .add(R.id.details, details)
               .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
               .addToBackStack(null)
               .commit();
      }
      else {
        details.loadRestaurant(String.valueOf(id));
      }

    }

  }

}
