package csci498.strupper.munchlist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

public class MunchList extends Activity {

	List<Restaurant> restaurants = new ArrayList<Restaurant>();
	ArrayAdapter<Restaurant> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_munch_list);
		
		adapter = new ArrayAdapter<Restaurant>(
					this, 
		            android.R.layout.simple_list_item_1,
		            restaurants);

		Button save = (Button)findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Restaurant r = new Restaurant();
				r.setName(((EditText) findViewById(R.id.name)).getText()
						.toString());
				r.setAddress(((EditText) findViewById(R.id.address)).getText()
						.toString());
				
				RadioGroup types = (RadioGroup) findViewById(R.id.types);
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
