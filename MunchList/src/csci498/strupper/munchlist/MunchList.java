package csci498.strupper.munchlist;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MunchList extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_munch_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_munch_list, menu);
        return true;
    }
}
