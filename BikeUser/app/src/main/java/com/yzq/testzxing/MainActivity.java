package com.yzq.testzxing;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import com.yzq.testzxing.zxing.android.CaptureActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton scanBtn;
    private TextView resultTv;
    private WebView webView;
    private WebView resultview;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private Double intmoney;
    private static final String TAG_EXIT = "exit";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        AppClose.getInstance().addActivity(this);
        initView();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        scanBtn = (ImageButton) findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);
//        resultTv = (TextView) findViewById(R.id.resultTv);
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("http://wuchout.cn/shared_bike/location_test1.php");
        //链接不以浏览器的方式打开
        webView.setWebViewClient(new WebViewClient());
        //得到webview设置
        WebSettings webSettings = webView.getSettings();
        //允许使用javascript
        webSettings.setJavaScriptEnabled(true);
        resultview = (WebView) findViewById(R.id.resuletview);

        //侧边栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View rootView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView nametv = (TextView) rootView.findViewById(R.id.nametv);
        final TextView moneytv = (TextView) rootView.findViewById(R.id.moneytv);

        final SharedPreferences sharedPreferences = getSharedPreferences("test",
                Activity.MODE_PRIVATE);

        final String name = sharedPreferences.getString("username", "");
        final String money = sharedPreferences.getString("money", "");
        intmoney = Double.parseDouble(money);
        nametv.setText(name);
        moneytv.setText(money);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_pay:
//                        View paylayout = View.inflate(MainActivity.this, R.layout.dialog_pay,
//                                null);
//                        final EditText pay_edit = (EditText) paylayout.findViewById(R.id.pay_edit);
                       final EditText pay_edit = new EditText(MainActivity.this);
                        new AlertDialog.Builder(MainActivity.this).setTitle("请输入想要充值的金额").setIcon(android.R.drawable.ic_dialog_info)
                                .setView(pay_edit).setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s = pay_edit.getText().toString();
                                if(s != null && s.matches("^[0.0-9.0]+$")){
                                    Toast.makeText(MainActivity.this, "充值" + s + "元成功!", Toast.LENGTH_LONG).show();
                                    resultview.loadUrl("http://wuchout.cn/shared_bike/change_money.php?username=\"" +
                                            name + "\"&money="+ String.valueOf(intmoney + Double.parseDouble(s)));
                                    moneytv.setText(String.valueOf(intmoney + Double.parseDouble(s)));
                                    intmoney += Double.parseDouble(s);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("money", intmoney.toString());
                                    editor.apply();
                                }else{
                                    Toast.makeText(MainActivity.this, "请输入正确金额", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                                .setNegativeButton("取消", null).show();
                        break;
                    case R.id.nav_manage:

                        Intent repairint = new Intent(MainActivity.this, RepairRequest.class);
                        startActivity(repairint);
                        break;
                    case R.id.nav_change:

                        Intent backint = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(backint);
                    case R.id.nav_exit:
                        AppClose.getInstance().exit();
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
//        View headerView = navigationView.getHeaderView(0);


        //drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scanBtn:
////                测试用代码
//                Intent jump = new Intent(MainActivity.this, RideActivity.class);
//                startActivity(jump);
//                Intent startride = new Intent(MainActivity.this, RideActivity.class);
//                startActivity(startride);
                if (intmoney <= 0.0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "余额不足，请先充值", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(MainActivity.this,
                            CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                    break;
                }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String bikenumber = data.getStringExtra("codedContent");
//              resultTv.setText("解码结果： \n" + content);
                resultview.loadUrl("http://wuchout.cn/shared_bike/change_bike_state.php?bike_command=1"+"&bike_id=" + bikenumber);

                Intent startride = new Intent(MainActivity.this, RideActivity.class);
                startride.putExtra("BikeNumber", bikenumber);
                startride.putExtra("currentMoney", intmoney.toString());
                startActivity(startride);

            }
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode)
            return false;
        return super.onKeyDown(keyCode, event);
    }

}
