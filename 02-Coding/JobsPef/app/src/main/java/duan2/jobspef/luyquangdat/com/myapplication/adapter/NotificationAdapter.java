package duan2.jobspef.luyquangdat.com.myapplication.adapter;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.NotificationResponse;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentPostDetail;

/**
 * Created by nguye on 7/7/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private Activity context;
    private ArrayList<NotificationResponse> listItem;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvShortDescription;
        View view;

        public MyViewHolder(View view) {
            super(view);
            tvShortDescription = view.findViewById(R.id.tvShortDescription);
            this.view = view;
        }
    }

    public NotificationAdapter(Activity activity, ArrayList<NotificationResponse> listItem) {
        this.context = activity;
        this.listItem = listItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NotificationResponse entity = listItem.get(position);
        Log.e("html", entity.getContent());
        holder.tvShortDescription.setText(fromHtml(entity.getMessage()));

        if (entity.getPost_id() != null && !entity.getPost_id().equals("")) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentPostDetail fragment_offerDetail = new FragmentPostDetail();
                    Bundle data = new Bundle();
                    data.putSerializable(Constants.OFFER_ID, entity.getPost_id());
                    fragment_offerDetail.setArguments(data);
                    ((MainActivity) context)
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.main_container, fragment_offerDetail)
                            .hide(((MainActivity) context).getSupportFragmentManager().findFragmentById(R.id.main_container))
                            .addToBackStack(null).commit();
                }
            });
        }

//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment_NotificationDetail fragmentNotificationDetail = new Fragment_NotificationDetail();
//                Bundle data = new Bundle();
//                data.putSerializable(Constants.NOTIFICATION_OBJECT,entity);
//                fragmentNotificationDetail.setArguments(data);
//                ((MainActivity) context)
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(R.id.main_container, fragmentNotificationDetail)
//                        .hide(((MainActivity) context).getSupportFragmentManager().findFragmentById(R.id.main_container))
//                        .addToBackStack(null).commit();
//            }
//        });
    }

    @SuppressWarnings("deprecation")
    public static String fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(source).toString();
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
