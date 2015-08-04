package afterteam.com.babymoment.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import afterteam.com.babymoment.R;
import afterteam.com.babymoment.utils.LogUtils;

/**
 * Created by hyes on 2015. 8. 4..
 */
public class BabyMomentWidget extends AppWidgetProvider{

    private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());
    private Context context;

    @Override
    public void onEnabled(Context context) {
        Log.i(TAG, "OnEnabled");
        super.onEnabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.context = context;

        for(int i = 0; i<appWidgetIds.length; i++){
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
