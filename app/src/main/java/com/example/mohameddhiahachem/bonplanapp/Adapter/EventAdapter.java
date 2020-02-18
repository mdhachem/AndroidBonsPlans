package com.example.mohameddhiahachem.bonplanapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohameddhiahachem.bonplanapp.Entity.EventPlan;
import com.example.mohameddhiahachem.bonplanapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<EventPlan> {

    private Context context;

    public EventAdapter(@NonNull Context context, @NonNull ArrayList<EventPlan> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);

        TextView eventPlanId = (TextView) convertView.findViewById(R.id.event_sheet_id);
        TextView eventName = (TextView) convertView.findViewById(R.id.event_name);
        TextView eventAddress = (TextView) convertView.findViewById(R.id.event_Address);
        TextView eventDate = (TextView) convertView.findViewById(R.id.event_date);
        ImageView eventImage = (ImageView) convertView.findViewById(R.id.event_pic);

        EventPlan eventPlan = getItem(position);
        eventPlanId.setText(String.valueOf(eventPlan.getId()));
        eventName.setText(eventPlan.getName());
        eventDate.setText( eventPlan.getDateEvent().substring(8,10) +"-"+ eventPlan.getDateEvent().substring(5,7) +"-"+ eventPlan.getDateEvent().substring(0,4) +" At " + eventPlan.getDateEvent().substring(11,16) );
        eventAddress.setText(eventPlan.getAddress() + ", " + eventPlan.getCity() + ", " + eventPlan.getGovernorate() );
        Picasso.get().load("http://10.0.3.2:8000/uploads/events/" + eventPlan.getImage()).resize(100,100).into(eventImage);

        return convertView;
    }
}