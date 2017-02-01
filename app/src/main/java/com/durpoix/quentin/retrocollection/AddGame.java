package com.durpoix.quentin.retrocollection;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddGame extends AppCompatActivity {
    EditText champ;
    int ChangeGamePosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        champ = (EditText)findViewById(R.id.editTextAddNameGame);

        Intent AddGame = getIntent();
        if(AddGame.hasExtra("ChangeGamePosition")) {
            ChangeGamePosition = AddGame.getIntExtra("ChangeGamePosition", -1);
        }
        if(AddGame.hasExtra("ChangeGame")) {

            String ChangeGameValue = AddGame.getStringExtra("ChangeGame");


            if (!ChangeGameValue.equals("")) {
                champ.setText(ChangeGameValue);
            }
        }

    }




    public void AddGame(View view) {
        Intent retour = new Intent();
        if(ChangeGamePosition!=-1){
            retour.putExtra("ChangeGamePositon",ChangeGamePosition);
        }
        retour.putExtra("NameGame",champ.getText().toString());
        setResult(Activity.RESULT_OK, retour);
        finish();
    }


}
