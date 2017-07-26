package duan2.jobspef.luyquangdat.com.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

/**
 * Created by quangnv on 7/25/17.
 */

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

    public static void buildDialogBottom(Context mContext, int animationSource, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thông báo !");
        builder.setMessage(message);
        builder.setNegativeButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = animationSource;
        dialog.show();
    }
}
