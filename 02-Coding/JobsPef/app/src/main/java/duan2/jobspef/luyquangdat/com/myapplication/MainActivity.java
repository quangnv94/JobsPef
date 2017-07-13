package duan2.jobspef.luyquangdat.com.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.adapter.HomeAdapter;
import duan2.jobspef.luyquangdat.com.myapplication.model.CategoryItem;
import duan2.jobspef.luyquangdat.com.myapplication.uti.PasreJson;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    public static Drawer drawer;
    ImageView imgmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        initSlideMenuItem();
        DowloadAsynTask asynTask = new DowloadAsynTask(this);
        asynTask.execute();
    }

    public void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecylver);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        imgmenu = (ImageView) findViewById(R.id.image_menu);
        imgmenu.setOnClickListener(this);
        setupDrawerLayout();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_menu:
                if (!MainActivity.drawer.isDrawerOpen()) {
                    MainActivity.getDrawer().openDrawer();

                    imgmenu.setVisibility(View.VISIBLE);
                }
        }
    }

    public class DowloadAsynTask extends AsyncTask<Void, Void, Void> {
        private Context context;
        private ArrayList<CategoryItem> list;

        public DowloadAsynTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            list = PasreJson.getListCategoryByJson("http://luynt123z.hol.es/api/category");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            HomeAdapter adapter = new HomeAdapter(MainActivity.this, list);
            mRecyclerView.setAdapter(adapter);
        }
    }

    private void setupDrawerLayout() {
        LayoutInflater inflater = getLayoutInflater();
        drawer = new DrawerBuilder()
                .withDrawerWidthPx(300)
                .withActivity(MainActivity.this)
                .withActionBarDrawerToggle(false)
                .withDrawerGravity(Gravity.RIGHT)
                .withCustomView(inflater.inflate(R.layout.slide_menu, null))
                .withTranslucentNavigationBar(true)
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .build();
    }

    public static Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        }
        super.onBackPressed();
    }

    public void initSlideMenuItem() {
        RecyclerView recyclerView_menu = (RecyclerView) drawer.getDrawerLayout().findViewById(R.id.recyclerview_menu);
    }

}
