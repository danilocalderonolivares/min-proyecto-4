package com.example.min_proyecto4;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class test extends AsyncTask<String, Void, Void > {
    private Exception exception;

    @Override
    protected Void doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL("https://1jj1hfrez4.execute-api.us-east-1.amazonaws.com/Clients/1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection client;

        JSONObject clientInfo = new JSONObject();
        try {
            clientInfo.put("idClient", "1");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            client = (HttpURLConnection) url.openConnection();
            /*client.setRequestMethod("GET");
            client.setRequestProperty("Content-Type", "application/json");
            client.setDoOutput(true);
            client.setDoInput(true);
            client.setChunkedStreamingMode(0);
            OutputStream out = new BufferedOutputStream(client.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(clientInfo.toString());
            writer.flush();*/

            int code = client.getResponseCode();
            /*if (code != 201) {
                throw new IOException("Invalid response from server: " + code);
            }*/

            BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                Log.i("data", line);
            }

            System.out.println(line);

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
