package com.example.thomas.projet100h.entities;


import java.util.Date;



/** Entities Publciation, gère les publications  **/
public class Publication implements Comparable<Publication>{
    private String media;
    private String texte;
    private int idPublication;
    private int idMedia;
    private boolean visibility;

    /** Créateur d'une publication **/
    public Publication(String media, String texte, int idPublication, int idMedia, boolean visibility){
        this.media = media;
        this.texte = texte;
        this.idPublication = idPublication;
        this.idMedia = idMedia;
        this.visibility=visibility;
        this.idPublication = idPublication;
    }

    /** Getter @return media message du média**/
    public String getMedia() {
        return media;
    }
    /** Getter @return texte message du texte**/
    public String getTexte() {
        return texte;
    }
    /** Getter @return idPublication l'id de la publication **/
    public int getIdPublication() {
        return idPublication;
    }
    /** Getter @return idMedia id du média**/
    public int getIdMedia() {
        return idMedia;
    }
    /** Getter @return visibility boolean de visibilité**/
    public boolean isVisibility() {
        return visibility;
    }

    /** Setter @param visibility nouvelle visibilité**/
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**  @param o
     * @return 0**/
    @Override
    public int compareTo(Publication o) {
        return 0;
    }
}
