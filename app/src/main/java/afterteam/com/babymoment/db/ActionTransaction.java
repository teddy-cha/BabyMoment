package afterteam.com.babymoment.db;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import afterteam.com.babymoment.model.Action;
import afterteam.com.babymoment.utils.TimeUtils;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by eunjooim on 15. 8. 12..
 */
public class ActionTransaction {

    private Context context;
    private TimeUtils timeUtils;
    private Realm realm;

    public ActionTransaction(Context context) {
        this.context = context;
        realm = Realm.getInstance(context);
        timeUtils = new TimeUtils();
    }

    public void closeTransaction() {
        realm.close();
    }

    public ArrayList<String> readAllAction(String baby_id) {

        ArrayList<String> dateArray = new ArrayList<>();
        RealmQuery<Action> query = realm.where(Action.class);

        query.equalTo("baby_id", baby_id);

        RealmResults<Action> results = query.findAll();
        results.sort("time");

        for (Action a : results) {
            Date date = a.getTime();
            String stringDate = timeUtils.getStringDate(date);

            if (!dateArray.contains(stringDate)) {
                dateArray.add(stringDate);
            }
        }

        return dateArray;
    }

    public ArrayList<Action> readDailyAction(String baby_id, String date) {

        if (date == null) return null;

        ArrayList<Action> resultArray = new ArrayList<>();
        RealmQuery<Action> query = realm.where(Action.class);

        Date targetDate = timeUtils.getDateFromStringDate(date);
        Date nextDate = new Date(targetDate.getTime() + (1000 * 60 * 60 * 24));

        query.equalTo("baby_id", baby_id)
                .greaterThanOrEqualTo("time", targetDate)
                .lessThan("time", nextDate);

        RealmResults<Action> results = query.findAll();
        results.sort("time", RealmResults.SORT_ORDER_DESCENDING);

        for (Action a : results) {
            resultArray.add(a);
        }

        return resultArray;
    }

    public int readActionCount(String baby_id, String date, int type) {

        ArrayList<Action> resultArray = readDailyAction(baby_id, date);

        for (Action a : resultArray) {
            if (type == a.getType()) {
                return a.getCount();
            }
        }

        return 0;
    }

    public String readActionLastTime(String baby_id, String date, int type) {

        ArrayList<Action> resultArray = readDailyAction(baby_id, date);

        for (Action a : resultArray) {
            if (type == a.getType()) {
                return timeUtils.getStringTime(a.getTime());
            }
        }

        return "no data";
    }

    public Action writeAction(final String baby_id, final int type, final Date time, final String detail) {

        final Action[] action = new Action[1];

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                action[0] = realm.createObject(Action.class);

                action[0].setAction_id(getNextKey());
                action[0].setType(type);
                action[0].setTime(time);
                action[0].setCount(getActionCount(type, time) + 1);
                action[0].setDetail(detail);

                // temporary photo index and baby
                action[0].setPhoto("1");
                action[0].setBaby_id(baby_id);
            }
        });

        return action[0];
    }

    // get action count
    public int getActionCount(int type, Date time) {
        RealmQuery<Action> query = realm.where(Action.class);
        Date today = getMidnight(new Date());

        query.equalTo("type", type)
                .greaterThanOrEqualTo("time", today)
                .lessThan("time", time);
        RealmResults<Action> result = query.findAll();

        return (result.max("count").intValue());
    }

    // get action time
    public String getActionTime(int type, Date time) {
        RealmQuery<Action> query = realm.where(Action.class);
        Date today = getMidnight(new Date());

        query.equalTo("type", type)
                .greaterThanOrEqualTo("time", today)
                .lessThan("time", time);
        RealmResults<Action> result = query.findAll();
        result.sort("count", RealmResults.SORT_ORDER_DESCENDING);

        return timeUtils.getStringTime(result.first().getTime());
    }

    // set midnight of designated date
    private Date getMidnight(Date date) {
        String midnight =  timeUtils.getStringDate(date) + " 00:00:00";
        return timeUtils.getDate(midnight);
    }

    // auto increment id
    public int getNextKey() {
        return (int) realm.where(Action.class).maximumInt("action_id") + 1;
    }
}
