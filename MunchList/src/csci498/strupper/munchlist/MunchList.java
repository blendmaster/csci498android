package csci498.strupper.munchlist;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MunchList extends Activity {

	Restaurant r = new Restaurant();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_munch_list);
        
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				r.setName(
						((EditText) findViewById(R.id.name))
						.getText().toString()
				);
				r.setAddress(
						((EditText) findViewById(R.id.address))
						.getText().toString()
				);
			}
		});
        
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_munch_list, menu);
        return true;
    }
}
