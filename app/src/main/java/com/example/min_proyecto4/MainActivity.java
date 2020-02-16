package com.example.min_proyecto4;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.miniproyecto.models.RequestInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestInfo[] data = new RequestInfo[1];
        RequestInfo requestInfo = new RequestInfo("PUT", null, "https://1jj1hfrez4.execute-api.us-east-1.amazonaws.com/Clients", "idClient=5&fullName=John Doe&address=Aguantafilo&hobby=Birritas");

        data[0] = requestInfo;
        new HttpRequestHandler().execute(data);

        Intent remindersActivity = new Intent(MainActivity.this, RemindersActivity.class);
        startActivity(remindersActivity);
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestInfo[] data = new RequestInfo[1];
        JSONObject requestParams = new JSONObject();

        // Este es un URL con query params
        // En API gateway se configura el endpoint para que reciba query params o un JSON
        URL url = new URL("https://1jj1hfrez4.execute-api.us-east-1.amazonaws.com/Clients?idClient=5&fullName=John Doe&address=Aguantafilo&hobby=Birritas");

        // Este try se utiliza cuando la informacion se envia por JSON y no por query params
        // Asi construimos el JSON como se haria en postman
        try {
            requestParams.put("idClient", 5);
            requestParams.put("fullName", "John Doe");
            requestParams.put("address", "California");
            requestParams.put("hobby", "Beers");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Si los parametros se envian por query params, en el parametro requestParams podemos poner null
        // Si se envia por JSON el URL es asi https://1jj1hfrez4.execute-api.us-east-1.amazonaws.com/Clients/
        // Es como la base del URL al controller
        RequestInfo requestInfo = new RequestInfo("GET", requestParams, "https://1jj1hfrez4.execute-api.us-east-1.amazonaws.com/Clients/5");
        data[0] = requestInfo;
        new HttpRequestHandler().execute(data);

        Intent remindersActivity = new Intent(MainActivity.this, RemindersActivity.class);
        startActivity(remindersActivity);
    }*/
}