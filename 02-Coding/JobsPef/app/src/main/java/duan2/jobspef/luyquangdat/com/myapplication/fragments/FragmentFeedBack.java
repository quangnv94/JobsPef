package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;
import com.mikepenz.materialdrawer.Drawer;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.entity.SimpleResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFeedBack extends Fragment implements View.OnClickListener {
    private Context context;
    private Toolbar toolbar;
    private ProgressDialog progDialog = null;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtMessage;
    private Button btnSend;
    private Drawer drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.hideKeyboard(getActivity());
            }
        });
        context = rootView.getContext();
        toolbar = rootView.findViewById(R.id.toolbar);
        drawer = ((MainActivity) getActivity()).getDrawer();
        initController(rootView);
        setupData();
        return rootView;
    }

    private void sendMessage() {
        if (edtEmail.getText().toString().equals("") || edtMessage.getText().toString().equals("") || edtName.getText().toString().equals("")) {
            MyUtils.showToast(getContext(), getString(R.string.please_fill_all_field));
        } else if(!MyUtils.isEmailValid(edtEmail.getText().toString().trim())) {
            MyUtils.showToast(getContext(), getString(R.string.email_invalid));
        }
        else{
            showProgressDialog();
            ConnectServer.getResponseAPI().sendFeedBack(edtEmail.getText().toString(), edtName.getText().toString(), edtMessage.getText().toString()).enqueue(new Callback<SimpleResponse>() {
                @Override
                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                    dismissProgressDialog();
                    if (response.isSuccessful()) {
                        MyUtils.showToast(getContext(), getString(R.string.message_send_success));
                    } else {
                        MyUtils.showToast(getContext(), getString(R.string.oops_something_gone_wrong));
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                    dismissProgressDialog();

                }
            });
        }
    }

    private void initController(View v) {
        edtName = v.findViewById(R.id.edtName);
        edtEmail = v.findViewById(R.id.edtEmail);
        edtMessage = v.findViewById(R.id.edtMessage);
        btnSend = v.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        TextView txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(getString(R.string.feedback));
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
        ImageView imgBack = toolbar.findViewById(R.id.imgCreatePost);
        imgBack.setVisibility(View.INVISIBLE);
    }

    private void setupData() {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                MyUtils.hideKeyboard(getActivity());
                sendMessage();
                break;
        }
    }
}
