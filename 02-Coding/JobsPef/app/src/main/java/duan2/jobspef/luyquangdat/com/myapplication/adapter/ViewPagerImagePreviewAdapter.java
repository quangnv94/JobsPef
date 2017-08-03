package duan2.jobspef.luyquangdat.com.myapplication.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;


public class ViewPagerImagePreviewAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> IMAGES;

    public ViewPagerImagePreviewAdapter(Context context, ArrayList<String> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public Object instantiateItem(View collection, final int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_image_pager, null);
        ((ViewPager) collection).addView(view);
        ImageView img = view.findViewById(R.id.img);
        Glide.with(context)
                .load(IMAGES.get(position))
                .into(img);

        return view;
    }

}