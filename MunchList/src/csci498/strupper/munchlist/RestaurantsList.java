package csci498.strupper.munchlist;

import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RestaurantsList extends ListFragment {
  RestaurantAdapter adapter;
  private RestaurantHelper helper;

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

    helper = new RestaurantHelper(getActivity());
    adapter = new RestaurantAdapter(helper.getAll());
    setListAdapter(adapter);
  }
}
