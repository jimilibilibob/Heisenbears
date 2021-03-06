package com.example.thomas.projet100h.Activity;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Acceuil extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private TextView textView;
    private static final String TAG_TEXTE = "texte";
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
    /** onCreate, fonction lancée au démarrage de l'activité **/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**On associe les parties "visible" de l'application à l'activité **/
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

        /** On lance l'affichage des news**/
        getDataAncre();
        getDataList();

    }

    /** Fonction permettant de quitter le menu **/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /** Fonction permettant de clicquer sur les items du menu et d'associer à chaque item une action
     * @param item l'item sur lequel l'utilisateur a cliquer **/
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
            intent = new Intent(this, jeu.class);
            startActivity(intent);
        } else if (id == R.id.nav_equipe) {
            intent = new Intent(this, roster.class);
            startActivity(intent);
        } else if (id == R.id.nav_resCal) {
            intent = new Intent(this, score.class);
            startActivity(intent);
        } else if (id == R.id.nav_profil) {
            intent = new Intent(this, profil.class);
            startActivity(intent);
        }else if (id == R.id.nav_propos) {
            intent = new Intent(this, Apropos.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            intent = new Intent(this, pageConnection.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /** Affiche dans le listeView les 5 dernières publciation**/
    public void getDataList(){
        /** Class qui récupere les pubications en question, cette class descend de Asyntask,
         *  elle ne s'éxécute pas dans le même
         * timing que les autres activitées visibles**/
        class GetDataJSON extends AsyncTask<String, Void, String> {

            /** Tache qui s'éxécutera au lancement de la class, elle s'éxécute
             * @param params prend en parametre une liste de string
             * @return string return le resultat de la requete en string**/
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                /**Méthode GET, prend en paramètre l'URL du webservice  **/
                HttpGet httpget = new HttpGet(TAG_URL_LIST+"1"+"-0");
                httpget.setHeader("Content-type", "application/json");
                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httpget);
                    HttpEntity entity = response.getEntity();
                    inputStream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    Log.e("Error"," Error in getDataList");
                }
                finally {
                    try{
                        if(inputStream != null)inputStream.close();
                    }catch(Exception squish){
                        Log.e("Error"," Error in getDataList");
                    }
                }
                return result;
            }

            /** S'éxécute à la suite du doInBackGround
             * @param result prend en paramètre le result renvoyé par le doInBackGround **/
            @Override
            protected void onPostExecute(String result){
                try {
                    /** Transforme de result en une list de Json qui seront associé a une publication pour etre en suite retourné
                     * au FileArrayAdapter **/
                    JSONArray jsonObj = new JSONArray(result);
                    for(int i=0;i<jsonObj.length();i++){
                        JSONObject c = jsonObj.getJSONObject(i);
                        String  texte = c.getString(TAG_TEXTE);
                        String id = c.getString(TAG_ID);
                        String  idmedia = c.getString(TAG_IDMEDIA) ;
                        String media  = c.getString(TAG_CONTENUMEDIA);
                        int idPubli = Integer.parseInt(id);
                        int idmediaPubli = Integer.parseInt(idmedia);
                        boolean visibility = c.getBoolean(TAG_VISIBILITY);
                        Log.e("IdPublication_Acceuil",id);
                        Publication publi = new Publication( media, texte,  idPubli,  idmediaPubli, visibility);

                        publications.add(publi);
                    }
                    adapter = new FileArrayAdapter( Acceuil.this, R.layout.list_item, publications, 1);
                    list.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    /** Affiche dans la publication ancré la dernière publication ancré de la BDD **/
    public void getDataAncre(){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            /** Tache qui s'éxécutera au lancement de la class, elle s'éxécute
            * @param params prend en parametre une liste de string
            * @return string return le resultat de la requete en string**/
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpGet httpget = new HttpGet(TAG_URL_ANCRE);
                httpget.setHeader("Content-type", "application/json");
                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httpget);
                    HttpEntity entity = response.getEntity();
                    inputStream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    Log.e("Error"," Error in getDataAnre");
                }
                finally {
                    try{
                        if(inputStream != null)inputStream.close();
                    }catch(Exception squish){
                        Log.e("Error"," Error in getDataAnre");
                    }
                }
                return result;
            }

            /** S'éxécute à la suite du doInBackGround
             * @param result prend en paramètre le result renvoyé par le doInBackGround **/
            @Override
            protected void onPostExecute(String result){
                try {

                    JSONObject c = new JSONObject(result);
                    String  texte = c.getString(TAG_TEXTE);
                    String id = c.getString(TAG_ID);
                    String  idmedia = c.getString(TAG_IDMEDIA) ;
                    String media  = c.getString(TAG_CONTENUMEDIA);
                    //int idPubli = Integer.parseInt(id);
                    int idmediaPubli = Integer.parseInt(idmedia);
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
