package duan2.jobspef.luyquangdat.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Timer().schedule(new TimerTask() {
            public void run() {

                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        }, 2000);
    }
}
