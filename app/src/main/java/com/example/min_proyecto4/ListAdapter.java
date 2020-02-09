package com.example.min_proyecto4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
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
        View viewToRender = layoutPopulator.inflate(R.layout.reminders_list, null);
        setElementsEvents(viewToRender);
        String clickedReminder = remindersList.get(position);
        TextView textView = viewToRender.findViewById(R.id.reminderText);
        textView.setText(clickedReminder);

        return viewToRender;
    }

    public void setElementsEvents(View viewToRender) {
        ImageButton deleteButton = viewToRender.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("I was clicked");
            }
        });

        ImageButton archiveButton = viewToRender.findViewById(R.id.archiveButton);
        archiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Archive was clicked");
            }
        });
    }
}
