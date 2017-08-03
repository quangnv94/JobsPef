package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.adapter.CategoryAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.CategoryResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by quangnv on 7/14/2017.
 */
public class FragmentCategory extends Fragment {
    private Context context;
    private ProgressDialog progDialog = null;
    private RecyclerView rcCategory;
    private ArrayList<CategoryResponse> listCategory = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private Toolbar toolbar;
    private Drawer drawer;
    private ViewPager viewPager;
    private ImageView imgMenu, imgNotifi;


    private ArrayList<CategoryResponse> listCategoryFinal = new ArrayList<CategoryResponse>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        toolbar = rootView.findViewById(R.id.toolbar);
        initToolbar(toolbar);
        drawer = ((MainActivity) getActivity()).getDrawer();
        context = rootView.getContext();
        initController(rootView);
        return rootView;
    }


    private void initController(View v) {
        rcCategory = (RecyclerView) v.findViewById(R.id.rcCategory);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setVisibility(View.GONE);
    }

    private void initToolbar(View v) {
        imgMenu = (ImageView) v.findViewById(R.id.imgMore);
        imgNotifi = (ImageView) v.findViewById(R.id.imgCreatePost);

        imgNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new FragmentCreateJobs()).addToBackStack(null).commit();

            }
        });
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen()) {
                    drawer.openDrawer();
                } else {
                    drawer.closeDrawer();
                }
            }
        });
        TextView tb = v.findViewById(R.id.txtToolbarTitle);
        tb.setText(R.string.tbl_title);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listCategory == null || listCategory.isEmpty()) {
            getCategory();
        } else {
            rcCategory.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            categoryAdapter = new CategoryAdapter(getActivity(), listCategory);
            rcCategory.setAdapter(categoryAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void getCategory() {
        showProgressDialog();
        ConnectServer.getResponseAPI().getCategory().enqueue(new Callback<ArrayList<CategoryResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryResponse>> call, Response<ArrayList<CategoryResponse>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    listCategory = response.body();
                    rcCategory.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    categoryAdapter = new CategoryAdapter(getActivity(), listCategory);
                    rcCategory.setAdapter(categoryAdapter);
                } else {
                    MyUtils.showToast(getContext(), getString(R.string.oops_something_gone_wrong));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryResponse>> call, Throwable t) {
                dismissProgressDialog();

            }
        });
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
