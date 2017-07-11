package duan2.jobspef.luyquangdat.com.myapplication.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import duan2.jobspef.luyquangdat.com.myapplication.MainActivity;
import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.model.CategoryItem;

/**
 * Created by nguye on 7/11/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements View.OnClickListener{
    private MainActivity mContext;
    private ArrayList<CategoryItem> categoryItems;

    @Override
    public void onClick(View view) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title=(TextView)view.findViewById(R.id.titleD);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }
    public HomeAdapter(MainActivity context, ArrayList<CategoryItem> list) {
        this.mContext=context;
        this.categoryItems=list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CategoryItem album = categoryItems.get(position);
        holder.title.setText(album.getCategory_name());

//        Drawable rs=n(R.drawable.facebook);
//        holder.thumbnail.setImageDrawable(rs);
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }
}
