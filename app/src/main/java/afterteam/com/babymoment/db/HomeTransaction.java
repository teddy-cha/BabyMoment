package afterteam.com.babymoment.db;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

import afterteam.com.babymoment.home.ActionDTO;
import afterteam.com.babymoment.model.Action;
import afterteam.com.babymoment.utils.TimeUtils;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by eunjooim on 15. 8. 12..
 */
public class HomeTransaction {

    private Context context;
    private TimeUtils timeUtils;
    private Realm realm;

    public HomeTransaction(Context context) {
        this.context = context;
        realm = Realm.getInstance(context);
        timeUtils = new TimeUtils();
    }

    public void writeAction(final ActionDTO inputAction) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Action action = realm.createObject(Action.class);

                action.setAction_id(getNextKey());
                action.setType(inputAction.getType());
                action.setDetail(inputAction.getDetail());
                action.setTime(timeUtils.getDate(inputAction.getTime()));

                // Baby 확인 필요
//              action.setBaby();
            }
        });
    }

    public Action writeAction(final int type, final Date time, final String detail) {

        final Action[] action = new Action[1];

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                action[0] = realm.createObject(Action.class);

                action[0].setAction_id(getNextKey());
                action[0].setType(type);
                action[0].setTime(time);
                action[0].setCount(getActionCount(type, time));
                action[0].setDetail(detail);

                // temporary photo index
                action[0].setPhoto("1");
            }
        });

        return action[0];
    }

    // get action count
    public int getActionCount(int type, Date time) {
        RealmQuery<Action> query = realm.where(Action.class);
        String today = timeUtils.getStringDate(new Date()) + " 00:00:00";

        query.equalTo("type", type)
                .greaterThanOrEqualTo("time", timeUtils.getDate(today))
                .lessThan("time", time);
        RealmResults<Action> result = query.findAll();

        return (result.max("count").intValue()) + 1;
    }

    public int getActionCount(ActionDTO actionDTO) {
        RealmQuery<Action> query = realm.where(Action.class);

        String today = timeUtils.getStringDate(new Date()) + " 00:00:00";

        query.greaterThanOrEqualTo("time", timeUtils.getDate(today))
                .lessThan("time", timeUtils.getDate(actionDTO.getTime()));
        RealmResults<Action> result = query.findAll();

        return (result.max("count").intValue()) + 1;
    }

    // auto increment id
    public int getNextKey() {
        return (int) realm.where(Action.class).maximumInt("action_id") + 1;
    }
}
