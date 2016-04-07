package com.andela.motustracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.andela.motustracker.dbParser.DbCursorAdapter;
import com.andela.motustracker.manager.DbManager;
import com.andela.motustracker.model.LocationData;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationListFragment extends Fragment {


    public LocationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.id_frag_listView);
        DbCursorAdapter adapter = new DbCursorAdapter(new DbManager().readDataFromDb(),0);
        listView.setAdapter(adapter);
        return view;
    }

}
