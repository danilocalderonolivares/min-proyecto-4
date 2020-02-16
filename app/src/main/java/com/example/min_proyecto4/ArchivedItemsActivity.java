package com.example.min_proyecto4;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.com.miniproyecto.adapters.ArchivedItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArchivedItemsActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> reminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders_layout);

        showArchivedItems();
    }

    private void showArchivedItems() {
        listView = findViewById(R.id.listView);
        reminders = new ArrayList<>();

        reminders.add("Jose");
        reminders.add("Adrian");
        reminders.add("Xinia");
        reminders.add("Mario");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reminders);
        listView.setAdapter(adapter);

        ArchivedItemsAdapter itemsAdapter = new ArchivedItemsAdapter(this, R.layout.reminders_layout, reminders);
        listView.setAdapter(itemsAdapter);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Es el xml en res/menu
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItem:
                //showAlertDialog();
                return true;
            case R.id.archivedItems:
                //startActivity(new Intent(RemindersActivity.this, ArchivedItemsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /*@Override
    public void onBackPressed() {
        startActivity(new Intent(ArchivedItemsActivity.this, RemindersActivity.class));
    }*/
}
