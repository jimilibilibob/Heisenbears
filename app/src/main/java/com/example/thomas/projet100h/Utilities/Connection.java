package com.example.thomas.projet100h.Utilities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.thomas.projet100h.Activity.Acceuil;
import com.example.thomas.projet100h.Activity.pageConnection;
import com.example.thomas.projet100h.entities.Utilisateur;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.thomas.projet100h.Activity.pageConnection.user;


public class Connection extends AsyncTask<String, Void, String> {

    private static final String TAG_URL_USER ="http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/user/";
    private String URL;
    private String id;
    private String URL_ADD;
    private AccessToken accessToken;
    private static final String TAG_URL_ADD ="http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/user/";

    public Connection(String id, AccessToken accessToken){
        this.id = id;
        this.URL = this.TAG_URL_USER + id;
        this.accessToken = accessToken;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        Log.i("onPreExecute","onPreExecute");
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.i("makemachine", "onCancelled()");
    }

    @Override
    protected String doInBackground(String... params) {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        Log.e("url",URL);
        HttpGet httpget = new HttpGet(URL);
        // Depends on your web service
        httpget.setHeader("Content-type", "application/json;charset=utf-8");
        InputStream inputStream = null;
        String result = null;


        try {
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
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


        if(result.startsWith("null")){
            FirstConnection();
        }else if(!result.startsWith("null")){
            try {
                JSONObject jsonObj = new JSONObject(result);
                String  nom = jsonObj.getString("nom");
                String  prenom = jsonObj.getString("prenom");
                int idStatut = jsonObj.getInt("idStatut");
                user.setIdFacebook(id);
                user.setIdStatut(idStatut);
                user.setNom(nom);
                user.setPrenom(prenom);

                Log.e("idF",id);
                Log.e("nom", nom);
                Log.e("prenom", prenom);
                Log.e("idStatut", idStatut+"");

            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }



    public void FirstConnection() {

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObj, GraphResponse response) {
                        try {
                            String nom = jsonObj.getString("last_name");
                            String prenom = jsonObj.getString("first_name");
                            user.setIdFacebook(id);
                            user.setNom(nom);
                            user.setPrenom(prenom);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,last_name,first_name");
        request.setParameters(parameters);
        request.executeAsync();

        class AddUser extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                URL_ADD = TAG_URL_ADD+user.getIdFacebook()+"-"+user.getPrenom()+"-"+user.getNom();
                Log.e("URL_ADD", URL_ADD);
                HttpGet httpget = new HttpGet(URL_ADD);
                // Depends on your web service
                httpget.setHeader("Content-type", "application/json;charset=utf-8");
                InputStream inputStream = null;
                String result = null;


                try {
                    HttpResponse response = httpclient.execute(httpget);
                    HttpEntity entity = response.getEntity();
                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                } finally {
                    try {
                        if (inputStream != null) inputStream.close();
                    } catch (Exception squish) {
                    }
                }
                return result;
            }


            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    id = jsonObj.getString("idFacebook");
                    String nom = jsonObj.getString("nom");
                    String prenom = jsonObj.getString("prenom");
                    user.setIdFacebook(id);
                    user.setNom(nom);
                    user.setPrenom(prenom);

                    Log.e("idF", id);
                    Log.e("nom", nom);
                    Log.e("prenom", prenom);
                    Log.e("idStatut", user.getIdStatut().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        AddUser g = new AddUser();
        g.execute();

    }
}
