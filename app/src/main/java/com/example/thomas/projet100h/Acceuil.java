package com.example.thomas.projet100h;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.facebook.login.LoginResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Acceuil extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private String myJSON;
    private TextView textView;
    private static final String TAG_RESULTS="result";
    private static final String TAG_TEXTE = "texte";
    private static final String TAG_DATE = "date";
    private static final String TAG_ID ="id";
    private static final String TAG_IDMEDIA ="idMedia";
    private static final String TAG_CONTENUMEDIA ="contenuMedia";
    private static final String TAG_URL ="http://192.168.1.16/projet100h/";

    JSONArray publication = null;
    JSONArray ancre = null;
    FileArrayAdapter adapter;
    int idFacebook;

    List<Publication> publications;

    ListView list;
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

        intent = new Intent(this, Acceuil.class);
        idFacebook = intent.getIntExtra("id",0);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textView = (TextView) findViewById(R.id.textView2);

        list = (ListView) findViewById(R.id.listView);
        publications = new ArrayList<>();


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
            finish();
        } else if (id == R.id.nav_jeu) {

        } else if (id == R.id.nav_equipe) {

        } else if (id == R.id.nav_resCal) {

        } else if (id == R.id.nav_profil) {

        }else if (id == R.id.nav_propos) {

        }else if (id == R.id.nav_logout) {
            intent = new Intent(this, pageConnection.class);
            startActivity(intent);
            finish();
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
                HttpPost httppost = new HttpPost(TAG_URL +"php/listePubli.php");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");
                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
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
                    JSONObject jsonObj = new JSONObject(result);
                    publication = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<publication.length();i++){
                        JSONObject c = publication.getJSONObject(i);
                        String  texte = c.getString(TAG_TEXTE);
                        String  date = c.getString(TAG_DATE);
                        String id = c.getString(TAG_ID);
                        String  idmedia = c.getString(TAG_IDMEDIA) ;
                        String media  = c.getString(TAG_CONTENUMEDIA);
                        int idPubli = Integer.parseInt(id);
                        int idmediaPubli = Integer.parseInt(idmedia);
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
                        Date datePubli = format.parse(date);
                        Publication publi = new Publication( media, texte, datePubli,  idPubli,  idmediaPubli);

                        publications.add(publi);
                    }

                    adapter = new FileArrayAdapter(
                            Acceuil.this, R.layout.list_item, publications, 1);
                    adapter.setNotifyOnChange(true);

                    list.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }





}
