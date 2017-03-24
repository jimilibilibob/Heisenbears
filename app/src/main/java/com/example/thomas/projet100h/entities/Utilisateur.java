package com.example.thomas.projet100h.entities;





/** Entities Utilisateur, gère l'utilisateur utilisant l'aplication  **/
public class Utilisateur {

    private String idFacebook;
    private Integer idStatut;
    private String nom;
    private String prenom;

    /** Créateur d'un utilisateur **/
    public Utilisateur(String idFacebook, Integer idStatut,
                       String nom, String prenom ) {
        super();
        this.idFacebook = idFacebook;
        this.idStatut = idStatut;
        this.nom = nom;
        this.prenom = prenom;
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
}