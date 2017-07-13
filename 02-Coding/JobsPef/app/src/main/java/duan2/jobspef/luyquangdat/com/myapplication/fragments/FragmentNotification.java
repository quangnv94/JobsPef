package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.adapter.NotificationAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.entity.NotificationResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentNotification extends Fragment {
    private Context context;
    private Toolbar toolbar;
    private ProgressDialog progDialog = null;
    private RecyclerView rcOffer;
    private ArrayList<NotificationResponse> listNotification = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private Drawer drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification_history, container, false);
        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        drawer = ((MainActivity) getActivity()).getDrawer();
        context = rootView.getContext();
        initController(rootView);
        setupData();
        return rootView;
    }


    private void initController(View v) {
        rcOffer = (RecyclerView) v.findViewById(R.id.rcOffer);
//        toolbar = (Toolbar) v.findViewById(R.id.toolbar);

    }

    @Override
    public void onResume() {
        super.onResume();
        TextView txtToolbarTitle = (TextView) toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getString(R.string.notification_history));
        ImageView imgBack = (ImageView) toolbar.findViewById(R.id.imgBack);
        imgBack.setVisibility(View.GONE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ImageView imgMore = (ImageView) toolbar.findViewById(R.id.imgMore);
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!drawer.isDrawerOpen()){
                    drawer.openDrawer();
                }
                else {
                    drawer.closeDrawer();
                }
            }
        });
    }

    private void setupData() {
        getNotification();
    }

    private void getNotification() {
        showProgressDialog();
        ConnectServer.getResponseAPI().getAllNotification().enqueue(new Callback<ArrayList<NotificationResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationResponse>> call, Response<ArrayList<NotificationResponse>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    listNotification = response.body();
                    notificationAdapter = new NotificationAdapter(getActivity(), listNotification);
                    rcOffer.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rcOffer.setAdapter(notificationAdapter);
                } else {
                    MyUtils.showToast(getContext(), getString(R.string.oops_something_gone_wrong));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NotificationResponse>> call, Throwable t) {
                dismissProgressDialog();
                Log.e("notification", t.toString());
            }
        });
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setUpToolbar();
//    }
//
//    private void setUpToolbar() {
//        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
//        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//    }

    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(context);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage(getString(R.string.loading));
        progDialog.show();
    }

    private void dismissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }
}
