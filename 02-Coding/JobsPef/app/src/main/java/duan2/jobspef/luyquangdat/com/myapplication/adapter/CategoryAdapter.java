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


import java.util.ArrayList;

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
        ImageView imgIcon;
        View view;
        public MyViewHolder(View view) {
            super(view);
            tvCategoryName = (TextView) view.findViewById(R.id.tvCategoryName);
            imgIcon =(ImageView) view.findViewById(R.id.imgIcon);
            this.view =view;
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
//        holder.imgIcon.post(new Runnable() {
//            @Override
//            public void run() {
//               int width = holder.imgIcon.getWidth();
//               int height = holder.imgIcon.getWidth()*3/4;
//                holder.imgIcon.getLayoutParams().height =width*3/4;
//                Picasso.with(context).load(Constants.HOST+"/lbmedia/"+entity.getIcon_id()+"?height="+height+"&width="+width+"&style=scale_to_fit").into(holder.imgIcon);
//                Log.e("good man",Constants.HOST+"/lbmedia/"+entity.getIcon_id()+"?height="+height+"&width="+width+"&style=scale_to_fit");
//            }
//        });
        holder.tvCategoryName.setText(entity.getCategory_name());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putSerializable(Constants.LIST_CATEGORY,listItem);
                data.putInt(Constants.POSITION,position);
                FragmentTabsNews fragmentTabsNews = new FragmentTabsNews();
                fragmentTabsNews.setArguments(data);
                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragmentTabsNews).addToBackStack(null).commit();
//                Intent intent = new Intent(context, NewsTabsActivity.class);
//                intent.putExtra(Constants.DATA,data);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
