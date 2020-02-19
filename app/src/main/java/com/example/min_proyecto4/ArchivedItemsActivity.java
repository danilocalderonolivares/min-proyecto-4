package com.example.min_proyecto4;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.com.miniproyecto.adapters.ArchivedItemsAdapter;
import com.miniproyecto.models.Reminder;

import java.util.ArrayList;

public class ArchivedItemsActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Reminder> archivedReminders, activeReminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders_layout);

        Bundle bundle = this.getIntent().getExtras();
        archivedReminders = bundle.getParcelableArrayList("archivedItems");
        activeReminders = bundle.getParcelableArrayList("activeItems");

        showArchivedItems();
    }

    private void showArchivedItems() {
        listView = findViewById(R.id.listView);

        ArrayAdapter<Reminder> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, archivedReminders);
        listView.setAdapter(adapter);

        ArchivedItemsAdapter itemsAdapter = new ArchivedItemsAdapter(this, R.layout.reminders_layout, archivedReminders, activeReminders);
        listView.setAdapter(itemsAdapter);
    }
}
