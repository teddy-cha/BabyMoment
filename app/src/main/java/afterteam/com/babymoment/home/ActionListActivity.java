package afterteam.com.babymoment.home;

import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

    private ArrayList<String> mDateList;
    private ArrayList<ArrayList<String>> mTitleList;
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

        // add data
        setData();

        // add new date to expendable list if necessary
        addNewDate();

        mBaseExpandableAdapter = new BaseExpandableAdapter(this, mTitleList, mChildList);
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
        textViewCount.setText(String.valueOf(actionTransaction.getTodayActionCount(type, new Date())));
    }

    private void setClickListener(final int type, ImageButton imageButton, final TextView textView, final TextView textViewCount) {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String now = timeUtils.getStringTime(new Date());

                //int id, int type, int count, String time, String detail, String photo
                Action action = actionTransaction.writeAction(baby.getBaby_id(), type, new Date(), "");

                ArrayList<String> updated = mTitleList.get(0);
                updated.set(action.getType(), String.valueOf(action.getCount()));
                mTitleList.set(0, updated);
                mChildList.get(0).add(0, action);
                textView.setText(now);
                textViewCount.setText(String.valueOf(action.getCount()));

                mBaseExpandableAdapter.notifyDataSetChanged();

                Toast toast = Toast.makeText(getApplicationContext(), "데이터를 입력합니다.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
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

        if (mDateList.isEmpty() || timeUtils.compareDate(mDateList.get(0))) {
            mDateList.add(0, today);
            mChildList.add(0, new ArrayList<Action>());
        }
    }

    // setup data
    private void setData() {
        mDateList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mChildList = new ArrayList<>();
        baby = new Baby();
        baby.setBaby_id("1");
        ArrayList<Action> mChildListContent;

        ArrayList<String> dateList = actionTransaction.readAllAction(baby.getBaby_id());

        for (String date : dateList) {
            mDateList.add(0, date);

            mTitleList.add(0, actionTransaction.readTitleCount(baby.getBaby_id(), date));

            mChildListContent = actionTransaction.readDailyAction(baby.getBaby_id(), date);
            mChildList.add(0, mChildListContent);

        }
    }
}