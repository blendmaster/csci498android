package csci498.strupper.munchlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class MunchFragment extends android.app.ListFragment {

  public interface OnRestaurantListener {
    void onRestaurantSelected(long id);
  }

  private OnRestaurantListener listener;

  public void setOnRestaurantListener(OnRestaurantListener listener) {
    this.listener = listener;
  }

  RestaurantAdapter adapter;
  private RestaurantHelper helper;
  private SharedPreferences prefs;

  static class RestaurantHolder {
    private final TextView name, address;

    RestaurantHolder(View row) {
      name = (TextView)row.findViewById(R.id.title);
      address = (TextView)row.findViewById(R.id.address);
    }

    void populateFrom(Cursor c) {
      Restaurant r = RestaurantHelper.restaurantOf(c);
      name.setText(r.getName());
      address.setText(r.getAddress());
    }
  }

  class RestaurantAdapter extends CursorAdapter {
    public RestaurantAdapter(Cursor c) {
      super(getActivity(), c, true);
    }

    @Override
    public void bindView(View row, Context context, Cursor c) {
      ((RestaurantHolder)row.getTag()).populateFrom(c);

    }

    @Override
    public View newView(Context context, Cursor c, ViewGroup parent) {
      LayoutInflater inflater = getActivity().getLayoutInflater();
      View row = inflater.inflate(R.layout.restaurant_row, null);
      RestaurantHolder holder = new RestaurantHolder(row);
      row.setTag(holder);
      return row;
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

  }

  private void refresh() {
    adapter =
        new RestaurantAdapter(
                              helper.getAll(prefs.getString("sort_order",
                                                            "name")));
    setListAdapter(adapter);
  }

  @Override
  public void onPause() {
    super.onPause();
    helper.close();
  }

  @Override
  public void onResume() {
    super.onResume();

    helper = new RestaurantHelper(getActivity());
    prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    prefs.registerOnSharedPreferenceChangeListener(
         new OnSharedPreferenceChangeListener() {
           public void
               onSharedPreferenceChanged(
                                         SharedPreferences sharedPreferences,
                                         String key)
           {
             if (key.equals("sort_order")) {
               refresh();
             }
           }
         });
    refresh();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.activity_munch_list, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.add_restaurant) {
      startActivity(new Intent(getActivity(), EditRestaurant.class));
      return true;
    } else if (item.getItemId() == R.id.menu_settings) {
      startActivity(new Intent(getActivity(), Preferences.class));
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public final static String ID_EXTRA = "apt.tutorial._ID";

  @Override
  public void onListItemClick(ListView list, View view,
                              int position, long id) {
    if (listener != null) {
      listener.onRestaurantSelected(id);
    }

  }

}
