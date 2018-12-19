package cn.gdxhlm.universaltools;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.gdxhlm.universaltools.activity.Zgjm_Activity;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button button_zgjm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findID();/*实例化控件ID*/
        controlOnClick();/*响应控件ID点击事件*/

    }

    private void controlOnClick() {
        button_zgjm.setOnClickListener(this);
    }

    private void findID() {
        button_zgjm = findViewById(R.id.btn_zgjm);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_zgjm:
                Intent intent=new Intent(MainActivity.this,Zgjm_Activity.class);
                startActivity(intent);
                break;
        }
    }
}
