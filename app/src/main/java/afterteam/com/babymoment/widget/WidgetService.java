package afterteam.com.babymoment.widget;


import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Date;

import afterteam.com.babymoment.R;
import afterteam.com.babymoment.db.ActionTransaction;
import afterteam.com.babymoment.utils.ActionType;
import afterteam.com.babymoment.utils.LogUtils;

/**
 * Created by hyes on 2015. 8. 20..
 */

public class WidgetService extends Service {

    private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());
    private ActionTransaction actionTransaction;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        doUpdate();
        return super.onStartCommand(intent, flags, startId);
    }

    private void doUpdate()
    {
        actionTransaction = new ActionTransaction(getApplicationContext());

        String medicine_count = String.valueOf(actionTransaction.getTodayActionCount(ActionType.MEDICINE, new Date()));
        String sleep_count = String.valueOf(actionTransaction.getTodayActionCount(ActionType.SLEEP, new Date()));
        String diaper_count = String.valueOf(actionTransaction.getTodayActionCount(ActionType.DIAPER, new Date()));
        String feed_count = String.valueOf(actionTransaction.getTodayActionCount(ActionType.FEED, new Date()));

        String medicine_time = String.valueOf(actionTransaction.getActionTime(ActionType.MEDICINE, new Date()));
        String sleep_time = String.valueOf(actionTransaction.getActionTime(ActionType.SLEEP, new Date()));
        String diaper_time = String.valueOf(actionTransaction.getActionTime(ActionType.DIAPER, new Date()));
        String feed_time = String.valueOf(actionTransaction.getActionTime(ActionType.FEED, new Date()));

        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.layout_widget);

        rv.setTextViewText(R.id.tv_home_bottom_medicine_count, medicine_count);
        rv.setTextViewText(R.id.tv_home_bottom_sleep_count, sleep_count);
        rv.setTextViewText(R.id.tv_home_bottom_diaper_count, diaper_count);
        rv.setTextViewText(R.id.tv_home_bottom_feed_count, feed_count);

        rv.setTextViewText(R.id.tv_home_bottom_medicine, medicine_time);
        rv.setTextViewText(R.id.tv_home_bottom_sleep, sleep_time);
        rv.setTextViewText(R.id.tv_home_bottom_diaper, diaper_time);
        rv.setTextViewText(R.id.tv_home_bottom_feed, feed_time);

        ComponentName thisWidget = new ComponentName(this, BabyMomentWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget, rv);

        Log.i(TAG, "doUpdated");

    }

    @Override
    public void onDestroy() {
        actionTransaction.closeTransaction();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
