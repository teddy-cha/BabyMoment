package afterteam.com.babymoment.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by hyes on 2015. 8. 21..
 */
public class SizeUtils {

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

        // since SDK_INT = 1;
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                width = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                height = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception ignored) {
            }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                width = realSize.x;
                height = realSize.y;
            } catch (Exception ignored) {
            }
    }
}

//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;


