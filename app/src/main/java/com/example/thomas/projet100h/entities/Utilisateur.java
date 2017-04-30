package com.example.thomas.projet100h.entities;


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
    private final static String URL_STRING = "http://192.168.1.16:8080/heisenbears/poste/";

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
        getPosteString(posteI);
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
        getPosteString(posteI);
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

    public void getPosteString(int pos){
        /** Class qui récupere les pubications en question, cette class descend de Asyntask, elle ne s'éxécute pas dans le même
         * timing que les autres activitées visibles**/
        class GetDataJSON extends AsyncTask<String, Void, String> {
            private int pos;
            private String  post;
            public GetDataJSON(int pos) {
                this.pos = pos;
            }



            /** Tache qui s'éxécutera au lancement de la class, elle s'éxécute
             * @param params prend en parametre une liste de string
             * @return string return le resultat de la requete en string**/
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                /**Méthode GET, prend en paramètre l'URL du webservice  **/
                Log.e("POSTE_URL",URL_STRING+posteI );
                HttpGet httpget = new HttpGet(URL_STRING+posteI);
                httpget.setHeader("Content-type", "application/json");
                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httpget);
                    HttpEntity entity = response.getEntity();
                    inputStream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    Log.e("Error"," Error in getDataList");
                }
                finally {
                    try{
                        if(inputStream != null)inputStream.close();
                    }catch(Exception squish){
                        Log.e("Error"," Error in getDataList");
                    }
                }
                return result;
            }

            /** S'éxécute à la suite du doInBackGround
             * @param result prend en paramètre le result renvoyé par le doInBackGround **/
            @Override
            protected void onPostExecute(String result){
                try {
                    /** Transforme de result en une list de Json qui seront associé a une publication pour etre en suite retourné
                     * au FileArrayAdapter **/
                    JSONObject jsonObj = new JSONObject(result);
                    post = jsonObj.getString("libelle");
                    posteS = post;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetDataJSON g = new GetDataJSON(pos);
        g.execute();
    }
}