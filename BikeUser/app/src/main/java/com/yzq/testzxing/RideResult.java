package com.yzq.testzxing;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class RideResult extends AppCompatActivity {
    private TextView textView;
    private TextView numberView;
    private Button backBtn;
    private WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_result);
        AppClose.getInstance().addActivity(this);
        textView = (TextView) findViewById(R.id.tv);
        numberView = (TextView) findViewById(R.id.shownumber);
        Intent timeintent = getIntent();
        final String cost = getChronometerMins(timeintent.getStringExtra("time"));
        textView.setText("本次使用消费金额:" + cost + "元");
        numberView.setText("使用单车编号："+ timeintent.getStringExtra("BikeNumber")+ "结束");
        //修改本地的金额同时上传到服务器
        SharedPreferences sharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        String currentMoney = sharedPreferences.getString("money","");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //用putString的方法保存数据
        final String currentmoney = String.valueOf(Double.parseDouble(currentMoney) - Double.parseDouble(cost));
        editor.putString("money",currentmoney);
        editor.apply();


        
        backBtn = (Button) findViewById(R.id.button2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= getSharedPreferences("test",
                        Activity.MODE_PRIVATE);
                wb = (WebView) findViewById(R.id.sendmoney);
                wb.loadUrl("http://wuchout.cn/shared_bike/change_money.php?username=\""+ sharedPreferences.getString("username","") +
                        "\"&money=" + currentmoney);
                Intent backtoMain = new Intent(RideResult.this, MainActivity.class);
                startActivity(backtoMain);
            }
        });

    }

    public  static String getChronometerMins(String time) {
            String[] a = time.split(":");
            String strtime = a[1];
            int i = Integer.valueOf(strtime);
            double pay = (i+1) * 0.5;
            strtime = String.valueOf(pay);
            return strtime;

    }
@Override
public boolean onKeyDown(int keyCode, KeyEvent event){
    if(KeyEvent.KEYCODE_BACK==keyCode)
        return false ;
    return super.onKeyDown(keyCode, event);
}
public String getCurrentMoney(String cost){
    SharedPreferences sharedPreferences= getSharedPreferences("test",
            Activity.MODE_PRIVATE);
    String currentMoney = sharedPreferences.getString("money","");
    Double temp = Double.parseDouble(currentMoney);
    temp = temp - Double.parseDouble(cost);
    return temp.toString();
}
}
