package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.internal.CollectionMapper;
import com.libre.mylibs.MyUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.adapter.PostAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.PostResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPost extends Fragment {
    private Context context;
    private ProgressDialog progDialog = null;
    private RecyclerView rcOffer;
    private ArrayList<PostResponse> listPost = new ArrayList<>();
    private PostAdapter postAdapter;
    private String categoryID = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        context = rootView.getContext();
        Bundle data = getArguments();
        categoryID = data.getString(Constants.CATEGORY_ID);
        initController(rootView);
        getAllPost();
        return rootView;
    }


    private void initController(View v) {
        rcOffer = v.findViewById(R.id.rcOffer);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listPost == null || listPost.isEmpty()) {
            showProgressDialog();
            postAdapter = new PostAdapter(getActivity(), listPost);
            rcOffer.setLayoutManager(new LinearLayoutManager(getActivity()));
            rcOffer.setAdapter(postAdapter);
            getAllPost();
        } else {
            postAdapter = new PostAdapter(getActivity(), listPost);
            rcOffer.setLayoutManager(new LinearLayoutManager(getActivity()));
            rcOffer.setAdapter(postAdapter);
        }
    }

    private void getAllPost() {
        ConnectServer.getResponseAPI().getPost(categoryID).enqueue(new Callback<ArrayList<PostResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PostResponse>> call, Response<ArrayList<PostResponse>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    listPost = response.body();
                    postAdapter = new PostAdapter(getActivity(), listPost);
                    Collections.reverse(listPost);
                    rcOffer.setAdapter(postAdapter);
                } else {
                    MyUtils.showToast(getContext(), getString(R.string.oops_something_gone_wrong));
                }
            }


            @Override
            public void onFailure(Call<ArrayList<PostResponse>> call, Throwable t) {

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
