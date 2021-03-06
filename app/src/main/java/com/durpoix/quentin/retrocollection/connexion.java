package com.durpoix.quentin.retrocollection;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class connexion extends AppCompatActivity {
    Toast toast;
    String RETURN;
    Boolean stop = true;
    String passV;
    String ERROR;
    String emailV;
    Session session;//global variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
    }




    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if((networkInfo != null && networkInfo.isConnected())){
            return true;
        }else{
            toast = Toast.makeText(this, "Veuillez vous connecter à internet", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    public void Connexion(View view) {

        EditText email = (EditText)findViewById(R.id.input_email);
        EditText password = (EditText)findViewById(R.id.input_password);
        if(email.getText().toString().equals("")||password.getText().toString().equals("")){
             stop = false;
        }else {
            stop = true;
            emailV = email.getText().toString();
            passV = password.getText().toString();
        }

        if(isOnline()&&stop){
            session = new Session(connexion.this);
            final ProgressDialog progressDialog = new ProgressDialog(connexion.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Connexion...");
            progressDialog.show();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        String uri = "http://78.213.65.69/android.php?email="+emailV+"&password="+passV;
                        URL url;
                        URLConnection conn;
                        url = new URL(uri);
                        conn = url.openConnection();
                        conn.setReadTimeout(3000);
                        conn.setConnectTimeout(3000);
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        JSONObject returnPhp = new JSONObject(reader.readLine());
                        ERROR = returnPhp.getString("ERROR");
                        RETURN = returnPhp.getString("RETURN");
                        if(RETURN.equals("true")){

                           session.setusename(emailV);
                            Intent returnIntent = new Intent();
                            setResult(RESULT_CANCELED, returnIntent);
                            finish();


                        }else{
                            connexion.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    toast = Toast.makeText(connexion.this, ERROR, Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });

                        }
                        progressDialog.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }else{
            toast = Toast.makeText(this, "Veuillez renseigner un login et un password", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

}
