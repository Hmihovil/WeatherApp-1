package com.example.shrikant.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.communication.parameter.PlaceParameter;
import com.hamweather.aeris.maps.AerisMapOptions;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
//import com.hamweather.aeris.maps.AerisMapView.OnAerisMapLongClickListener;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.tiles.AerisTile;

/**
 * Created by shrikant on 9/12/15.
 */

    public class MapFragment extends MapViewFragment implements OnAerisMapLongClickListener, AerisCallback {
        //AerisEngine//.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), this);

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            String lat=getArguments().getString("lat");
            String lon=getArguments().getString("lon");
            //System.out.println("lon="+lon+" lat="+lat);
            AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), "com.example.shrikant.weatherapp.MapFragment");
            View view = inflater.inflate(R.layout.fragment_interactive_maps, container, false);
            mapView = (AerisMapView) view.findViewById(R.id.aerisfragment_map);
            mapView.init(savedInstanceState, AerisMapView.AerisMapType.GOOGLE);
            mapView.setOnAerisMapLongClickListener(this);
            mapView.addLayer(AerisTile.RADSAT);
            mapView.setZoomControlsEnabled(true);
            //String jsonFormattedString = result.replaceAll("\\\\", "");
            //String[] keyvalue=jsonFormattedString.split(",");
            Double late = Double.parseDouble(lat);
            Double lng = Double.parseDouble(lon);
            //Location location=new Location("Los Angeles, CA");
            mapView.moveToLocation(new LatLng(late, lng), 9);
            //mapView.moveToLocation(location, (float) 7.0);
            return view;
        }

        @Override
        public void onMapLongClick(double lat, double longitude) {
            // code to handle map long press. i.e. Fetch current conditions?
            // see demo app MapFragment.java
        }

        @Override
        public void onResult(EndpointType endpointType, AerisResponse aerisResponse) {

        }
    }
