package afterteam.com.babymoment.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Date;

import afterteam.com.babymoment.R;
import afterteam.com.babymoment.db.ActionTransaction;
import afterteam.com.babymoment.model.Baby;
import afterteam.com.babymoment.utils.ActionType;
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
    private ActionTransaction actionTransaction;
    private PendingIntent service = null;


    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public void onEnabled(Context context) {
        Log.i(TAG, "OnEnabled");
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final Calendar TIME = Calendar.getInstance();
        TIME.set(Calendar.MINUTE, 0);
        TIME.set(Calendar.SECOND, 0);
        TIME.set(Calendar.MILLISECOND, 0);

        final Intent intent = new Intent(context, WidgetService.class);

        if (service == null)
        {
            service = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }

        m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), 1000 * 60 * 10, service);
        init(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        Log.i(TAG, "OnUpdated");
    }
    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);

        Baby baby = new Baby();
        baby.setBaby_id("1");

        actionTransaction= new ActionTransaction(context);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        if(intent.getAction().equals(ACTION_FEED)){
            actionTransaction.writeAction(baby.getBaby_id(), ActionType.FEED, new Date(), "");
            actionTransaction.closeTransaction();
            Log.i(TAG, "writeActionFeed");
        }else if(intent.getAction().equals(ACTION_SLEEP)){
            actionTransaction.writeAction(baby.getBaby_id(), ActionType.SLEEP, new Date(), "");
            actionTransaction.closeTransaction();
            Log.i(TAG, "writeActionSleep");
        }if(intent.getAction().equals(ACTION_DIAPER)){
            actionTransaction.writeAction(baby.getBaby_id(), ActionType.DIAPER, new Date(), "");
            actionTransaction.closeTransaction();
            Log.i(TAG, "writeActionDiaper");
        }if(intent.getAction().equals(ACTION_MEDICINE)){
            actionTransaction.writeAction(baby.getBaby_id(), ActionType.MEDICINE, new Date(), "");
            actionTransaction.closeTransaction();
            Log.i(TAG, "writeActionMedi");
        }


        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, WidgetService.class);
                context.startService(intent);
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 400);

//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//        ComponentName cpName = new ComponentName(context, BabyMomentWidget.class);
//        appWidgetManager.updateAppWidget(cpName, rv);

    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        m.cancel(service);
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
