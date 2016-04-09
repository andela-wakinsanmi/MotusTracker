package com.andela.motustracker.dbParser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andela.motustracker.R;
import com.andela.motustracker.model.LocationData;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Spykins on 08/04/16.
 */
public class DbListAdapter extends ArrayAdapter<LocationData> {
    private ArrayList<LocationData> allLocationData;
    private int groupId;
    private Context context;


    public DbListAdapter(Context context, int resource, int textViewResourceId,
                         ArrayList<LocationData> objects) {
        super(context, resource, textViewResourceId, objects);
        this.allLocationData = objects;
        this.groupId = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Get the data item for this position
        LocationData locationData = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_view_custom_layout, parent, false);
        }
        // Lookup view for data population
        TextView addressTextView = (TextView) view.findViewById(R.id.id_frag_address);
        TextView latitudeTextView = (TextView)view.findViewById(R.id.id_frag_latitude);
        TextView longitudeTextView = (TextView)view.findViewById(R.id.id_frag_longitude);
        TextView timeSpentTextView = (TextView)view.findViewById(R.id.id_frag_timeSpent);
        TextView dateTextView = (TextView)view.findViewById(R.id.id_frag_date);
        // Populate the data into the template view using the data object
        addressTextView.setText(locationData.getAddress());
        latitudeTextView.setText(String.valueOf(locationData.getLatitude()));
        longitudeTextView.setText(String.valueOf(locationData.getLongitude()));
        timeSpentTextView.setText(timeFormatter((long)locationData.getTimeSpent()));
        dateTextView.setText(locationData.getDate());
        // Return the completed view to render on screen
        return view;
    }

    public void clearAllDataInList() {
        allLocationData.clear();
    }

    public void addNewDataToList(ArrayList<LocationData> locationData) {
        if(allLocationData.size() != 0) {
            allLocationData.clear();
        } else {
            populateDataInList(locationData);
        }
    }

    private void populateDataInList(ArrayList<LocationData> locationData) {
        for(LocationData data : locationData) {
            allLocationData.add(data);
        }
    }

    private String timeFormatter(long milliseconds) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1));
    }
}