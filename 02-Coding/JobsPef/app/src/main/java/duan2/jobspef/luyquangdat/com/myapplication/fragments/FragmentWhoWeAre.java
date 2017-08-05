package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;


import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.entity.WhoWeAreResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentWhoWeAre extends Fragment {
    private Context context;
    private Toolbar toolbar;
    private ProgressDialog progDialog = null;
    private TextView tvWhoWeAre;
    private Drawer drawer;
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_who_we_are, container, false);
        toolbar = rootView.findViewById(R.id.toolbar);
        drawer = ((MainActivity) getActivity()).getDrawer();
        context = rootView.getContext();
        initController(rootView);
        setupData();
        return rootView;
    }


    private void initController(View v) {
////        tvWhoWeAre = (TextView) v.findViewById(R.id.tvWhoWeAre);
////        tvWhoWeAre.setMovementMethod(new ScrollingMovementMethod());
//        webView = v.findViewById(R.id.my_webView);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setSupportZoom(true);

        toolbar = v.findViewById(R.id.toolbar);
        TextView txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getString(R.string.who_we_are));
        ImageView imgBack = toolbar.findViewById(R.id.imgCreatePost);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentCreateJobs()).addToBackStack(null).commit();
            }
        });
        ImageView imgMore = toolbar.findViewById(R.id.imgMore);
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen()) {
                    drawer.openDrawer();
                } else {
                    drawer.closeDrawer();
                }
            }
        });
    }

    private void setupData() {
        getWhoWeAre();
    }


    private void getWhoWeAre() {
  //      showProgressDialog();
//        ConnectServer.getResponseAPI().getWhoWeAre().enqueue(new Callback<WhoWeAreResponse>() {
//            @Override
//            public void onResponse(Call<WhoWeAreResponse> call, Response<WhoWeAreResponse> response) {
//                dismissProgressDialog();
//                if (response.isSuccessful()) {
////                    tvWhoWeAre.setText(response.body().getContent());
//                    webView.loadDataWithBaseURL("", response.body().getContent(), "text/html", "UTF-8", "");
//                } else {
//                    MyUtils.showToast(getContext(), getString(R.string.oops_something_gone_wrong));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WhoWeAreResponse> call, Throwable t) {
//                dismissProgressDialog();
//            }
//        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpToolbar();
    }

    private void setUpToolbar() {
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

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
