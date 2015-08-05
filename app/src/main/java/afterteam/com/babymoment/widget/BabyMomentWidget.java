package afterteam.com.babymoment.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
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
    public static final String ACTION_FEED ="com.babymoment.widget.Feed";
    public static final String ACTION_SLEEP ="com.babymoment.widget.Sleep";
    public static final String ACTION_DIAPER ="com.babymoment.widget.Diaper";
    public static final String ACTION_MEDICINE ="com.babymoment.widget.Medicine";
    private Context context;

    @Override
    public void onEnabled(Context context) {
        Log.i(TAG, "OnEnabled");
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.context = context;
        init(context, appWidgetManager, appWidgetIds);
        for(int i = 0; i<appWidgetIds.length; i++){
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        //동작 확인용
        String temp = "widge test";
        //to do- DB에서 횟수 불러와서 적용하기

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        if(intent.getAction().equals(ACTION_FEED)){
            rv.setTextViewText(R.id.tv_home_bottom_feed, temp);
        }else if(intent.getAction().equals(ACTION_SLEEP)){
            rv.setTextViewText(R.id.tv_home_bottom_sleep, temp);
        }if(intent.getAction().equals(ACTION_DIAPER)){
            rv.setTextViewText(R.id.tv_home_bottom_diaper, temp);
        }if(intent.getAction().equals(ACTION_MEDICINE)){
            rv.setTextViewText(R.id.tv_home_bottom_medicine, temp);
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName cpName = new ComponentName(context, BabyMomentWidget.class);
        appWidgetManager.updateAppWidget(cpName, rv);

    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    public void init(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);

        Intent feedIntent = new Intent(ACTION_FEED);
        Intent sleepIntent = new Intent(ACTION_SLEEP);
        Intent diaperIntent = new Intent(ACTION_DIAPER);
        Intent medicineIntent = new Intent(ACTION_MEDICINE);

        PendingIntent feedPending = PendingIntent.getBroadcast(context, 0, feedIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent sleepPending = PendingIntent.getBroadcast(context, 0, sleepIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent diaperPending = PendingIntent.getBroadcast(context, 0, diaperIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent medicinePending = PendingIntent.getBroadcast(context, 0, medicineIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.btn_home_bottom_feed, feedPending);
        views.setOnClickPendingIntent(R.id.btn_home_bottom_sleep, sleepPending);
        views.setOnClickPendingIntent(R.id.btn_home_bottom_diaper, diaperPending);
        views.setOnClickPendingIntent(R.id.btn_home_bottom_medicine, medicinePending);

        for(int appWidgetId : appWidgetIds){
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }
}
