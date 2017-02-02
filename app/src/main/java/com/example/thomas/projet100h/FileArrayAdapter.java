package com.example.thomas.projet100h;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FileArrayAdapter extends ArrayAdapter<Publication> {
    private Context c;
    private int id;
    private List<Publication> items;
    ImageView image;
    private Bitmap bmp;

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

            if (o.getIdMedia() == 1) {
                layout3.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
                String texte = o.getTexte().toString();
                String title = o.getMedia().toString();
                if (texte != null && title != null) {
                    texte1.setText(texte);
                    titre.setText(title);
                }
                if (texte != null && title == null) {
                    titre.setVisibility(View.GONE);
                    texte1.setText(texte);
                }
                if (texte == null && title != null) {
                    texte1.setVisibility(View.GONE);
                    titre.setText(title);
                }

            }
            if (o.getIdMedia() == 2) {
                layout1.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                String texte = o.getTexte().toString();
                String url = o.getMedia().toString();
                if (texte != null && url != null) {
                    texte2.setText(texte);
                }
                if (texte != null && url == null) {
                    image.setVisibility(View.GONE);
                    texte2.setText(texte);
                }
                if (texte == null && url != null) {
                    texte2.setVisibility(View.GONE);


                }
            }
            if (o.getIdMedia() == 3) {
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.GONE);
                layout3.setVisibility(View.VISIBLE);
                String texte = o.getTexte().toString();
                String url = o.getMedia().toString();
                if (texte != null && url != null) {
                    texte3.setText(texte);
                    int imageResource = c.getResources().getIdentifier(url, null, c.getPackageName());
                    Drawable image2 = c.getResources().getDrawable(imageResource);
                    image.setImageDrawable(image2);
                }
                if (texte != null && url == null) {
                    video.setVisibility(View.GONE);
                    texte3.setText(texte);
                }
                if (texte == null && url != null) {
                    texte3.setVisibility(View.GONE);
                    int videoResource = c.getResources().getIdentifier(url, null, c.getPackageName());
                    Movie movie = c.getResources().getMovie(videoResource);
                }
            }

        }

        return v;
    }






}
