package csci498.strupper.munchlist;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

public class MunchList extends Activity implements EditRestaurant.SaveRestaurantListener {

  private RestaurantHelper helper;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // setup action bar for tabs
    {
      ActionBar actionBar = getActionBar();
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      actionBar.setDisplayShowTitleEnabled(false);

      Tab tab =
          actionBar.newTab()
                   .setText("Restaurants")
                   .setTabListener(new TabListener<RestaurantsList>(this,
                                                                    "list",
                                                                    RestaurantsList.class));
      actionBar.addTab(tab);

      tab =
          actionBar.newTab()
                   .setText("Details")
                   .setTabListener(
                                   new TabListener<EditRestaurant>(this,
                                                                   "details",
                                                                   EditRestaurant.class));
      actionBar.addTab(tab);
    }

    helper = new RestaurantHelper(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    helper.close();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_munch_list, menu);
    return true;
  }

  public void onRestaurantSave(Restaurant r) {
    helper.insert(r);
  }

  // from http://developer.android.com/guide/topics/ui/actionbar.html
  public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    /** Constructor used each time a new tab is created.
      * @param activity  The host Activity, used to instantiate the fragment
      * @param tag  The identifier tag for the fragment
      * @param clz  The fragment's Class, used to instantiate the fragment
      */
    public TabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, mFragment, mTag);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        }
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
  }
}
