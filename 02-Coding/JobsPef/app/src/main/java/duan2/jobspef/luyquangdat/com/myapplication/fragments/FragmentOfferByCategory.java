package duan2.jobspef.luyquangdat.com.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libre.mylibs.MyUtils;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.adapter.OfferAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.OfferResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOfferByCategory extends Fragment {
    private Context context;
    private ProgressDialog progDialog = null;
    private RecyclerView rcOffer;
    private ArrayList<OfferResponse.Data> listOffer = new ArrayList<>();
    private OfferAdapter offerAdapter;
    private String categoryID = "";
    private String categoryName = "";
    private int page=1;
    private SwipyRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offer_by_category, container, false);
        context = rootView.getContext();
        Bundle data = getArguments();
        categoryID = data.getString(Constants.CATEGORY_ID);
        categoryName = data.getString(Constants.CATEGORY_NAME);
        initController(rootView);
        setupData();
        return rootView;
    }


    private void initController(View v) {
        swipeRefreshLayout = (SwipyRefreshLayout) v.findViewById(R.id.swipeTefreshlayout);
        rcOffer = (RecyclerView) v.findViewById(R.id.rcOffer);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(listOffer==null||listOffer.isEmpty()){
            showProgressDialog();
            offerAdapter = new OfferAdapter(getActivity(), listOffer);
            rcOffer.setLayoutManager(new LinearLayoutManager(getActivity()));
            rcOffer.setAdapter(offerAdapter);
            getAllOffer(0);
        }else {
            offerAdapter = new OfferAdapter(getActivity(), listOffer);
            rcOffer.setLayoutManager(new LinearLayoutManager(getActivity()));
            rcOffer.setAdapter(offerAdapter);
        }
    }

    private void setupData() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(  direction== SwipyRefreshLayoutDirection.BOTTOM){
                    getAllOffer(1);
                }else if(  direction== SwipyRefreshLayoutDirection.TOP){
                    getAllOffer(page);
                }
            }
        });
    }
        private void getAllOffer(int value) {
            final String city = MyUtils.getStringData(getContext(), Constants.CITY_ID);
        ConnectServer.getResponseAPI().getAllOfferInCategory(categoryID, value).enqueue(new Callback<OfferResponse>() {
            @Override
            public void onResponse(Call<OfferResponse> call, Response<OfferResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    for (OfferResponse.Data offerResponse : response.body().getData()) {
                        if (!listOffer.contains(offerResponse)) {
                            listOffer.add(offerResponse);
                        }
                    }
                    offerAdapter.notifyDataSetChanged();
//                    if (!listOffer.isEmpty()) {
//                        lastOfferID = listOffer.get(listOffer.size() - 1).g;
//                    }
                    page+= 1;
                } else {
                    MyUtils.showToast(getContext(), getString(R.string.oops_something_gone_wrong));
                }
            }

            @Override
            public void onFailure(Call<OfferResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                dismissProgressDialog();
                Log.e("All offer", t.toString());
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
