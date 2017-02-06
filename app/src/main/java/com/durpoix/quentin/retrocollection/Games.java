package com.durpoix.quentin.retrocollection;

import android.app.Activity;
import android.app.ListActivity;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Games extends AppCompatActivity{
    ListView listview;
    private Game jeu1;
    private Game jeu2;
    private Game jeu3;
    private Game jeu4;
    private Game jeu5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jeu1 = new Game("Resident Evil VII","PS4", 70);
        jeu2 = new Game("DOOM","PS4", 20);
        jeu3 = new Game("Battlefield 1","PS4", 50);
        jeu4 = new Game("Oddworld : L'Exode d'Abe","PSX", 20);
        jeu5 = new Game("Oddworld : L'Odyssée de Munch","XBOX", 15);
        jeu1.setImg(R.drawable.jeu1);
        jeu2.setImg(R.drawable.jeu2);
        jeu3.setImg(R.drawable.jeu3);
        jeu4.setImg(R.drawable.jeu4);
        jeu5.setImg(R.drawable.jeu5);


        setContentView(R.layout.activity_games);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        listview = (ListView) findViewById(R.id.listView);


        List<Game> listeJeux = genererJeux();
        MonAdaptateurDeListe adapter = new MonAdaptateurDeListe(Games.this, listeJeux);


        listview.setAdapter(adapter);

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

    private List<Game> genererJeux(){
        List<Game> jeux = new ArrayList<Game>();
        jeux.add(jeu1);
        jeux.add(jeu2);
        jeux.add(jeu3);
        jeux.add(jeu4);
        jeux.add(jeu5);
        return jeux;
    }

}
