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
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.thomas.projet100h.R;
import com.example.thomas.projet100h.Utilities.FileArrayAdapter;
import com.example.thomas.projet100h.entities.Publication;

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

import static android.R.id.list;
import static com.example.thomas.projet100h.Activity.pageConnection.user;

public class Actu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private FileArrayAdapter adapter;
    private int idStatut;
    private List<Publication> publications;
    private ListView list;
    private static final String TAG_TEXTE = "texte";
    private static final String TAG_ID ="IdPublication";
    private static final String TAG_IDMEDIA ="idMedia";
    private static final String TAG_VISIBILITY ="validation";
    private static final String TAG_CONTENUMEDIA ="contenuMedia";
    private static final String TAG_URL_LIST ="http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/list/";
    private Button button;
    private String idLastPublication;
    private int topPubli;

    /** onCreate, fonction lancée au démarrage de l'activité **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        button = (Button) findViewById(R.id.buttonMore) ;


        list = (ListView) findViewById(R.id.listViewActu);
        publications = new ArrayList<>();
        idStatut = user.getIdStatut();
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
            intent = new Intent(this, Acceuil.class);
            startActivity(intent);
        } else if (id  == R.id.nav_actu) {
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

    /** Affiche dans le listeView les publciations, en fonction de l'id de l'utilisateur **/
    public void getDataList(){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            /** Tache qui s'éxécutera au lancement de la class, elle s'éxécute
             * @param params prend en parametre une liste de string
             * @return string return le resultat de la requete en string**/
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

                Log.e("URL",TAG_URL_LIST+idStatut+"-0");
                HttpGet httpget = new HttpGet(TAG_URL_LIST+idStatut+"-0");
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
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                Log.e("yo",result);
                return result;
            }

            /** S'éxécute à la suite du doInBackGround
             * @param result prend en paramètre le result renvoyé par le doInBackGround **/
            @Override
            protected void onPostExecute(String result){
                try {

                    JSONArray jsonObj = new JSONArray(result);
                    for(int i=0;i<jsonObj.length();i++){
                        JSONObject c = jsonObj.getJSONObject(i);
                        String  texte = c.getString(TAG_TEXTE);
                        String idPublication = c.getString(TAG_ID);
                        String  idmedia = c.getString(TAG_IDMEDIA) ;
                        String media  = c.getString(TAG_CONTENUMEDIA);
                        int idPubli = Integer.parseInt(idPublication);
                        int idmediaPubli = Integer.parseInt(idmedia);
                        boolean visibility = c.getBoolean(TAG_VISIBILITY);
                        Publication publi = new Publication( media, texte, idPubli,  idmediaPubli, visibility);

                        publications.add(publi);
                        idLastPublication = idPublication;
                    }


                    adapter = new FileArrayAdapter(
                            Actu.this, R.layout.list_item, publications, idStatut);
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
    /** Fonction qui affiche 5 publications de plus dans le listview  **/
    public void More(View v){
        topPubli = publications.size() - 1;
        class GetDataJSON extends AsyncTask<String, Void, String> {

            /** Tache qui s'éxécutera au lancement de la class, elle s'éxécute
             * @param params prend en parametre une liste de string
             * @return string return le resultat de la requete en string**/
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                Log.e("URL",TAG_URL_LIST+idStatut+"-"+idLastPublication);
                HttpGet httpget = new HttpGet(TAG_URL_LIST+idStatut+"-"+idLastPublication);
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
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                Log.e("yo",result);
                return result;
            }

            /** S'éxécute à la suite du doInBackGround
             * @param result prend en paramètre le result renvoyé par le doInBackGround **/
            @Override
            protected void onPostExecute(String result){
                try {

                    JSONArray jsonObj = new JSONArray(result);
                    for(int i=0;i<jsonObj.length();i++){
                        JSONObject c = jsonObj.getJSONObject(i);
                        String  texte = c.getString(TAG_TEXTE);
                        String idPublication = c.getString(TAG_ID);
                        String  idmedia = c.getString(TAG_IDMEDIA) ;
                        String media  = c.getString(TAG_CONTENUMEDIA);
                        int idPubli = Integer.parseInt(idPublication);
                        int idmediaPubli = Integer.parseInt(idmedia);
                        boolean visibility = c.getBoolean(TAG_VISIBILITY);
                        Publication publi = new Publication( media, texte,  idPubli,  idmediaPubli, visibility);

                        publications.add(publi);
                        idLastPublication = idPublication;
                    }

                    adapter.setNotifyOnChange(true);


                    list.setAdapter(adapter);

                    list.setSelection(topPubli);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }


}
