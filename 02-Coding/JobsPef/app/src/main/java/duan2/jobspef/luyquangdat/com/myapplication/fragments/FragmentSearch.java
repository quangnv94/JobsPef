package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;

import java.util.ArrayList;
import java.util.Collections;

import duan2.jobspef.luyquangdat.com.myapplication.LoginActivity;
import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.adapter.PostAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.entity.PostResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentSearch extends Fragment implements View.OnClickListener {
    private Context context;
    private Toolbar toolbar;
    private ProgressDialog progDialog = null;
    private EditText edtSearch;
    private Button btnSearch;
    private RecyclerView rcSearch;
    private ArrayList<PostResponse> listPost = new ArrayList<>();
    private PostAdapter postAdapter;
    private TextView tvResult;
    private LinearLayout layoutTop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        toolbar = rootView.findViewById(R.id.toolbar);
        context = rootView.getContext();
        initController(rootView);
        return rootView;
    }


    private void initController(View v) {

        edtSearch = v.findViewById(R.id.edtSearch);
        btnSearch = v.findViewById(R.id.btnSearch);
        tvResult = v.findViewById(R.id.tvResult);
        btnSearch.setOnClickListener(this);
        rcSearch = v.findViewById(R.id.rcSearch);
        toolbar = v.findViewById(R.id.toolbar);
        TextView txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getString(R.string.search));
        ImageView imgBack = toolbar.findViewById(R.id.imgCreatePost);
        imgBack.setVisibility(View.INVISIBLE);
        ImageView imgMore = toolbar.findViewById(R.id.imgMore);
        imgMore.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(getContext());
        rcSearch.setLayoutManager(mLayoutManager);
    }

    private void getData(final String title) {
        showProgressDialog();
        ConnectServer.getResponseAPI().findPost(title).enqueue(new Callback<ArrayList<PostResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PostResponse>> call, Response<ArrayList<PostResponse>> response) {
                MyUtils.hideKeyboard(getActivity());
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    listPost = response.body();
                    tvResult.setText("Tìm thấy " + listPost.size() + " kết quả với từ khoá " + title);
                    postAdapter = new PostAdapter(getActivity(), listPost);
                    Collections.reverse(listPost);
                    rcSearch.setAdapter(postAdapter);
                } else {
                    showDialogConfirm(R.drawable.warning, R.style.DialogAnimationBottom,
                            getString(R.string.find_error), getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostResponse>> call, Throwable t) {
                dismissProgressDialog();
                showDialogConfirm(R.drawable.warning, R.style.DialogAnimationBottom,
                        getString(R.string.find_error), getString(R.string.error));
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (listPost != null || !listPost.isEmpty()) {
            postAdapter = new PostAdapter(getActivity(), listPost);
            rcSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
            rcSearch.setAdapter(postAdapter);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                String title = edtSearch.getText().toString().trim();
                getData(title);
                break;
        }
    }

    public void showDialogConfirm(int icon, int animationSource, String message, String title) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(icon);

        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = animationSource;
        alertDialog.show();


    }
}
