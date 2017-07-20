package duan2.jobspef.luyquangdat.com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.R;
import duan2.jobspef.luyquangdat.com.myapplication.entity.TestResponse;


public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<TestResponse> listItem;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName;
        ImageView imgIcon;
        View view;

        public MyViewHolder(View view) {
            super(view);
            tvCategoryName = view.findViewById(R.id.tvCategoryName);
            imgIcon = view.findViewById(R.id.imgIcon);
            this.view = view;
        }
    }

    public TestAdapter(Context _context, ArrayList<TestResponse> _listItem) {
        this.context = _context;
        this.listItem = _listItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new TestAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final TestResponse entity = listItem.get(position);
        holder.tvCategoryName.setText(entity.getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(listItem.get(position).getPdfLink())));
//                Bundle data = new Bundle();
//                data.putSerializable(Constants.LIST_DOCUMENT, listItem);
//                data.putInt(Constants.POSITION, position);
//                FragmentTabsNews fragmentTabsNews = new FragmentTabsNews();
//                fragmentTabsNews.setArguments(data);
//                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragmentTabsNews).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

}
