package com.yzq.testzxing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {
    Boolean isFirstIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        AppClose.getInstance().addActivity(this);
        SharedPreferences perPreferences = getSharedPreferences("firstuser", MODE_PRIVATE);
        isFirstIn = perPreferences.getBoolean("firstuser", true);
        if (!isFirstIn) {
            //到主界面
            Intent start= new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(start);
        } else {
            SharedPreferences.Editor editor = perPreferences.edit();
            editor.putBoolean("firstuser", false);
            editor.commit();
            Intent start= new Intent(WelcomeActivity.this, showguide.class);
            startActivity(start);
        }
    }
}
