package com.durpoix.quentin.retrocollection;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

            String req = "SELECT * FROM GAME WHERE id_game = "+GameId;
            Cursor mCur = mDb.rawQuery(req, new String[]{});
            mCur.moveToFirst();
            String nameReq= mCur.getString(mCur.getColumnIndex("name"));
            int priceReq= mCur.getInt(mCur.getColumnIndex("price"));
            int img= mCur.getInt(mCur.getColumnIndex("image"));
            name.setText(nameReq);
            price.setText(priceReq+" €");
            category.setText("La category");
            console.setText("PS4");
            if (img == 0) {
                image.setImageResource(R.drawable.space_invader);
            }else{
                image.setImageResource(img);
            }
        }


    }
}
