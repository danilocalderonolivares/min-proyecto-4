package com.example.min_proyecto4;

import android.net.Uri;
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
        URL url = null;
        JSONObject responseData = null;
        HttpURLConnection connection = null;

        try {
            JSONObject requestParams = requestInfo[0].requestParams;

            if (requestInfo[0].queryParams != null) {
                // Asi se forma el URL con lo query params
                // Lo ideal es enviar en el objeto que viene en el request info
                Uri uri = Uri.parse(requestInfo[0].URL).buildUpon()
                        .appendQueryParameter("idClient", "5")
                        .appendQueryParameter("fullName", "RichardGere")
                        .appendQueryParameter("address", "ConchaLaLora")
                        .appendQueryParameter("hobby", "Cuatroplumas")
                        .build();
                url = new URL(uri.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
            }

            // Este if se activa cuando se envia un JSON por parametros y no query params
            if (requestParams != null) {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(requestInfo[0].method);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setChunkedStreamingMode(0);
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(requestParams.toString());
                writer.flush();
            }

            // Este en un ejemplo de como se obtiene el responde code y que hacer en base al valor
            int code = connection.getResponseCode();
            /*if (code != 201) {
                throw new IOException("Invalid response from server: " + code);
            }*/

            JSONArray jsonArray;
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
            System.out.println(e);
            // Agregamos el codigo de como manejaremos la excepcion
            // Puede ser por URL invalido
            // Excepcion desde el servidor
            // Entre otras
        }

        // El valor del return se puede modificar en la declaracion de la clase
        return responseData;
    }
}
