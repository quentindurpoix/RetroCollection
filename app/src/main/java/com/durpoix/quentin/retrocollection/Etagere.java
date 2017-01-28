package com.durpoix.quentin.retrocollection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Etagere extends AppCompatActivity {

    private Button btnGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etagere);

        btnGames = (Button)findViewById(R.id.buttonGames);

    }

    public void onGame(View view){

        startActivity(new Intent(this, Games.class));

    }
}
