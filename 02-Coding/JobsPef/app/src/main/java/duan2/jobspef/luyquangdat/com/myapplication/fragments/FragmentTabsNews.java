package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import duan2.jobspef.luyquangdat.com.myapplication.adapter.ViewPagerTabsAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.CategoryResponse;


public class FragmentTabsNews extends Fragment {
    private Toolbar toolbar;
    private Drawer drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerTabsAdapter viewPagerTabsAdapter;
    private ArrayList<CategoryResponse> categoryResponses = new ArrayList<>();
    private int position;
    private FloatingActionButton lbtnCreatePost;
    private TextView txtToolbarTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_news, container, false);
        toolbar = ((MainActivity) getActivity()).getToolbar();
        initController(rootView);
        Bundle data = getArguments();
        categoryResponses = (ArrayList<CategoryResponse>) data.getSerializable(Constants.LIST_CATEGORY);
        position = data.getInt(Constants.POSITION);
        tabsLayoutBuilder();
        drawer = ((MainActivity) getActivity()).getDrawer();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getText(R.string.app_name));
        ImageView imgBack = toolbar.findViewById(R.id.imgCreatePost);
        imgBack.setVisibility(View.VISIBLE);
        ImageView imgMore = toolbar.findViewById(R.id.imgMore);
        imgMore.setVisibility(View.VISIBLE);
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
        imgBack.setImageDrawable(getResources().getDrawable(R.drawable.loupe));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentSearch()).addToBackStack(null).commit();

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

    private void initController(View v) {
        toolbar = v.findViewById(R.id.toolbar);
        txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getText(R.string.app_name));
        ImageView imgBack = toolbar.findViewById(R.id.imgCreatePost);
        imgBack.setVisibility(View.VISIBLE);
        ImageView imgMore = toolbar.findViewById(R.id.imgMore);
        imgMore.setVisibility(View.VISIBLE);
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen()) {
                    drawer.openDrawer();
                }
            }
        });
        tabLayout = v.findViewById(R.id.tabs);
        viewPager = v.findViewById(R.id.viewpager);

        lbtnCreatePost = v.findViewById(R.id.lbtnCreatePost);
        lbtnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentCreateJobs()).addToBackStack(null).commit();
            }
        });
    }

    private void tabsLayoutBuilder() {
        int number = 0;
        viewPagerTabsAdapter = new ViewPagerTabsAdapter(getChildFragmentManager());
        for (CategoryResponse categoryResponse : categoryResponses) {
            Bundle data = new Bundle();
            data.putString(Constants.CATEGORY_ID, "" + categoryResponse.getId_category());
            data.putString(Constants.CATEGORY_NAME, "" + categoryResponse.getCategory_name());
            FragmentPost fragmentPost = new FragmentPost();
            fragmentPost.setArguments(data);
            viewPagerTabsAdapter.addFrag(fragmentPost, "DashBoard");
            tabLayout.addTab(tabLayout.newTab().setText(categoryResponse.getCategory_name()), number);
            number = number + 1;
        }
        viewPager.setAdapter(viewPagerTabsAdapter);
        viewPager.setCurrentItem(position);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MyUtils.hideKeyboard(getActivity());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                MyUtils.hideKeyboard(getActivity());
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.dark_blue));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}
