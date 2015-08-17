package afterteam.com.babymoment.home;

import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import afterteam.com.babymoment.R;
import afterteam.com.babymoment.db.ActionTransaction;
import afterteam.com.babymoment.detail.DiaperActivity;
import afterteam.com.babymoment.detail.FeedActivity;
import afterteam.com.babymoment.detail.MedicineActivity;
import afterteam.com.babymoment.detail.SleepActivity;
import afterteam.com.babymoment.model.Action;
import afterteam.com.babymoment.model.Baby;
import afterteam.com.babymoment.utils.TimeUtils;


public class ActionListActivity extends ActionBarActivity{

    private ArrayList<String> mGroupList;
    private ArrayList<ArrayList<Action>> mChildList;
    public BaseExpandableAdapter mBaseExpandableAdapter;
    private ImageButton ibMedicine, ibSleep, ibDiaper, ibFeed;
    private TextView tvMedicine, tvSleep, tvDiaper, tvFeed, tvMedicineCount, tvSleepCount, tvDiaperCount, tvFeedCount;
    private TimeUtils timeUtils;
    private ActionTransaction actionTransaction;
    private Baby baby;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_main);

        setLayout();

        setActionList();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        actionTransaction.closeTransaction();
    }

    private void setActionList() {
        actionTransaction = new ActionTransaction(this);
        timeUtils = new TimeUtils();

        // temporarily input dummy data by hard coding
        setDummyData();

        // add new date to expendable list if necessary
        addNewDate();

        mBaseExpandableAdapter = new BaseExpandableAdapter(this, mGroupList, mChildList);
        mListView.setAdapter(mBaseExpandableAdapter);

        // child click event
        mListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Action child = mBaseExpandableAdapter.getChild(groupPosition, childPosition);
                Intent intent = null;

                switch (child.getType()) {
                    case 1:
                        intent = new Intent(getApplicationContext(), MedicineActivity.class);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), SleepActivity.class);
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), DiaperActivity.class);
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(), FeedActivity.class);
                        break;
                    default:
                        return false;
                }

                if (intent != null) {
                    intent.putExtra("id", child.getAction_id());
                    startActivity(intent);
                }

                return false;
            }
        });

