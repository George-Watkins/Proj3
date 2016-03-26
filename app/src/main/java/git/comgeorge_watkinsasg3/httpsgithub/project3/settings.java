package git.comgeorge_watkinsasg3.httpsgithub.project3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class settings extends Activity {

    AlertDialog levelDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView tv = (TextView) findViewById(R.id.editText);

        tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                chooseServer();
            }
        });

    }

    private void chooseServer() {
        final CharSequence[] items = {"CNU - Defender", "Pair - Tenton Software - Pets"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int item){
                switch (item){
                    case 0:
                        proj3.setURL("http://www.pcs.cnu.edu/~kperkins/pets/");
                        //code when cnu defender is selected, CHANGE THE URL IN PROJ3.CLASS
                        Toast.makeText(settings.this, "Cnu server selected", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        proj3.setURL("http://www.tetonsoftware.com/pets/pets.json");
                        //code when Pair - Tenton is selected, CHANGE THE URL IN PROJ3.CLASS
                        Toast.makeText(settings.this, "Tenton software selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                levelDialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", null);
        levelDialog = builder.create();
        levelDialog.show();
    }
}
