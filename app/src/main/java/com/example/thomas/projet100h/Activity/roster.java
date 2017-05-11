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
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.projet100h.R;
import com.example.thomas.projet100h.Utilities.FileArrayAdapter;
import com.example.thomas.projet100h.Utilities.RosterAdapter;
import com.example.thomas.projet100h.entities.Publication;
import com.example.thomas.projet100h.entities.Utilisateur;

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
import java.util.List;

public class roster extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final static String TAG_URL_LIST = "http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/list/user/";
    private Intent intent;
    GridView gridView;
    private RosterAdapter rosterAdapter;
    private List<Utilisateur> users;
    TextView poste;
    TextView poid;
    TextView taille;
    TextView nom;
    ImageView img;
    LinearLayout rosterDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        poste = (TextView) findViewById(R.id.posteRoster);
        poid = (TextView) findViewById(R.id.poidRoster);
        taille = (TextView) findViewById(R.id.tailleRoster);
        nom = (TextView) findViewById(R.id.nomRoster);
        img = (ImageView) findViewById(R.id.imageViewRoster);
        users = new ArrayList<>();

        rosterDetail = (LinearLayout) findViewById(R.id.rosterDetail);
        rosterDetail.setVisibility(View.GONE);

        gridView = (GridView) findViewById(R.id.grid);

       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                detail(position);


            }
        });

        getRoster2();
        getRoster3();
        getRoster4();
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
            intent = new Intent(this, Actu.class);
            startActivity(intent);
        } else if (id == R.id.nav_jeu) {
            intent = new Intent(this, jeu.class);
            startActivity(intent);
        } else if (id == R.id.nav_equipe) {
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

    public void getRoster2(){
        /** Class qui récupere les pubications en question, cette class descend de Asyntask, elle ne s'éxécute pas dans le même
         * timing que les autres activitées visibles**/
        class GetDataJSON extends AsyncTask<Integer, Void, String> {

            /** Tache qui s'éxécutera au lancement de la class, elle s'éxécute
             * @param params prend en parametre une liste de string
             * @return string return le resultat de la requete en string**/


            @Override
            protected String doInBackground(Integer[] params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                /**Méthode GET, prend en paramètre l'URL du webservice  **/
                HttpGet httpget = new HttpGet(TAG_URL_LIST+"2");
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
                    Log.e("error", result);
                    /** Transforme de result en une list de Json qui seront associé a une publication pour etre en suite retourné
                     * au FileArrayAdapter **/
                    JSONArray jsonObj = new JSONArray(result);
                    for(int i=0;i<jsonObj.length();i++){
                        JSONObject c = jsonObj.getJSONObject(i);
                        String  idFacebook = c.getString("idFacebook");
                        int idStatut =  c.getInt("idStatut");
                        String nom = c.getString("nom");
                        String  prenom = c.getString("prenom") ;
                        int tel  = c.getInt("telephone");
                        int poid =  c.getInt("poid");
                        int taille =  c.getInt("taille");
                        String lebelle = c.getString("poste");
                        String img = c.getString("img");
                        Utilisateur user = new Utilisateur( idFacebook,idStatut, nom,  prenom,  tel,poid,taille,lebelle, img);

                        users.add(user);
                    }
                    rosterAdapter = new RosterAdapter(roster.this, users);
                    gridView.setAdapter(rosterAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    public void getRoster3(){
        /** Class qui récupere les pubications en question, cette class descend de Asyntask, elle ne s'éxécute pas dans le même
         * timing que les autres activitées visibles**/
        class GetDataJSON extends AsyncTask<Integer, Void, String> {

            /** Tache qui s'éxécutera au lancement de la class, elle s'éxécute
             * @param params prend en parametre une liste de string
             * @return string return le resultat de la requete en string**/


            @Override
            protected String doInBackground(Integer[] params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                /**Méthode GET, prend en paramètre l'URL du webservice  **/
                HttpGet httpget = new HttpGet(TAG_URL_LIST+"3");
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
                    Log.e("error", result);
                    /** Transforme de result en une list de Json qui seront associé a une publication pour etre en suite retourné
                     * au FileArrayAdapter **/
                    JSONArray jsonObj = new JSONArray(result);
                    for(int i=0;i<jsonObj.length();i++){
                        JSONObject c = jsonObj.getJSONObject(i);
                        String  idFacebook = c.getString("idFacebook");
                        int idStatut =  c.getInt("idStatut");
                        String nom = c.getString("nom");
                        String  prenom = c.getString("prenom") ;
                        int tel  = c.getInt("telephone");
                        //int idPoste =  c.getInt("idPoste");
                        int poid =  c.getInt("poid");
                        int taille =  c.getInt("taille");
                        String lebelle = c.getString("poste");
                        String img = c.getString("img");
                        Utilisateur user = new Utilisateur( idFacebook,idStatut, nom,  prenom,  tel,poid,taille,lebelle, img);

                        users.add(user);
                    }
                    rosterAdapter = new RosterAdapter(roster.this, users);
                    gridView.setAdapter(rosterAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    public void getRoster4(){
        /** Class qui récupere les pubications en question, cette class descend de Asyntask, elle ne s'éxécute pas dans le même
         * timing que les autres activitées visibles**/
        class GetDataJSON extends AsyncTask<Integer, Void, String> {

            /** Tache qui s'éxécutera au lancement de la class, elle s'éxécute
             * @param params prend en parametre une liste de string
             * @return string return le resultat de la requete en string**/


            @Override
            protected String doInBackground(Integer[] params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                /**Méthode GET, prend en paramètre l'URL du webservice  **/
                HttpGet httpget = new HttpGet(TAG_URL_LIST+"4");
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
                    Log.e("error", result);
                    /** Transforme de result en une list de Json qui seront associé a une publication pour etre en suite retourné
                     * au FileArrayAdapter **/
                    JSONArray jsonObj = new JSONArray(result);
                    for(int i=0;i<jsonObj.length();i++){
                        JSONObject c = jsonObj.getJSONObject(i);
                        String  idFacebook = c.getString("idFacebook");
                        int idStatut =  c.getInt("idStatut");
                        String nom = c.getString("nom");
                        String  prenom = c.getString("prenom") ;
                        int tel  = c.getInt("telephone");
                        //int idPoste =  c.getInt("idPoste");
                        int poid =  c.getInt("poid");
                        int taille =  c.getInt("taille");
                        String lebelle = c.getString("poste");
                        String img = c.getString("img");
                        Utilisateur user = new Utilisateur( idFacebook,idStatut, nom,  prenom,  tel,poid,taille,lebelle, img);

                        users.add(user);
                    }
                    rosterAdapter = new RosterAdapter(roster.this, users);
                    gridView.setAdapter(rosterAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    public void detail(int pos){
        gridView.setVisibility(View.GONE);
        rosterDetail.setVisibility(View.VISIBLE);

        poste.setText(users.get(pos).getPoste());
        poid.setText(users.get(pos).getPoid()+"");
        taille.setText(users.get(pos).getTaille()+"");
        nom.setText(users.get(pos).getNom()+" "+users.get(pos).getPrenom());
    }

    public void change(View v){
        gridView.setVisibility(View.VISIBLE);
        rosterDetail.setVisibility(View.GONE);
    }
}
