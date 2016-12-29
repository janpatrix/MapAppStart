package com.example.patrickgross.mapappstart;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, OnStreetViewPanoramaReadyCallback {

    //Map variables
    private static boolean mapReady = false;
    private static GoogleMap m_Map = null;
    private static StreetViewPanorama m_Panorama = null;
    private static final LatLng LOCATION_BERLIN = new LatLng(52.4912047, 13.4307885);
    private static final LatLng LOCATION_NEW_YORK = new LatLng(40.730610, -73.935242);
    private static final LatLng LOCATION_TOKYO = new LatLng(35.652832, 139.839478);

    //Buttons
    Button btnBerlin = null;
    Button btnTokyo = null;
    Button btnNewYork = null;

    //Markers
    MarkerOptions home;
    CircleOptions circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up Map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Set up Panorama
        StreetViewPanoramaFragment streetFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.street);
        streetFragment.getStreetViewPanoramaAsync(this);

        //Setting up Buttons
        final Button btnMap = (Button) findViewById(R.id.btn_map);
        final Button btnSatellite = (Button) findViewById(R.id.btn_sat);
        final Button btnHybrid = (Button) findViewById(R.id.btn_hyb);
        btnBerlin = (Button) findViewById(R.id.btn_berlin);
        btnNewYork = (Button) findViewById(R.id.btn_newYork);
        btnTokyo = (Button) findViewById(R.id.btn_tokyo);

        //Initialize Markers
        home = new MarkerOptions()
                .position(new LatLng(52.4912047, 13.4307885))
                .title("Zuhause");

        //Adding Map Types ClickListeners
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

        //Adding Map Travel ClickListeners
        btnBerlin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (mapReady) {
                    mapTravel(LOCATION_BERLIN);
                }
            }
        });

        btnTokyo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (mapReady) {
                    mapTravel(LOCATION_TOKYO);
                }
            }
        });

        btnNewYork.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (mapReady) {
                    mapTravel(LOCATION_NEW_YORK);
                }
            }
        });

        circle = new CircleOptions()
                .center(LOCATION_BERLIN)
                .radius(100)
                .strokeColor(Color.RED);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        m_Panorama = streetViewPanorama;
        m_Panorama.setPosition(LOCATION_BERLIN);
        StreetViewPanoramaCamera streetCamera = new StreetViewPanoramaCamera
                .Builder()
                .bearing(180)
                .build();
        m_Panorama.animateTo(streetCamera, 5000);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        m_Map = googleMap;
        m_Map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        m_Map.addMarker(home);
        m_Map.addCircle(circle);
        mapTravel(LOCATION_BERLIN);
    }

    private void mapTravel(LatLng destination) {
        CameraPosition target = CameraPosition.builder()
                .target(destination)
                .zoom(17)
                .tilt(65)
                .build();
        m_Map.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);
    }


}
