package com.com.miniproyecto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.example.min_proyecto4.R;
import com.example.min_proyecto4.RemindersActivity;
import com.miniproyecto.models.Reminder;
import com.miniproyecto.models.ViewHolder;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context listAdapter;
    private int layout;
    private List<Reminder> activeReminders, archivedReminders;

    public ListAdapter(Context listAdapter, int layout, List<Reminder> activeReminders, List<Reminder> archivedReminders) {
        this.listAdapter = listAdapter;
        this.layout = layout;
        this.activeReminders = activeReminders;
        this.archivedReminders = archivedReminders;
    }

    @Override
    public int getCount() {
        return this.activeReminders.size();
    }

    @Override
    public Object getItem(int position) {
        return this.activeReminders.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder es un patron que se usa cuando hay scroll en list, esto hace que
        // la vista no tenga que crearse cada vez que se scrolea, solo cuando es nueva
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutPopulator = LayoutInflater.from(this.listAdapter);
            convertView = layoutPopulator.inflate(R.layout.reminders_list, null);

            viewHolder = new ViewHolder();
            viewHolder.reminderText = convertView.findViewById(R.id.reminderText);
            viewHolder.reminderDate = convertView.findViewById(R.id.date);
            viewHolder.reminderHour = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
            setElementsEvents(convertView, position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Reminder clickedReminder = activeReminders.get(position);
        viewHolder.reminderText.setText(clickedReminder.description);
        viewHolder.reminderHour.setText(clickedReminder.time);
        viewHolder.reminderDate.setText(clickedReminder.date);

        return convertView;
    }

    private void setElementsEvents(View viewToRender, final int position) {
        ImageButton deleteButton = viewToRender.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeReminders.remove(position);
                notifyDataSetChanged();
            }
        });

        ImageButton archiveButton = viewToRender.findViewById(R.id.archiveButton);
        archiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                archivedReminders.add(activeReminders.get(position));
                activeReminders.remove(position);
                notifyDataSetChanged();
            }
        });
    }
}
