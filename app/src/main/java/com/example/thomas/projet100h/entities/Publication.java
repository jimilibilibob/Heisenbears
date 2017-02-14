package com.example.thomas.projet100h.entities;


import java.util.Date;

/**
 * Created by Thomas on 30/01/2017.
 */

public class Publication implements Comparable<Publication>{
    private String media;
    private String texte;
    private Date date;
    private int idPublication;
    private int idMedia;
    private boolean visibility;

    public Publication(String media, String texte, Date date, int idPublication, int idMedia, boolean visibility){
        this.media = media;
        this.texte = texte;
        this.date = date;
        this.idPublication = idPublication;
        this.idMedia = idMedia;
        this.visibility=visibility;
        this.idPublication = idPublication;
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
    public int getIdPublication() {
        return idPublication;
    }
    public int getIdMedia() {
        return idMedia;
    }
    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public int compareTo(Publication o) {
        return 0;
    }
}
