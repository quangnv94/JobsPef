package duan2.jobspef.luyquangdat.com.myapplication;

import android.app.ProgressDialog;
import android.content.Context;

import com.github.ybq.android.spinkit.style.FadingCircle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AppUtils {
    static ProgressDialog progressDialog;
    public final static long ONE_SECOND = 1000;

    public final static long ONE_MINUTE = ONE_SECOND * 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;

    public final static long ONE_DAY = ONE_HOUR * 24;

    public final static long ONE_MONTH = ONE_DAY * 31;

    public static void showProgressDialog(Context mContext, String content) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(content);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgressDialog(Context mContext) {
        progressDialog.cancel();
    }

    public static String formatDateString(String dat) {
//        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        TimeZone utc = TimeZone.getTimeZone("GMT");
//        sdf.setTimeZone(utc);
        String value = "";
        //            Date date = sdf.parse(dat);
        Date date = new Date(dat);
        Date timeNow = new Date();
        long duration = timeNow.getTime() - date.getTime();
        if (duration / ONE_MONTH > 4) {
            value = new SimpleDateFormat("dd-MM-yyyy").format(date);
        } else {
            value = millisToLongDHMS(duration);
        }
        return value;
    }

    public static String formatDateStringTwo(String dat) {
//        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        TimeZone utc = TimeZone.getTimeZone("GMT");
//        sdf.setTimeZone(utc);
        String value = "";
        //            Date date = sdf.parse(dat);
        Date date = new Date(dat);
        Date timeNow = new Date();
        long duration = timeNow.getTime() - date.getTime();
        value = new SimpleDateFormat("dd-MM-yyyy").format(date);

        return value;
    }

    public static String millisToLongDHMS(long duration) {
        StringBuffer res = new StringBuffer();
        long temp = 0;
        int dayOffMon = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (duration >= ONE_SECOND) {

            temp = duration / ONE_MONTH;
            if (temp > 0 && temp < 4) {
                return res.append(temp).append(" tháng trước").toString();
            }

            temp = duration / ONE_DAY;
            if (temp > 0 && temp < dayOffMon) {
                return res.append(temp).append(" ngày trước").toString();
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                return res.append(temp).append(" giờ trước").toString();
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                return res.append(temp).append(" phút trước").toString();
            }

        }
        return "Mới đăng !";
    }
}
