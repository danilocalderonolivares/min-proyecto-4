package com.miniproyecto.models;

import android.net.Uri;

import org.json.JSONObject;

public class RequestInfo {
    public String method;
    public JSONObject requestParams;
    public Uri queryParams;
    public String URL;

    public RequestInfo(String method, JSONObject requestParams, String URL, Uri queryParams) {
        this.method = method;
        this.requestParams = requestParams;
        this.URL = URL;
        this.queryParams = queryParams;
    }
}
