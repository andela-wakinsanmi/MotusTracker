package com.andela.motustracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.andela.motustracker.dbParser.DbListAdapter;
import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.manager.DbManager;
import com.andela.motustracker.model.LocationData;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationListFragment extends Fragment {
    private Spinner spinner;
    private ListView listView;
    private DbListAdapter listAdapter;


    public LocationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        spinner = (Spinner) view.findViewById(R.id.id_spinnerView);
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(AppContext.get(),
                R.array.spinnerDropDown, R.layout.spinner_custom_view);
        spinner.setAdapter(spinnerAdapter);
        listView = (ListView) view.findViewById(R.id.id_frag_listView);
        listAdapter = new DbListAdapter(getContext(),R.layout.fragment_location_list,
                R.id.id_userLatitudeText, new DbManager().readDataFromDb());
        listView.setAdapter(listAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //final String ad = getResources().getString(R.string.list_by_name);
                switch (item) {
                    case "List By Day":
                        listAdapter.clear();
                        listAdapter.addNewDataToList(new DbManager().readDataFromDb());
                        listAdapter.notifyDataSetChanged();
                        break;
                    case "List By Time Spent":
                        //List location by time spent in the ListView
                        listAdapter.clear();
                        ArrayList<LocationData> data = new DbManager().readListBaseOnLocation();
                        listAdapter.addNewDataToList(data);
                        listAdapter.notifyDataSetChanged();
                        break;
                }

                // Showing selected spinner item

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

}
