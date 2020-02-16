package com.miniproyecto.models;

import org.json.JSONObject;

public class RequestInfo {
    public String method;
    public JSONObject requestParams;
    public String queryParams;
    public String URL;

    public RequestInfo(String method, JSONObject requestParams, String URL, String queryParams) {
        this.method = method;
        this.requestParams = requestParams;
        this.URL = URL;
        this.queryParams = queryParams;
    }
}
