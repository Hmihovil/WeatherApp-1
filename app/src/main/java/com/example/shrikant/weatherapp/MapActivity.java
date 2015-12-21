package com.example.shrikant.weatherapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.model.AerisResponse;

/**
 * Created by shrikant on 9/12/15.
 */
public class MapActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        Intent i=getIntent();
        String result= i.getStringExtra("result");
        String city=i.getStringExtra("city");
        String state=i.getStringExtra("state");
        String jsonFormattedString = result.replaceAll("\\\\", "");
        String[] keyvalue=jsonFormattedString.split(",");

            String[] j=keyvalue[0].split(":");

            String[] k=keyvalue[1].split(":");
        Bundle bundle=new Bundle();
        bundle.putString("lat", j[1]);
        bundle.putString("lon", k[1]);


        MapFragment mFragment = new MapFragment();
        mFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_placeholder,mFragment);
        fragmentTransaction.commit();
    }
}