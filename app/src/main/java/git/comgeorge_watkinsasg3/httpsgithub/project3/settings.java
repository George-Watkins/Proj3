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
import android.widget.Toast;

import java.util.ArrayList;

public class settings extends Activity {

    private RadioGroup radioServerGroup;
    private RadioButton radioServerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        addListener();
    }

    private void addListener() {
        radioServerGroup = (RadioGroup) findViewById(R.id.radioServer);

        radioServerGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioServerGroup.getCheckedRadioButtonId();
                Toast.makeText(settings.this, radioServerButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
