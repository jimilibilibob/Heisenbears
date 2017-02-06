package com.example.thomas.projet100h.TexteViewAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.thomas.projet100h.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class FileArrayAdapter extends ArrayAdapter<Publication> {
    private Context c;
    private int id;
    private List<Publication> items;
    ImageView image;
    private static Bitmap bmp;

    public FileArrayAdapter(Context context, int textViewResourceId,
                            List<Publication> objects, int idStatut) {
        super(context, textViewResourceId, objects);
        c = context;
        id = textViewResourceId;
        items = objects;


    }

    public Publication getItem(int i)
    {
        return items.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item, null);
        }

        final Publication o = items.get(position);
        TextView texte1 = (TextView) v.findViewById(R.id.texte1);
        TextView titre = (TextView) v.findViewById(R.id.titre);

        TextView texte2 = (TextView) v.findViewById(R.id.texte2);
         image = (ImageView) v.findViewById(R.id.image);

        TextView texte3 = (TextView) v.findViewById(R.id.texte3);
        VideoView video = (VideoView) v.findViewById(R.id.video);

        View layout1 =  v.findViewById(R.id.layout1);
        View layout2 =  v.findViewById(R.id.layout2);
        View layout3 =  v.findViewById(R.id.layout3);


        if (o != null) {

            String texte = o.getTexte();
            String media = o.getMedia();

            if (o.getIdMedia() == 1) {
                layout3.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
                if (!texte.equals("null") && !media.equals("null")) {
                        texte1.setText(texte);
                    titre.setText(media);
                }
                if (!texte.equals("null") && media.equals("null")) {
                    titre.setVisibility(View.GONE);
                    texte1.setText(texte);
                }
                if (texte.equals("null") && !media.equals("null")) {
                    texte1.setVisibility(View.GONE);
                    titre.setText(media);
                }

            }
            if (o.getIdMedia() == 2) {
                layout1.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                if (!texte.equals("null") && !media.equals("null")) {
                    texte2.setText(texte);


                }
                if (!texte.equals("null") && media.equals("null")) {
                    image.setVisibility(View.GONE);
                    texte2.setText(texte);
                }
                if (texte.equals("null") && !media.equals("null")) {
                    texte2.setVisibility(View.GONE);


                }
            }
            if (o.getIdMedia() == 3) {
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.GONE);
                layout3.setVisibility(View.VISIBLE);
                if (!texte.equals("null") && !media.equals("null")) {
                    texte3.setText(texte);
                    int imageResource = c.getResources().getIdentifier(media, null, c.getPackageName());
                    Drawable image2 = c.getResources().getDrawable(imageResource);
                    image.setImageDrawable(image2);
                }
                if (!texte.equals("null") && media.equals("null")) {
                    video.setVisibility(View.GONE);
                    texte3.setText(texte);
                }
                if (texte.equals("null") && !media.equals("null")) {
                    texte3.setVisibility(View.GONE);
                    int videoResource = c.getResources().getIdentifier(media, null, c.getPackageName());
                    Movie movie = c.getResources().getMovie(videoResource);
                }
            }

        }

        return v;
    }






    public static Bitmap getBitmapFromURL() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL("http://192.168.1.16/projet100h/img/logoAppli.jpg");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    bmp = myBitmap;
                } catch (IOException e) {
                    // Log exception
                    return null;
                }
                return null;

            }


        }
        return bmp;
    }



}
