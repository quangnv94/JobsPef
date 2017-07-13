package duan2.jobspef.luyquangdat.com.myapplication.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.widget.TextView;


public class JobsPef {
    public static boolean getBooleanData(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean r = preferences.getBoolean(key, true);
        return r;
    }

    public static void setLayoutFont(Context context, TextView textView) {
        Typeface mFont = Typeface.createFromAsset(context.getAssets(), "fonts/JF-Flat-regular.ttf");
        textView.setTypeface(mFont);
    }
}
