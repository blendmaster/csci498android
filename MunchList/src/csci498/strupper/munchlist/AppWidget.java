package csci498.strupper.munchlist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class AppWidget extends AppWidgetProvider {
  @Override
  public void onUpdate(Context ctxt,
                       AppWidgetManager mgr,
                       int[] appWidgetIds) {
    for (int i = 0; i < appWidgetIds.length; i++) {
      Intent svcIntent = new Intent(ctxt, WidgetService.class);
      svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
      svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
      RemoteViews widget = new RemoteViews(ctxt.getPackageName(),
                                           R.layout.widget);
      widget.setRemoteAdapter(R.id.restaurants,
                              svcIntent);
      Intent clickIntent = new Intent(ctxt, EditRestaurant.class);
      PendingIntent clickPI =
          PendingIntent
                       .getActivity(ctxt, 0,
                                    clickIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
      widget.setPendingIntentTemplate(R.id.restaurants, clickPI);

      mgr.updateAppWidget(appWidgetIds[i], widget);
      super.onUpdate(ctxt, mgr, appWidgetIds);
    }
  }

}
