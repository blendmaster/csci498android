package csci498.strupper.munchlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MunchList extends Activity {

  List<Restaurant> restaurants = new ArrayList<Restaurant>();
  ArrayAdapter<Restaurant> adapter;

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
      super(MunchList.this, android.R.layout.simple_list_item_1, restaurants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View row = convertView;
      RestaurantHolder holder;

      if (row == null) {
        LayoutInflater inflater = getLayoutInflater();
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
    setContentView(R.layout.activity_munch_list);

    adapter = new RestaurantAdapter();

    Button save = (Button)findViewById(R.id.save);
    save.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        Restaurant r = new Restaurant();
        r.setName(((EditText)findViewById(R.id.name)).getText().toString());
        r.setAddress(((EditText)findViewById(R.id.address)).getText()
                                                           .toString());

        RadioGroup types = (RadioGroup)findViewById(R.id.types);
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

        adapter.add(r);
      }
    });

    ((ListView)findViewById(R.id.restaurants)).setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_munch_list, menu);
    return true;
  }
}
