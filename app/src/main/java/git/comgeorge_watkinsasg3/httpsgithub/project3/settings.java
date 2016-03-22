package git.comgeorge_watkinsasg3.httpsgithub.project3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class settings extends Activity {

    private RadioGroup radioServerGroup;
    private RadioButton radioServerButton;
    AlertDialog levelDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView tvButton = (TextView) findViewById(R.id.editText);

        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseServer();
            }
        });
    }

    private void chooseServer() {

        final CharSequence[] items = {"CNU - Defender","Pair - Tenton Software - Pets"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int item){
                switch (item){
                    case 0:
                        //code when CNU Defender is selected
                        //Change the url code in proj3.class
                        break;
                    case 1:
                        //code when Pair - Tenton is selected
                        //change the url code in proj3.code
                        break;
                }
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    //need to add a dialog box somewhere in here
//
//    private void addListener() {
//        radioServerGroup = (RadioGroup) findViewById(R.id.radioServer);
//
//        radioServerGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedId = radioServerGroup.getCheckedRadioButtonId();
//                Toast.makeText(settings.this, radioServerButton.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
