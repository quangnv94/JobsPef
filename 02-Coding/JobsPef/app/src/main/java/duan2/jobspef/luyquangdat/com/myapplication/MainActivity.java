package duan2.jobspef.luyquangdat.com.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentCategory;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentFeedBack;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentWhoWeAre;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentSettings;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, FragmentSettings.UpdateImage {
    public static Drawer drawer;
    private Toolbar toolbar;
    private LinearLayout layoutHome;
    private LinearLayout layoutSetting;
    private LinearLayout layoutContactUs;
    private LinearLayout layoutWhoWeAre;
    private LinearLayout layoutShare;
    private LinearLayout layoutSignOut;
    private TextView tvName;
    private ImageView imgAva;

    private boolean isFragment = true;

    private String image;

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

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentCategory()).addToBackStack("FragmentCategory").commit();
        initController();
        getAva();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            if (isFragment == true) {
                super.onBackPressed();
            }
        }
        getActivityFragment();
    }

    public FragmentCategory getActivityFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }

        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        if (tag != null) {
            if (tag.equals("FragmentCategory")) {
                isFragment = false;
            } else {
                isFragment = true;
            }
        } else {
            isFragment = true;
        }
        return (FragmentCategory) getSupportFragmentManager().findFragmentByTag(tag);

    }

    private void setupDrawerLayout() {
        LayoutInflater inflater = getLayoutInflater();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withRootView(R.id.drawer_container)
                .withDrawerGravity(Gravity.LEFT)
                .withDisplayBelowStatusBar(false)
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
        layoutHome = (LinearLayout) findViewById(R.id.layout_home);
        layoutSetting = (LinearLayout) findViewById(R.id.layout_setting);
        layoutContactUs = (LinearLayout) findViewById(R.id.layout_contact_us);
        layoutWhoWeAre = (LinearLayout) findViewById(R.id.layout_who_we_are);
        layoutShare = (LinearLayout) findViewById(R.id.layout_share);
        layoutSignOut = (LinearLayout) findViewById(R.id.layoutSignOut);
        imgAva = (ImageView) findViewById(R.id.imgAvatar);
        tvName = (TextView) findViewById(R.id.tvName);
        layoutHome.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);
        layoutContactUs.setOnClickListener(this);
        layoutWhoWeAre.setOnClickListener(this);
        layoutSignOut.setOnClickListener(this);
        layoutShare.setOnClickListener(this);

        tvName.setText(getString(R.string.hello) + " " + MyUtils.getStringData(MainActivity.this, Constants.NAME) + "!");
        getAva();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAvatar:
                break;
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
            case R.id.layoutSignOut:
                MyUtils.insertStringData(getApplicationContext(), Constants.TOKEN, "");
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.layout_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Link share");
                startActivity(shareIntent);
                break;
        }
    }

    public void getAva() {
        image = MyUtils.getStringData(getApplicationContext(), Constants.IMAGE_ID);
        if (image != null || !image.equals("")) {
            Glide.with(getApplicationContext()).load(image).error(R.drawable.avatar).into(imgAva);
        }
    }

    @Override
    public void onDataPass() {
        getAva();
        tvName.setText(getString(R.string.hello) + " " + MyUtils.getStringData(MainActivity.this, Constants.NAME) + "!");
    }
}
