package com.example.min_proyecto4;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] urls = new String[0];
        new test().execute(urls);

        Intent remindersActivity = new Intent(MainActivity.this, RemindersActivity.class);
        startActivity(remindersActivity);
    }
}
