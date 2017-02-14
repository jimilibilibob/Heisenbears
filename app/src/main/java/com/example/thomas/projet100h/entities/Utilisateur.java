package com.example.thomas.projet100h.entities;

/**
 * Created by Thomas on 12/02/2017.
 */

import java.math.BigInteger;
import java.sql.Date;

public class Utilisateur {

    private String idFacebook;
    private Integer idStatut;
    private String nom;
    private String prenom;

    public Utilisateur(String idFacebook, Integer idStatut,
                       String nom, String prenom ) {
        super();
        this.idFacebook = idFacebook;
        this.idStatut = idStatut;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getIdFacebook() {
        return idFacebook;
    }


    public Integer getIdStatut() {
        return idStatut;
    }


    public String getNom() {
        return nom;
    }


    public String getPrenom() {
        return prenom;
    }


    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public void setIdStatut(Integer idStatut) {
        this.idStatut = idStatut;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}