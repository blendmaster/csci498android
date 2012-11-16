package csci498.strupper.munchlist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class ListViewsFactory implements RemoteViewsFactory {
  private final Context ctxt;
  private RestaurantHelper helper;
  private Cursor restaurants;

  public ListViewsFactory(Context ctxt, Intent intent) {
    this.ctxt = ctxt;
  }

  public int getCount() {
    return restaurants.getCount();
  }

  public long getItemId(int position) {
    restaurants.moveToPosition(position);

    return restaurants.getInt(0);

  }

  public RemoteViews getLoadingView() {
    return null;
  }

  public RemoteViews getViewAt(int position) {
    RemoteViews row = new RemoteViews(ctxt.getPackageName(),
                                      R.layout.widget_row);
    restaurants.moveToPosition(position);
    row.setTextViewText(android.R.id.text1, restaurants.getString(1));
    Intent i = new Intent();
    Bundle extras = new Bundle();
    extras.putString(MunchList.ID_EXTRA,
                     String.valueOf(restaurants.getInt(0)));
    i.putExtras(extras);
    row.setOnClickFillInIntent(android.R.id.text1, i);
    return row;

  }

  public int getViewTypeCount() {
    return 1;
  }

  public boolean hasStableIds() {
    return true;
  }

  public void onCreate() {
    helper = new RestaurantHelper(ctxt);
    restaurants = helper
                        .getReadableDatabase()
                        .rawQuery("SELECT _ID, name FROM restaurant", null);
  }

  public void onDataSetChanged() {
    // don't care
  }

  public void onDestroy() {
    restaurants.close();
    helper.close();
  }
}
