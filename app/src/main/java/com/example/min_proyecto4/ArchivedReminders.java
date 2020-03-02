package com.example.min_proyecto4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.com.miniproyecto.adapters.ArchivedItemsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miniproyecto.models.Reminder;

import java.util.ArrayList;

public class ArchivedReminders extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Reminder> archivedReminders, activeReminders;
    ArchivedItemsAdapter itemsAdapter;
    FloatingActionButton recordVoiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders_layout);
        recordVoiceButton = findViewById(R.id.recorderVoiceButton);

        Bundle bundle = this.getIntent().getExtras();
        archivedReminders = bundle.getParcelableArrayList("archivedItems");
        activeReminders = bundle.getParcelableArrayList("activeItems");

        if (archivedReminders == null) {
            archivedReminders = new ArrayList<>();
        }

        if (activeReminders == null) {
            activeReminders = new ArrayList<>();
        }

        showArchivedItems();
    }

    private void showArchivedItems() {
        listView = findViewById(R.id.listView);

        ArrayAdapter<Reminder> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, archivedReminders);
        listView.setAdapter(adapter);

        itemsAdapter = new ArchivedItemsAdapter(this, R.layout.reminders_layout, archivedReminders, activeReminders);
        listView.setAdapter(itemsAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(ArchivedReminders.this, RemindersActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("archivedItems", archivedReminders);
        bundle.putParcelableArrayList("activeItems", activeReminders);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        recordVoiceButton.hide();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            archivedReminders = bundle.getParcelableArrayList("archivedReminders");
            if (archivedReminders == null) archivedReminders = new ArrayList<>();
            itemsAdapter = new ArchivedItemsAdapter(this, R.layout.reminders_layout, archivedReminders, activeReminders);
            listView.setAdapter(itemsAdapter);
        }
    }
}
