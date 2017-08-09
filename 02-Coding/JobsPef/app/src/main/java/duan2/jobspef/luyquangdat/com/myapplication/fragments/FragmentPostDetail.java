package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private FloatingActionButton btnCall;
    private String numberPhone;

    private static int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 999;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);
        toolbar = rootView.findViewById(R.id.toolbar);
        context = rootView.getContext();
        drawer = ((MainActivity) getActivity()).getDrawer();
        initController(rootView);
        checkPermission();
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
        btnCall = v.findViewById(R.id.lbtnCall);
        btnCall.setOnClickListener(this);
        getData();


    }

    public void getData() {
        Bundle bundle = getArguments();
        PostResponse postResponse = (PostResponse) bundle.getSerializable(Constants.POST);
        TextView txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(postResponse.getTitle());

        tvTitle.setText(postResponse.getTitle());
        tvDate.setText(getResources().getString(R.string.create_at) + postResponse.getCreated_at());
        tvDatelimit.setText(getResources().getString(R.string.time_limited) + postResponse.getTime_limited());
        tvAuthor.setText(getResources().getString(R.string.create_by) + postResponse.getCreated_by());

        tvPlace.setText(getResources().getString(R.string.address) + " " + postResponse.getPlace());
        tvDescription.setText(getResources().getString(R.string.content) + "\n" + postResponse.getDescription());
        tvRequirement.setText(getResources().getString(R.string.content2) + "\n" + postResponse.getRequirement());
        tvBenefits.setText(getResources().getString(R.string.salary) + "\n" + postResponse.getBenefits());

        numberPhone = postResponse.getContact();
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
            case R.id.lbtnCall:
                Log.d("davaodast", "dasdsad");
                Intent mIntent = new Intent(Intent.ACTION_CALL);
                String number = ("tel:" + numberPhone);
                mIntent = new Intent(Intent.ACTION_CALL);
                mIntent.setData(Uri.parse(number));
// Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_CODE_SOME_FEATURES_PERMISSIONS);

                    // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                } else {
                    //You already have permission
                    try {
                        startActivity(mIntent);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int callPermission = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);

            List<String> permissions = new ArrayList<>();
            if (callPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

        }

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(
                getActivity(), new String[]{Manifest.permission.CAMERA}, 998);

    }
}
