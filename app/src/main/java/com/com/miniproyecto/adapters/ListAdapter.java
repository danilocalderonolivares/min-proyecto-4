package com.com.miniproyecto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.example.min_proyecto4.R;
import com.miniproyecto.models.Reminder;
import com.miniproyecto.models.ViewHolder;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context listAdapter;
    private int layout;
    private List<Reminder> remindersList;

    /*public ListAdapter(Context listAdapter, int layout, List<String> remindersList) {
        this.listAdapter = listAdapter;
        this.layout = layout;
        this.remindersList = remindersList;
    }*/

    public ListAdapter(Context listAdapter, int layout, List<Reminder> remindersList) {
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

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder es un patron que se usa cuando hay scroll en list, esto hace que
        // la vista no tenga que crearse cada vez que se scrolea, solo cuando es nueva
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutPopulator = LayoutInflater.from(this.listAdapter);
            convertView = layoutPopulator.inflate(R.layout.reminders_list, null);

            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.reminderText);
            convertView.setTag(viewHolder);
            setElementsEvents(convertView, position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String clickedReminder = remindersList.get(position);
        viewHolder.textView.setText(clickedReminder);

        return convertView;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder es un patron que se usa cuando hay scroll en list, esto hace que
        // la vista no tenga que crearse cada vez que se scrolea, solo cuando es nueva
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutPopulator = LayoutInflater.from(this.listAdapter);
            convertView = layoutPopulator.inflate(R.layout.reminders_list, null);

            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.reminderText);
            convertView.setTag(viewHolder);
            setElementsEvents(convertView, position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Reminder clickedReminder = remindersList.get(position);
        viewHolder.textView.setText(clickedReminder.description);

        return convertView;
    }

    private void setElementsEvents(View viewToRender, final int position) {
        ImageButton deleteButton = viewToRender.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remindersList.remove(position);
                notifyDataSetChanged();
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
