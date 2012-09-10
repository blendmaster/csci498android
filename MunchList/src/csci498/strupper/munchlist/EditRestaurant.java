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

}
