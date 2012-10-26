package csci498.strupper.munchlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditRestaurant extends Activity {

  private RestaurantHelper helper;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    helper = new RestaurantHelper(this);

    // restore form
    if (savedInstanceState != null) {
      String s;
      if ((s = savedInstanceState.getString("name")) != null) {
        ((EditText)findViewById(R.id.name)).setText(s);
      }
      if ((s = savedInstanceState.getString("address")) != null) {
        ((EditText)findViewById(R.id.address)).setText(s);
      }
      if ((s = savedInstanceState.getString("type")) != null) {
        if (s.equals("sit_down")) {
          ((RadioButton)findViewById(R.id.sit_down)).setChecked(true);
        } else if (s.equals("take_out")) {
          ((RadioButton)findViewById(R.id.take_out)).setChecked(true);
        } else if (s.equals("delivery")) {
          ((RadioButton)findViewById(R.id.delivery)).setChecked(true);
        }
      }
    }

    /// attach save listener
    Button save = (Button)findViewById(R.id.save);
    save.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        Restaurant r = new Restaurant();
        r.setName(((EditText)v.findViewById(R.id.name))
                  .getText()
                  .toString());
        r.setAddress(((EditText)v.findViewById(R.id.address))
                     .getText()
                     .toString());

        RadioGroup types = (RadioGroup)v.findViewById(R.id.types);
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

        helper.insert(r);
      }
    });
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    outState
      .putString("name",
                 ((EditText)findViewById(R.id.name)).getText().toString());
    outState
      .putString("address",
                 ((EditText)findViewById(R.id.address)).getText().toString());
    RadioGroup types = (RadioGroup)findViewById(R.id.types);
    switch (types.getCheckedRadioButtonId()) {
    case R.id.sit_down:
      outState.putString("type", "sit_down");
      break;
    case R.id.take_out:
      outState.putString("type", "take_out");
      break;
    case R.id.delivery:
      outState.putString("type", "delivery");
      break;
    }
  }
}
