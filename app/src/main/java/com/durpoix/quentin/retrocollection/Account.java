package com.durpoix.quentin.retrocollection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Account extends AppCompatActivity {
Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new Session(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        TextView name_account = (TextView)findViewById(R.id.Name_account);
        name_account.setText(session.getusename());
    }

    public void deco(View view) {
        session = new Session(this);
        session.setusename("");
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }
}
