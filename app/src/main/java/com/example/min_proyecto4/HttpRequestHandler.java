package com.example.min_proyecto4;

import android.os.AsyncTask;

import com.miniproyecto.models.RequestInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestHandler extends AsyncTask<RequestInfo, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(RequestInfo[] requestInfo) {
        URL url;
        JSONObject responseData = null;
        HttpURLConnection client;

        try {
            JSONObject requestParams = requestInfo[0].requestParams;;
            url = new URL(requestInfo[0].URL);
            client = (HttpURLConnection) url.openConnection();

            // Este if se activa cuando se envia un JSON por parametros y no query params
            if (requestParams != null) {
                client.setRequestMethod(requestInfo[0].method);
                client.setRequestProperty("Content-Type", "application/json");
                client.setDoOutput(true);
                client.setDoInput(true);
                client.setChunkedStreamingMode(0);
                OutputStream out = new BufferedOutputStream(client.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(requestParams.toString());
                writer.flush();
            }

            // Este en un ejemplo de como se obtiene el responde code y que hacer en base al valor
            int code = client.getResponseCode();
            /*if (code != 201) {
                throw new IOException("Invalid response from server: " + code);
            }*/

            JSONArray jsonArray;

            BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;

            // En este while se extraen los valores del objeto retornado
            // Si el while se cae (Null exception) es porque el lambda no esta haciendo return del result
            while ((line = rd.readLine()) != null) {
                jsonArray = new JSONArray(line);
                responseData = jsonArray.getJSONObject(0);
            }

            // Asi es que se extraen los valores de la respuesta del server
            // Se debe hacer desde el metodo que invoca a este
            // De aqui se retorna solo el el responseData
            /*int id = responseData.getInt("idclient");
            String name = responseData.getString("fullName");
            String address = responseData.getString("address");
            String hobby = responseData.getString("hobby");*/

        } catch (Exception e) {
            // Agregamos el codigo de como manejaremos la excepcion
            // Puede ser por URL invalido
            // Excepcion desde el servidor
            // Entre otras
        }

        // El valor del return se puede modificar en la declaracion de la clase
        return responseData;
    }

    //Esta es la implementacion cuando se va a hacer un POST
    /*@Override
    protected JSONObject doInBackground(RequestInfo[] requestInfo) {
        URL url;
        JSONObject jsonObject = null;
        HttpURLConnection client;

        Este codigo se usa cuando se envian los datos via JSON y no por query params
        try {
            url = new URL(requestInfo[0].URL);
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod(requestInfo[0].method);
            client.setRequestProperty("Content-Type", "application/json");
            client.setDoOutput(true);
            client.setDoInput(true);
            client.setChunkedStreamingMode(0);
            OutputStream out = new BufferedOutputStream(client.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(dataInfo.toString());
            writer.flush();

            Este en un ejemplo de como se obtiene el responde code y que hacer en base al valor
            int code = client.getResponseCode();
            if (code != 201) {
                throw new IOException("Invalid response from server: " + code);
            }

            jsonObject = requestInfo[0].requestParams;
            JSONArray jsonArray;

            BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;

            En este while se extraen los valores del objeto retornado
            Si el while se cae (Null exception) es porque el lambda no esta haciendo return del result
            while ((line = rd.readLine()) != null) {
                jsonArray = new JSONArray(line);
                jsonObject = jsonArray.getJSONObject(0);
            }

            int id = jsonObject.getInt("idclient");
            String name = jsonObject.getString("fullName");
            String address = jsonObject.getString("address");
            String hobby = jsonObject.getString("hobby");

        } catch (Exception e) {
            Agregamos el codigo de como manejaremos la excepcion
        }

        El valor del return se puede modificar en la declaracion de la clase
        return jsonObject;
    }*/

    //Esta es la implementacion cuando se va a hacer un PUT
    /*@Override
    protected JSONObject doInBackground(RequestInfo[] requestInfo) {
        URL url;
        JSONObject jsonObject = null;
        HttpURLConnection client;

        Este codigo se utiliza cuando los valores se envian por querya params y no por JSON
        try {
            url = new URL(requestInfo[0].URL);
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod(requestInfo[0].method);

            Este en un ejemplo de como se obtiene el responde code y que hacer en base al valor
            int code = client.getResponseCode();
            if (code != 201) {
                throw new IOException("Invalid response from server: " + code);
            }

            jsonObject = requestInfo[0].requestParams;
            JSONArray jsonArray;

            BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;

            En este while se extraen los valores del objeto retornado
            Si el while se cae (Null exception) es porque el lambda no esta haciendo return del result
            while ((line = rd.readLine()) != null) {
                jsonArray = new JSONArray(line);
                jsonObject = jsonArray.getJSONObject(0);
            }

            int id = jsonObject.getInt("idclient");
            String name = jsonObject.getString("fullName");
            String address = jsonObject.getString("address");
            String hobby = jsonObject.getString("hobby");

        } catch (Exception e) {
            Agregamos el codigo de como manejaremos la excepcion
        }

        El valor del return se puede modificar en la declaracion de la clase
        return jsonObject;
    }*/

    //Esta es la implementacion cuando se va a hacer un GET
    /*@Override
    protected JSONObject doInBackground(RequestInfo[] requestInfo) {
        URL url;
        HttpURLConnection client;
        JSONObject jsonObject = null;

        Este codigo se utiliza cuando los valores se envian por query params y no por JSON
        try {
            url = new URL(requestInfo[0].URL);
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod(requestInfo[0].method);

            Este en un ejemplo de como se obtiene el responde code y que hacer en base al valor
            int code = client.getResponseCode();
            if (code != 201) {
                throw new IOException("Invalid response from server: " + code);
            }

            jsonObject = new JSONObject();
            JSONArray jsonArray;

            BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;

            En este while se extraen los valores del objeto retornado
            Si el while se cae (Null exception) es porque el lambda no esta haciendo return del result
            while ((line = rd.readLine()) != null) {
                jsonArray = new JSONArray(line);
                jsonObject = jsonArray.getJSONObject(0);
            }

            int id = jsonObject.getInt("idclient");
            String name = jsonObject.getString("fullName");
            String address = jsonObject.getString("address");
            String hobby = jsonObject.getString("hobby");

        } catch (Exception e) {
            Agregamos el codigo de como manejaremos la excepcion
        }

        El valor del return se puede modificar en la declaracion de la clase
        return jsonObject;
    }*/
}
