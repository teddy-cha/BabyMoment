package afterteam.com.babymoment.home;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import afterteam.com.babymoment.R;


public class ActionListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_main);

        setLayout();

        ArrayList<String> mGroupList = new ArrayList<>();
        ArrayList<ArrayList<ActionDTO>> mChildList = new ArrayList<>();
        ArrayList<ActionDTO> mChildListContent1 = new ArrayList<>();
        ArrayList<ActionDTO> mChildListContent2 = new ArrayList<>();
        ArrayList<ActionDTO> mChildListContent3 = new ArrayList<>();


        mGroupList.add("2015-07-25");
        mGroupList.add("2015-07-24");
        mGroupList.add("2015-07-23");

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

        mChildList.add(mChildListContent1);
        mChildList.add(mChildListContent2);
        mChildList.add(mChildListContent3);

        final BaseExpandableAdapter mBaseExpandableAdapter = new BaseExpandableAdapter(this, mGroupList, mChildList);
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
//                Toast.makeText(getApplicationContext(), "c click = " + childPosition,
//                        Toast.LENGTH_SHORT).show();
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

        // 맨 위 그룹을 펼침
//        int groupCount = mBaseExpandableAdapter.getGroupCount();
        mListView.expandGroup(0);
    }

    /*
     * Layout
     */
    private ExpandableListView mListView;

    private void setLayout() {
        mListView = (ExpandableListView) findViewById(R.id.elv_list);
    }
}