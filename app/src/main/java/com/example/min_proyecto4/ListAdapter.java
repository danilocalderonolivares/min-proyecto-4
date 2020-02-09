package com.example.min_proyecto4;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context listAdapter;
    private int layout;
    private List<String> remindersList;

    public ListAdapter(Context listAdapter, int layout, List<String> remindersList) {
        this.listAdapter = listAdapter;
        this.layout = layout;
        this.remindersList = remindersList;
    }

    @Override
    public int getCount() {
        return this.remindersList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.remindersList.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutPopulator = LayoutInflater.from(this.listAdapter);
        View viewToCreate = layoutPopulator.inflate(R.layout.reminders_list, null);
        String clickedReminder = remindersList.get(position);
        TextView textView = viewToCreate.findViewById(R.id.textView);
        textView.setText(clickedReminder);

        return viewToCreate;
    }
}
