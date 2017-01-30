package com.example.thomas.projet100h;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;

public class Acceuil extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private String myJSON;
    private TextView textView;
    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "nom";
    private static final String TAG_NAME = "prenom";
    private static final String TAG_ADD ="age";

    JSONArray peoples = null;
    JSONArray ancre = null;

    ArrayList<HashMap<String, String>> personList;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textView = (TextView) findViewById(R.id.textView2);

        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<>();
        ListAdapter adapter = new SimpleAdapter(
                Acceuil.this, personList, R.layout.list_item,
                new String[]{"Connection...","",""},
                new int[]{R.id.id, R.id.name, R.id.address}
        );
        list.setAdapter(adapter);

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
                HttpPost httppost = new HttpPost("http://192.168.1.18/projet100h/listePubli.php");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");
                Log.i("5 dernières Publication", httppost.toString());
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
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address =  Integer.toString(c.getInt(TAG_ADD));

                HashMap<String,String> persons = new HashMap<>();

                persons.put(TAG_ID,id);
                persons.put(TAG_NAME,name);
                persons.put(TAG_ADD,address);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    Acceuil.this, personList, R.layout.list_item,
                    new String[]{TAG_ID,TAG_NAME,TAG_ADD},
                    new int[]{R.id.id, R.id.name, R.id.address}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getDataAncre(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://192.168.1.18/projet100h/ancrePubli.php");
                Log.e("Yo","1");
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
                myJSON = result;
                try {
                    JSONObject jsonObj = new JSONObject(myJSON);
                    ancre = jsonObj.getJSONArray(TAG_RESULTS);
                    JSONObject c = ancre.getJSONObject(0);
                    String id = c.getString(TAG_ID);
                    textView.setText(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }


}
