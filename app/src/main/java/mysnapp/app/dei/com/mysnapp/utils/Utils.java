package mysnapp.app.dei.com.mysnapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

  /*Given time(in string) in UTC+OFFSET format
    is converted to string as SimpleDateFormat dd-MMM-yyyy hh:mm a */

    public static String getFormattedDateString(String input) {
        String result = input.replaceAll("^/Date\\(", "");
        int index = result.indexOf('+');
        if (index == -1) {
            index = result.indexOf('-');
        }
        String dateMili = result.substring(0, index);
        String timezone = "GMT" + result.substring(dateMili.length(), result.indexOf(')'));
        long milli = new Long(dateMili);
        TimeZone zone = TimeZone.getTimeZone(timezone);
        Calendar cal = Calendar.getInstance(zone);
        cal.setTimeInMillis(milli);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.ENGLISH);
        dateFormatter.setTimeZone(zone);
        String s = dateFormatter.format(cal.getTime()).toUpperCase();
        return s;
    }

    public static Date getFormattedDate(String input) {
        String result = input.replaceAll("^/Date\\(", "");
        int index = result.indexOf('+');
        if (index == -1) {
            index = result.indexOf('-');
        }
        String dateMili = result.substring(0, index);
        String timezone = "GMT" + result.substring(dateMili.length(), result.indexOf(')'));
        long milli = new Long(dateMili);
        TimeZone zone = TimeZone.getTimeZone(timezone);
        Calendar cal = Calendar.getInstance(zone);
        cal.setTimeInMillis(milli);
        return cal.getTime();
    }

}
