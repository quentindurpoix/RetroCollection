package com.durpoix.quentin.retrocollection;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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

public class Etagere extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listview;
    protected SQLiteDatabase mDb = null;
    protected Database mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mHandler = new Database(this.getApplicationContext());

        mDb = mHandler.getWritableDatabase();
        mHandler.cleaning(mDb);
        initialisationDb(mDb);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etagere);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        majListview();

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent retour = new Intent(view.getContext(),desc_game.class);
                        int id_game = (int) id;
                        retour.putExtra("IDGame",id_game);
                        setResult(Activity.RESULT_OK, retour);
                        startActivityForResult(retour,2);

                    }
                }
        );

        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here
                startActivityForResult(new Intent(v.getContext(),AddGame.class),3);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        MenuItem nav_login = menu.findItem(R.id.nav_login);
        Session session = new Session(this);
        if(!session.getusename().equals("")){
            nav_login.setTitle(session.getusename());
        }else{
            nav_login.setTitle("Login");
        }

    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_login) {

            Session session = new Session(this);
            if(session.getusename().equals("")) {
                Intent retour = new Intent(this, connexion.class);
                setResult(Activity.RESULT_OK, retour);
                startActivityForResult(retour,1);
            }else{
                Intent retour = new Intent(this, Account.class);
                setResult(Activity.RESULT_OK, retour);
                startActivityForResult(retour,1);

            }

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
        if(data != null) {
            switch (requestCode) {
                case 1:
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    Menu menu = navigationView.getMenu();
                    MenuItem nav_login = menu.findItem(R.id.nav_login);
                    Session session = new Session(this);
                    if (!session.getusename().equals("")) {
                        nav_login.setTitle(session.getusename());
                    } else {
                        nav_login.setTitle("Login");
                    }break;

                case 2:

                    String rea = "";
                    if (data.hasExtra("action")) {
                        rea = data.getStringExtra("action");
                    }

                    if (rea.equals("delete")) {
                        majListview();
                        Toast a = Toast.makeText(this, "jeu supprimé !", Toast.LENGTH_SHORT);
                        a.show();
                    }
                    break;
                case 3:
                    if (resultCode == RESULT_OK) {
                        majListview();
                        Toast t = Toast.makeText(this, "jeu ajouté", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.CENTER, 0, -100); // décalé du centre
                        t.show();
                    }
                    break;
                case 91:
                    if (resultCode == RESULT_OK) {

                        Toast t = Toast.makeText(this, " changed in ", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.CENTER, 0, -100); // décalé du centre
                        t.show();
                    }
                    break;
            }
        }
    }


    private void majListview(){

        listview = (ListView) findViewById(R.id.listView);
        String mQuery = "SELECT GAME.name as nom_jeu,CONSOLE.name as nom_console,image,id_game as _id,GAME.price as prix_jeu,CATEGORY.name as name_cate FROM GAME JOIN CATEGORY using(id_category) JOIN CONSOLE using(id_console)";
        Cursor mCur = mDb.rawQuery(mQuery, new String[]{});
        MonAdaptateurDeListeCursor adapter = new MonAdaptateurDeListeCursor(Etagere.this,mCur);
        listview.setAdapter(adapter);

    }



    private void initialisationDb(SQLiteDatabase mDb){
        String verifReq ="SELECT COUNT(*) FROM GAME";
        Cursor mCursor = mDb.rawQuery(verifReq,null);
        mCursor.moveToFirst();
        int count= mCursor.getInt(0);
        Log.i("Nb ligne",count+"");
        if(count == 0){
           String jeuTest="INSERT INTO GAME(name,id_console,id_category,price) VALUES (\"Resident Evil VII\",1,1,40.0)";//,"+R.drawable.jeu1+")";
            mDb.execSQL(jeuTest);
            jeuTest="INSERT INTO GAME(name,id_console,id_category,price) VALUES (\"DOOM\",1,2,30.0)";//,"+R.drawable.jeu2+")";
            mDb.execSQL(jeuTest);
            jeuTest="INSERT INTO GAME(name,id_console,id_category,price) VALUES (\"Battlefield 1\",1,4,40.0)";//,"+R.drawable.jeu3+")";
            mDb.execSQL(jeuTest);
            jeuTest="INSERT INTO GAME(name,id_console,id_category,price) VALUES (\"Oddworld : L'Exode d'Abe\",2,3,20.0)";//,"+R.drawable.jeu4+")";
            mDb.execSQL(jeuTest);
            jeuTest="INSERT INTO GAME(name,id_console,id_category,price) VALUES (\"Oddworld : L'Odyssée de Munch\",2,3,15.0)";
            mDb.execSQL(jeuTest);
            String console_test= "INSERT INTO CONSOLE(name,price,nb_contro) VALUES (\"Playstation 4\",300.00,2)";
            mDb.execSQL(console_test);
            console_test= "INSERT INTO CONSOLE(name,price,nb_contro) VALUES (\"Playstation one\",30.00,3)";
            mDb.execSQL(console_test);
            console_test= "INSERT INTO CONSOLE(name,price,nb_contro) VALUES (\"Super Nintendo\",50.00,2)";
            mDb.execSQL(console_test);
            String category_test="INSERT INTO CATEGORY(name) VALUES (\"Survival Horror\")";
            mDb.execSQL(category_test);
            category_test="INSERT INTO CATEGORY(name) VALUES (\"Shoot'em up\")";
            mDb.execSQL(category_test);
            category_test="INSERT INTO CATEGORY(name) VALUES (\"Plates-formes\")";
            mDb.execSQL(category_test);
            category_test="INSERT INTO CATEGORY(name) VALUES (\"First Person Shooter\")";
            mDb.execSQL(category_test);
        }
        mCursor.close();
    }

}
