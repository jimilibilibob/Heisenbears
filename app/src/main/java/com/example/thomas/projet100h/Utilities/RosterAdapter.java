package com.example.thomas.projet100h.Utilities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thomas.projet100h.R;
import com.example.thomas.projet100h.entities.Utilisateur;

import java.util.List;


public class RosterAdapter extends BaseAdapter  {

    private final Context c;
    private final List<Utilisateur> users;
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



            TextView poste = (TextView) gridView.findViewById(R.id.poste);
            poste.setText(users.get(position).getPoste());
        } else {
            gridView = convertView;
        }

        return gridView;

    }
}

