package afterteam.com.babymoment.utils;

import android.util.Log;

/**
 * Util for processing the Log
 * Use [private final String TAG = LogUtils.makeTag(this.getClass().getSimpleName());]
 * @author chayongbin
 */

public class LogUtils {
    public static final String PRE_FIX = "BabyMoment_";

    public static String makeTag(String className) {
        return PRE_FIX + className;
    }

    // For Special exception of Kakao

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) return "";
        return Log.getStackTraceString(tr);
    }
}