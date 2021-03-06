package com.example.thomas.projet100h.Activity;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;

import com.example.thomas.projet100h.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.R.id.message;
import static com.example.thomas.projet100h.Activity.pageConnection.user;

public class profil extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private View signalement;
    private View share;
    private View information;
    private View playbook;
    private View pageFb;
    private View groupFb;
    private View notification;
    private View gestion_profil;
    private int idStatu;
    private TextView salutation;
    private TextView signalement1;
    private TextView signalement2;
    private TextView signalement3;
    private static final String TAG_CHANGE_ID = "http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/changeId/";
    private static String URL_CHANGE_ID;
    private String idFacebook;
    private String messageNonCo;
    private String messageSignalement;
    private Intent intent;

    /** onCreate, fonction lancée au démarrage de l'activité **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        messageNonCo = getResources().getString(R.string.CoFacebook);
        messageSignalement = getResources().getString(R.string.signalementMessage);

        idFacebook = user.getIdFacebook();
        idStatu = user.getIdStatut();

        signalement = findViewById(R.id.signalement);
        share = findViewById(R.id.share);
        information = findViewById(R.id.information);
        playbook =  findViewById(R.id.playbook);
        pageFb = findViewById(R.id.pageFb);
        groupFb = findViewById(R.id.groupFb);
        notification = findViewById(R.id.notification);
        gestion_profil = findViewById(R.id.profil_gestion);


        signalement1 = (TextView) findViewById(R.id.signalement1);
        signalement2 = (TextView) findViewById(R.id.signalement2);
        signalement3 = (TextView) findViewById(R.id.signalement3);
        salutation = (TextView) findViewById(R.id.textView5);

        signalement.setVisibility(View.GONE);
        share.setVisibility(View.GONE);
        information.setVisibility(View.GONE);
        playbook.setVisibility(View.GONE);
        pageFb.setVisibility(View.GONE);
        groupFb.setVisibility(View.GONE);
        notification.setVisibility(View.GONE);
        gestion_profil.setVisibility(View.GONE);
        salutation.setVisibility(View.GONE);
        signalement1.setVisibility(View.GONE);
        signalement2.setVisibility(View.GONE);
        signalement3.setVisibility(View.GONE);

        if(idStatu == 1){
            signalement.setVisibility(View.VISIBLE);
            signalement1.setVisibility(View.VISIBLE);
            signalement2.setVisibility(View.VISIBLE);
            pageFb.setVisibility(View.VISIBLE);
        }
        if(idStatu ==2){
            String prenom = user.getPrenom();
            String message=  salutation.getText().toString();
            salutation.setText(message+" "+prenom+" !");
            salutation.setVisibility(View.VISIBLE);

            share.setVisibility(View.VISIBLE);
            information.setVisibility(View.VISIBLE);
            playbook.setVisibility(View.VISIBLE);
            pageFb.setVisibility(View.VISIBLE);
            groupFb.setVisibility(View.VISIBLE);

        }
        if(idStatu == 3 || idStatu == 4){
            String prenom = user.getPrenom();
            String message=  salutation.getText().toString();
            salutation.setText(message+" "+prenom+" !");
            salutation.setVisibility(View.VISIBLE);

            share.setVisibility(View.VISIBLE);
            information.setVisibility(View.VISIBLE);
            playbook.setVisibility(View.VISIBLE);
            pageFb.setVisibility(View.VISIBLE);
            groupFb.setVisibility(View.VISIBLE);
            notification.setVisibility(View.VISIBLE);
            gestion_profil.setVisibility(View.VISIBLE);
        }
        if(idStatu == 5){
            signalement.setVisibility(View.VISIBLE);
            pageFb.setVisibility(View.VISIBLE);
            signalement3.setVisibility(View.VISIBLE);
        }

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
            intent = new Intent(this, roster.class);
            startActivity(intent);
        } else if (id == R.id.nav_resCal) {
            intent = new Intent(this, score.class);
            startActivity(intent);
        } else if (id == R.id.nav_profil) {
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

    /**  @param V bouton pour se signaler comme étant un joueur**/
    public void signalement2(View V){
        Log.e("Signalement","Onclic");
        if(!(idFacebook.equals("null"))){
            class Signalement extends AsyncTask<String, Void, String> {


                @Override
                protected String doInBackground(String... params) {
                    DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                    URL_CHANGE_ID = TAG_CHANGE_ID+idFacebook+"-5" ;
                    Log.e("URL_VIS",URL_CHANGE_ID);
                    HttpGet httpget = new HttpGet(URL_CHANGE_ID);
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
                    return null;
                }



            }
            signalement1.setVisibility(View.GONE);
            signalement2.setVisibility(View.GONE);
            signalement.setVisibility(View.VISIBLE);
            pageFb.setVisibility(View.VISIBLE);
            signalement3.setVisibility(View.VISIBLE);
            signalement3.setText(messageSignalement);


            Signalement s = new Signalement();
            s.execute();
        }else{
            signalement1.setText(messageNonCo);
            signalement2.setVisibility(View.GONE);
        }
    }

    /**  @param V bouton pour aller à la page de partage**/
    public void share(View V){
        Log.e("2","share");
        intent = new Intent(this, partage.class);
        startActivity(intent);
    }

    /**  @param V bouton pour accéder à la page des informations du profil**/
    public void information(View V){
        Log.e("3","information");
    }

    /**  @param V bouton pour télécharger le playbook**/
    public void playbook(View V){
        Log.e("4","playbook");
    }

    /**  @param V bouton pour accéder à la page public facebook**/
    public void pageFb(View V){
        Log.e("5","pageFb");
        String url = "https://www.facebook.com/heisenbears/?fref=ts";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /**  @param V bouton pour envoyer une notification**/
    public void notification(View V){
        Log.e("6","notification");
        intent = new Intent(this, notif.class);
        startActivity(intent);
    }

    /**  @param V bouton pour gérer le profil des joueurs**/
    public void gestion_profil(View V){
        Log.e("7","gestion_profil");
    }

    /**  @param V bouton pour accéder au groupe faceook**/
    public void grouFb(View V){
        String url = "https://www.facebook.com/groups/406565032802791/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        Log.e("8","grouFb");
    }


}
