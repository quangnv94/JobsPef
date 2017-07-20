package duan2.jobspef.luyquangdat.com.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.libre.mylibs.MyUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.OfferResponse;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentOfferDetail;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<OfferResponse.Data> listItem;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePreview;
        TextView tvTitle;
        TextView tvDate;
        TextView tvCategoryName;
        TextView tvShortDescription;
        View view;
        public MyViewHolder(View view) {
            super(view);
            imagePreview = view.findViewById(R.id.imgNews);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDate = view.findViewById(R.id.tvDate);
            tvCategoryName = view.findViewById(R.id.tvCategoryName);
            tvShortDescription = view.findViewById(R.id.tvShortDescription);
            this.view =view;
        }
    }


    public OfferAdapter(Activity activity, ArrayList<OfferResponse.Data> listItem) {
        this.context = activity;
        this.listItem = listItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offer, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final OfferResponse.Data entity = listItem.get(position);
        if(entity.getPreview_image_id()!=null) {
            holder.imagePreview.post(new Runnable() {
                @Override
                public void run() {
                    int width = holder.imagePreview.getWidth();
                    int height = holder.imagePreview.getHeight();
                    Picasso.with(context).load(Constants.HOST+"/lbmedia/"+entity.getPreview_image_id()+"?height="+height+"&width="+width+"&style=scale_to_fit").into(holder.imagePreview);
                }
            });
        }
        holder.tvTitle.setText(entity.getTitle());
        holder.tvDate.setText(MyUtils.getFormatedDate("yyyy-MM-dd HH:mm:ss","dd-MM-yyyy",entity.getUpdated_at()));
        if(entity.getCategory()!=null) {
            holder.tvCategoryName.setText(entity.getCategory().getCategory_name());
        }
        holder.tvShortDescription.setText(entity.getShort_description());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString(Constants.OFFER_ID,""+entity.getId());
                FragmentOfferDetail fragmentOfferDetail = new FragmentOfferDetail();
                fragmentOfferDetail.setArguments(data);
                ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragmentOfferDetail).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
