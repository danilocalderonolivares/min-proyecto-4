package com.example.min_proyecto4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.com.miniproyecto.adapters.ListAdapter;
import com.miniproyecto.models.Reminder;

import java.util.ArrayList;
import java.util.Calendar;

public class RemindersActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private ArrayList<Reminder> reminders, archivedReminders;
    private ListAdapter listAdapter;
    private Button datePicker, timePicker;
    private int year, month, day, hour, minute;
    private EditText reminderInput, reminderDate, reminderTime, textDate, textTime;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders_layout);

        listView = findViewById(R.id.listView);
        reminders = new ArrayList<>();

        ArrayAdapter<Reminder> adapter = new ArrayAdapter<Reminder>(this, android.R.layout.simple_list_item_1, reminders);
        listView.setAdapter(adapter);

        archivedReminders = new ArrayList<>();
        listAdapter = new ListAdapter(this, R.layout.reminders_layout, reminders, archivedReminders);
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
                Intent intent = new Intent(RemindersActivity.this, ArchivedItemsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("archivedItems", archivedReminders);
                bundle.putParcelableArrayList("activeItems", reminders);
                intent.putExtras(bundle);
                startActivity(intent);
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

        reminderInput = viewInflated.findViewById(R.id.reminderText);
        reminderDate = viewInflated.findViewById(R.id.date);
        reminderTime = viewInflated.findViewById(R.id.time);

        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int newItem) {
                // Trim elimina los espacios al final si es que los hay
                String reminderText = reminderInput.getText().toString().trim();

                if (reminderText.length() > 0) {
                    saveReminder(new Reminder(reminderDate.getText().toString(), reminderTime.getText().toString(), reminderText));
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
                if (!TextUtils.isEmpty(s)) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
        });

        datePicker = alertDialog.findViewById(R.id.datePicker);
        timePicker = alertDialog.findViewById(R.id.timePicker);
        textDate = alertDialog.findViewById(R.id.date);
        textTime = alertDialog.findViewById(R.id.time);

        datePicker.setOnClickListener(this);
        timePicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == datePicker) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            textDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        }
                    }, year, month, day);

            datePickerDialog.show();
        }

        if (view == timePicker) {
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            textTime.setText(hourOfDay + ":" + minute);
                        }
                    }, hour, minute, false);

            timePickerDialog.show();
        }
    }

    private void saveReminder(Reminder reminderInfo) {
        this.reminders.add(reminderInfo);
        // Esto hace que cada vez que se agrega un reminder la vista se actualice
        this.listAdapter.notifyDataSetChanged();
    }
}
