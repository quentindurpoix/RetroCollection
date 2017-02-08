package com.durpoix.quentin.retrocollection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Etagere extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listview;
    private Game jeu1;
    private Game jeu2;
    private Game jeu3;
    private Game jeu4;
    private Game jeu5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etagere);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        listview = (ListView) findViewById(R.id.listView);


        List<Game> listeJeux = genererJeux();
        MonAdaptateurDeListe adapter = new MonAdaptateurDeListe(Etagere.this, listeJeux);


        listview.setAdapter(adapter);

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Game changeGame = (Game) parent.getItemAtPosition(position);

                        Intent retour = new Intent(view.getContext(),AddGame.class);
                        retour.putExtra("ChangeGame",changeGame.getName());
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.etagere, menu);
        return true;
    }
*/
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    Game changedGame=(Game)listview.getItemAtPosition(resPos);
                    changedGame.setName(res);
                    String ChangedGame = changedGame.getName();
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
