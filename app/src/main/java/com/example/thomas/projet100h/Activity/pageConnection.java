package com.example.thomas.projet100h.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.thomas.projet100h.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class pageConnection extends AppCompatActivity {

    private CallbackManager callbackManager;
    private Intent i;
    private LoginButton loginButton;
    private static final String TAG_URL_USER ="http://localhost:8080/heisenbears/user/";
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        i = new Intent(this, Acceuil.class);
        setContentView(R.layout.activity_page_connection);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions("email");



        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("Yo","1");
                Log.e("Yo",loginResult.toString());
                Log.e("Yo",loginResult.getAccessToken().toString());

                id = loginResult.getAccessToken().getUserId();
                Log.e("Yo",id);

            }

            @Override
            public void onCancel() {   Log.e("Yo","cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("Yo","error");
            }
        });

        Connection();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public void onClick(View V){
        i.putExtra("id",0);
        startActivity(i);
    }

    public void Connection(){

        class TheTask  extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                Log.e("Yo","3");
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                Log.e("Yo",TAG_URL_USER+"1");
                String URL = TAG_URL_USER+"1";
                HttpGet httpget = new HttpGet(URL);
                // Depends on your web service
                httpget.setHeader("Content-type", "application/json");
                InputStream inputStream = null;
                String result = null;


                try {
                    Log.e("Yo","4");
                    HttpResponse response = httpclient.execute(httpget);
                    Log.e("ae",response.getStatusLine().toString());
                    HttpEntity entity = response.getEntity();
                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    Log.e("YP",sb.toString());
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        Log.e("YP",line);
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                try {

                    JSONObject jsonObj = new JSONObject(result);
                    String  nom = jsonObj.getString("nom");
                    String  prenom = jsonObj.getString("prenom");
                    String idStatut = jsonObj.getString("idStatut");




                }  catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        new TheTask().execute(TAG_URL_USER+"1");
    }

}

