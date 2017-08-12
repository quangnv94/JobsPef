package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;

public class FragmentWhoWeAre extends Fragment {
    private Context context;
    private Toolbar toolbar;
    private Drawer drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_who_we_are, container, false);
        toolbar = rootView.findViewById(R.id.toolbar);
        drawer = ((MainActivity) getActivity()).getDrawer();
        context = rootView.getContext();
        initController(rootView);
        return rootView;
    }


    private void initController(View v) {
        toolbar = v.findViewById(R.id.toolbar);
        TextView txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getString(R.string.who_we_are));
        ImageView imgSearch = toolbar.findViewById(R.id.imgCreatePost);
        imgSearch.setVisibility(View.INVISIBLE);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpToolbar();
    }

    private void setUpToolbar() {
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}
