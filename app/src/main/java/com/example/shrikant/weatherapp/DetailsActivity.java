package com.example.shrikant.weatherapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by shrikant on 9/12/15.
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        Intent i=getIntent();
        String result= i.getStringExtra("result");
        String city=i.getStringExtra("city");
        String state=i.getStringExtra("state");
        String temperature=i.getStringExtra("temperature");
        String jsonFormattedString = result.replaceAll("\\\\", "");
        String[] keyvalue=jsonFormattedString.split(",");
        //System.out.println(keyvalue);
        TextView t1=(TextView)findViewById(R.id.tv);
        if(temperature.equalsIgnoreCase("si")){
            t1.setText("Temp"+ Html.fromHtml("(<sup>\u00b0</sup>C)"));
        }else{
            t1.setText("Temp"+Html.fromHtml("(<sup>\u00b0</sup>F)"));
        }

        TextView t=(TextView)findViewById(R.id.textView28);
        t.setText("More Details for " + city + ", " + state);
        final TabHost mTabHost = (TabHost)findViewById(R.id.tabHost);

        mTabHost.setup();
        TabHost.TabSpec spec = mTabHost.newTabSpec("tab1").setIndicator("Next 24 hours").setContent(R.id.linearLayout);
        mTabHost.addTab(spec);
        spec=mTabHost.newTabSpec("tab2").setIndicator("Next 7 days").setContent(R.id.linearLayout2);
        mTabHost.addTab(spec);
        //TextView tv =(TextView)findViewById(R.id.tv);

        currently(keyvalue,temperature);
        View view=mTabHost.getCurrentTabView();
        view.setBackgroundColor(Color.parseColor("#1E90FF"));
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setTabColor(mTabHost);
            }
        });
    }
    public static void setTabColor(TabHost tabhost) {
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++) {
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF")); //unselected
        }
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#1E90FF")); // selected
    }

    public void currently(String[] objectype, String temperature){
        //System.out.println(objectype.length);
        final TableLayout ll = (TableLayout) findViewById(R.id.table1);
        int startindexofHourly=0;
        int closeindexofHourly=0;
        for(int i=0;i<objectype.length;i++) {
            if(objectype[i].contains("hourly")){
                startindexofHourly=i+2;
                //System.out.println(startindexofHourly);
            }
            if(objectype[i].contains("daily")){

                closeindexofHourly=i-1;
                //System.out.println("daily index: "+i);
            }

        }
        String[] currently= Arrays.copyOfRange(objectype, startindexofHourly, closeindexofHourly);
        String[] daily=Arrays.copyOfRange(objectype,closeindexofHourly+4,objectype.length);
        DisplayHours(daily,temperature);
        int k=0;
        int l=0;
        int m=0;
        final String [] icons=new String[100];
        final String [] time= new String[100];
        final String [] temp=new String[100];
        for(int i=0;i<currently.length;i++) {
            String pair=currently[i];
            pair=pair.replaceAll("\"","");
            String[] j=pair.split(":");
            //System.out.println(j[0]);
            if(j[0].contains("icon")){
                icons[k]=j[1];
                k++;
            }
            if(j[0].contains("time")){
                //TextView tvv=(TextView)findViewById(R.id.tv1);
                time[l] = getTime(j[1]);
                l++;
                //tvv.setText(date);
            }
            if(j[0].contains("temperature")){
                //TextView tv1=(TextView)findViewById(R.id.tv2);
                //tv1.setText(String.valueOf((int) Float.parseFloat(j[1].trim())));
                temp[m]=String.valueOf((int) Float.parseFloat(j[1].trim()));
                m++;
            }
                //ystem.out.println(i+" ) "+j[0]);
        }

        int z=1;
        for (int i=0;i<24;i++){
            //int z=1;
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            if(i%2==0) {
                row.setBackgroundColor(Color.parseColor("#808080"));
            }
            row.setLayoutParams(lp);
            //checkBox = new CheckBox(this);
            TextView tv = new TextView(this);
            tv.setText(temp[i]);
            ImageView img =new ImageView(this);
            //img.setCropToPadding(true);
            img.setAdjustViewBounds(true);
            img.setMaxHeight(80);
            img.setMaxWidth(80);
            //img.setMaxHeight(5);
            if(("clear-day").contains(icons[i])){
                    //System.out.println("hahaha");
                img.setImageResource(R.drawable.clear);
            }else if(("clear-night").contains(icons[i])){
                img.setImageResource(R.drawable.clear_night);
            }else if(("rain").contains(icons[i])){
                img.setImageResource(R.drawable.rain);
            }else if(("snow").contains(icons[i])){
                img.setImageResource(R.drawable.snow);
            }else if(("sleet").contains(icons[i])){
                img.setImageResource(R.drawable.sleet);
            }else if(("wind").contains(icons[i])){
                img.setImageResource(R.drawable.wind);
            }else if (("fog").contains(icons[i])){
                img.setImageResource(R.drawable.fog);
            }else if(("cloudy").contains(icons[i])){
                //img.setImageBitmap(R.drawable);
                img.setImageResource(R.drawable.cloudy);
            }else if (("partly-cloudy-day").contains(icons[i])){
                img.setImageResource(R.drawable.cloud_day);
            }else if(("partly-cloudy-night").contains(icons[i])){
                    //System.out.println("ahahahaaha");
                img.setImageResource(R.drawable.cloud_night);
            }
            TextView qty = new TextView(this);
            qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            //checkBox.setText("hello");
            qty.setText(time[i]);
            //row.addView(checkBox);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            //row.addView(minusBtn);
            //img.setForegroundGravity(Gravity.RIGHT);
            if(i==23){
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        next24hours(icons,time,temp);
                    }
                });
            }

            tv.setGravity(Gravity.RIGHT);
            row.addView(qty);
            row.addView(img);
            row.addView(tv);
            ll.addView(row,z);
            z++;
        }

    }

    private void DisplayHours(String[] daily,String temperature) {
        String[] icons=new String[18];
        String[] tempmax=new String[18];
        String[] time=new String[18];
        String[] tempmin=new String[18];
        int k=0;
        int l=1;
        int m=0;
        int n=0;
        int lastIndexofDaily=0;
        time[0]="";
        for(int i=0;i<daily.length&&lastIndexofDaily==0;i++){
            if(daily[i].contains("}]}")){
                lastIndexofDaily=i;
                //System.out.println(i);
            }
        }
        daily=Arrays.copyOfRange(daily,0,lastIndexofDaily+1);
        final TableLayout ll = (TableLayout) findViewById(R.id.table2);
        for(int i=0;i<daily.length && l<9&& m<9&& k<9;i++) {
            String pair=daily[i];
            pair=pair.replaceAll("\"","");
            String[] j=pair.split(":");
            //System.out.println(daily[i]);
            if(j[0].contains("icon")){
                icons[k]=j[1];
                k++;
            }
            if(j[0].contains("time")){
                //TextView tvv=(TextView)findViewById(R.id.tv1);

                time[l] = getDays(j[1]);
                l++;
                //tvv.setText(date);
            }
            if(("temperatureMax").contains(j[0])){
                //TextView tv1=(TextView)findViewById(R.id.tv2);
                //tv1.setText(String.valueOf((int) Float.parseFloat(j[1].trim())));
                tempmax[m]=String.valueOf((int) Float.parseFloat(j[1].trim()));
                m++;
            }
            if(("temperatureMin").contains(j[0])){
                //TextView tv1=(TextView)findViewById(R.id.tv2);
                //tv1.setText(String.valueOf((int) Float.parseFloat(j[1].trim())));
                tempmin[n]=String.valueOf((int) Float.parseFloat(j[1].trim()));
                n++;
            }

        }

        for (int i=1;i<8;i++){
            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);

            int leftMargin=20;
            int topMargin=0;
            int rightMargin=20;
            int bottomMargin=50;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            String s="";
            TableRow row= new TableRow(this);
            if(i==1) {
                row.setBackgroundColor(Color.parseColor("#FFCC00"));
            }else if(i==2){
                row.setBackgroundColor(Color.parseColor("#00CCFF"));
            }
            else if(i==3){
                row.setBackgroundColor(Color.parseColor("#ff66ff"));
            }
            else if(i==4){
                row.setBackgroundColor(Color.parseColor("#66ff66"));
            }
            else if(i==5){
                row.setBackgroundColor(Color.parseColor("#FF6666"));
            }
            else if(i==6){
                row.setBackgroundColor(Color.parseColor("#FFFF99"));
            }
            else if(i==7){
                row.setBackgroundColor(Color.parseColor("#9999ff"));
            }
            row.setLayoutParams(tableRowParams);
            //checkBox = new CheckBox(this);
            //Text//View tv = new TextView(this);
            if(temperature.equalsIgnoreCase("si")) {
                s="Min: " + tempmin[i] + Html.fromHtml(" <sup>\u00b0</sup>C") + " | Max: " + tempmax[i] + Html.fromHtml(" <sup>\u00b0</sup>C");
            }else{
                s="Min: " + tempmin[i] + Html.fromHtml(" <sup>\u00b0</sup>F") + " | Max: " + tempmax[i] + Html.fromHtml(" <sup>\u00b0</sup>F");
            }//ImageView big=new ImageView(this);
            ImageView img =new ImageView(this);
            img.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            img.setAdjustViewBounds(true);
            img.setMaxHeight(80);
            img.setMaxWidth(80);

            if(("clear-day").contains(icons[i])){
                //System.out.println("hahaha");
                img.setImageResource(R.drawable.clear);
            }else if(("clear-night").contains(icons[i])){
                img.setImageResource(R.drawable.clear_night);
            }else if(("rain").contains(icons[i])){
                img.setImageResource(R.drawable.rain);
            }else if(("snow").contains(icons[i])){
                img.setImageResource(R.drawable.snow);
            }else if(("sleet").contains(icons[i])){
                img.setImageResource(R.drawable.sleet);
            }else if(("wind").contains(icons[i])){
                img.setImageResource(R.drawable.wind);
            }else if (("fog").contains(icons[i])){
                img.setImageResource(R.drawable.fog);
            }else if(("cloudy").contains(icons[i])){
                //img.setImageBitmap(R.drawable);
                img.setImageResource(R.drawable.cloudy);
            }else if (("partly-cloudy-day").contains(icons[i])){
                img.setImageResource(R.drawable.cloud_day);
            }else if(("partly-cloudy-night").contains(icons[i])){
                //System.out.println("ahahahaaha");
                img.setImageResource(R.drawable.cloud_night);
            }
            TextView qty = new TextView(this);
            qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            //checkBox.setText("hello");
            qty.setText(time[i]+"\n"+s);
            //qty.setGravity(Gravity.CENTER);
            qty.setPadding(0,0,200,0);
            row.addView(qty);
            row.addView(img);
            //row.addView(tv);
            ll.addView(row,i-1);
        }

    }

    public String getDays(String s) {
        long j1= Long.parseLong(s);
        Date time=new Date((long)j1*1000);
        //System.out.println(time.toString());
        SimpleDateFormat simpleDateformatter = new SimpleDateFormat("EEEE, dd");
        String convertedDate = simpleDateformatter .format(time);
        //System.out.println(convertedDate);
        return convertedDate;

    }

    public void next24hours(String[] icons,String[] time,String[] temp){
        TableLayout ll = (TableLayout) findViewById(R.id.table1);
        for (int i=24;i<48;i++){
            //int z=1;
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            if(i%2==0) {
                row.setBackgroundColor(Color.parseColor("#808080"));
            }
            row.setLayoutParams(lp);
            //checkBox = new CheckBox(this);
            TextView tv = new TextView(this);
            tv.setText(temp[i]);
            ImageView img =new ImageView(this);
            img.setAdjustViewBounds(true);
            img.setMaxHeight(80);
            img.setMaxWidth(80);
            //img.setMaxHeight(5);
            if(("clear-day").contains(icons[i])){
                //System.out.println("hahaha");
                img.setImageResource(R.drawable.clear);
            }else if(("clear-night").contains(icons[i])){
                img.setImageResource(R.drawable.clear_night);
            }else if(("rain").contains(icons[i])){
                img.setImageResource(R.drawable.rain);
            }else if(("snow").contains(icons[i])){
                img.setImageResource(R.drawable.snow);
            }else if(("sleet").contains(icons[i])){
                img.setImageResource(R.drawable.sleet);
            }else if(("wind").contains(icons[i])){
                img.setImageResource(R.drawable.wind);
            }else if (("fog").contains(icons[i])){
                img.setImageResource(R.drawable.fog);
            }else if(("cloudy").contains(icons[i])){
                //img.setImageBitmap(R.drawable);
                img.setImageResource(R.drawable.cloudy);
            }else if (("partly-cloudy-day").contains(icons[i])){
                img.setImageResource(R.drawable.cloud_day);
            }else if(("partly-cloudy-night").contains(icons[i])){
                //System.out.println("ahahahaaha");
                img.setImageResource(R.drawable.cloud_night);
            }
            TextView qty = new TextView(this);
            qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            //checkBox.setText("hello");
            qty.setText(time[i]);
            //row.addView(checkBox);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            tv.setGravity(Gravity.RIGHT);
            row.addView(qty);
            row.addView(img);
            row.addView(tv);
            ll.addView(row,i+1);

        }
    }

    public String getTime(String j){
        //System.out.println(j);
        long j1= Long.parseLong(j);
        Date time=new Date((long)j1*1000);
        //System.out.println(time.toString());
        SimpleDateFormat simpleDateformatter = new SimpleDateFormat("hh:mm a");
        String convertedDate = simpleDateformatter .format(time);
        //System.out.println(convertedDate);
        return convertedDate;
    }
}