//        // 보존용
//        // group click event
//        mListView.setOnGroupClickListener(new OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                // Toast.makeText(getApplicationContext(), "g click = " + groupPosition,
//                //        Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//
//        // group close event
//        mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//            }
//        });
//
//        // group open event
//        mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//            }
//        });

        // open first group
        mListView.expandGroup(0);

        // button click
        ibMedicine = (ImageButton) findViewById(R.id.btn_home_bottom_medicine);
        ibSleep = (ImageButton) findViewById(R.id.btn_home_bottom_sleep);
        ibDiaper = (ImageButton) findViewById(R.id.btn_home_bottom_diaper);
        ibFeed = (ImageButton) findViewById(R.id.btn_home_bottom_feed);

        tvMedicine = (TextView) findViewById(R.id.tv_home_bottom_medicine);
        tvSleep = (TextView) findViewById(R.id.tv_home_bottom_sleep);
        tvDiaper = (TextView) findViewById(R.id.tv_home_bottom_diaper);
        tvFeed = (TextView) findViewById(R.id.tv_home_bottom_feed);

        tvMedicineCount = (TextView) findViewById(R.id.tv_home_button_medicine_count);
        tvSleepCount = (TextView) findViewById(R.id.tv_home_bottom_sleep_count);
        tvDiaperCount = (TextView) findViewById(R.id.tv_home_bottom_diaper_count);
        tvFeedCount = (TextView) findViewById(R.id.tv_home_bottom_feed_count);

        setClickListener(1, ibMedicine, tvMedicine, tvMedicineCount);
        setClickListener(2, ibSleep, tvSleep, tvSleepCount);
        setClickListener(3, ibDiaper, tvDiaper, tvDiaperCount);
        setClickListener(4, ibFeed, tvFeed, tvFeedCount);

        setButtonText(1, tvMedicine, tvMedicineCount);
        setButtonText(2, tvSleep, tvSleepCount);
        setButtonText(3, tvDiaper, tvDiaperCount);
        setButtonText(4, tvFeed, tvFeedCount);
    }

    private void setButtonText(int type, final TextView textView, final TextView textViewCount) {
        textView.setText(actionTransaction.getActionTime(type, new Date()));
        textViewCount.setText(String.valueOf(actionTransaction.getActionCount(type, new Date())));
    }

    private void setClickListener(final int type, ImageButton imageButton, final TextView textView, final TextView textViewCount) {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String now = timeUtils.getStringTime(new Date());

                //int id, int type, int count, String time, String detail, String photo
                Action action = actionTransaction.writeAction(baby.getBaby_id(), type, new Date(), "");

                mChildList.get(0).add(0, action);
                textView.setText(now);
                textViewCount.setText(String.valueOf(action.getCount()));

                mBaseExpandableAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Layout
    private ExpandableListView mListView;

    private void setLayout() {
        mListView = (ExpandableListView) findViewById(R.id.lv_home_expendablelist);
    }

    // add new date to expendable list if necessary
    private void addNewDate() {
        String today = timeUtils.getStringDate(new Date());

        if (mGroupList.isEmpty() || timeUtils.compareDate(mGroupList.get(0))) {
            mGroupList.add(0, today);
            mChildList.add(0, new ArrayList<Action>());
        }
    }

//    // compare today and latest data
//    private Boolean compareDate(String today) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
//
//        try {
//            Date todayDate = simpleDateFormat.parse(today);
//            Date latestDate = simpleDateFormat.parse(mGroupList.get(0));
//
//            String test = String.valueOf(todayDate.after(latestDate));
//
//            return todayDate.after(latestDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

    // setup temporary dummy data
    private void setDummyData() {
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        baby = new Baby();
        baby.setBaby_id("1");
        ArrayList<Action> mChildListContent;

        ArrayList<String> dateList = actionTransaction.readAllAction(baby.getBaby_id());

        for (String date : dateList) {
            mGroupList.add(0, date);

            mChildListContent = actionTransaction.readDailyAction(baby.getBaby_id(), date);
            mChildList.add(0, mChildListContent);

        }
//        ArrayList<ActionDTO> mChildListContent1 = new ArrayList<>();
//        ArrayList<ActionDTO> mChildListContent2 = new ArrayList<>();
//        ArrayList<ActionDTO> mChildListContent3 = new ArrayList<>();
//
//        ActionDTO test11 = new ActionDTO(1, 2, 1, "AM 06:30", "~ AM 09:00", "3");
//        ActionDTO test12 = new ActionDTO(2, 4, 1, "AM 09:00", "250ml", "2");
//        ActionDTO test13 = new ActionDTO(3, 3, 1, "AM 09:05", "poo, picture", "1");
//        ActionDTO test14 = new ActionDTO(4, 4, 2, "AM 09:09", "왼쪽, 10분", "1");
//        ActionDTO test15 = new ActionDTO(5, 2, 2, "AM 09:10", "~ AM 10:00", "1");
//        ActionDTO test16 = new ActionDTO(6, 1, 2, "AM 10:00", "콧물", "3");
//        ActionDTO test17 = new ActionDTO(7, 3, 2, "AM 10:05", "pee", "2");
//        ActionDTO test18 = new ActionDTO(8, 3, 3, "AM 10:10", "", "1");
//
//        mChildListContent1.add(test11);
//        mChildListContent1.add(test12);
//        mChildListContent1.add(test13);
//        mChildListContent1.add(test14);
//        mChildListContent1.add(test15);
//        mChildListContent1.add(test16);
//        mChildListContent1.add(test17);
//        mChildListContent1.add(test18);
//
//        mChildListContent2.add(test11);
//        mChildListContent2.add(test12);
//        mChildListContent2.add(test13);
//        mChildListContent2.add(test14);
//        mChildListContent2.add(test15);
//        mChildListContent2.add(test16);
//
//        mChildListContent3.add(test11);
//        mChildListContent3.add(test12);
//        mChildListContent3.add(test13);
//        mChildListContent3.add(test14);
//
//        mGroupList.add(0, "2015-07-23");
//        mChildList.add(0, mChildListContent3);
//
//        mGroupList.add(0, "2015-07-24");
//        mChildList.add(0, mChildListContent2);
//
//        mGroupList.add(0, "2015-07-25");
//        mChildList.add(0, mChildListContent1);
    }
}