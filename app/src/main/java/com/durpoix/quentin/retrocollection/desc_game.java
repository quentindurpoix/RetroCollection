package com.durpoix.quentin.retrocollection;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class desc_game extends AppCompatActivity {
    TextView name;
    TextView price;
    TextView console;
    TextView category;
    ImageView image;
    int GameId;
    protected SQLiteDatabase mDb = null;
    protected Database mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_game);
        name = (TextView)findViewById(R.id.name_game);
        price = (TextView)findViewById(R.id.price_game);
        console = (TextView)findViewById(R.id.console_game);
        category = (TextView)findViewById(R.id.category_game);

        image = (ImageView) findViewById(R.id.image_game);



        mHandler = new Database(this.getApplicationContext());
        mDb = mHandler.getWritableDatabase();

        Intent AddGame = getIntent();

        if(AddGame.hasExtra("IDGame")) {
            GameId = AddGame.getIntExtra("IDGame", -1);

            String req = "SELECT GAME.name as nom_jeu,CONSOLE.name as nom_console,image,id_game,GAME.price as prix_jeu,CATEGORY.name as name_cate FROM GAME JOIN CATEGORY using(id_category) JOIN CONSOLE using(id_console) WHERE id_game = "+GameId;
            Cursor mCur = mDb.rawQuery(req, new String[]{});
            mCur.moveToFirst();
            String nameReq= mCur.getString(mCur.getColumnIndex("nom_jeu"));
            String name_cateReq= mCur.getString(mCur.getColumnIndex("name_cate"));
            String name_consReq= mCur.getString(mCur.getColumnIndex("nom_console"));
            int priceReq= mCur.getInt(mCur.getColumnIndex("prix_jeu"));
            int img= mCur.getInt(mCur.getColumnIndex("image"));
            name.setText(nameReq);
            String pricenB=priceReq+" €";
            price.setText(pricenB);
            category.setText(name_cateReq);
            console.setText(name_consReq);
            if (img == 0) {
                image.setImageResource(R.drawable.space_invader);
            }else{
                image.setImageResource(img);
            }
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.etagere, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Voulez vous vraiment supprimer cet objet ?").setTitle("Supression");
            builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent AddGame = getIntent();
                    GameId = AddGame.getIntExtra("IDGame", -1);
                    finish();
                }
            });
            builder.setNegativeButton("non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        if (id == R.id.action_modify) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
