package mysnapp.app.dei.com.mysnapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import mysnapp.app.dei.com.mysnapp.MyApp;
import mysnapp.app.dei.com.mysnapp.R;
import mysnapp.app.dei.com.mysnapp.listeners.ImageSaveListener;

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

    public static DisplayMetrics getDisplayMetrics (Activity context)  {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static void saveImage(final String fileName, final Bitmap image, final ImageSaveListener listener) {

        Context c = MyApp.getAppContext();
        if (isExternalStorageWritable()) {

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(path, c.getResources().getString(R.string.app_name) + "/" + fileName);
            file.getParentFile().mkdirs();

            writeToStorage(file, image, listener);

        } else {
            File file = new File("/mnt/sdcard/Pictures/", c.getResources().getString(R.string.app_name) + "/" + fileName);
            file.getParentFile().mkdirs();
            writeToStorage(file, image, listener);
        }
    }

    private static void writeToStorage(File file, Bitmap image, final ImageSaveListener listener) {
        Context c = MyApp.getAppContext();
        try {
            if (file.exists())
                file.delete();
            if (image != null) {
                /*File dir = new File(file.getParent());
                if (!dir.exists()) {
                    dir.mkdirs();
                }*/
                image.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
                MediaScannerConnection.scanFile(c, new String[]{file.toString()}, null,
                        (path, uri) -> listener.onImageSave(uri, path)
                );
            } else {
                listener.onError(c.getResources().getString(R.string.string_image_not_downloaded_properly));
            }
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }


}
