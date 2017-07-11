package duan2.jobspef.luyquangdat.com.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import duan2.jobspef.luyquangdat.com.myapplication.adapter.HomeAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.model.CategoryItem;
import duan2.jobspef.luyquangdat.com.myapplication.uti.PasreJson;

public class MainActivity extends AppCompatActivity {
private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        DowloadAsynTask asynTask=new DowloadAsynTask(this);
        asynTask.execute();
    }

    public void init(){
        mRecyclerView=(RecyclerView)findViewById(R.id.mRecylver);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public class DowloadAsynTask extends AsyncTask<Void,Void,Void>{
        private Context context;
        private   ArrayList<CategoryItem> list;
        public DowloadAsynTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            list=PasreJson.getListCategoryByJson("http://luynt123z.hol.es/api/category");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            HomeAdapter adapter=new HomeAdapter(MainActivity.this,list);
            mRecyclerView.setAdapter(adapter);
        }
    }
}
