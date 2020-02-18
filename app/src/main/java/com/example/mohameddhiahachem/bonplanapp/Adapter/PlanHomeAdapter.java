package com.example.mohameddhiahachem.bonplanapp.Adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohameddhiahachem.bonplanapp.Entity.Plan;
import com.example.mohameddhiahachem.bonplanapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlanHomeAdapter extends ArrayAdapter<Plan> {

    private Context context;

    public PlanHomeAdapter(@NonNull Context context, @NonNull ArrayList<Plan> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.item_plan, parent, false);

        TextView placeId = (TextView) convertView.findViewById(R.id.place_id);
        TextView placeName = (TextView) convertView.findViewById(R.id.place_name);
        TextView placeAddress = (TextView) convertView.findViewById(R.id.place_Address);
        ImageView placeImage = (ImageView) convertView.findViewById(R.id.place_pic);

        Plan plan = getItem(position);

        placeId.setText(String.valueOf(plan.getId()));
        placeName.setText(plan.getName());
        placeAddress.setText(plan.getAddress() + ", " + plan.getCity() + ", " + plan.getGovernorate() );
        Picasso.get().load("http://10.0.3.2:8000/uploads/plans/" + plan.getImage()).resize(100,100).into(placeImage);

        return convertView;
    }
}
