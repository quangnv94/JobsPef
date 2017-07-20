package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;
import com.squareup.picasso.Picasso;

import com.viewpagerindicator.CirclePageIndicator;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.adapter.ViewPagerImagePreviewAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.OfferDetailResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOfferDetail extends Fragment implements View.OnClickListener {
    private Context context;
    private Toolbar toolbar;
    private ProgressDialog progDialog = null;
    private WebView wvDescription;
    private ImageView imgPreview;
    private TextView tvDate;
    private TextView tvCategoryName;
    private TextView tvTitle;
    private ViewPager pagerPreview;
    private String youtubeURL = "";
    private CirclePageIndicator circlePageIndicator;
    private String type;
    private String videoURL;
    private ImageView imgBanner;
    private RelativeLayout layoutPreview;
    private Drawer drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offer_detail, container, false);
        toolbar = rootView.findViewById(R.id.toolbar);
        context = rootView.getContext();
        drawer = ((MainActivity) getActivity()).getDrawer();
        initController(rootView);
        setupData();
        return rootView;
    }


    private void initController(View v) {
        layoutPreview = v.findViewById(R.id.layoutPreview);
        imgBanner = v.findViewById(R.id.imgBanner);
        circlePageIndicator = v.findViewById(R.id.circlePageIndicator);
        tvDate = v.findViewById(R.id.tvDate);
        tvCategoryName = v.findViewById(R.id.tvCategoryName);
        tvTitle = v.findViewById(R.id.tvTitle);
        imgPreview = v.findViewById(R.id.imgPreview);
        imgPreview.setOnClickListener(this);
        wvDescription = v.findViewById(R.id.wvDescription);
        pagerPreview = v.findViewById(R.id.pagerPreview);
//        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ImageView imgBack = toolbar.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        imgBack.setImageResource(R.drawable.ic_back);
        tvTitle.setText("");
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
        imgBanner.post(new Runnable() {
            @Override
            public void run() {
                imgBanner.getLayoutParams().height = (int) ((float) MyUtils.getScreenHeight() / 8);
                Log.e("ádasd", "" + imgBanner.getWidth());
            }
        });
        pagerPreview.post(new Runnable() {
            @Override
            public void run() {
                pagerPreview.getLayoutParams().height = (int) ((float) pagerPreview.getWidth() / 1.67);
            }
        });
        getNewsDetail();
//        getBanner();
    }

    private void getNewsDetail() {
        showProgressDialog();
        Bundle data = getArguments();
        Log.e("bleu", data.getString(Constants.OFFER_ID));

        ConnectServer.getResponseAPI().getOfferDetail(data.getString(Constants.OFFER_ID)).enqueue(new Callback<OfferDetailResponse>() {
            @Override
            public void onResponse(Call<OfferDetailResponse> call, final Response<OfferDetailResponse> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    tvDate.setText(MyUtils.getFormatedDate("yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy", response.body().getUpdated_at()));
                    tvCategoryName.setText(response.body().getCategory().getCategory_name());
                    tvTitle.setText(response.body().getTitle());
                    wvDescription.getSettings().setJavaScriptEnabled(true);
//                    wvDescription.setJavaScriptEnabled(true);
                    wvDescription.loadUrl("about:blank");
                    wvDescription.setWebViewClient(new WebViewClient() {
                        boolean loadFinish = false;

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            if (!loadFinish) {
                                wvDescription.loadDataWithBaseURL("javascript:MyApp.resize(document.body.getBoundingClientRect().height)", "<style>img{display: inline;max-height: 100%;max-width: 100%;height: inherit !important;}</style>" + response.body().getDescription().toString(), "text/html", "charset=UTF-8", "");
                                loadFinish = true;
                            }
                            super.onPageFinished(view, url);
                        }
                    });
                    type = response.body().getType();
                    if (response.body().getBanner_image_id() != null) {
                        Picasso.with(context)
                                .load(Constants.HOST + "/lbmedia/" + response.body().getBanner_image_id())
                                .into(imgBanner);
                        imgBanner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String bannerURL = response.body().getBanner_url();
                                if (!bannerURL.startsWith("https://") && !bannerURL.startsWith("http://")) {
                                    bannerURL = "http://" + bannerURL;
                                }
                                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(bannerURL)));
                            }
                        });
                    } else {
                        imgBanner.setVisibility(View.GONE);
                    }
                    if (response.body().getType().equals("1")) {
                        ViewPagerImagePreviewAdapter previewAdapter = new ViewPagerImagePreviewAdapter(getActivity(), response.body().getMedia().getImages());
                        pagerPreview.setAdapter(previewAdapter);
                        circlePageIndicator.setViewPager(pagerPreview);
                        layoutPreview.setVisibility(View.GONE);
                    } else if (response.body().getType().equals("2")) {
                        pagerPreview.setVisibility(View.GONE);
                        circlePageIndicator.setVisibility(View.GONE);
                        layoutPreview.setVisibility(View.VISIBLE);
                        videoURL = response.body().getMedia().getVideo();
                        Picasso.with(getContext())
                                .load(Constants.HOST + "/lbmedia/" + response.body().getPreview_image_id())
                                .into(imgPreview);
                    } else {
                        pagerPreview.setVisibility(View.GONE);
                        circlePageIndicator.setVisibility(View.GONE);
                        layoutPreview.setVisibility(View.VISIBLE);
                        youtubeURL = response.body().getMedia().getYoutube();
                        String youtubeImageURL = response.body().getMedia().getYoutube().replace("https://youtu.be/", "");
                        youtubeImageURL = youtubeImageURL.replace("https://www.youtube.com/watch?v=", "");
                        Picasso.with(getContext())
                                .load("https://img.youtube.com/vi/" + youtubeImageURL + "/hqdefault.jpg")
                                .into(imgPreview);

                    }
                }
            }

            @Override
            public void onFailure(Call<OfferDetailResponse> call, Throwable t) {
                dismissProgressDialog();
                Log.e("failesd", t.toString());
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPreview:
                if (type.equals("2")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(Constants.HOST + videoURL), "video/mp4");
                    Log.e("ssj", Constants.HOST + videoURL);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.HOST + videoURL));
//                    intent.setType("video/*");
                    getActivity().startActivity(intent);
                } else {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL)));
                }
                break;
        }
    }
}
