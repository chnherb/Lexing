package com.yzq.testzxing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RideActivity extends AppCompatActivity {

    private WebView wb;
    private Button returnButton;
    private Chronometer chronometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        //PollingUtils.startPollingService(this, 5, PollingService.class, PollingService.ACTION);
        AppClose.getInstance().addActivity(this);
        initView();
    }

    private void initView() {

        wb = (WebView) findViewById(R.id.webview);
        wb.loadUrl("http://wuchout.cn/shared_bike/location_test1.php");
        //加载本地中的html
        //myWebView.loadUrl("file:///android_asset/www/test2.html");
        //加上下面这段代码可以使网页中的链接不以浏览器的方式打开
        wb.setWebViewClient(new WebViewClient());
        //得到webview设置
        WebSettings webSettings = wb.getSettings();
        //允许使用javascript
        webSettings.setJavaScriptEnabled(true);
        chronometer = (Chronometer) findViewById(R.id.timer);
        chronometer.setFormat("已骑行时间:%s");
        chronometer.start();
        Intent numberInt = getIntent();
        final String number = numberInt.getStringExtra("BikeNumber");
//        final String money = numberInt.getStringExtra("currentMoney");
        SharedPreferences sharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bikeid", number);
        editor.apply();
        //String name=sharedPreferences.getString("username","");
        TextView numberview = (TextView) findViewById(R.id.getnumber);
        numberview.setText("正在使用单车 编号："+ number);
        returnButton = (Button) findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int bike_state = jsonResponse.getInt("bike_state");

                            if (bike_state == 0) {
                                wb.loadUrl("http://wuchout.cn/shared_bike/change_bike_state.php?bike_command=0&bike_id=" + number);
                                Intent gotoresult = new Intent();
                                gotoresult.putExtra("BikeNumber", number);
                                gotoresult.putExtra("time", chronometer.getText().toString());
                                gotoresult.setClass(RideActivity.this, RideResult.class);
                                startActivity(gotoresult);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RideActivity.this);
                                builder.setMessage("还车失败,请先将车上锁!")
                                        .setNegativeButton("重试", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ReturnBikeRequest returnRequest = new ReturnBikeRequest(number, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RideActivity.this);
                queue.add(returnRequest);
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(KeyEvent.KEYCODE_BACK==keyCode)
            return false ;
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Stop polling service
        //PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
    }

}
