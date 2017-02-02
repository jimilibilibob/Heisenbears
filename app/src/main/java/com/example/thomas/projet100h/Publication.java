package com.example.thomas.projet100h;


import java.util.Date;

/**
 * Created by Thomas on 30/01/2017.
 */

public class Publication implements Comparable<Publication>{
    private String media;
    private String texte;
    private Date date;
    private int id;
    private int idMedia;

    public Publication(String media, String texte, Date date, int id, int idMedia){
        this.media = media;
        this.texte = texte;
        this.date = date;
        this.id = id;
        this.idMedia = idMedia;
    }

    public String getMedia() {
        return media;
    }
    public String getTexte() {
        return texte;
    }
    public Date getDate() {
        return date;
    }
    public int getId() {
        return id;
    }
    public int getIdMedia() {
        return idMedia;
    }

    @Override
    public int compareTo(Publication o) {
        return 0;
    }
}
