package afterteam.com.babymoment.graph;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import afterteam.com.babymoment.R;
import afterteam.com.babymoment.utils.LogUtils;


/**
 * Created by hyes on 2015. 7. 27..
 */
public class DailyGraph extends Activity {

    private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());

    LinearLayout linear;
    private DrawingView drawingView;
    Window win;
    float startPos = 0.0f;
    float endPos = 0.0f;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawingView = new DrawingView(this);
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
                    Log.i("test", "down~: " + startPos + "");
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    endPos = motionEvent.getX();
                    Log.i("test", "up~: " + endPos + "");

                    if ((startPos - endPos) > 0) {
                        //오늘이 마지막날인지 확인해보고 다음 날짜가 있으면 다음날짜 그래프로 이동
                        Log.i("test", "다음날짜로~~~~~~~~~~~~~~~");
                    } else if ((startPos - endPos) < 0) {
                        //이전 날짜 그래프로~
                        Log.i("test", "이전날짜로~~~~~~~~~~~~~~~");
                    }
                }

                return true;
            }

        });

    }
}



//    class DrawingView extends View {
//
//        Paint feedPaint, cPaint, textPaint, textPaint2, sleepPaint, diaperPaint, medicinePaint;
//        int comparingTime;
//
//        public DrawingView(Context context) {
//            super(context);
//            paintSetting();
//        }
//
//        private void paintSetting() {
//
//            cPaint = new Paint();
//            cPaint.setColor(Color.DKGRAY);
//            cPaint.setStrokeWidth(4);
//            cPaint.setAntiAlias(true);
//            cPaint.setTextSize(30);
//
//            textPaint = new Paint();
//            textPaint.setTextSize(20);
//            textPaint.setColor(Color.DKGRAY);
//
//            textPaint2 = new Paint();
//            textPaint2.setTextSize(20);
//            textPaint2.setColor(Color.argb(255, 66, 75, 81));
//
//            feedPaint = new Paint();
//            feedPaint.setColor(Color.argb(200, 238, 80, 87));
//            feedPaint.setAntiAlias(true);
//
//            diaperPaint = new Paint();
//            diaperPaint.setTextSize(20);
//            diaperPaint.setColor(Color.argb(200, 250, 227, 0));
//
//            medicinePaint = new Paint();
//            medicinePaint.setTextSize(20);
//            medicinePaint.setColor(Color.argb(200, 82, 187, 187));
//
//            sleepPaint = new Paint();
//            sleepPaint.setTextSize(30);
//            sleepPaint.setStyle(Paint.Style.FILL);
//            sleepPaint.setStrokeWidth(1);
//            sleepPaint.setAntiAlias(true);
//            sleepPaint.setColor(Color.argb(200, 230, 240, 153));
//        }
//
//        public void onDraw(Canvas canvas) {
//            canvas.drawColor(Color.WHITE);
//
//            Resources r = getContext().getResources();
//
//            Bitmap milk = BitmapFactory.decodeResource(r, R.drawable.icon_feed_scarlet);
//            Bitmap sleep = BitmapFactory.decodeResource(r, R.drawable.icon_sleep_green);
//            Bitmap diaper = BitmapFactory.decodeResource(r, R.drawable.icon_diper_yellow);
//            Bitmap diaper2 = BitmapFactory.decodeResource(r, R.drawable.icon_diper_orange);
//            Bitmap medicine = BitmapFactory.decodeResource(r, R.drawable.icon_medicine_sky);
//
//            Baby baby = new Baby();
//            baby.setBaby_id("1");
//
//            ActionTransaction transaction = new ActionTransaction(getApplicationContext());
//
//            ArrayList<Action> actionList;
//            actionList =  transaction.readDailyAction(baby.getBaby_id(), new Date().toString());
////
////type 하루치를 종류별?로 또는 전체를 찾아서
////                DBCursor cursorDoc = collection.find();
////                while (cursorDoc.hasNext()) {
////                    System.out.println(cursorDoc.next());
////                   String tempType = jsonObject.get("type");
////                   String tempTime =  jsonObject.get("time");
////                   int tempHour = Integer.parseInt(tempTime.substring(9, 10));
////                    comparingTime = tempHour;
////                     if(comparingTime == tempHour){
////                count++;
////            }else{
////                comparingTime = 0;
////
////                    drawType(canvas, w_unit*(tempHour+2), h_unit * 4, milk);
////
////          }
//////                }
//
////json parsing이전 테스트용
//            drawType(canvas, w_unit * 7, h_unit * 4, milk);
//            drawType(canvas, w_unit * 23, h_unit * 4, milk);
//            drawType(canvas, w_unit * 17, h_unit * 4, milk);
//            drawType(canvas, w_unit * 14, h_unit * 4, milk);
//            drawType(canvas, w_unit * 6, h_unit * 4, milk);
//            drawType(canvas, w_unit * 21, h_unit * 4, milk);
//            drawType(canvas, w_unit * 2, h_unit * 4, milk);
//            drawMultipleMark("feed", canvas, w_unit * 7, h_unit * 4, 3);
//            drawType(canvas, w_unit * 18, h_unit * 4, milk);
//            drawType(canvas, w_unit * 13, h_unit * 4, milk);
//            drawType(canvas, w_unit * 12, h_unit * 1, medicine);
//            drawMultipleMark("medicine", canvas, w_unit * 12, h_unit * 1, 2);
//            drawType(canvas, w_unit * 12, h_unit * 3, diaper);
//            drawType(canvas, w_unit * 2, h_unit * 3, diaper);
//            drawMultipleMark("diaper", canvas, w_unit * 2, h_unit * 3, 2);
//            drawType(canvas, w_unit * 6, h_unit * 3, diaper2);
//            drawType(canvas, w_unit * 7, h_unit * 3, diaper);
//            drawType(canvas, w_unit * 16, h_unit * 3, diaper);
//            drawType(canvas, w_unit * 21, h_unit * 3, diaper2);
//            drawType(canvas, w_unit * 2, h_unit * 3, diaper);
//
//
//            drawType(canvas, w_unit * 7 + 30, h_unit * 2, sleep);
//            drawSleep(canvas, w_unit * 7 + 30, h_unit * 2, (w_unit * 11) + 80, (h_unit * 2) + 50);
//
//            drawType(canvas, w_unit * 22, h_unit * 2, sleep);
//            drawSleep(canvas, w_unit * 22, h_unit * 2, (w_unit * 27), (h_unit * 2) + 50);
//
//            drawType(canvas, w_unit * 3, h_unit * 2, sleep);
//            drawSleep(canvas, w_unit * 3, h_unit * 2, (w_unit * 6), (h_unit * 2) + 50);
//
//            drawType(canvas, w_unit * 19, h_unit * 2, sleep);
//            drawSleep(canvas, w_unit * 19, h_unit * 2, (w_unit * 20), (h_unit * 2) + 50);
//
//
//            drawText(canvas);
//
//
//        }
//
//
//        private void drawType(Canvas canvas, int a, int b, Bitmap img) {
//
//            RectF basicRec = new RectF(a, b, a + 50, b + 50);
//            canvas.drawBitmap(img, null, basicRec, null);
//
//        }
//
//        private void drawSleep(Canvas canvas, int a, int b, int c, int d) {
//
//            canvas.drawRect(a + 60, b, c - 30, d, sleepPaint);
//
//        }
//
//        private void drawMultipleMark(String type, Canvas canvas, int a, int b, int count) {
//
//            //2개 표시 샘플
//            switch(type){
//                case "feed":
//                    canvas.drawCircle(a + 45, b + 40, 15, feedPaint);
//                    canvas.drawText(count + "", a + 40, b + 48, textPaint2);
//                    break;
//                case "diaper":
//                    canvas.drawCircle(a + 45, b + 40, 15, diaperPaint);
//                    canvas.drawText(count + "", a + 40, b + 48, textPaint2);
//                    break;
//                case "sleep":
//                    canvas.drawCircle(a + 45, b + 40, 15, sleepPaint);
//                    canvas.drawText(count + "", a + 40, b + 48, textPaint2);
//                    break;
//                case "medicine":
//                    canvas.drawCircle(a + 45, b + 40, 15, medicinePaint);
//                    canvas.drawText(count + "", a + 40, b + 48, textPaint2);
//                    break;
//                default : break;
//            }
//        }
//
//        private void drawText(Canvas canvas) {
//
//            SizeUtils su = new SizeUtils(getApplicationContext());
//            w_unit = (su.getWidth() / 27);
//            h_unit = (su.getHeight() / 6);
//
//            //db날짜 파싱해서 넣기로 대체 예정
//            String temp = new Date().toString().substring(0, 10) + " " + new Date().toString().substring(23, 28);
//            //            temp.length()/2
//            canvas.drawText(temp, (su.getWidth() / 2) - 100, 50, cPaint);
//
//            //시간 표시
//            for (int i = 0; i < 25; i++) {
//                canvas.drawText(i + "", 120 + (w_unit * i), (h_unit * 5), textPaint);
//            }
//
//            //type구분
//            canvas.drawText("medicine", 40, 20 + (h_unit * 1), textPaint);
//            canvas.drawText("sleep", 40, 20 + (h_unit * 2), textPaint);
//            canvas.drawText("diaper", 40, 20 + (h_unit * 3), textPaint);
//            canvas.drawText("milk", 40, 20 + (h_unit * 4), textPaint);
//
//
//        }
//    }
