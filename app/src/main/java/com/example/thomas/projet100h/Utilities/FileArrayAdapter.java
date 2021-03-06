package com.example.thomas.projet100h.Utilities;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.thomas.projet100h.R;
import com.example.thomas.projet100h.entities.Publication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/** Utilities FileArrayAdapter, gère l'affichage des publications **/
public class FileArrayAdapter extends ArrayAdapter<Publication> {

    private static final String TAG_VISIBILITY = "http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/visibility/";
    private static final String TAG_DELETE = "http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/delete/";
    private static String TAG_IMAGE = "http://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-401731657040/image/";
    private Context c;
    private int id;
    private List<Publication> items;
    private ImageView image;
    private static Bitmap bmp;
    private int idStatut;
    private PopupMenu popup ;
    private String visibilit;
    private static String URL_VISIBILITY ;
    private static String URL_DELETE ;
    private boolean visibility;
    private static URL URL_IMAGE ;
    private Bitmap bitmap;

    /** Créateur du FileArrayAdapter **/
    public FileArrayAdapter(Context context, int textViewResourceId,
                            List<Publication> objects, int idStatut) {
        super(context, textViewResourceId, objects);
        c = context;
        id = textViewResourceId;
        items = objects;
        this.idStatut = idStatut;

    }

    /** Getter de publication
     * @param i position de la publication dans la list
     * @return publciation à la position i **/
    public Publication getItem(int i)
    {
        return items.get(i);
    }

