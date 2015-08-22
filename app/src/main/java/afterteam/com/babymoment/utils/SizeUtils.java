package afterteam.com.babymoment.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by hyes on 2015. 8. 21..
 */
public class SizeUtils {

    private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());
    private Display display;
    private DisplayMetrics metrics;
    private int width, height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SizeUtils(Context context) {
        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        metrics = context.getResources().getDisplayMetrics();
        getDefaultSize();
    }

    private void getDefaultSize() {

        width = metrics.widthPixels;
        height = metrics.heightPixels;

        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                width = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                height = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                width = realSize.x;
                height = realSize.y;
            } catch (Exception e) {
                e.printStackTrace();
            }

        Log.i(TAG, "width: "+ width + "height: " + height);

    }
}


