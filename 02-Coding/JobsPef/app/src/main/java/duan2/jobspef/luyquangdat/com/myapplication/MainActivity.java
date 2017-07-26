package duan2.jobspef.luyquangdat.com.myapplication;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;


import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentCategory;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentFeedBack;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentWhoWeAre;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentOfferDetail;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentSettings;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Drawer drawer;
    private Toolbar toolbar;
    private LinearLayout layoutHome;
    private LinearLayout layoutSetting;
    private LinearLayout layoutContactUs;
    private LinearLayout layoutWhoWeAre;
    private LinearLayout layoutShare;
    private LinearLayout layoutSignout;
    private TextView tvDevelopedBy;
    private ProgressDialog progDialog;

    public static Drawer getDrawer() {
        return drawer;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDrawerLayout();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentCategory()).commit();

        initController();
        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
    }

    private void setupDrawerLayout() {
        LayoutInflater inflater = getLayoutInflater();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withRootView(R.id.drawer_container)
                .withDrawerGravity(Gravity.LEFT)
                .withDrawerWidthPx(Integer.parseInt(String.valueOf("" + MyUtils.getScreenWidth() * 3 / 4)))
                .withCustomView(inflater.inflate(R.layout.navigator_drawer, null))
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        MyUtils.hideKeyboard(MainActivity.this);
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                    }
                })
                .build();
    }

    private void initController() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView txtToolbarTitle = (TextView) toolbar.findViewById(R.id.txtToolbarTitle);
//        txtToolbarTitle.setText(getText(R.string.app_name));
//        ImageView imgBack = (ImageView) toolbar.findViewById(R.id.imgBack);
//        imgBack.setVisibility(View.VISIBLE);
//        ImageView imgMore = (ImageView) toolbar.findViewById(R.id.imgMore);
//        imgMore.setVisibility(View.VISIBLE);
//        imgMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!drawer.isDrawerOpen()) {
//                    drawer.openDrawer();
//                }
//            }
//        });
        Intent notificationIntent = getIntent();
        Bundle extras = notificationIntent.getExtras();

        if (extras != null && notificationIntent.getAction().equals("get_post_id")) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new FragmentNews()).commit();
            String postId = extras.getString("post_id");
            Bundle data = new Bundle();
            data.putString(Constants.OFFER_ID, postId);
            FragmentOfferDetail fragmentOfferDetail = new FragmentOfferDetail();
            fragmentOfferDetail.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragmentOfferDetail).addToBackStack(null).commit();
        }
        tvDevelopedBy = (TextView) findViewById(R.id.tvDevelopedBy);
        layoutHome = (LinearLayout) findViewById(R.id.layout_home);
        layoutSetting = (LinearLayout) findViewById(R.id.layout_setting);
        layoutContactUs = (LinearLayout) findViewById(R.id.layout_contact_us);
        layoutWhoWeAre = (LinearLayout) findViewById(R.id.layout_who_we_are);
        layoutShare = (LinearLayout) findViewById(R.id.layout_share);
        layoutSignout = (LinearLayout) findViewById(R.id.layout_signout);
        layoutHome.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);
        layoutContactUs.setOnClickListener(this);
        layoutWhoWeAre.setOnClickListener(this);
        layoutShare.setOnClickListener(this);
        tvDevelopedBy.setOnClickListener(this);
        layoutSignout.setOnClickListener(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_home:
                drawer.closeDrawer();
                if (getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof FragmentCategory) {
                    return;
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentCategory()).commit();
                }
                break;
            case R.id.layout_setting:
                drawer.closeDrawer();
                if (getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof FragmentSettings) {
                    return;
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentSettings()).commit();
                }
                break;
            case R.id.layout_contact_us:
                drawer.closeDrawer();
                if (getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof FragmentFeedBack) {
                    return;
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentFeedBack()).commit();
                }
                break;
            case R.id.layout_who_we_are:
                drawer.closeDrawer();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentWhoWeAre()).commit();
                break;
            case R.id.layout_signout:
                MyUtils.insertStringData(getApplicationContext(), Constants.TOKEN, "");
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.layout_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Link share");
                startActivity(shareIntent);
                break;
            case R.id.tvDevelopedBy:
                String url3 = "http://facebook.com/neverloves94";
                Intent i3 = new Intent(Intent.ACTION_VIEW);
                i3.setData(Uri.parse(url3));
                startActivity(i3);
                break;
        }
    }

    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
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
