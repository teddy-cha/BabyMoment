package afterteam.com.babymoment.utils;

import android.util.Log;

/**
 * Util for processing the Log
 * Use [private static final String TAG = LogUtils.makeTag(<current class name>);]
 * @author chayongbin
 */

public class LogUtils {
    public static final String PRE_FIX = "BabyMoment_";

    public static String makeTag(Class clazz) {
        return PRE_FIX + clazz.getSimpleName();
    }

    // For Special exception of Kakao

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) return "";
        return Log.getStackTraceString(tr);
    }
}