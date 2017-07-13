package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.adapter.ViewPagerTabsAdapter;


public class FragmentTabsCategory extends Fragment {
    private Toolbar toolbar;
    private Drawer drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerTabsAdapter viewPagerTabsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_category, container, false);
//        toolbar = ((MainActivity)getActivity()).getToolbar();
        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        initController(rootView);
        tabsLayoutBuilder();
        drawer = ((MainActivity) getActivity()).getDrawer();
        return rootView;
    }


    private void initController(View v) {
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView txtToolbarTitle =(TextView) toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getText(R.string.app_name));
        ImageView imgBack =(ImageView) toolbar.findViewById(R.id.imgBack);
        imgBack.setVisibility(View.VISIBLE);
        ImageView imgMore =(ImageView) toolbar.findViewById(R.id.imgMore);
        imgMore.setVisibility(View.VISIBLE);
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!drawer.isDrawerOpen()) {
                    drawer.openDrawer();
                }else {
                    drawer.closeDrawer();
                }
            }
        });
        imgBack.setImageResource(R.drawable.bell);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container,new FragmentNotification()).addToBackStack(null).commit();
            }
        });
    }

    private void tabsLayoutBuilder() {
        viewPagerTabsAdapter = new ViewPagerTabsAdapter(getChildFragmentManager());
        viewPagerTabsAdapter.addFrag(new FragmentCategory(),"DashBoard");
        viewPagerTabsAdapter.addFrag(new FragmentCategory(),"Order");
        viewPagerTabsAdapter.addFrag(new FragmentCategory(),"History");
        viewPagerTabsAdapter.addFrag(new FragmentCategory(),"Account Information");
        viewPager.setAdapter(viewPagerTabsAdapter);

        final TabLayout.Tab Dashboard = tabLayout.newTab();
//        final TabLayout.Tab Order = tabLayout.newTab();
//        final TabLayout.Tab History = tabLayout.newTab();
//        final TabLayout.Tab AccountInformation = tabLayout.newTab();

        Dashboard.setText("Dashboard");
//        Order.setText("Order");
//        History.setText("History");
//        AccountInformation.setText("Account Information");

        tabLayout.addTab(Dashboard, 0);
        tabLayout.addTab(Dashboard, 1);
        tabLayout.addTab(Dashboard, 2);

//        tabLayout.addTab(Order, 1);
//        tabLayout.addTab(History, 2);
//        tabLayout.addTab(AccountInformation, 3);
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

            }
        });
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.gray));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}
