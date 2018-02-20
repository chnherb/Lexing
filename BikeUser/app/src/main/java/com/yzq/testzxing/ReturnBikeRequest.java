package com.yzq.testzxing;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class ReturnBikeRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://wuchout.cn/shared_bike/check_bike_state.php";
    private Map<String, String> params;

    public ReturnBikeRequest(String bike_id, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL+"?bike_id="+ bike_id , listener, null);
//        params = new HashMap<>();
//        params.put("username", username);
//        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
