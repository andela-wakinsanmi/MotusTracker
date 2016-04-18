package com.andela.motustracker.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.andela.motustracker.R;
import com.andela.motustracker.manager.GeocoderManager;
import com.andela.motustracker.model.DistanceCalculator;
import com.andela.motustracker.model.LocationData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private double longitude;
    private double latitude;
    private Toolbar toolbar;
    private String address;
    private ArrayList<LocationData> dataFromDb;
    private LocationData selectedData;
    private DistanceCalculator distanceCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        toolbar = (Toolbar) findViewById(R.id.id_toolBar);
        setSupportActionBar(toolbar);
        setTitle("Map");
        TextView latitudeTextView = (TextView) findViewById(R.id.id_frag_latitude);
        TextView longitudeTextView = (TextView) findViewById(R.id.id_frag_longitude);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        selectedData = getIntent().getParcelableExtra("selectedItem");
        latitude = selectedData.getLatitude();
        longitude = selectedData.getLongitude();
        distanceCalculator = new DistanceCalculator(latitude, longitude);
        address = selectedData.getAddress();
        if(address.equals(this.getResources().getString(R.string.location_unknown))) {
            attemptToFetchAndUpdateDb();
        }
        dataFromDb = getIntent().getParcelableArrayListExtra("allData");
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (latitudeTextView != null && longitudeTextView != null) {
            latitudeTextView.setText(String.valueOf(latitude));
            longitudeTextView.setText(String.valueOf(longitude));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng currentLocation = new LatLng(latitude, longitude);
        Marker marker = map.addMarker(new MarkerOptions()
                .position(currentLocation)
                .title(address));
        marker.setVisible(true);
        marker.setAlpha(0.8F);
        marker.showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        map.animateCamera(CameraUpdateFactory.zoomIn());
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

    private void attemptToFetchAndUpdateDb() {
        Location targetLocation = new Location("");
        targetLocation.setLatitude(latitude);
        targetLocation.setLongitude(longitude);
        GeocoderManager geocoderManager = new GeocoderManager();
        geocoderManager.startIntentService(targetLocation);
    }

}
