package duan2.jobspef.luyquangdat.com.myapplication;

import android.app.ProgressDialog;
import android.content.Context;

import com.github.ybq.android.spinkit.style.FadingCircle;

public class AppUtils {
    static ProgressDialog progressDialog;

    public static void showProgressDialog(Context mContext, String content) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(content);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgressDialog(Context mContext) {
        progressDialog.cancel();
    }

}
