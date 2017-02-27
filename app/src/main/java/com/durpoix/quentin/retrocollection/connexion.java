package com.durpoix.quentin.retrocollection;

import android.app.ProgressDialog;
import android.content.Context;
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
    String emailV;
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
                        String ERROR = returnPhp.getString("ERROR");
                        RETURN = returnPhp.getString("RETURN");
                        Log.i("connexion",RETURN);
                        progressDialog.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }else{
            toast = Toast.makeText(this, "Nope", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

}
