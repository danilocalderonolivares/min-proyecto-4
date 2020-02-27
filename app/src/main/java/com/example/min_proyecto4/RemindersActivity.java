package com.example.min_proyecto4;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.com.miniproyecto.adapters.ListAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.miniproyecto.models.Reminder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class RemindersActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    private ListView listView;
    private ArrayList<Reminder> activeReminders, archivedReminders;
    private ListAdapter listAdapter;
    private Button datePicker, timePicker;
    private int yearSelected, monthSelected, minuteSelected, hourSelected, daySelected;
    private EditText reminderInput, reminderDate, reminderTime, textDate, textTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders_layout);

        listView = findViewById(R.id.listView);
        activeReminders = new ArrayList<>();
        archivedReminders = new ArrayList<>();

        ArrayAdapter<Reminder> adapter = new ArrayAdapter<Reminder>(this, android.R.layout.simple_list_item_1, activeReminders);
        listView.setAdapter(adapter);

        listAdapter = new ListAdapter(this, R.layout.reminders_layout, activeReminders, archivedReminders);
        listView.setAdapter(listAdapter);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // new NotificationHandler(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            activeReminders = bundle.getParcelableArrayList("activeItems");
            archivedReminders = bundle.getParcelableArrayList("archivedItems");
            listAdapter = new ListAdapter(this, R.layout.reminders_layout, activeReminders, archivedReminders);
            listView.setAdapter(listAdapter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent(RemindersActivity.this, ArchivedItemsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("activeItems", activeReminders);
        bundle.putParcelableArrayList("archivedReminders", archivedReminders);
        intent.putExtras(bundle);
        startActivity(intent);
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
                bundle.putParcelableArrayList("activeItems", activeReminders);
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

                if (reminderText.length() > 0 && validateFields()) {
                    try {
                        saveReminder(new Reminder(yearSelected, monthSelected, daySelected, minuteSelected, hourSelected, reminderText, activeReminders.size() + 1,
                                reminderDate.getText().toString(), reminderTime.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Campos inválidos", Toast.LENGTH_SHORT).show();
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
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            yearSelected = year;
                            monthSelected = month;
                            daySelected = dayOfMonth;
                            textDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        }
                    }, year, month, day);

            datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Aceptar", datePickerDialog);
            datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancelar", datePickerDialog);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        }

        if (view == timePicker) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            hourSelected = hourOfDay;
                            minuteSelected = minute;
                            String hour = hourOfDay <= 9 ? "0" + hourOfDay : String.valueOf(hourOfDay);
                            String minutes = minute <= 9 ? "0" + minute : String.valueOf(minute);
                            textTime.setText(hour + ":" + minutes);
                        }
                    }, hour, minute, false);

            timePickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE, "Aceptar", timePickerDialog);
            timePickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancelar", timePickerDialog);
            timePickerDialog.show();
        }
    }

    private boolean validateFields() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minutesOfDay = calendar.get(Calendar.MINUTE);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        String reminderText = textDate.getText().toString();

        if (daySelected > dayOfMonth && !TextUtils.isEmpty(reminderText) || monthSelected > month && !TextUtils.isEmpty(reminderText)) {
            return true;
        } else if (daySelected == dayOfMonth && hourSelected > hourOfDay && !TextUtils.isEmpty(reminderText)) {
            return true;
        } else if (hourSelected == hourOfDay && minuteSelected > minutesOfDay && !TextUtils.isEmpty(reminderText)) {
            return true;
        } else {
            return false;
        }
    }

    private void saveReminder(Reminder reminderInfo) throws ParseException {
        this.activeReminders.add(reminderInfo);
        // Esto hace que cada vez que se agrega un reminder la vista se actualice
        this.listAdapter.notifyDataSetChanged();
        recordNewReminder(reminderInfo);
        setNotification(reminderInfo);
    }

    private void recordNewReminder(Reminder reminder) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(reminder.id));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, reminder.description);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "String");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    private void setNotification(Reminder reminder) {
        long startMillis;
        long endMillis;

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(reminder.year, reminder.month, reminder.day, reminder.hour, reminder.minutes);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(reminder.year, reminder.month, reminder.day, reminder.hour, reminder.minutes + 30);
        endMillis = endTime.getTimeInMillis();

        final Calendar cal = Calendar.getInstance();
        ContentResolver cr = this.getContentResolver();
        ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.DTSTART, startMillis);
        event.put(CalendarContract.Events.DTEND, endMillis);
        event.put(CalendarContract.Events.TITLE, reminder.description);
        event.put(CalendarContract.Events.CALENDAR_ID, 1);
        event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        checkPermission(42, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, event);

        ContentValues reminders = new ContentValues();
        reminders.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(uri.getLastPathSegment()));
        reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        reminders.put(CalendarContract.Reminders.MINUTES, 10);
        cr.insert(CalendarContract.Reminders.CONTENT_URI, reminders);
    }

    private void checkPermission(int callbackId, String... permissionsId) {
        boolean permissions = true;

        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED;
        }

        if (!permissions) {
            ActivityCompat.requestPermissions(this, permissionsId, callbackId);
        }
    }
}
