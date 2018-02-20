package com.example.servicetrial;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start polling service
        System.out.println("Start polling service...");
        PollingUtils.startPollingService(this, 5, PollingService.class, PollingService.ACTION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Stop polling service
        System.out.println("Stop polling service...");
        PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
    }
    Response.Listener<String> responseListener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            System.out.println("ok");
            try {
                JSONObject jsonResponse = new JSONObject(response);
                System.out.println(response);
                boolean success = jsonResponse.getBoolean("success");

                if (success) {
//                    String name = jsonResponse.getString("name");
//                    int age = jsonResponse.getInt("age");
//
//                    Intent intent = new Intent();
////                    intent.setClass(PollingService.this, MainActivity.class);
//					intent.putExtra("username", "wuchou");//username��ȡ
////                  intent.putExtra("money",money);
//                    startActivity(intent);
                    System.out.println("ok");
                } else {
                    System.out.println("��¼ʧ��");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    TestRequest tr= new TestRequest("test1","465",responseListener);
    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
    queue.add;

}
