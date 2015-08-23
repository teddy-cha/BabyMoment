package afterteam.com.babymoment.graph;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import java.util.Date;

import afterteam.com.babymoment.R;
import afterteam.com.babymoment.utils.LogUtils;


/**
 * Created by hyes on 2015. 7. 27..
 */
public class DailyGraph extends Activity {

    private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());

    private LinearLayout linear;
    private DrawingView drawingView;
    private Window win;
    private float startPos = 0.0f;
    private float endPos = 0.0f;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String baby_id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        Date date = (Date)intent.getSerializableExtra("time");
        Log.i(TAG, baby_id + "title: " + title + "date: " + date);

        drawingView = new DrawingView(this, baby_id, title, date);
        win = getWindow();

        linear = (LinearLayout) findViewById(R.id.linear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        win.addContentView(drawingView, params);

        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startPos = motionEvent.getX();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    endPos = motionEvent.getX();
                    if ((startPos - endPos) > 0) {
                        //오늘이 마지막날인지 확인해보고 다음 날짜가 있으면 다음날짜 그래프로 이동
                        Log.i(TAG, "다음날짜로~~~~~~~~~~~~~~~");
                    } else if ((startPos - endPos) < 0) {
                        //이전 날짜 그래프로~
                        Log.i(TAG, "이전날짜로~~~~~~~~~~~~~~~");
                    }
                }
                return true;
            }
        });
    }
}
