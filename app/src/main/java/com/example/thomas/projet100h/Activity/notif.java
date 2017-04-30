package com.example.thomas.projet100h.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.projet100h.R;

public class notif extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private Button sndNotificationBtn;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        editText = (EditText) findViewById(R.id.editText);

        sndNotificationBtn = (Button) findViewById(R.id.button);
        sndNotificationBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Toast.makeText(getBaseContext(), "Envoie d'une notification", Toast.LENGTH_SHORT).show();
                createNotification();
            }
        });
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

        } else if (id == R.id.nav_profil) {
            intent = new Intent(this, profil.class);
            startActivity(intent);
        }else if (id == R.id.nav_propos) {

        }else if (id == R.id.nav_logout) {
            intent = new Intent(this, pageConnection.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private final void createNotification(){
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);



        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                //  .setSmallIcon(R.drawable.notification)
                .setContentTitle("Heisenbears")
                .setContentText(editText.getText())
                .setSmallIcon(R.drawable.logo);


        mNotification.notify(1, builder.build());
    }
}
