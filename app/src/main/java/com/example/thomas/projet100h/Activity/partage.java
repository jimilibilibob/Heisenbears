package com.example.thomas.projet100h.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import com.example.thomas.projet100h.R;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.thomas.projet100h.Activity.pageConnection.user;

public class partage extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private static int RESULT_LOAD_IMG = 1;
   // private RadioButton texteRD;
    //private RadioButton photoRD;
    //private RadioButton videoRD;
    private Button select;
    private EditText titreET;
    private EditText texteET;
    private int idMedia = 1;
    private ImageView imageView;
    private VideoView videoview;
    private String idFacebook;
    private int idStatut;
    private Date datePublication;
   // private String imgName;
    // private String vidName;
    private String contenuMedia;
    private Boolean publique;
    private Boolean validation;
    private Boolean prioritaire;
    private CheckBox prioritaireBox;
    private CheckBox publiqueBox;
    private String texte;
    private Button share;
    //private String KEY_IMAGE = "image";
    //private String KEY_NAME = "name";
    //private int idPublication;
    String filePath;
    String file_extn;
    Bitmap bitmap;
    private String URL_ADD = "http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/publi/add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //texteRD = (RadioButton) findViewById(R.id.radioButtonText);
       // photoRD = (RadioButton) findViewById(R.id.radioButtonImg);
       // videoRD = (RadioButton) findViewById(R.id.radioButtonVid);
        select = (Button) findViewById(R.id.Select);
        share = (Button) findViewById(R.id.button4);
        titreET = (EditText) findViewById(R.id.editText2);
        texteET = (EditText) findViewById(R.id.editText3);
        imageView = (ImageView) findViewById(R.id.imageView8);
        videoview = (VideoView) findViewById(R.id.videoView) ;
        prioritaireBox = (CheckBox) findViewById(R.id.checkBox3);
        publiqueBox = (CheckBox) findViewById(R.id.checkBox4);



        imageView.setVisibility(View.GONE);
        videoview.setVisibility(View.GONE);
        select.setVisibility(View.GONE);



        idFacebook = user.getIdFacebook();
        idStatut = user.getIdStatut();
        datePublication = new Date(Calendar.getInstance().getTime().getTime());
        Log.e("Date",datePublication.toString());
        Log.e("idFacebook",idFacebook);
        Log.e("idStatut",idStatut+"");



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

        } else if (id == R.id.nav_jeu) {

        } else if (id == R.id.nav_equipe) {

        } else if (id == R.id.nav_resCal) {

        } else if (id == R.id.nav_profil) {

        }else if (id == R.id.nav_propos) {

        }else if (id == R.id.nav_logout) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onSelectText(View V){
        idMedia = 1;
       imageView.setVisibility(View.GONE);
       videoview.setVisibility(View.GONE);
       select.setVisibility(View.GONE);
       titreET.setVisibility(View.VISIBLE);
    }

    // Permet d'aller dans la gallery des images
    public void onSelectImg(View V){
        idMedia = 2;
        imageView.setVisibility(View.VISIBLE);
        videoview.setVisibility(View.GONE);
        titreET.setVisibility(View.GONE);
        select.setVisibility(View.VISIBLE);
    }

    // Permet d'aller dans la gallery des vidéos
    public void onSelectVid(View V){
        idMedia = 3;
        imageView.setVisibility(View.GONE);
        titreET.setVisibility(View.GONE);
        select.setVisibility(View.VISIBLE);
        if(videoview.getDuration()==0){
            videoview.setVisibility(View.GONE);
        }
    }

    // Permet d'aller dans la gallery
    public void loadGallery(View view) {
        if (idMedia == 2){
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


        }if (idMedia == 3){
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                if(idMedia == 2) {
                    Uri selectedImage = data.getData();
                    Log.e("nameImg1", selectedImage.toString());
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    imageView.setImageBitmap(bitmap);
                    imageView.setMaxHeight(10);
                    imageView.setMaxWidth(10);
                    filePath = getPath(selectedImage);
                    file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                   /** Log.e("bitmap", bitmap.getConfig().toString());
                    Log.e("selectedImagePath", selectedImage.getPath());

                    Log.e("filePath", filePath);
                    Log.e("file_extn", file_extn); **/
                }
                if(idMedia == 3){
                    videoview.setVisibility(View.VISIBLE);
                    Uri selectedVideo = data.getData();
                    videoview.setVideoURI(selectedVideo);
                }



            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void onClicShare(View v){
        addPublication();
    }

    public void addPublication(){
        class add extends AsyncTask<String, Void, String> {
            public add(){
                if(idMedia == 1){
                    contenuMedia = titreET.getText().toString();
                }if(idMedia == 2){
                    contenuMedia = getStringImage(bitmap);
                }if(idMedia == 3){
                   // contenuMedia = vidName;
                }

                publique = publiqueBox.isChecked();
                prioritaire = prioritaireBox.isChecked();

                texte = texteET.getText().toString();
                validation = false;
                if(idStatut == 3 ||idStatut == 4){
                    validation = true;
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL_ADD);
                try {

                    List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                    nameValuePairs.add(new BasicNameValuePair("idFacebook", idFacebook));
                    nameValuePairs.add(new BasicNameValuePair("idMedia", idMedia+""));
                    nameValuePairs.add(new BasicNameValuePair("datePublication", datePublication.toString()));
                    nameValuePairs.add(new BasicNameValuePair("texte", texte));
                    nameValuePairs.add(new BasicNameValuePair("contenuMedia", contenuMedia));
                  //  nameValuePairs.add(new BasicNameValuePair("tailleCM", contenuMedia.length()+""));
                   // Log.e("contenuMedia", contenuMedia);
                    nameValuePairs.add(new BasicNameValuePair("publique", publique+""));
                    nameValuePairs.add(new BasicNameValuePair("validation", validation+""));
                    nameValuePairs.add(new BasicNameValuePair("prioritaire", prioritaire+""));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    httpclient.execute(httppost);

                } catch (Exception e) {
                    Log.e("Error","Error in addPublication");
                    e.printStackTrace();
                }
                return null;
            }



        }
        add g = new add();
        g.execute();
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
