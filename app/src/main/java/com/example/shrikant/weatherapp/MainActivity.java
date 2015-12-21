package com.example.shrikant.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hamweather.aeris.communication.AerisEngine;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] stateAbbr = new String[52];
        String[] stateNames = new String[52];

        int stateCount = 0;
        stateAbbr[stateCount ] = "";
        stateNames[stateCount ++] = "Select your state...";

        stateAbbr[stateCount]= "AL";
        stateNames[stateCount++] = "Alabama";
        stateAbbr[stateCount]= "AK";
        stateNames[stateCount++] = "Alaska";
        stateAbbr[stateCount]= "AZ";
        stateNames[stateCount++] = "Arizona";
        stateAbbr[stateCount]= "AR";
        stateNames[stateCount++] = "Arkansas";
        stateAbbr[stateCount]= "CA";
        stateNames[stateCount++] = "California";
        stateAbbr[stateCount]= "CO";
        stateNames[stateCount++] = "Colorado";
        stateAbbr[stateCount]= "CT";
        stateNames[stateCount++] = "Connecticut";
        stateAbbr[stateCount]= "DE";
        stateNames[stateCount++] = "Delaware";
        stateAbbr[stateCount]= "FL";
        stateNames[stateCount++] = "Florida";
        stateAbbr[stateCount]= "GA";
        stateNames[stateCount++] = "Georgia";
        stateAbbr[stateCount]= "HI";
        stateNames[stateCount++] = "Hawaii";
        stateAbbr[stateCount]= "ID";
        stateNames[stateCount++] = "Idaho";
        stateAbbr[stateCount]= "IA";
        stateNames[stateCount++] = "Iowa";
        stateAbbr[stateCount]= "IL";
        stateNames[stateCount++] = "Illinois";
        stateAbbr[stateCount]= "IN";
        stateNames[stateCount++] = "Indiana";
        stateAbbr[stateCount]= "KS";
        stateNames[stateCount++] = "Kansas";
        stateAbbr[stateCount]= "KY";
        stateNames[stateCount++] = "Kentucky";
        stateAbbr[stateCount]= "LA";
        stateNames[stateCount++] = "Louisiana";
        stateAbbr[stateCount]= "ME";
        stateNames[stateCount++] = "Maine";
        stateAbbr[stateCount]= "MD";
        stateNames[stateCount++] = "Maryland";
        stateAbbr[stateCount]= "MA";
        stateNames[stateCount++] = "Massachusetts";
        stateAbbr[stateCount]= "MI";
        stateNames[stateCount++] = "Michigan";
        stateAbbr[stateCount]= "MN";
        stateNames[stateCount++] = "Minnesota";
        stateAbbr[stateCount]= "MS";
        stateNames[stateCount++] = "Mississippi";
        stateAbbr[stateCount]= "MO";
        stateNames[stateCount++] = "Missouri";
        stateAbbr[stateCount]= "MT";
        stateNames[stateCount++] = "Montana";
        stateAbbr[stateCount]= "NE";
        stateNames[stateCount++] = "Nebraska";
        stateAbbr[stateCount]= "NV";
        stateNames[stateCount++] = "Nevada";
        stateAbbr[stateCount]= "NH";
        stateNames[stateCount++] = "New Hampshire";
        stateAbbr[stateCount]= "NJ";
        stateNames[stateCount++] = "New Jersey";
        stateAbbr[stateCount]= "NM";
        stateNames[stateCount++] = "New Mexico";
        stateAbbr[stateCount]= "NY";
        stateNames[stateCount++] = "New York";
        stateAbbr[stateCount]= "NC";
        stateNames[stateCount++] = "North Carolina";
        stateAbbr[stateCount]= "ND";
        stateNames[stateCount++] = "North Dakota";
        stateAbbr[stateCount]= "OH";
        stateNames[stateCount++] = "Ohio";
        stateAbbr[stateCount]= "OK";
        stateNames[stateCount++] = "Oklahoma";
        stateAbbr[stateCount]= "OR";
        stateNames[stateCount++] = "Oregon";
        stateAbbr[stateCount]= "PA";
        stateNames[stateCount++] = "Pennsylvania";
        stateAbbr[stateCount]= "RI";
        stateNames[stateCount++] = "Rhode Island";
        stateAbbr[stateCount]= "SC";
        stateNames[stateCount++] = "South Carolina";
        stateAbbr[stateCount]= "SD";
        stateNames[stateCount++] = "South Dakota";
        stateAbbr[stateCount]= "TN";
        stateNames[stateCount++] = "Tennessee";
        stateAbbr[stateCount]= "TX";
        stateNames[stateCount++] = "Texas";
        stateAbbr[stateCount]= "UT";
        stateNames[stateCount++] = "Utah";
        stateAbbr[stateCount]= "VT";
        stateNames[stateCount++] = "Vermont";
        stateAbbr[stateCount]= "VA";
        stateNames[stateCount++] = "Virginia";
        stateAbbr[stateCount]= "WA";
        stateNames[stateCount++] = "Washington";
        stateAbbr[stateCount]= "WV";
        stateNames[stateCount++] = "West Virgina";
        stateAbbr[stateCount]= "WI";
        stateNames[stateCount++] = "Wisconsin";
        stateAbbr[stateCount]= "WY";
        stateNames[stateCount++] = "Wyoming";

        ImageView imgFavorite = (ImageView) findViewById(R.id.imageView1);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://forecast.io"));
                startActivity(intent);
            }
        });

        final Spinner dropdown = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stateNames);
        dropdown.setAdapter(adapter);
        final RadioGroup radiogroup=(RadioGroup)findViewById(R.id.radio);
        final TextView error1= (TextView)findViewById(R.id.textView4);
        final RadioButton degree = (RadioButton) findViewById(R.id.radioButton);
        final EditText editTextStreet = (EditText) findViewById(R.id.editText2);
        final Button button = (Button) findViewById(R.id.button2);
        final EditText editTextCity = (EditText) findViewById(R.id.editText3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                editTextStreet.setText("");

                //EditText editTextCity = (EditText) findViewById(R.id.editText3);
                editTextCity.setText("");

                //Spinner spinner=(Spinner)findViewById(R.id.spinner2);
                dropdown.setSelection(0);

                degree.toggle();
                //RadioGroup rg =(RadioGroup)findViewById(R.id.);
                error1.setText("");
            }
        });

        final Button search = (Button) findViewById(R.id.button);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                int flag=1;
                error1.setText("");
                String street= editTextStreet.getText().toString();
                if(flag==1 && street.trim().isEmpty()){
                    //TextView error1= (TextView)findViewById(R.id.textView4);
                    error1.setText("Please enter a Street");
                    flag=0;
                    //Toast.makeText(getApplicationContext(),"Empty Street Address!", Toast.LENGTH_SHORT).show();
                }
                if(flag==1 && editTextCity.getText().toString().trim().isEmpty()){
                    flag=0;
                    error1.setText("Please enter a city");
                    //Toast.makeText(getApplicationContext(),"Empty City!", Toast.LENGTH_SHORT).show();
                }
                if(flag==1 && dropdown.getSelectedItemId()==0){
                    flag=0;
                    error1.setText("Please select a state");
                    //Toast.makeText(getApplicationContext(),"Empty State Address!", Toast.LENGTH_SHORT).show();
                }
                if(flag==1){


                    String abbr = new String();
                    String temperature="us";
                    abbr=stateAbbr[(int)dropdown.getSelectedItemId()];
                    //output(v);
                    int selected=radiogroup.getCheckedRadioButtonId();
                    RadioButton rd=(RadioButton)findViewById(selected);
                    //System.out.println(rd.getText().toString());
                    if(rd.getText().toString().equals("Celsius")){

                        temperature="si";
                    }
                    //System.out.println(temperature);
                    String city =editTextCity.getText().toString();
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("street",street);
                    intent.putExtra("city",city);
                    intent.putExtra("state",abbr);
                    intent.putExtra("temperature",temperature);
                    startActivity(intent);

                }

                //Toast.makeText(getApplicationContext(),"Searching", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void about(View view)
    {
        Intent intent = new Intent(MainActivity.this, About.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
