package com.example.min_proyecto4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.com.miniproyecto.adapters.ArchivedItemsAdapter;
import com.com.miniproyecto.adapters.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RemindersActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> reminders;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders_layout);

        listView = findViewById(R.id.listView);
        reminders = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reminders);
        listView.setAdapter(adapter);

        listAdapter = new ListAdapter(this, R.layout.reminders_layout, reminders);
        listView.setAdapter(listAdapter);
    }

    @Override
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
                showAlertDialog();
                return true;
            case R.id.archivedItems:
                showArchivedItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nuevo recordatorio");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.new_text_reminder, null);
        builder.setView(viewInflated);

        final EditText reminderInput = viewInflated.findViewById(R.id.reminderText);

        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int newItem) {
                // Trim elimina los espacios al final si es que los hay
                String reminderText = reminderInput.getText().toString().trim();

                if (reminderText.length() > 0) {
                    saveReminder(reminderText);
                }
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        reminderInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });
    }

    private void saveReminder(String reminderInfo) {
        this.reminders.add(reminderInfo);
        // Esto hace que cada vez que se agrega un reminder la vista se actualice
        this.listAdapter.notifyDataSetChanged();
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
}
