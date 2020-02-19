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

public class ArchivedItemsAdapter extends BaseAdapter {

    private Context listAdapter;
    private int layout;
    private List<Reminder> archivedReminders, activeReminders;

    public ArchivedItemsAdapter(Context listAdapter, int layout, List<Reminder> archivedReminders, List<Reminder> activeReminders) {
        this.listAdapter = listAdapter;
        this.layout = layout;
        this.archivedReminders = archivedReminders;
        this.activeReminders = activeReminders;
    }

    @Override
    public int getCount() {
        return this.archivedReminders.size();
    }

    @Override
    public Object getItem(int position) {
        return this.archivedReminders.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutPopulator = LayoutInflater.from(this.listAdapter);
            convertView = layoutPopulator.inflate(R.layout.archived_reminders, null);

            viewHolder = new ViewHolder();
            viewHolder.reminderText = convertView.findViewById(R.id.archivedReminderText);
            viewHolder.reminderDate = convertView.findViewById(R.id.archivedItemDate);
            viewHolder.reminderHour = convertView.findViewById(R.id.archivedItemTime);
            convertView.setTag(viewHolder);
            setElementsEvents(convertView, position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Reminder clickedReminder = archivedReminders.get(position);
        viewHolder.reminderText.setText(clickedReminder.description);
        viewHolder.reminderHour.setText(clickedReminder.time);
        viewHolder.reminderDate.setText(clickedReminder.date);

        return convertView;
    }

    private void setElementsEvents(View viewToRender, final int position) {
        ImageButton deleteButton = viewToRender.findViewById(R.id.deleteArchivedItem);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                archivedReminders.remove(position);
                notifyDataSetChanged();
            }
        });

        ImageButton archiveButton = viewToRender.findViewById(R.id.undoArchivedItem);
        archiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeReminders.add(archivedReminders.get(position));
                archivedReminders.remove(position);
                notifyDataSetChanged();
            }
        });
    }
}
