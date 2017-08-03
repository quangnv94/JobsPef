package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.adapter.ViewPagerImagePreviewAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.PostResponse;

public class FragmentPostDetail extends Fragment implements View.OnClickListener {
    private Context context;
    private Toolbar toolbar;
    private TextView tvDate;
    private TextView tvAuthor;
    private TextView tvTitle;
    private TextView tvDatelimit;
    private TextView tvPlace;
    private TextView tvDescription;
    private TextView tvRequirement;
    private TextView tvBenefits;
    private ViewPager pagerPreview;
    private CirclePageIndicator circlePageIndicator;
    private Drawer drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);
        toolbar = rootView.findViewById(R.id.toolbar);
        context = rootView.getContext();
        drawer = ((MainActivity) getActivity()).getDrawer();
        initController(rootView);
        return rootView;
    }


    private void initController(View v) {
        circlePageIndicator = v.findViewById(R.id.circlePageIndicator);
        tvDate = v.findViewById(R.id.tvDate);
        tvTitle = v.findViewById(R.id.tvTitle);
        pagerPreview = v.findViewById(R.id.pagerPreview);
        tvAuthor = v.findViewById(R.id.tvAuthor);
        tvDatelimit = v.findViewById(R.id.tvDatelimit);
        tvPlace = v.findViewById(R.id.tvPlace);
        tvDescription = v.findViewById(R.id.tvDescription);
        tvRequirement = v.findViewById(R.id.tvRequirement);
        tvBenefits = v.findViewById(R.id.tvBenefits);
        ImageView imgBack = toolbar.findViewById(R.id.imgCreatePost);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentCreateJobs()).addToBackStack(null).commit();

            }
        });
        ImageView imgMore = toolbar.findViewById(R.id.imgMore);
        imgMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        getData();

    }

    public void getData() {
        Bundle bundle = getArguments();
        PostResponse postResponse = (PostResponse) bundle.getSerializable(Constants.POST);

        tvTitle.setText(postResponse.getTitle());
        tvDate.setText(getString(R.string.create_at) + postResponse.getCreated_at());
        tvDatelimit.setText(getString(R.string.time_limited) + postResponse.getTime_limited());
        tvAuthor.setText(getString(R.string.create_by) + postResponse.getCreated_by());

        tvPlace.setText(getString(R.string.address) + postResponse.getPlace());
        tvDescription.setText(getString(R.string.content) + postResponse.getDescription());
        tvRequirement.setText(getString(R.string.content2) + postResponse.getRequirement());
        tvBenefits.setText(getString(R.string.salary) + postResponse.getBenefits());
        if (postResponse.getImages().length() > 0) {
            String[] images = postResponse.getImages().split(",");
            ArrayList<String> listImg = new ArrayList<>(Arrays.asList(images));
            ViewPagerImagePreviewAdapter previewAdapter = new ViewPagerImagePreviewAdapter(getActivity(), listImg);
            pagerPreview.setAdapter(previewAdapter);
            circlePageIndicator.setViewPager(pagerPreview);
        }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
