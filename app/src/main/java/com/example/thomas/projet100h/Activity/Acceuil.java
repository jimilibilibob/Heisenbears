package com.example.thomas.projet100h.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thomas.projet100h.Utilities.FileArrayAdapter;
import com.example.thomas.projet100h.entities.Publication;
import com.example.thomas.projet100h.R;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.thomas.projet100h.Activity.pageConnection.user;

public class Acceuil extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private String myJSON;
    private TextView textView;
    private static final String TAG_TEXTE = "texte";
    private static final String TAG_DATE = "datePublication";
    private static final String TAG_ID ="IdPublication";
    private static final String TAG_IDMEDIA ="idMedia";
    private static final String TAG_CONTENUMEDIA ="contenuMedia";
    private static final String TAG_VISIBILITY ="validation";
    private static final String TAG_URL_LIST ="http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/list/";
    private static final String TAG_URL_ANCRE ="http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/ancre";

    private String ancre;
    private FileArrayAdapter adapter;
    private List<Publication> publications;

    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textView = (TextView) findViewById(R.id.textView2);

        list = (ListView) findViewById(R.id.listView);
        publications = new ArrayList<>();
        getDataAncre();
        getDataList();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_acceuil) {
        } else if (id  == R.id.nav_actu) {
            intent = new Intent(this, Actu.class);
            startActivity(intent);
        } else if (id == R.id.nav_jeu) {

        } else if (id == R.id.nav_equipe) {

        } else if (id == R.id.nav_resCal) {

        } else if (id == R.id.nav_profil) {

        }else if (id == R.id.nav_propos) {

        }else if (id == R.id.nav_logout) {
            intent = new Intent(this, pageConnection.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getDataList(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpGet httpget = new HttpGet(TAG_URL_LIST+"1"+"-0");
                // Depends on your web service
                httpget.setHeader("Content-type", "application/json");
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
                try {

                    JSONArray jsonObj = new JSONArray(result);
                    for(int i=0;i<jsonObj.length();i++){
                        JSONObject c = jsonObj.getJSONObject(i);
                        String  texte = c.getString(TAG_TEXTE);
                        String  date = c.getString(TAG_DATE);
                        String id = c.getString(TAG_ID);
                        String  idmedia = c.getString(TAG_IDMEDIA) ;
                        String media  = c.getString(TAG_CONTENUMEDIA);
                        int idPubli = Integer.parseInt(id);
                        int idmediaPubli = Integer.parseInt(idmedia);
                        boolean visibility = c.getBoolean(TAG_VISIBILITY);
                        //DateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.FRANCE);
                        //Date datePubli = format.parse(date);
                        Date datePubli = new Date();
                        Log.e("IdPublication_Acceuil",id);
                        Publication publi = new Publication( media, texte, datePubli,  idPubli,  idmediaPubli, visibility);

                        publications.add(publi);
                    }
                    adapter = new FileArrayAdapter(
                            Acceuil.this, R.layout.list_item, publications, 1);
                    adapter.setNotifyOnChange(true);

                    list.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    public void getDataAncre(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpGet httpget = new HttpGet(TAG_URL_ANCRE);
                // Depends on your web service
                httpget.setHeader("Content-type", "application/json");
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
                try {

                    JSONObject c = new JSONObject(result);
                    String  texte = c.getString(TAG_TEXTE);
                    String  date = c.getString(TAG_DATE);
                    String id = c.getString(TAG_ID);
                    String  idmedia = c.getString(TAG_IDMEDIA) ;
                    String media  = c.getString(TAG_CONTENUMEDIA);
                    int idPubli = Integer.parseInt(id);
                    int idmediaPubli = Integer.parseInt(idmedia);
                   // DateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.FRANCE);
                   // Date datePubli = format.parse(date);
                    ancre = texte;

                }  catch (JSONException e1) {
                    e1.printStackTrace();
                }

                textView.setText(ancre);


            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }




}
