package com.example.ideafoundation.meetaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by ideafoundation on 08/02/17.
 */

public class ShowMapActivity   extends AppCompatActivity implements OnMapReadyCallback
{
    Toolbar mToolbar;

    private GoogleMap mMap;
    String addres;
    String markerTitle;
    LatLng location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmap);
        addres=getIntent().getStringExtra("list");
        markerTitle=getIntent().getStringExtra("title");
        String[] latlong =  addres.split(" ,");
        Log.e("latlongdddd",""+latlong[0]);
        String one=latlong[0];
        String [] splitst=one.split("\\s");
        String two=splitst[1].substring(1,splitst[1].length()-1);
        Log.e("twp",""+two);
        String [] spli=two.split(",");
        Log.e("sdsd",""+spli[0]+" ddd"+spli[1]);
        double latitude = Double.parseDouble(spli[0]);
        double longitude = Double.parseDouble(spli[1]);
        location = new LatLng(latitude, longitude);
        Log.e("loc",""+location);
        initToolbar();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .zoom(17).build();
        //Zoom in and animate the camera.
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //Initialize Google Play Services
        mMap.addMarker(new MarkerOptions()
                .title(markerTitle)
                .position(location));
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);

                // Getting the position from the marker
                LatLng latLng = marker.getPosition();

                // Getting reference to the TextView to set latitude
                TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);


                // Setting the latitude
                tvLat.setText(marker.getTitle());



                // Returning the view containing InfoWindow contents
                return v;
            }
        });

    }

    void initToolbar(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        TextView header=(TextView)mToolbar.findViewById(R.id.header);
        header.setText("Map Activity");
        ImageView click=(ImageView)mToolbar.findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowMapActivity.this,ActivityNav.class);
                startActivity(intent);
            }
        });

    }


}

