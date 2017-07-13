package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.adapter.TestAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.entity.TestResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by QuangNV on 7/14/2017.
 */


public class FragmentCreateJobs extends Fragment {
    private Toolbar toolbar;
    private Drawer drawer;
    private Context context;
    private ProgressDialog progDialog = null;
    private RecyclerView rcDocument;
    private ArrayList<TestResponse> listDocument = new ArrayList<>();
    private TestAdapter documentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment__document, container, false);
        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        drawer = ((MainActivity) getActivity()).getDrawer();
        context = rootView.getContext();
        initController(rootView);
        return rootView;
    }

    public void initController(View v) {
        rcDocument = (RecyclerView) v.findViewById(R.id.rcDocument);
    }

    @Override
    public void onResume() {
        super.onResume();
//        toolbar.setVisibility(View.GONE);
        if (listDocument == null || listDocument.isEmpty()) {
            getDocument();
        } else {
            rcDocument.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            documentAdapter = new TestAdapter(getActivity(), listDocument);
            rcDocument.setAdapter(documentAdapter);
        }
    }

    private void getDocument() {
        showProgressDialog();
        ConnectServer.getResponseAPI().getDocument().enqueue(new Callback<ArrayList<TestResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<TestResponse>> call, Response<ArrayList<TestResponse>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    listDocument = response.body();
                    rcDocument.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    documentAdapter = new TestAdapter(getActivity(), listDocument);
                    rcDocument.setAdapter(documentAdapter);
                } else {
                    MyUtils.showToast(getContext(), getString(R.string.oops_something_gone_wrong));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TestResponse>> call, Throwable t) {
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
