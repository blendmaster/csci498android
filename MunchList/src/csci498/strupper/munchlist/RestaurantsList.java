package csci498.strupper.munchlist;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RestaurantsList extends ListFragment {
  List<Restaurant> restaurants = new ArrayList<Restaurant>();
  RestaurantAdapter adapter;

  static class RestaurantHolder {
    private final TextView name, address;

    RestaurantHolder(View row) {
      name = (TextView)row.findViewById(R.id.title);
      address = (TextView)row.findViewById(R.id.address);
    }

    void populateFrom(Restaurant r) {
      name.setText(r.getName());
      address.setText(r.getAddress());
    }
  }

  class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    public RestaurantAdapter() {
      super(getActivity(),
            android.R.layout.simple_list_item_1,
            restaurants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View row = convertView;
      RestaurantHolder holder;

      if (row == null) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        row = inflater.inflate(R.layout.restaurant_row, null);
        holder = new RestaurantHolder(row);
        row.setTag(holder);
      } else {
        holder = (RestaurantHolder)row.getTag();
      }

      holder.populateFrom(restaurants.get(position));

      return row;
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    adapter = new RestaurantAdapter();
    setListAdapter(adapter);
  }

  public void add(Restaurant r) {
    adapter.add(r);
  }

}
