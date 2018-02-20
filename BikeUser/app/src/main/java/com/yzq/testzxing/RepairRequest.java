package com.yzq.testzxing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yzq.testzxing.zxing.android.CaptureActivity;

public class RepairRequest extends AppCompatActivity {
    private EditText editText;
    private Button submitBtn;
    private Button ScannumberBtn;
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppClose.getInstance().addActivity(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "客服电话:18651048321, 点击右侧即可拨打", Snackbar.LENGTH_LONG)
                        .setAction("拨打", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18651048321"));
                                startActivity(intent);
                            }
                        }).show();
            }
        });
        editText = (EditText) findViewById(R.id.editText);
        submitBtn = (Button) findViewById(R.id.submit);
        ScannumberBtn = (Button) findViewById(R.id.Scan);
        //提交报修信息
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editText.getText())){
                    wb = (WebView) findViewById(R.id.webview);
                    wb.loadUrl("http://wuchout.cn/shared_bike/change_report_times.php?bike_id=" + editText.getText());
                    Toast.makeText(RepairRequest.this, "报修成功!我们将尽快处理!" , Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(RepairRequest.this, "请输入车辆编号或选择拨打客服电话" , Toast.LENGTH_SHORT).show();
                }
            }
        });
        ScannumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RepairRequest.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                editText.setText(data.getStringExtra("codedContent").toCharArray(), 0 , data.getStringExtra("codedContent").length());

            }
        }
    }
}
