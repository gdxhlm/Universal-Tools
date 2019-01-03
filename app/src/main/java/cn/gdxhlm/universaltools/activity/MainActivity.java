package cn.gdxhlm.universaltools.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.gdxhlm.universaltools.R;
import cn.gdxhlm.universaltools.utils.LogUtil;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button button_zgjm;
    private Button button_robot;
    private int zdyversion=20181220;
    boolean needupdate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bbkz();//检测最新版本与本地版本做比较，判断是否跳转升级
        findID();/*实例化控件ID*/
        controlOnClick();/*响应控件ID点击事件*/
        JPushInterface.requestPermission(this);

    }

    private void bbkz() {
        final StringBuilder stringBuilder=new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL("http://www.gdxhlm.cn/BBKZ/UniversalTools.json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    String line;

                    while ((line=bufferedReader.readLine())!=null){
                        LogUtil.d("ZDY","line"+line);
                        stringBuilder.append(line);

                    }

                    LogUtil.d("ZDY","stringBuilder"+String.valueOf(stringBuilder));

                    JSONObject jsonObject=new JSONObject(String.valueOf(stringBuilder));
                    LogUtil.d("ZDY","jsonObject: "+jsonObject);

                    String version=jsonObject.getString("version");
                    LogUtil.d("ZDY","version: "+version);

                    String address=jsonObject.getString("address");
                    LogUtil.d("ZDY","address: "+address);


                   LogUtil.d("ZDY","zdyversion: "+zdyversion);
                   LogUtil.d("ZDY","Integer.parseInt(version): "+Integer.parseInt(version));
                   if(version!=null&&version.length()>0){
                       if(zdyversion<Integer.parseInt(version)){
                           LogUtil.d("ZDY","Integer.parseInt(version)");
                           LogUtil.d("ZDY","更新提示");
                           final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                           builder.setTitle("更新提醒");
                           builder.setMessage("是否需要更新");
                           builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   Intent intent=new Intent(Intent.ACTION_VIEW);
                                   intent.setData(Uri.parse("https://www.lanzous.com/b530068"));
                                   startActivity(intent);
                                   finish();

                       }
                   });
                           builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   finish();
                               }
                           });
                           Looper.prepare();
                           builder.show();
                           Looper.loop();

                       }else {
                           Looper.prepare();
                        //   Toast.makeText(MainActivity.this, "版本失败", Toast.LENGTH_SHORT).show();
                           Looper.loop();
                       }
                   }



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }).start();




    }


    private void controlOnClick() {
        button_zgjm.setOnClickListener(this);
        button_robot.setOnClickListener(this);
    }

    private void findID() {
        button_zgjm = findViewById(R.id.btn_zgjm);
        button_robot=findViewById(R.id.btn_robot);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_zgjm:
                Intent intent=new Intent(MainActivity.this,Zgjm_Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_robot:
                Intent intent1=new Intent(MainActivity.this,Robot_Activity.class);
                startActivity(intent1);
                break;
        }
    }
}
