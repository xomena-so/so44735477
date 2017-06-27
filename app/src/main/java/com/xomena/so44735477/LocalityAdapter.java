package com.xomena.so44735477;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by google_training2 on 27/06/2017.
 */

public class LocalityAdapter extends ArrayAdapter<Locality> {
    public LocalityAdapter(Context context, ArrayList<Locality> localities) {
        super(context, 0, localities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Locality loc = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_locality, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        // Populate the data into the template view using the data object
        tvName.setText(loc.name);
        tvName.setTag(position);

        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                Locality loc = getItem(position);

                Intent mapIntent = new Intent(getContext(), MapsActivity.class);
                mapIntent.putExtra("lat", loc.latitude);
                mapIntent.putExtra("lng", loc.longitude);
                mapIntent.putExtra("name", loc.name);

                getContext().startActivity(mapIntent);

            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}