package com.example.runningtrial.pkgs;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyWeather {
}

class AuthenJsonObjectRequest extends JsonObjectRequest {
    private boolean oldSpec = true;
    private String authen = null;
    private String authenMethod = null;

    public String getAuthen() { return authen; }
    public void setAuthen(String authen) { this.authen = authen; }

    public String getAuthenMethod() { return authenMethod; }
    public void setAuthenMethod(String authenMethod) { this.authenMethod = authenMethod; }

    AuthenJsonObjectRequest(int method, String url, JSONObject jsonRequest,
                            Response.Listener<JSONObject> listener,
                            @Nullable Response.ErrorListener errorListener){
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() {
        // return super.getHeaders();
        Map<String,String> headers = new HashMap<>();
        if(oldSpec){
            headers.put("authen", authen);
        } else {
            if (authenMethod == null) headers.put("Authorization", authen );
            else headers.put("Authorization", authenMethod + " " + authen );
        }
        return headers;
    }
}
