package com.example.servicetrial;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TestRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://wuchout.cn/shared_bike/check_login.php";
    private Map<String, String> params;
   
    public TestRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL+"?username=\""+ username+"\"&password="+password, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        System.out.println("why");
    }
   
    @Override
    public Map<String, String> getParams() {
        return params;
    }
    
}