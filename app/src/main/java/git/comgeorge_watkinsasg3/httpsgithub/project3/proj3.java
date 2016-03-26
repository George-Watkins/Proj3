package git.comgeorge_watkinsasg3.httpsgithub.project3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class proj3 extends AppCompatActivity {

    Spinner spinner;

    ImageView imageBackground;


    private static final String TAG = "ParseJSON";
    private static String MYURLServer = "http://www.tetonsoftware.com/pets/pets.json";
    private static String MYURLImage = "http://www.tetonsoftware.com/pets/";

    public static final int MAX_LINES = 15;
    private static final int SPACES_TO_INDENT_FOR_EACH_LEVEL_OF_NESTING = 2;

    JSONObject jsonObject;
    JSONArray jsonArray;
    ArrayList<String> petList = new ArrayList<String>();

    int numberEntries = -1;
    int currentEntry = -1;
    SharedPreferences myPreference;
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    DownloadImage imageDownload;
    ImageView backgroundImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj3);
        imageDownload = new DownloadImage();
        backgroundImage = (ImageView)findViewById(R.id.backgroundImage);

        myPreference = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                //this is supposed to be where the load image call goes.
                //Tried to test it using visible and invisible calls but did not work
                if (key.equals("listPref")) {
//                    loadImage();
                    String str = myPreference.getString("listPref", "Nothing");
                    if(str.equals("Winston")){
                        imageDownload = new DownloadImage();
                        imageDownload.execute(MYURLImage + "p0.png");
                        backgroundImage.setVisibility(View.INVISIBLE);
                    }
                    else if(str.equals("Higgens")){
                        imageDownload = new DownloadImage();
                        imageDownload.execute(MYURLImage + "p1.png");
                        backgroundImage.setVisibility(View.VISIBLE);
                    }
                    else if(str.equals("Broccoli")){
                        imageDownload = new DownloadImage();
                        imageDownload.execute(MYURLImage + "p2.png");
                        backgroundImage.setVisibility(View.INVISIBLE);
                    }
                }
            }
        };
        myPreference.registerOnSharedPreferenceChangeListener(listener);

        ConnectivityCheck myCheck = new ConnectivityCheck(this);
        if (myCheck.isNetworkReachableAlertUserIfNot()) {
            DownloadTask myTask = new DownloadTask(this);
            myTask.setnameValuePair("name 1", "value 1");
            myTask.setnameValuePair("name 2", "value 2");
            myTask.setnameValuePair("name 3", "value 3");

            myTask.execute(MYURLServer);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addItemsOnSpinner();
//        loadImage();
    }

    private void loadImage() {
        imageBackground = (ImageView)findViewById(R.id.backgroundImage);

        try{
            //CONVERT THE URL TO A BITMAP TO A DRAWABLE
            //so it can be made the background of textView backgroundImage
            //would this be just a call to DownloadImageTask?

            URL firstImage = new URL(MYURLImage + getJSONFile(0));
            URLConnection conn = firstImage.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bm = BitmapFactory.decodeStream(bis);

            Drawable d = new BitmapDrawable(bm);

            bis.close();
            is.close();

            imageBackground.setBackground(d);
        }
        catch(Exception e){
            e.printStackTrace();
        }


        for(int i = 0; i < jsonArray.length() - 1; i++){
            getJSONFile(i);
        }
    }


    private void addItemsOnSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String item = "nothing";
//
//                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Winston"))
//                    item = parent.getItemAtPosition(position).toString();
//                else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Higgens"))
//                    item = parent.getItemAtPosition(position).toString();
//                else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Broccoli"))
//                    item = parent.getItemAtPosition(position).toString();
//
//                Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, petList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proj3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, settings.class));
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void processJSON(String string) {
        try {
            JSONObject jsonobject = new JSONObject(string);
            jsonArray = jsonobject.getJSONArray("pets");

            numberEntries = jsonArray.length();

            currentEntry = 0;

            while(currentEntry < numberEntries){
                setJSONUI(currentEntry);
                currentEntry++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setJSONUI(int i) {
        if (jsonArray == null) {
            return;
        }

        try {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            petList.add(jsonObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getJSONFile(int i){
        if(jsonArray == null){
            return null;
        }

        try{
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            return(jsonObject.getString("file"));
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void setURL(String url){
        MYURLServer = url;
    }
}