    /** Gère l'affichage des lignes
     * @param position position de la ligne
     * @param convertView ligne
     * @param parent ligne précèdente
     * @return le listview compléter**/
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item, null);
        }

        final Publication o = items.get(position);
        TextView texteV = (TextView) v.findViewById(R.id.texte1);

        TextView titre = (TextView) v.findViewById(R.id.titre);
        titre.setVisibility(View.GONE);

        image = (ImageView) v.findViewById(R.id.image);
        image.setVisibility(View.GONE);

        VideoView video = (VideoView) v.findViewById(R.id.video);
        video.setVisibility(View.GONE);

        Button btn = (Button) v.findViewById(R.id.button2);
        btn.setVisibility(View.GONE);



        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popup = new PopupMenu(c, v);



                popup.getMenuInflater().inflate(R.menu.popmenu_publication, popup.getMenu());

                if(o.isVisibility()){
                    visibilit = c.getResources().getString(R.string.visibility);
                    popup.getMenu().getItem(0).setTitle(visibilit);
                    visibility = false;
                }else if(!o.isVisibility()){
                    visibilit = c.getResources().getString(R.string.invisibility);
                    popup.getMenu().getItem(0).setTitle(visibilit);
                    visibility = true;
                }

                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        int id = item.getItemId();

                        if (id == R.id.visibility) {
                            URL_VISIBILITY = TAG_VISIBILITY+o.getIdPublication();
                            changeVisibility();

                            o.setVisibility(visibility);
                            if(o.isVisibility()){
                                visibilit = c.getResources().getString(R.string.visibility);
                                popup.getMenu().getItem(0).setTitle(visibilit);
                                visibility = true;
                            }else if(!o.isVisibility()){
                                visibilit = c.getResources().getString(R.string.invisibility);
                                popup.getMenu().getItem(0).setTitle(visibilit);
                                visibility = false;
                            }
                            Toast.makeText(getApplicationContext(),
                                    "Done", Toast.LENGTH_SHORT).show();

                        } else if (id  == R.id.delete) {

                            URL_DELETE = TAG_DELETE+o.getIdPublication();
                            changeDelete();
                            items.remove(o);
                            notifyDataSetChanged();

                            Toast.makeText(getApplicationContext(),
                                    "Done", Toast.LENGTH_SHORT).show();


                        }


                        return true;
                    }
                });

            }

        });








        if (o != null) {

            String texte = o.getTexte();
            String media = o.getMedia();

            if (o.getIdMedia() == 1) {
                image.setVisibility(View.GONE);
                video.setVisibility(View.GONE);
                titre.setVisibility(View.VISIBLE);
                if (!texte.equals("null") && !media.equals("null")) {
                    texteV.setText(texte);
                    titre.setText(media);
                }
                if (!texte.equals("null") && media.equals("null")) {
                    titre.setVisibility(View.GONE);
                    texteV.setText(texte);
                }
                if (texte.equals("null") && !media.equals("null")) {
                    texteV.setVisibility(View.GONE);
                    titre.setText(media);
                }

                if(idStatut == 3 || idStatut == 4){
                    btn.setVisibility(View.VISIBLE);
                }

            }

            if (o.getIdMedia() == 2) {
                titre.setVisibility(View.GONE);
                video.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                if (!texte.equals("null") && !media.equals("null")) {
                    texteV.setText(texte);
                    Image(media);
                }
                if (!texte.equals("null") && media.equals("null")) {
                    image.setVisibility(View.GONE);
                    texteV.setText(texte);
                }
                if (texte.equals("null") && !media.equals("null")) {
                    texteV.setVisibility(View.GONE);


                }

                if(idStatut == 3 || idStatut == 4){
                    btn.setVisibility(View.VISIBLE);
                }

            }
            if (o.getIdMedia() == 3) {
                image.setVisibility(View.GONE);
                titre.setVisibility(View.GONE);
                video.setVisibility(View.VISIBLE);
                if (!texte.equals("null") && !media.equals("null")) {
                    texteV.setText(texte);
                }
                if (!texte.equals("null") && media.equals("null")) {
                    video.setVisibility(View.GONE);
                    texteV.setText(texte);
                }
                if (texte.equals("null") && !media.equals("null")) {
                    texteV.setVisibility(View.GONE);
                    int videoResource = c.getResources().getIdentifier(media, null, c.getPackageName());
                    Movie movie = c.getResources().getMovie(videoResource);
                }

                if(idStatut == 3 || idStatut == 4){
                    btn.setVisibility(View.VISIBLE);
                }
            }

        }
        

        return v;
    }

    /** Fonction qui retourne l'image qui est stockée sur le serveur
     * @param name nom de l'image**/
    private void Image(String name){
        final String imageName = name;
        class GetDataJSON extends AsyncTask<Void, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(Void... params) {

                HttpURLConnection urlConnection = null;
                try {
                    URL_IMAGE = new URL(TAG_IMAGE+imageName);
                    urlConnection = (HttpURLConnection) URL_IMAGE.openConnection();
                    int statusCode = urlConnection.getResponseCode();
                    if (statusCode != HttpStatus.SC_OK) {
                        return null;
                    }

                    InputStream inputStream = urlConnection.getInputStream();
                    if (inputStream != null) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        bitmap = BitmapFactory.decodeStream(inputStream,null,options);


                        return bitmap;
                    }
                } catch (Exception e) {
                    urlConnection.disconnect();
                    Log.w("ImageDownloader", "Error downloading image from " + URL_IMAGE);
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                return null;
            }


            @Override
            protected void onPostExecute(Bitmap bmp){


                image.setImageBitmap(bmp);

            }



        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    /** Fonction qui change la visibiliter d'une publication**/
    private static void changeVisibility(){
        class Visibility extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                Log.e("URL_VIS",URL_VISIBILITY);
                HttpGet httpget = new HttpGet(URL_VISIBILITY);
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
                return null;
            }



        }
        Visibility v = new Visibility();
        v.execute();
    }

    /** Fonction qui suprime une publication**/
    private static void changeDelete(){
        class Delete extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                Log.e("URL_DELETE",URL_DELETE);
                HttpGet httpget = new HttpGet(URL_DELETE);
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
                return null;
            }



        }
        Delete d = new Delete();
        d.execute();
    }
}
