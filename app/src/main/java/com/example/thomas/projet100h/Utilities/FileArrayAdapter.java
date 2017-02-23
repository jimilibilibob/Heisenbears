package com.example.thomas.projet100h.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.thomas.projet100h.Activity.Actu;
import com.example.thomas.projet100h.R;
import com.example.thomas.projet100h.entities.Publication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.example.thomas.projet100h.R.id.imageView;
import static com.facebook.FacebookSdk.getApplicationContext;


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

    public FileArrayAdapter(Context context, int textViewResourceId,
                            List<Publication> objects, int idStatut) {
        super(context, textViewResourceId, objects);
        c = context;
        id = textViewResourceId;
        items = objects;
        this.idStatut = idStatut;
    }

    public Publication getItem(int i)
    {
        return items.get(i);
    }

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
                            URL_VISIBILITY = TAG_VISIBILITY+o.getIdPublication()+"-"+visibility;
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
                    Log.e("56","entrer");
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
                    int imageResource = c.getResources().getIdentifier(media, null, c.getPackageName());
                    Drawable image2 = c.getResources().getDrawable(imageResource);
                    image.setImageDrawable(image2);
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



    private void Image(String name){
        final Bitmap[] bmp = new Bitmap[1];
        final String imageName = name;
        class GetDataJSON extends AsyncTask<Void, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(Void... params) {


                try {
                    URL_IMAGE = new URL(TAG_IMAGE+imageName);
                    HttpURLConnection connection = (HttpURLConnection)URL_IMAGE.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bmp[0] = BitmapFactory.decodeStream(input);

                    Log.e("1",URL_IMAGE.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return bmp[0];
            }

            @Override
            protected void onPostExecute(Bitmap bmp){
                if (isCancelled()) {
                    bmp = null;
                }

                image.setImageBitmap(bmp);

            }



        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

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
