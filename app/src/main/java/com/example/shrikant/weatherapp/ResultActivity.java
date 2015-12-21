package com.example.shrikant.weatherapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.FacebookException;
//import com.facebook.share.Sharer;
//import com.facebook.share.Sharer;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.hamweather.aeris.maps.*;
import com.facebook.FacebookSdk;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.TextAttribute;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shrikant on 6/12/15.
 */
public class ResultActivity extends AppCompatActivity {
    //Intent intent=getIntent();
    //String result="";
    ShareDialog shareDialog;
    CallbackManager callbackManager;
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
        //FacebookCallback<Sharer.Result> shareCallBack;
        shareDialog.registerCallback(callbackManager,new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                //showToast(message(R.string.title_fbShare)).show();
                Toast.makeText(ResultActivity.this, "Facebook Post Successful", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancel() {
                Toast.makeText(ResultActivity.this, "Post Cancelled", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException error) {
                //showToast(message(R.string.msgerr_shareOnFB) + " -- " + error.getMessage()).show();
                Toast.makeText(ResultActivity.this, "Post Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        final ConnectClass g= new ConnectClass();
        g.execute();
        final Button viewdetails=(Button)findViewById(R.id.button3);
        viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewintent = new Intent(ResultActivity.this, DetailsActivity.class);
                //startActivity(viewintent);//Intent;
                String state = g.intent.getStringExtra("state");
                String temperature = g.intent.getStringExtra("temperature");
                String streetaddress = g.intent.getStringExtra("street");
                String city = g.intent.getStringExtra("city");
                viewintent.putExtra("result", g.result);
                viewintent.putExtra("city", city);
                viewintent.putExtra("state", state);
                viewintent.putExtra("street", streetaddress);
                viewintent.putExtra("temperature", temperature);
                startActivity(viewintent);
            }
        });
        final Button mapview=(Button)findViewById(R.id.button4);
        mapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewintent =new Intent(ResultActivity.this,MapActivity.class);
                //startActivity(viewintent);//Intent;
                String state= g.intent.getStringExtra("state");
                String temperature=g.intent.getStringExtra("temperature");
                String streetaddress = g.intent.getStringExtra("street");
                String city = g.intent.getStringExtra("city");
                viewintent.putExtra("result", g.result);
                viewintent.putExtra("city",city);
                viewintent.putExtra("state",state);
                viewintent.putExtra("street", streetaddress);
                //viewintent.putExtra("temperature",temperature);
                startActivity(viewintent);
            }
        });
        final ImageButton imv=(ImageButton)findViewById(R.id.imageButton);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent viewintent =new Intent(ResultActivity.this,MapActivity.class);
                //startActivity(viewintent);//Intent;
                String state= g.intent.getStringExtra("state");
                String temperature=g.intent.getStringExtra("temperature");
                String streetaddress = g.intent.getStringExtra("street");
                String city = g.intent.getStringExtra("city");
                String result=g.result;
                //System.out.println(result);
                String summary="";
                String icon="";
                String temp="";
                String jsonFormattedString = result.replaceAll("\\\\", "");
                String[] keyvalue=jsonFormattedString.split(",");
                //System.out.println(keyvalue);
                int tempcount=0;
                String[] data=Arrays.copyOfRange(keyvalue, 0, 20);
                for (int i=0;i<20;i++){
                    String pair=data[i];
                    //.indexOf("data");
                    pair=pair.replaceAll("\"","");
                    String[] j=pair.split(":");
                    //String[] j=data[i].split(":");
                    if(j[0].contains("summary")){
                        summary=j[1];
                    }
                    if(j[0].contains("icon")){
                        icon=geticon(j[1]);
                    }
                    if(j[0].contains("temperature")&&tempcount==0){
                        tempcount++;
                        //TextView tv1=(TextView)findViewById(R.id.textView10);
                        String s= String.valueOf((int)Float.parseFloat(j[1].trim()));
                        if(temperature.equals("us")){
                            //AttributedString deg= new AttributedString("F");
                            //deg.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, 0, 2);
                            //System.out.println(j[1]);

                            temp=s+ Html.fromHtml("<sup><sup>\u00b0</sup>F</sup>");
                        }else {
                            temp=s + Html.fromHtml("<sup><sup>\u00b0</sup>C</sup>");
                        }
                    }
                    //System.out.println(data[i]);
                }

                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("http://forecast.io"))
                        .setContentTitle("Current Weather in "+city.trim()+", "+state)
                        .setContentDescription(summary+", "+temp)
                        .setImageUrl(Uri.parse(icon))
                        .build();
                shareDialog.show(content);
                //shareDialog.registerCallback(callbackManager,);



            }
        });
    }

    private String geticon(String s) {
        //return null;
        //System.out.println(s);
        switch (s){
            case "clear-day":
                return "http://cs-server.usc.edu:45678/hw/hw8/images/clear.png";
                //break;
            case "clear-night":
                return "http://cs-server.usc.edu:45678/hw/hw8/images/clear_night.png";
                //break;
            case "rain":
                return "http://cs-server.usc.edu:45678/hw/hw8/images/rain.png";
                //break;
            case "snow":
                return "http://cs-server.usc.edu:45678/hw/hw8/images/snow.png";
                //break;
            case "sleet":
                return "http://cs-server.usc.edu:45678/hw/hw8/images/sleet.png";
                //break;
            case "wind":
                return "http://cs-server.usc.edu:45678/hw/hw8/images/wind.png";
                //break;
            case "fog":
                return "http://cs-server.usc.edu:45678/hw/hw8/images/fog.png";
                //break;
            case "cloudy":
                return "http://cs-server.usc.edu:45678/hw/hw8/images/cloudy.png";
                //break;
            case "partly-cloudy-day":
                return "http://cs-server.usc.edu:45678/hw/hw8/images/cloud_day.png";
                //break;
            case "partly-cloudy-night":
                return "http://cs-server.usc.edu:45678/hw/hw8/images/cloud_night.png";
                //break;
        }
        return null;
    }

    public class ConnectClass extends AsyncTask<String, String, String> {

        public Intent intent=getIntent();
        public String result="";
        @Override
        protected String doInBackground(String... params) {
            //Intent intent=getIntent();
            String state= intent.getStringExtra("state");
            String temperature=intent.getStringExtra("temperature");
            String streetaddress = intent.getStringExtra("street");
            String city = intent.getStringExtra("city");
            //String querystring= "http://gangadewebtech-env.elasticbeanstalk.com/streetaddress="+streetaddress+"&city="+city+"&state="+state+"&temperature="+temperature+"&Search=Search";
            String querystring= null;
            try {
              querystring = "http://gangadewebtech-env.elasticbeanstalk.com/?streetaddress="+ URLEncoder.encode(streetaddress, "UTF-8")+"&city="+URLEncoder.encode(city, "UTF-8")+"&state="+URLEncoder.encode(state, "UTF-8")+"&temperature="+URLEncoder.encode(temperature, "UTF-8")+"&Search=Search";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //System.out.println(querystring);
            URL url = null;
            try {
                url = new URL(querystring);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }




            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"),8);
                //JSONObject jObject = new JSONObject((Map) in);
                StringBuilder sBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null){
                    sBuilder.append(inputLine);
                //System.out.println(inputLine);
                }
                in.close();
                result=sBuilder.toString().substring(0,sBuilder.toString().length()-1);
                //Log.e("JSON:: ", result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result1) {


            //result=result.substring(1,result.length()-1);
            String jsonFormattedString = result.replaceAll("\\\\", "");
            String[] keyvalue=jsonFormattedString.split(",");
            tp(keyvalue);
            currently(keyvalue);
            int iconcount=0;
            int summarycount=0;
            int tempcount=0;
            for(String pair:keyvalue){


                String[] j=pair.split(":");
                //System.out.println(j[0]);

                if(j[0].contains("icon")&&iconcount==0){
                    //System.out.println("yaay!");
                    iconcount++;
                    displayicon(j);
                }
                if(j[0].contains("summary")&&summarycount==0){
                    summarycount++;
                    String summary=j[1];
                    summary=summary.replaceAll("\"","");
                    //System.out.println("yolo");
                    TextView tv=(TextView)findViewById(R.id.textView7);
                    tv.setText(summary +" in "+ intent.getStringExtra("city").trim()+", "+intent.getStringExtra("state"));
                }
                if(j[0].contains("temperature")&&tempcount==0){
                    tempcount++;
                    TextView tv1=(TextView)findViewById(R.id.textView10);
                    String s= String.valueOf((int)Float.parseFloat(j[1].trim()));
                    if(intent.getStringExtra("temperature").equals("us")){

                        tv1.setText(s+ Html.fromHtml("<sup><sup>\u00b0</sup>F</sup>"));
                    }else {
                        tv1.setText(s + Html.fromHtml("<sup><sup>\u00b0</sup>C</sup>"));
                    }
                }

           }


        }
        public void displayicon(String[] j){
            //System.out.println(j[0]);
            //System.out.println(j[1]);
            ImageView image=(ImageView)findViewById(R.id.imageView2);
            j[1]=j[1].replaceAll("\"","");
            if(("clear-day").contains(j[1])){
                //System.out.println("hahaha");
                image.setImageResource(R.drawable.clear);
            }else if(("clear-night").contains(j[1])){
                image.setImageResource(R.drawable.clear_night);
            }else if(("rain").contains(j[1])){
                image.setImageResource(R.drawable.rain);
            }else if(("snow").contains(j[1])){
                image.setImageResource(R.drawable.snow);
            }else if(("sleet").contains(j[1])){
                image.setImageResource(R.drawable.sleet);
            }else if(("wind").contains(j[1])){
                image.setImageResource(R.drawable.wind);
            }else if (("fog").contains(j[1])){
                image.setImageResource(R.drawable.fog);
            }else if(("cloudy").contains(j[1])){
                image.setImageResource(R.drawable.cloudy);
            }else if (("partly-cloudy-day").contains(j[1])){
                image.setImageResource(R.drawable.cloud_day);
            }else if(("partly-cloudy-night").contains(j[1])){
                //System.out.println("ahahahaaha");
                image.setImageResource(R.drawable.cloud_night);
            }
            //System.out.println("shaa");

        }
        public void tp(String[] objectype){
            //System.out.println("Object length "+ objectype.length);

            String[] data=Arrays.copyOfRange(objectype, 943, objectype.length);
            //System.out.println("Data length "+data.length);
            int count=0;
            int acount=0;
            int ccount=0;
            int dcount=0;
            String mintemp="";
            String maxtemp="";
            /*for (int i=0;i<objectype.length;i++){
                System.out.println(i+" ) "+objectype[i]);
            }*/
            for(int i=0;i<objectype.length-944;i++){
                String pair=data[i];
                //.indexOf("data");
                pair=pair.replaceAll("\"","");
                String[] j=pair.split(":");
                //if(s)
                //summary=summary.replaceAll("\"","");
                //System.out.println(i+" ) "+j[0]);
                TextView tv=(TextView)findViewById(R.id.textView11);
                if((j[0]).contains("temperatureMin")&& count==0){

                    //System.out.println(j[1]);
                    mintemp=String.valueOf((int)Float.parseFloat(j[1].trim()));
                    //System.out.println( i+ "inside min temp" + j[1]+ " "+ mintemp);
                    count++;
                }
                if((j[0]).contains("temperatureMax")&& acount==0){

                    maxtemp=String.valueOf((int)Float.parseFloat(j[1].trim()));
                    //System.out.println( i+ "inside max temp"+j[1]+" "+maxtemp);
                    acount++;
                }
                if(("sunriseTime").contains(j[0])&& ccount==0){
                    TextView tv5=(TextView)findViewById(R.id.textView21);
                    String date=getTime(j[1]);
                    tv5.setText(date);
                    ccount++;
                }
                if(("sunsetTime").contains(j[0])&&dcount==0){
                    TextView tvv=(TextView)findViewById(R.id.textView25);
                    String date= getTime(j[1]);
                    tvv.setText(date);
                    dcount++;
                }
                tv.setText("L: "+mintemp+ Html.fromHtml("<sup>\u00b0</sup>")+" | H: " +maxtemp+ Html.fromHtml("<sup>\u00b0</sup>"));

               //System.out.println(j[0] +"= " +j[1]);
            }

        }
        public void currently(String[] objectype){
            //System.out.println(objectype.length);

            String[] currently=Arrays.copyOfRange(objectype, 5, 21);
            for(int i=0;i<16;i++){
                String pair=currently[i];
                pair=pair.replaceAll("\"","");
                String[] j=pair.split(":");
                //System.out.println(i+" = "+j[0]);
                if(j[0].contains("precipIntensity")){
                    getPrecipitation(j[1]);
                }
                if(j[0].contains("precipProbability")){
                    TextView tv=(TextView)findViewById(R.id.textView15);
                    int rain=percentageFormat(Float.parseFloat(j[1]));
                    tv.setText((Integer.toString(rain) + " %"));

                }
                if(j[0].contains("windSpeed")){
                    TextView tv2=(TextView)findViewById(R.id.textView17);
                    double d= twodecimal(j[1]);
                    if(intent.getStringExtra("temperature").equals("us")) {
                        tv2.setText((Double.toString(d) + " mph"));
                    }else{
                        tv2.setText((Double.toString(d) + " m/s"));
                    }
                }
                if(j[0].contains("dewPoint")){
                    TextView tv2=(TextView)findViewById(R.id.textView19);
                    double d= twodecimal(j[1]);
                    if(intent.getStringExtra("temperature").equals("us")) {
                        tv2.setText((Double.toString(d)+ Html.fromHtml("<sup>\u00b0</sup>F")));
                    }else{
                        tv2.setText((Double.toString(d)+ Html.fromHtml("<sup>\u00b0</sup>C")));
                    }

                }
                if(j[0].contains("humidity")){
                    TextView tv4=(TextView)findViewById(R.id.textView23);
                    int rain=percentageFormat(Float.parseFloat(j[1]));
                    tv4.setText((Integer.toString(rain) + " %"));
                }
                if(j[0].contains("visibility")){
                    TextView tv2=(TextView)findViewById(R.id.textView27);
                    double d= twodecimal(j[1]);
                    if(intent.getStringExtra("temperature").equals("us")) {
                        tv2.setText((Double.toString(d)+ " mi"));
                    }else{
                        tv2.setText((Double.toString(d)+ " km"));
                    }
                }
            }
        }
        public double twodecimal(String j){
            double d=Double.parseDouble(j);
            return (double)Math.round(d*100)/100;

        }
        public int percentageFormat(float $val){
            int $rain=0;
            $rain= (int) ($val * 100);
            //alert($rain);
            //String $rains= $rain;
            return $rain;
        }
        public String getTime(String j){
            //System.out.println(j);
            long j1= Long.parseLong(j);
            Date time=new Date((long)j1*1000);
            //System.out.println(time.toString());
            SimpleDateFormat simpleDateformatter = new SimpleDateFormat("h:mm a");
            String convertedDate = simpleDateformatter .format(time);
            //System.out.println(convertedDate);
            return convertedDate;
        }

        public void getPrecipitation(String j){
            float $precipIntensity=Float.parseFloat(j);
            //System.out.println($precipIntensity);
            TextView tv=(TextView)findViewById(R.id.textView13);
            if(intent.getStringExtra("temperature").equals("us")){
                if($precipIntensity < 0.002){
                    tv.setText("None");
                }
                else if(0.002 <= $precipIntensity && $precipIntensity< 0.017){
                    tv.setText("Very Light");
                }
                else if(0.017 <= $precipIntensity && $precipIntensity < 0.1){
                    tv.setText("Light");
                }
                else if(0.1 <= $precipIntensity && $precipIntensity < 0.4){
                    tv.setText("Moderate");
                }
                else if(0.4 <= $precipIntensity){
                    tv.setText("Heavy");
                }
            }
            else{
                if($precipIntensity < 0.058){
                    tv.setText("None");
                }
                else if(0.058 <= $precipIntensity && $precipIntensity< 0.431){
                    tv.setText("Very Light");
                }
                else if(0.431 <= $precipIntensity && $precipIntensity < 2.54){
                    tv.setText("Light");
                }
                else if(2.54 <= $precipIntensity && $precipIntensity < 10.16){
                    tv.setText("Moderate");
                }
                else if(10.16 <= $precipIntensity){
                    tv.setText("Heavy");
                }
            }
        }

    }
}
