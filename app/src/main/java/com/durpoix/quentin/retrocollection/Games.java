package com.durpoix.quentin.retrocollection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Games extends AppCompatActivity {
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        listview = (ListView) findViewById(R.id.listView);

       listview.setOnItemClickListener(
               new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                       String ChangeGame=(String)parent.getItemAtPosition(position);

                       Intent retour = new Intent(view.getContext(),AddGame.class);
                       retour.putExtra("ChangeGame",ChangeGame);
                       retour.putExtra("ChangeGamePosition",position);
                       setResult(Activity.RESULT_OK, retour);
                       startActivityForResult(retour,91);

                   }
               }
       );

        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here
                startActivityForResult(new Intent(v.getContext(),AddGame.class),90);
            }
        });


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 90:
                if (resultCode == RESULT_OK) {
                    String res = data.getStringExtra("NameGame");

                    Toast t = Toast.makeText(this, res, Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,-100); // décalé du centre
                    t.show();
                }
                break;
            case 91:
                if (resultCode == RESULT_OK) {
                    String res = data.getStringExtra("NameGame");
                    int resPos = data.getIntExtra("ChangeGamePositon",-1);
                    String ChangedGame=(String)listview.getItemAtPosition(resPos);

                    Toast t = Toast.makeText(this, ChangedGame+" changed in "+res, Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,-100); // décalé du centre
                    t.show();
                }
                break;
        }
    }

}
