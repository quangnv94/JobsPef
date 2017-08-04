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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.PostResponse;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentPostDetail;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<PostResponse> listItem;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePreview;
        TextView tvTitle;
        TextView tvDate;
        TextView tvCategoryName;
        View view;

        public MyViewHolder(View view) {
            super(view);
            imagePreview = view.findViewById(R.id.imgNews);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDate = view.findViewById(R.id.tvDate);
            tvCategoryName = view.findViewById(R.id.tvCategoryName);
            this.view = view;
        }
    }


    public PostAdapter(Activity activity, ArrayList<PostResponse> listItem) {
        this.context = activity;
        this.listItem = listItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PostResponse entity = listItem.get(position);
        String linkimage = "";
        if (!entity.getImages().equals("")) {
            linkimage = entity.getImages().split(",")[0];
        }
        Glide.with(context).load(linkimage).error(R.drawable.landscape).error(R.drawable.icon).into(holder.imagePreview);
        holder.tvTitle.setText(entity.getTitle());
        holder.tvDate.setText(entity.getCreated_at());
        holder.tvCategoryName.setText(entity.getPlace());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putSerializable(Constants.POST, listItem.get(position));
                FragmentPostDetail fragmentOfferDetail = new FragmentPostDetail();
                fragmentOfferDetail.setArguments(data);
                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragmentOfferDetail).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
