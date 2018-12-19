package cn.gdxhlm.universaltools.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import cn.gdxhlm.universaltools.MainActivity;
import cn.gdxhlm.universaltools.R;
import cn.gdxhlm.universaltools.db.ZgjmSetGet;

public class Zgjm_Activity extends Activity implements View.OnClickListener {
    private EditText et_zgjm_gjz;
    private Button btn_zgjm_jm;
    private TextView tv_zgjm_jmjg;
    private static final String TAG = "ZDY";
    private List<ZgjmSetGet> zgjmSetGetList;
    private Spinner  mspinner;
    private TextView tv_title;
    private TextView tv_more;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zgjm_);
        zgjmSetGetList=new ArrayList<ZgjmSetGet>();
        findID();/*实例化控件ID*/
        control();/*响应控件ID点击事件*/



    }
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            return false;
        }
    });

    private void control() {
        btn_zgjm_jm.setOnClickListener(this);
        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv_title.setText("      "+zgjmSetGetList.get(i).getDesc());
                tv_more.setText(zgjmSetGetList.get(i).getList().replace("[","").replace("]","").replaceAll(",","\n\n").replaceAll("\"","        "));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void findID() {
        et_zgjm_gjz=findViewById(R.id.et_zgjm_gjz);
        btn_zgjm_jm=findViewById(R.id.btn_zgjm_jm);
        tv_zgjm_jmjg=findViewById(R.id.tv_zgjm_jmjg);
        mspinner=findViewById(R.id.spnr_zgjm_spinner);
        tv_title=findViewById(R.id.tv_title);
        tv_more=findViewById(R.id.tv_more);

    }


    public void request(final String httpUrl, final String httpArg) {




        new Thread(new Runnable() {
            String desc;

            @Override
            public void run() {


                BufferedReader reader = null;

                String result = null;
                String zhttpUrl;

                StringBuffer sbf = new StringBuffer();

                zhttpUrl = httpUrl + "?" + httpArg;

                try {

                     //URL url = new URL("http://www.gdxhlm.cn");
                   URL url = new URL(zhttpUrl);
                    Log.d(TAG, "API"+zhttpUrl);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    Log.d(TAG, "设置请求");
                  //  connection.setConnectTimeout(8000);
                  //  Log.d(TAG, "设置连接超时");
                  //  connection.setReadTimeout(8000);
                 //   Log.d(TAG, "设置读取请求");

                     connection.setRequestProperty("charset", "utf-8");

                     connection.setRequestProperty("Accept-Encoding", "gzip");

                    connection.connect();

                         InputStream is = new GZIPInputStream(connection.getInputStream());
                    //InputStream inputStream=connection.getInputStream();

                         reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    //reader=new BufferedReader(new InputStreamReader(inputStream));

                    String strRead = null;

                    while ((strRead = reader.readLine()) != null) {
                        Log.d(TAG, "strRead "+strRead);

                        sbf.append(strRead);

                        sbf.append("\r\n");

                    }
                    Log.d(TAG, "sbf "+sbf);

                    reader.close();

                    result = sbf.toString();


                    JSONObject jsonObject=new JSONObject(result);
                    Log.d(TAG, "jsonArray "+"实例化");


                    JSONArray jsonArray=jsonObject.optJSONArray("data");
                    Log.d(TAG, "jsonArray "+jsonArray);
                    zgjmSetGetList=new ArrayList<ZgjmSetGet>();

                    if(jsonArray!=null){
                        for(int i=0;i<jsonArray.length();i++){

                            ZgjmSetGet zgjmSetGet=new ZgjmSetGet();

                            JSONObject datajsonObject=jsonArray.optJSONObject(i);
                            Log.d(TAG, "datajsonObject "+"实例化");

                            String mtitle=datajsonObject.getString("title");
                            Log.d(TAG, "title: "+mtitle);
                            zgjmSetGet.setTitle(mtitle);


                            String mdesc =datajsonObject.getString("desc");
                            Log.d(TAG, "desc: "+mdesc);
                            zgjmSetGet.setDesc(mdesc);

                            String mlist =datajsonObject.getString("list");
                            Log.d(TAG, "list: "+mlist);
                            zgjmSetGet.setList(mlist);

                            zgjmSetGetList.add(zgjmSetGet);

                        }

                    }else if(jsonObject.optJSONObject("data")!=null) {
                        ZgjmSetGet zgjmSetGet=new ZgjmSetGet();

                        JSONObject jsonObject_th=jsonObject.optJSONObject("data");
                        Log.d(TAG, "jsonObject_th: "+jsonObject_th);

                        String mtitle=jsonObject_th.getString("title");
                        Log.d(TAG, "title: "+mtitle);
                        zgjmSetGet.setTitle(mtitle);


                        String mdesc=jsonObject_th.getString("desc");
                        Log.d(TAG, "desc: "+mdesc);
                        zgjmSetGet.setDesc(mdesc);

                        String mlist=jsonObject_th.getString("list");
                        Log.d(TAG, "list: "+mlist);
                        zgjmSetGet.setList(mlist);

                        zgjmSetGetList.add(zgjmSetGet);

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
                if(zgjmSetGetList.size()<=0){
                    Looper.prepare();
                    Toast.makeText(Zgjm_Activity.this, "解梦失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int zgjmSetGetListSize=zgjmSetGetList.size();//获取数组尺寸
                        String[] szSpner=new String[zgjmSetGetListSize];

                        for(int i=0;i<zgjmSetGetList.size();i++){
                            szSpner[i]=zgjmSetGetList.get(i).getTitle();
                        }
//                        Log.d(TAG, "szSpner: "+szSpner[0]);
//                        Log.d(TAG, "szSpner: "+szSpner[1]);
 //                       Log.d(TAG, "szSpner: "+szSpner[2]);

                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Zgjm_Activity.this,android.R.layout.simple_list_item_1,szSpner);
                    adapter.notifyDataSetChanged();
                      //  mspinner.setAdapter();
                        mspinner.setAdapter(adapter);
                        //String desc=zgjmSetGetList.get(mspinner.getSelectedItemPosition()).getDesc();
                      //  Log.d(TAG, "desc: "+desc);
                       // tv_title.setText("555");
                      //  Log.d(TAG, "t2: "+zgjmSetGetList.get(mspinner.getSelectedItemPosition()).getDesc());
                        //tv_more.setText(zgjmSetGetList.get(mspinner.getSelectedItemPosition()).getList());

                    }
                });


            }

        }).start();


    }





    private void dream() {
        if(et_zgjm_gjz.getText().toString()!=null&&et_zgjm_gjz.getText().toString().length()>0){
            String keyword= null;
            try {
                keyword = URLEncoder.encode(et_zgjm_gjz.getText().toString(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String appid="e63c9dffda76b68cff44727df439f4f3";

            String httpUrl = "https://api.shenjian.io/dream/query/";

            String httpArg = "appid="+appid+"&keyword="+keyword;
            request(httpUrl, httpArg);
           // String jsonResult = request(httpUrl, httpArg);
            Log.d(TAG, httpUrl+httpArg);
          //  Log.d(jsonResult, jsonResult);
          /*  if(jsonResult!=null){
                System.out.println(jsonResult);
            }else {
                System.out.println("未解析成功");
                Log.d(TAG, "未解析成功 ");
            }*/

        }else
            {
            Toast.makeText(this, "请输入关键字", Toast.LENGTH_SHORT).show();
        }






    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_zgjm_jm:
                InputMethodManager imm =(InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_zgjm_gjz.getWindowToken(),0);
                dream();
                break;
        }

    }
}
