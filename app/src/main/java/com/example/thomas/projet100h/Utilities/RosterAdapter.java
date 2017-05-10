package com.example.thomas.projet100h.Utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.projet100h.R;
import com.example.thomas.projet100h.entities.Utilisateur;

import org.apache.http.HttpStatus;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.R.attr.bitmap;


public class RosterAdapter extends BaseAdapter  {
    private static String TAG_IMAGE = "http://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-401731657040/roster/";
    private Bitmap bitmap;
    private final Context c;
    private final List<Utilisateur> users;
    private static URL URL_IMAGE;
    private ImageView img;

    public RosterAdapter(Context c, List<Utilisateur> users){
        this.c = c;
        this.users = users;
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(c);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.case_roster, null);
            TextView nom = (TextView) gridView.findViewById(R.id.nom);
            nom.setText(users.get(position).getNom()+" "+users.get(position).getPrenom());

            img = (ImageView) gridView.findViewById(R.id.imageView);

            TextView poste = (TextView) gridView.findViewById(R.id.poste);
            poste.setText(users.get(position).getPoste());

            Image(users.get(position).getImg());
        } else {
            gridView = convertView;
        }

        return gridView;

    }

    private void Image(String name){
        final Bitmap[] bmp = new Bitmap[1];
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


                img.setImageBitmap(bmp);

            }



        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }
}

