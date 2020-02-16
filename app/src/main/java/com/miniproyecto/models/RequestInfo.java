package com.miniproyecto.models;

import org.json.JSONObject;

public class RequestInfo {
    public String method;
    public JSONObject requestParams;
    public String URL;

    public RequestInfo(String method, JSONObject requestParams, String URL) {
        this.method = method;
        this.requestParams = requestParams;
        this.URL = URL;
    }
}
