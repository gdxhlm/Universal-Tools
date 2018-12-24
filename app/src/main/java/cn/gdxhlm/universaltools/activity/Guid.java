package cn.gdxhlm.universaltools.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import cn.gdxhlm.universaltools.R;

public class Guid extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(Guid.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        };
        timer.schedule(timerTask,3000);
    }
}
