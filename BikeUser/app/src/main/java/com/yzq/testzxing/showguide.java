package com.yzq.testzxing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class showguide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showguide);
        AppClose.getInstance().addActivity(this);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageView iv = (ImageView) findViewById(R.id.showguide);
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent start= new Intent(showguide.this, LoginActivity.class);
                startActivity(start);
                return false;
            }
        });
    }
}
