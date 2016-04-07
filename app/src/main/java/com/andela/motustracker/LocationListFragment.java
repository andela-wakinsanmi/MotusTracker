package com.andela.motustracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.andela.motustracker.dbParser.DbCursorAdapter;
import com.andela.motustracker.helper.AppContext;
import com.andela.motustracker.manager.DbManager;
import com.andela.motustracker.model.LocationData;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationListFragment extends Fragment {
    private Spinner spinner;
    private ListView listView;


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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //final String ad = getResources().getString(R.string.list_by_name);
                switch (item) {
                    case "List By Day":
                        //List location by day in the ListView
                        break;
                    case "List By Time Spent":
                        //List location by time spent in the ListView
                        break;
                }

                // Showing selected spinner item

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView = (ListView) view.findViewById(R.id.id_frag_listView);
        DbCursorAdapter adapter = new DbCursorAdapter(new DbManager().readDataFromDb(),0);
        listView.setAdapter(adapter);
        return view;
    }

}
