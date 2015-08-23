package afterteam.com.babymoment.graph;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

import afterteam.com.babymoment.R;
import afterteam.com.babymoment.db.ActionTransaction;
import afterteam.com.babymoment.model.Action;
import afterteam.com.babymoment.utils.ActionType;
import afterteam.com.babymoment.utils.LogUtils;
import afterteam.com.babymoment.utils.SizeUtils;
import afterteam.com.babymoment.utils.TimeUtils;

/**
 * Created by hyes on 2015. 8. 22..
 */
public class DrawingView extends View{

        private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());
        private Paint feedPaint, cPaint, textPaint, textPaint2, sleepPaint, diaperPaint, medicinePaint;
        private int w_unit, h_unit;
        private String baby_id, title;
        private Date date;
        private Context context;
        private TimeUtils tu;

        public DrawingView(Context context, String baby_id, String title, Date date) {
            super(context);
            this.context = context;
            this.baby_id= baby_id;
            this.title = title;
            this.date = date;
            paintSetting();
            SizeUtils su = new SizeUtils(getContext());

            w_unit = ( su.getWidth() / 27);
            h_unit = ( su.getHeight() / 6);

        }

        private void paintSetting() {

            cPaint = new Paint();
            textPaint = new Paint();
            textPaint2 = new Paint();
            feedPaint = new Paint();
            diaperPaint = new Paint();
            medicinePaint = new Paint();
            sleepPaint = new Paint();

            paintSetting(cPaint, Color.DKGRAY, 4, true, 30);
            paintSetting(textPaint, Color.DKGRAY, 4, true, 20);
            paintSetting(textPaint2, Color.argb(255, 66, 75, 81), 0, true, 20);
            paintSetting(feedPaint, Color.argb(200, 238, 80, 87), 0, true, 20);
            paintSetting(diaperPaint, Color.argb(200, 250, 227, 0), 0, true, 20);
            paintSetting(medicinePaint, Color.argb(200, 82, 187, 187), 0, true, 20);
            paintSetting(sleepPaint, Color.argb(200, 230, 240, 153), 1, true, 30);

//            cPaint.setColor(Color.DKGRAY);
//            cPaint.setStrokeWidth(4);
//            cPaint.setAntiAlias(true);
//            cPaint.setTextSize(30);
//
//
//            textPaint.setTextSize(20);
//            textPaint.setColor(Color.DKGRAY);
//
//            textPaint2 = new Paint();
//            textPaint2.setTextSize(20);
//            textPaint2.setColor(Color.argb(255, 66, 75, 81));

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

//            sleepPaint = new Paint();
//            sleepPaint.setTextSize(30);
//            sleepPaint.setStyle(Paint.Style.FILL);
//            sleepPaint.setStrokeWidth(1);
//            sleepPaint.setAntiAlias(true);
//            sleepPaint.setColor(Color.argb(200, 230, 240, 153));
        }

    private void paintSetting(Paint paint, int color, int strokeWidth, boolean antialias, float textSize) {
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(antialias);
        paint.setColor(color);
        paint.setTextSize(textSize);
    }

    public void onDraw(Canvas canvas) {

            canvas.drawColor(Color.WHITE);

            Bitmap milk = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_feed_scarlet);
            Bitmap sleep = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_sleep_green);
            Bitmap diaper = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_diper_yellow);
            Bitmap diaper2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_diper_orange);
            Bitmap medicine = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_medicine_sky);

//            Baby baby = new Baby();
//            baby.setBaby_id("1");

            ActionTransaction transaction = new ActionTransaction(context);
            tu = new TimeUtils();

            ArrayList<Action> mediList = transaction.readActionPerType(baby_id, date, ActionType.MEDICINE);
            ArrayList<Action> sleepList = transaction.readActionPerType(baby_id, date, ActionType.SLEEP);
            ArrayList<Action> diaperList = transaction.readActionPerType(baby_id, date, ActionType.DIAPER);
            ArrayList<Action> feedList = transaction.readActionPerType(baby_id, date, ActionType.FEED);

            drawGraph(canvas, mediList, ActionType.MEDICINE, medicine);
            drawGraph(canvas, sleepList, ActionType.SLEEP, sleep);
            drawGraph(canvas, diaperList, ActionType.DIAPER, diaper2);
            drawGraph(canvas, feedList, ActionType.FEED, milk);

            //수면 종료시간 확인가능하면 수정할 부분
    //            drawType(canvas, w_unit * 19, h_unit * 2, sleep);
    //            drawSleep(canvas, w_unit * 19, h_unit * 2, (w_unit * 20), (h_unit * 2) + 50);

            drawBasic(canvas);


        }

        private void drawGraph(Canvas canvas, ArrayList<Action> list, int type, Bitmap bitmap) {
            int idx =0;
            int temp = 1;

            for(int i = 0; i < list.size(); i++){
                Action action = list.get(i);
                int hour = tu.getIntHour(action.getTime());
                if(idx != hour){
                    idx = hour;
                    drawType(canvas, w_unit * (2 + hour), h_unit * type, bitmap);
                    temp = 1;
                }else if(idx == hour){
                    temp++;
                }
                if(temp > 1) {
                    drawMultipleMark(type, canvas, w_unit * (2 + hour), h_unit * type, temp);
                }
            }
        }


        private void drawType(Canvas canvas, int a, int b, Bitmap img) {

            RectF basicRec = new RectF(a, b, a + 50, b + 50);
            canvas.drawBitmap(img, null, basicRec, null);

        }

        private void drawSleep(Canvas canvas, int a, int b, int c, int d) {

            canvas.drawRect(a + 60, b, c - 30, d, sleepPaint);

        }

        private void drawMultipleMark(int type, Canvas canvas, int a, int b, int count) {

            switch(type){
                case ActionType.MEDICINE:
                    canvas.drawCircle(a + 45, b + 40, 15, medicinePaint);
                    canvas.drawText(count + "", a + 40, b + 48, textPaint2);
                    break;
                case ActionType.SLEEP:
                    canvas.drawCircle(a + 45, b + 40, 15, sleepPaint);
                    canvas.drawText(count + "", a + 40, b + 48, textPaint2);
                    break;
                case ActionType.DIAPER:
                    canvas.drawCircle(a + 45, b + 40, 15, diaperPaint);
                    canvas.drawText(count + "", a + 40, b + 48, textPaint2);
                    break;
                case ActionType.FEED:
                    canvas.drawCircle(a + 45, b + 40, 15, feedPaint);
                    canvas.drawText(count + "", a + 40, b + 48, textPaint2);
                    break;
                default : break;
            }
        }

        private void drawBasic(Canvas canvas) {

            SizeUtils su = new SizeUtils(context);

            //상단 날짜 표시
            canvas.drawText(title, (su.getWidth() / 2) - 100, 50, cPaint);

            //시간 표시
            for (int i = 0; i < 25; i++) {
                canvas.drawText(i + "", 120 + (w_unit * i), (h_unit * 5), textPaint);
            }

            //type구분
            canvas.drawText("medicine", 40, 20 + (h_unit * 1), textPaint);
            canvas.drawText("sleep", 40, 20 + (h_unit * 2), textPaint);
            canvas.drawText("diaper", 40, 20 + (h_unit * 3), textPaint);
            canvas.drawText("milk", 40, 20 + (h_unit * 4), textPaint);

        }
}
