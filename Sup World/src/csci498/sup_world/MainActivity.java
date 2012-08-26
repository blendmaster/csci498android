package csci498.sup_world;

import java.util.Date;

import edu.mines.sup_world.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        button = new Button(this);
        button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				button.setText(new Date().toString());
			}
        });
        button.setText(new Date().toString());
        setContentView(button);
        //setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
