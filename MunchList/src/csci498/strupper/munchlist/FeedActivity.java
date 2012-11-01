package csci498.strupper.munchlist;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeedActivity extends ListActivity {

  public static final String FEED_URL = "munchlist.FEED_URL";

  private static class FeedTask extends AsyncTask<String, Void, Void> {
    private Exception e = null;
    private FeedActivity activity = null;

    FeedTask(FeedActivity activity) {
      attach(activity);
    }

    void attach(FeedActivity activity) {
      this.activity = activity;
    }

    void detach() {
      this.activity = null;
    }

    @Override
    public Void doInBackground(String... urls) {
      try {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getMethod = new HttpGet(urls[0]);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = client.execute(getMethod,
                                             responseHandler);
        Log.d("FeedActivity", responseBody);
      } catch (Exception e) {
        this.e = e;
      }
      return (null);
    }

    @Override
    public void onPostExecute(Void unused) {
      if (e == null) {
        // TODO
      }
      else {
        Log.e("LunchList", "Exception parsing feed", e);
        activity.goBlooey(e);
      }
    }
  }

  private class FeedAdapter extends BaseAdapter {
    RSSFeed feed = null;

    FeedAdapter(RSSFeed feed) {
      super();
      this.feed = feed;
    }

    public int getCount() {
      return (feed.getItems().size());
    }

    public Object getItem(int position) {
      return (feed.getItems().get(position));
    }

    public long getItemId(int position) {
      return (position);
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
      View row = convertView;
      if (row == null) {
        LayoutInflater inflater = getLayoutInflater();

        row = inflater.inflate(android.R.layout.simple_list_item_1,
                               parent, false);
      }
      RSSItem item = (RSSItem)getItem(position);
      ((TextView)row).setText(item.getTitle());
      return (row);
    }
  }

  private static class InstanceState {
    RSSFeed feed = null;
    FeedTask task = null;
  }

  private InstanceState state = null;

  private void goBlooey(Throwable t) {
    new AlertDialog.Builder(this).setTitle("Exception!")
                                 .setMessage(t.toString())
                                 .setPositiveButton("OK", null)
                                 .show();
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    state = (InstanceState)getLastNonConfigurationInstance();
    if (state == null) {
      state = new InstanceState();
      state.task = new FeedTask(this);
      state.task.execute(getIntent().getStringExtra(FEED_URL));
    }
    else {
      if (state.task != null) {
        state.task.attach(this);
      }
      if (state.feed != null) {
        setFeed(state.feed);
      }
    }
  }

  @Override
  public Object onRetainNonConfigurationInstance() {
    if (state.task != null) {
      state.task.detach();
    }
    return (state);
  }

  private void setFeed(RSSFeed feed) {
    state.feed = feed;
    setListAdapter(new FeedAdapter(feed));
  }

}
