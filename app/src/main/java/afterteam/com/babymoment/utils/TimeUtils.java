package afterteam.com.babymoment.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eunjooim on 15. 8. 12..
 */
public class TimeUtils {

    private SimpleDateFormat simpleDateFormat;

    public TimeUtils() {
    }

    // return String format date / yyyy-MM-dd
    public String getStringDate(Date date) {
        if (date == null) return null;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public String getStringDate(String dateTime) {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateTime);
            return getStringDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // return String format time / a hh:mm
    public String getStringTime(Date date) {
        simpleDateFormat = new SimpleDateFormat("a hh:mm", java.util.Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public String getStringTime(String dateTime) {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateTime);
            return getStringTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getStringDateTime(Date date) {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public String getStringDateTimeForGraph(Date date) {
        simpleDateFormat = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public int getIntHour(Date date) {
        simpleDateFormat = new SimpleDateFormat("HH", java.util.Locale.getDefault());
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public Date getDate(String dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getDateFromStringDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // compare today and latest data
    public Boolean compareDate(String latest) {
        String today = getStringDate(new Date());
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());

        try {
            Date todayDate = simpleDateFormat.parse(today);
            Date latestDate = simpleDateFormat.parse(latest);

            String test = String.valueOf(todayDate.after(latestDate));

            return todayDate.after(latestDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}
