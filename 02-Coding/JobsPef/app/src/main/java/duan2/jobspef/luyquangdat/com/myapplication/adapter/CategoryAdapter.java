package duan2.jobspef.luyquangdat.com.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.CategoryResponse;
import duan2.jobspef.luyquangdat.com.myapplication.fragments.FragmentTabsNews;

/**
 * Created by QuangNV on 7/6/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CategoryResponse> listItem;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        CircleImageView imgIcon;
        View view;

        public MyViewHolder(View view) {
            super(view);
            tvCategoryName = view.findViewById(R.id.tvCategoryName);
            imgIcon = view.findViewById(R.id.imgIcon);
            this.view = view;
        }
    }

    public CategoryAdapter(Activity activity, ArrayList<CategoryResponse> listItem) {
        this.context = activity;
        this.listItem = listItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CategoryResponse entity = listItem.get(position);

        holder.tvCategoryName.setText(entity.getCategory_name());
        String image = Constants.HOST_IMAGE + entity.getIcon_id();
        Glide.with(context).load(image).error(R.drawable.landscape).into(holder.imgIcon);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putSerializable(Constants.LIST_CATEGORY, listItem);
                data.putInt(Constants.POSITION, position);
                data.putString(Constants.CATEGORY_ID, listItem.get(position).getId_category());
                FragmentTabsNews fragmentTabsNews = new FragmentTabsNews();
                fragmentTabsNews.setArguments(data);
                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragmentTabsNews).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
