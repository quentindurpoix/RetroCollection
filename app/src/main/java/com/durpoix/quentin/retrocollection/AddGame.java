package com.durpoix.quentin.retrocollection;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class AddGame extends AppCompatActivity {
    EditText name;
    EditText price;
    ImageButton buttonImg;
    Spinner spinner_console;
    Spinner spinner_category;

    protected SQLiteDatabase mDb = null;
    protected Database mHandler = null;

    int ChangeGamePosition;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        mHandler = new Database(this.getApplicationContext());
        mDb = mHandler.getWritableDatabase();

        name = (EditText)findViewById(R.id.input_AddGame_Name);
        price = (EditText)findViewById(R.id.input_AddGame_Prix);
        spinner_console = (Spinner) findViewById(R.id.spinner_AddGame_console);
        spinner_category = (Spinner) findViewById(R.id.spinner_AddGame_Category);
        buttonImg = (ImageButton)findViewById(R.id.imageButton_AddGame);

        String req = "SELECT id_console as _id, name FROM CONSOLE ";
        Cursor cursor_cons = mDb.rawQuery(req, new String[]{});
        String[] columns = new String[] { "name" };
        int[] adapterRowViews=new int[]{android.R.id.text1};
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor_cons,columns,adapterRowViews,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_console.setAdapter(mAdapter);

        String reqCat = "SELECT id_category as _id, name FROM CATEGORY ";
        Cursor cursor_cat = mDb.rawQuery(reqCat, new String[]{});
        String[] columnsCat = new String[] { "name" };
        int[] adapterRowViewsCat=new int[]{android.R.id.text1};
        SimpleCursorAdapter mAdapterCat = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor_cat,columnsCat,adapterRowViewsCat,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mAdapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(mAdapterCat);


        Intent AddGame = getIntent();
        if(AddGame.hasExtra("ChangeGamePosition")) {            //Si c'est une modification
            ChangeGamePosition = AddGame.getIntExtra("ChangeGamePosition", -1);
        }
        if(AddGame.hasExtra("ChangeGame")) {

            String ChangeGameValue = AddGame.getStringExtra("ChangeGame");


            if (!ChangeGameValue.equals("")) {
                //champ.setText(ChangeGameValue);
            }
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK){
                    Uri targetUri = data.getData();
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                        buttonImg.setImageBitmap(bitmap);//Bitmap.createScaledBitmap(bitmap,buttonImg.getWidth(),buttonImg.getHeight(), true));
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
        }



        }



    public void AddGame(View view) {
        Intent retour = new Intent();
        if(ChangeGamePosition!=-1){     //Si ça été une modification
            retour.putExtra("ChangeGamePositon",ChangeGamePosition);
        }
       // retour.putExtra("NameGame",champ.getText().toString());
        setResult(Activity.RESULT_OK, retour);
        finish();
    }


    public void AjouterPhoto(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    public void addGameAnul(View view) {
        finish();
    }

    public void addGameAdd(View view) {

        String nom = name.getText().toString();
        float prix = Float.valueOf(price.getText().toString());
        String console = ((Cursor)spinner_console.getSelectedItem()).getString(1).toString();

        //String verifReq ="SELECT id_console FROM CONSOLE where name = '"+console+"'";
        Cursor mCursor = mDb.query("CONSOLE",new String[] {"id_console"},"name = ?",new String[] {console},null,null,null);    //mDb.rawQuery(verifReq,null);
        int nbConsole=0;
        while (mCursor.moveToNext()) {
             nbConsole= Integer.parseInt(mCursor.getString( mCursor.getColumnIndex("id_console") ));
        }
        mCursor.close();

        String category = ((Cursor)spinner_category.getSelectedItem()).getString(1).toString();
       // verifReq ="SELECT id_category FROM CATEGORY where name = '"+category+"'";
         mCursor = mDb.query("CATEGORY",new String[] {"id_category"},"name = ?",new String[] {category},null,null,null);    //mDb.rawQuery(verifReq,null);
        int nbCategory=0;
        while (mCursor.moveToNext()) {
            nbCategory= Integer.parseInt(mCursor.getString( mCursor.getColumnIndex("id_category") ));
        }
        mCursor.close();


       // byte[] image = getBytes(bitmap);

        ContentValues cv = new  ContentValues();
        cv.put("name",nom);
        cv.put("id_console",nbConsole);
        cv.put("id_category",nbCategory);
        cv.put("price",prix);
        //cv.put("image",image);
        mDb.insert("GAME", null, cv);


        //String jeuTest="INSERT INTO GAME(name,id_console,id_category,price,image) VALUES ('"+nom+"',"+nbConsole+","+nbCategory+","+prix+","++")";
        //mDb.execSQL(jeuTest);
        Intent AddGame = new Intent();
        setResult(Activity.RESULT_OK, AddGame);
        finish();




    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
