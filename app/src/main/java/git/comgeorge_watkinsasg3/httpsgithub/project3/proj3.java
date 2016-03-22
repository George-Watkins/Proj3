package git.comgeorge_watkinsasg3.httpsgithub.project3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

public class proj3 extends AppCompatActivity {

    Spinner spinner;
    private static final String TAG = "ParseJSON";
    private static final String MYURL = "http://www.tetonsoftware.com/pets/pets.json";

    public static final int MAX_LINES = 15;
    private static final int SPACES_TO_INDENT_FOR_EACH_LEVEL_OF_NESTING = 2;

    JSONArray jsonArray;
    int numberEntries = -1;
    int currentEntry = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj3);

        ConnectivityCheck myCheck = new ConnectivityCheck(this);
        if(myCheck.isNetworkReachableAlertUserIfNot()){
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

    private void addItemsOnSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = "nothing";

                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("one 1"))
                    item = parent.getItemAtPosition(position).toString();
                else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("two 2"))
                    item = parent.getItemAtPosition(position).toString();
                else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("three 3"))
                    item = parent.getItemAtPosition(position).toString();
                else {
                    //do other things
                }

                Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> list = new ArrayList<String>();
        list.add("one 1");
        list.add("Two 2");
        list.add("Three 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
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

    public void processJSON(String string){
        try{
            JSONObject jsonobject = new JSONObject(string);
            jsonArray = jsonobject.getJSONArray("pets");

            numberEntries = jsonArray.length();

            currentEntry = 0;
            setJSONUI(currentEntry);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setJSONUI(int i){
        if (jsonArray == null){
            return;
        }

        try{
            JSONObject jsonObject = jsonArray.getJSONObject(i);

        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
