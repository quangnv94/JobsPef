package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;

import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.common.JobsPef;

public class FragmentSettings extends Fragment{
    private Context context;
    private Toolbar toolbar;
    private ProgressDialog progDialog = null;
    private Switch switchNotification;
    private Drawer drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        context = rootView.getContext();
        toolbar = rootView.findViewById(R.id.toolbar);
        drawer = ((MainActivity) getActivity()).getDrawer();
        initController(rootView);
        return rootView;
    }


    private void initController(View v) {
//        spnCity = (Spinner) v.findViewById(R.id.spnCity);
        switchNotification = v.findViewById(R.id.switch_notification);
        switchNotification.setChecked(JobsPef.getBooleanData(getContext(), Constants.NOTIFICATION_ON_FLAG));
//        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        TextView txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getString(R.string.settings));
        ImageView imgBack = toolbar.findViewById(R.id.imgBack);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container,new FragmentNotification()).addToBackStack(null).commit();
            }
        });
        ImageView imgMore = toolbar.findViewById(R.id.imgMore);
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

        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyUtils.insertBooleanData(getActivity(), Constants.NOTIFICATION_ON_FLAG, isChecked);
            }
        });
    }

}
