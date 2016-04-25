package com.andela.motustracker.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.andela.motustracker.activity.MapActivity;
import com.andela.motustracker.R;
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
    private boolean isLocationSortSet;
    private DbManager dbManager;

    public LocationListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);
        dbManager = new DbManager();

        spinner = (Spinner) view.findViewById(R.id.id_spinnerView);
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(AppContext.get(),
                R.array.spinnerDropDown, R.layout.spinner_custom_view);
        spinner.setAdapter(spinnerAdapter);
        listView = (ListView) view.findViewById(R.id.id_frag_listView);
        final ArrayList<LocationData> dataFromDb = dbManager.readDataFromDb();
        listAdapter = new DbListAdapter(getContext(),R.layout.fragment_location_list,
                R.id.activityDetected, dataFromDb);
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                LocationData itemClicked = listAdapter.getItem(position);
                Intent intent = new Intent(getContext(), MapActivity.class);
                intent.putExtra("selectedItem", itemClicked);
                intent.putParcelableArrayListExtra("allData", dataFromDb);
                startActivityForResult(intent, 10);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItemFromList(position);
                return true;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                switch (item) {
                    case "List By Date":
                        listAdapter.clear();
                        isLocationSortSet = false;
                        listAdapter.addNewDataToList(dbManager.readDataFromDb());
                        listAdapter.notifyDataSetChanged();
                        break;
                    case "List By Location":
                        listAdapter.clear();
                        isLocationSortSet = true;
                        listAdapter.addNewDataToList(dbManager.readListBaseOnLocation());
                        listAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void removeItemFromList(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Delete");
        LocationData dataClicked = listAdapter.getItem(position);
        if(isLocationSortSet) {
            alert.setMessage("Do you want to delete all saved data in " + dataClicked.getAddress() + " ?");
        } else {
            alert.setMessage("Do you want to delete address " + dataClicked.getAddress() +
                    " saved on " + dataClicked.getDate() + " ?");

        }
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isLocationSortSet) {
                    dbManager.deleteAllLocationOccurrences(listAdapter.getItem(position));
                    listAdapter.remove(listAdapter.getItem(position));
                } else {
                    dbManager.deleteInstanceOfLocation(listAdapter.getItem(position));
                    listAdapter.remove(listAdapter.getItem(position));
                }
                listAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetInvalidated();
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}
