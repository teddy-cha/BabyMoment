package afterteam.com.babymoment.home;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import afterteam.com.babymoment.R;
import afterteam.com.babymoment.detail.SleepActivity;


public class ActionListActivity extends ActionBarActivity {

    private ArrayList<String> mGroupList;
    private ArrayList<ArrayList<ActionDTO>> mChildList;
    public BaseExpandableAdapter mBaseExpandableAdapter;
    private ImageButton ibMedicine, ibSleep, ibDiaper, ibFeed;
    private TextView tvMedicine, tvSleep, tvDiaper, tvFeed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_main);

        setLayout();

        // temporarily input dummy data by hard coding
        setDummyData();

        // add new date to expendable list if necessary
        addNewDate();

        mBaseExpandableAdapter = new BaseExpandableAdapter(this, mGroupList, mChildList);
        mListView.setAdapter(mBaseExpandableAdapter);

        // 그룹 클릭 했을 경우 이벤트
        mListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
//                Toast.makeText(getApplicationContext(), "g click = " + groupPosition,
//                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // 차일드 클릭 했을 경우 이벤트
        mListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), "c click = " + childPosition,
                        Toast.LENGTH_SHORT).show();

                ActionDTO child = mBaseExpandableAdapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(getApplicationContext(), SleepActivity.class);
                intent.putExtra("id", child.getId());
                startActivity(intent);

                return false;
            }
        });

        // 그룹이 닫힐 경우 이벤트
        mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(), "g Collapse = " + groupPosition,
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // 그룹이 열릴 경우 이벤트
        mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(), "g Expand = " + groupPosition,
//                        Toast.LENGTH_SHORT).show();
            }
        });

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

        ibMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String now = getTime();
                ActionDTO medicineAction = new ActionDTO(0, 1, 1, now, "", "1");
                mChildList.get(0).add(0, medicineAction);
                tvMedicine.setText(now);

                mBaseExpandableAdapter.notifyDataSetChanged();
            }
        });

        ibSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String now = getTime();
                ActionDTO sleepAction = new ActionDTO(0, 2, 1, now, "", "1");
                mChildList.get(0).add(0, sleepAction);
                tvSleep.setText(now);

                mBaseExpandableAdapter.notifyDataSetChanged();
            }
        });

        ibDiaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String now = getTime();
                ActionDTO diaperAction = new ActionDTO(0, 3, 1, now, "", "1");
                mChildList.get(0).add(0, diaperAction);
                tvDiaper.setText(now);

                mBaseExpandableAdapter.notifyDataSetChanged();
            }
        });


        ibFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String now = getTime();
                ActionDTO feedAction = new ActionDTO(0, 4, 1, now, "", "1");
                mChildList.get(0).add(0, feedAction);
                tvFeed.setText(now);

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
        String today = getDate();
        if (compareDate(today)) {
            mGroupList.add(0, today);
            mChildList.add(0, new ArrayList<ActionDTO>());
        }
    }

    // return String format date / yyyy-MM-dd
    private String getDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());

        return simpleDateFormat.format(date);
    }

    // return String format time / a hh:mm
    private String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm", java.util.Locale.getDefault());

        return simpleDateFormat.format(date);
    }

    // compare today and latest data
    private Boolean compareDate(String today) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());

        try {
            Date todayDate = simpleDateFormat.parse(today);
            Date latestDate = simpleDateFormat.parse(mGroupList.get(0));

            String test = String.valueOf(todayDate.after(latestDate));

            return todayDate.after(latestDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    // setup temporary dummy data
    private void setDummyData() {
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        ArrayList<ActionDTO> mChildListContent1 = new ArrayList<>();
        ArrayList<ActionDTO> mChildListContent2 = new ArrayList<>();
        ArrayList<ActionDTO> mChildListContent3 = new ArrayList<>();

        ActionDTO test11 = new ActionDTO(1, 2, 1, "AM 06:30", "~ AM 09:00", "3");
        ActionDTO test12 = new ActionDTO(2, 4, 1, "AM 09:00", "250ml", "2");
        ActionDTO test13 = new ActionDTO(3, 3, 1, "AM 09:05", "poo, picture", "1");
        ActionDTO test14 = new ActionDTO(4, 4, 2, "AM 09:09", "왼쪽, 10분", "1");
        ActionDTO test15 = new ActionDTO(5, 2, 2, "AM 09:10", "~ AM 10:00", "1");
        ActionDTO test16 = new ActionDTO(6, 1, 2, "AM 10:00", "콧물", "3");
        ActionDTO test17 = new ActionDTO(7, 3, 2, "AM 10:05", "pee", "2");
        ActionDTO test18 = new ActionDTO(8, 3, 3, "AM 10:10", "", "1");

        mChildListContent1.add(test11);
        mChildListContent1.add(test12);
        mChildListContent1.add(test13);
        mChildListContent1.add(test14);
        mChildListContent1.add(test15);
        mChildListContent1.add(test16);
        mChildListContent1.add(test17);
        mChildListContent1.add(test18);

        mChildListContent2.add(test11);
        mChildListContent2.add(test12);
        mChildListContent2.add(test13);
        mChildListContent2.add(test14);
        mChildListContent2.add(test15);
        mChildListContent2.add(test16);

        mChildListContent3.add(test11);
        mChildListContent3.add(test12);
        mChildListContent3.add(test13);
        mChildListContent3.add(test14);

        mGroupList.add(0, "2015-07-23");
        mChildList.add(0, mChildListContent3);

        mGroupList.add(0, "2015-07-24");
        mChildList.add(0, mChildListContent2);

        mGroupList.add(0, "2015-07-25");
        mChildList.add(0, mChildListContent1);
    }
}