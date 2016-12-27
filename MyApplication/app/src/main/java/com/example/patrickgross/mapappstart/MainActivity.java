package com.example.patrickgross.mapappstart;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    //Map variables
    private static boolean mapReady = false;
    private static GoogleMap m_Map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up Buttons
        final Button btnMap = (Button) findViewById(R.id.btn_map);
        final Button btnSatellite = (Button) findViewById(R.id.btn_sat);
        final Button btnHybrid = (Button) findViewById(R.id.btn_hyb);

        //TODO Adding ClickListeners
        btnMap.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (mapReady) {
                    m_Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });

        btnSatellite.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (mapReady) {
                    m_Map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });

        btnHybrid.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (mapReady) {
                    m_Map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        m_Map = googleMap;
        LatLng berlin = new LatLng(52.4912047, 13.4307885);
        CameraPosition target = CameraPosition.builder().target(berlin).zoom(17).tilt(65).build();
        m_Map.moveCamera(CameraUpdateFactory.newCameraPosition(target));

    }
}
