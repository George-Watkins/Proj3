package git.comgeorge_watkinsasg3.httpsgithub.project3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class proj3 extends AppCompatActivity {

    Spinner spinner;

    private static final String TAG = "ParseJSON";
    private static String MYURL = "http://www.tetonsoftware.com/pets/pets.json";

    public static final int MAX_LINES = 15;
    private static final int SPACES_TO_INDENT_FOR_EACH_LEVEL_OF_NESTING = 2;

    JSONObject jsonObject;
    JSONArray jsonArray;
    ArrayList<String> petList = new ArrayList<String>();

    int numberEntries = -1;
    int currentEntry = -1;
    SharedPreferences myPreference;
    SharedPreferences.OnSharedPreferenceChangeListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj3);

        myPreference = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.equals("listPref")) {
                    loadImage();
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

            myTask.execute(MYURL);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addItemsOnSpinner();
    }

    private void loadImage() {

        try{
            //CONVERT THE URL TO A BITMAP TO A DRAWABLE
            //so it can be made the background of textView backgroundImage
            //would this be just a call to DownloadImageTask?

            //need to find some way of getting the file name from the server
            //and then concatinating it with the current URL

            URL firstImage = new URL(MYURL + FILE_NAME);
            URLConnection conn = firstImage.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();

            Drawable d = new BitmapDrawable(bm);

            TextView background = (TextView) findViewById(R.id.backgroundImage);
            background.setBackground(d);
        }
        catch(Exception e){
            e.printStackTrace();
        }


        for(int i = 0; i < jsonArray.length(); i++){
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

    private void getJSONFile(int i){
        if(jsonArray == null){
            return;
        }

        try{
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            petList.add(jsonObject.getString("file"));
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public static void setURL(String url){
        MYURL = url;
    }
}
