package csci498.strupper.munchlist;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditRestaurant extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.edit_restaurant, container, false);
  }

  public interface SaveRestaurantListener {
    public void onRestaurantSave(Restaurant r);
  }

  private SaveRestaurantListener listener;

  @Override public void onAttach(Activity a) {
    super.onAttach(a);
    try {
      listener = (SaveRestaurantListener)a;
    } catch (ClassCastException e) {
      throw new ClassCastException(a.toString() +
                                    " must implement SaveRestaurantListener");
    }
  }

  @Override
  public void onViewCreated(final View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // restore form
    if (savedInstanceState != null) {
      String s;
      if ((s = savedInstanceState.getString("name")) != null) {
        ((EditText)getActivity().findViewById(R.id.name)).setText(s);
      }
      if ((s = savedInstanceState.getString("address")) != null) {
        ((EditText)getActivity().findViewById(R.id.address)).setText(s);
      }
      if ((s = savedInstanceState.getString("type")) != null) {
        if (s.equals("sit_down")) {
          ((RadioButton)getActivity().findViewById(R.id.sit_down)).setChecked(true);
        } else if (s.equals("take_out")) {
          ((RadioButton)getActivity().findViewById(R.id.take_out)).setChecked(true);
        } else if (s.equals("delivery")) {
          ((RadioButton)getActivity().findViewById(R.id.delivery)).setChecked(true);
        }
      }
    }

    /// attach save listener
    Button save = (Button)view.findViewById(R.id.save);
    save.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        Restaurant r = new Restaurant();
        r.setName(((EditText)view.findViewById(R.id.name))
                  .getText()
                  .toString());
        r.setAddress(((EditText)view.findViewById(R.id.address))
                     .getText()
                     .toString());

        RadioGroup types = (RadioGroup)view.findViewById(R.id.types);
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

        listener.onRestaurantSave(r);
      }
    });
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    Activity a = getActivity();
    outState.putString("name", ((EditText)a.findViewById(R.id.name)).getText().toString());
    outState.putString("address", ((EditText)a.findViewById(R.id.address)).getText().toString());
    RadioGroup types = (RadioGroup)a.findViewById(R.id.types);
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
