package com.example.thomas.projet100h.entities;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.thomas.projet100h.Activity.Acceuil;
import com.example.thomas.projet100h.R;
import com.example.thomas.projet100h.Utilities.FileArrayAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.thomas.projet100h.Activity.pageConnection.user;

/** Entities Utilisateur, gère l'utilisateur utilisant l'aplication  **/
public class Utilisateur {

    private String idFacebook;
    private Integer idStatut;
    private String nom;
    private String prenom;
    private int numero;
    private int poid;
    private int taille;
    private String posteS;
    private int posteI;
    private final static String URL_STRING = "http://lowcost-env.pq8h39sfav.us-west-2.elasticbeanstalk.com/poste/";
    private String img;
    private Bitmap bitmap;



    /** Créateur d'un utilisateur **/
    public Utilisateur(String idFacebook, Integer idStatut,
                        String nom, String prenom ) {
        super();
        this.idFacebook = idFacebook;
        this.idStatut = idStatut;
        this.nom = nom;
        this.prenom = prenom;
        this.numero = 0;
        this.poid = 0;
        this.taille = 0;
        this.posteI = 0;
        this.posteS = "aucun";
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Utilisateur(String idFacebook, Integer idStatut,
                       String nom, String prenom, int numero, int taille, int poid, int posteI ) {
        super();
        this.idFacebook = idFacebook;
        this.idStatut = idStatut;
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;

        this.poid = poid;
        this.taille = taille;
        this.posteI = posteI;
        this.posteS = "aucun";
    }

    public Utilisateur(String idFacebook, Integer idStatut,
                       String nom, String prenom, int numero, int taille, int poid, String posteS, String img ) {
        super();
        this.idFacebook = idFacebook;
        this.idStatut = idStatut;
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.poid = poid;
        this.taille = taille;
        this.posteS = posteS;
        this.img=img;
    }

    /** Getter @return idFacebook id facebook de l'utilisateur**/
    public String getIdFacebook() {
        return idFacebook;
    }

    /** Getter @return idStatut id du statut de l'utilisateur**/
    public Integer getIdStatut() {
        return idStatut;
    }

    /** Getter @return nom **/
    public String getNom() {
        return nom;
    }

    /** Getter @return prenom**/
    public String getPrenom() {
        return prenom;
    }

    /** Setter @param idFacebook nouvel id facebokk**/
    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    /** Setter @param idStatut nouvel id statut**/
    public void setIdStatut(Integer idStatut) {
        this.idStatut = idStatut;
    }

    /** Setter @param nom nouveau nom**/
    public void setNom(String nom) {
        this.nom = nom;
    }

    /** Setter @param prenom nouveau prenom**/
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPoste() {
        return posteS;
    }

    public int getPoid() {
        return poid;
    }

    public int getTaille() {
        return taille;
    }

    public int getNumero() {
        return numero;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}