package com.example.thomas.projet100h.entities;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class UtilisateurTest {

    private Utilisateur user;

    //sera appellé avant chaque Test
    @Before
    public void setUp() throws Exception {
        user = new Utilisateur("1",0,"nom","prenom", 330000,  150, 50, "aucun", "img" );
    }

    @Test
    public void getImg() throws Exception {
        String img = "img";
        assertTrue(user.getImg().equals(img));
    }



    @Test
    public void getIdFacebook() throws Exception {
        String id = "1";
        assertTrue(user.getIdFacebook().equals(id));
    }

    @Test
    public void getIdStatut() throws Exception {
        int id = 0;
        assertTrue(user.getIdStatut() == id);
    }

    @Test
    public void getPoste() throws Exception {
        String poste = "aucun";
        assertTrue(user.getPoste().equals(poste));
    }

    @Test
    public void getPoid() throws Exception {
        int poid = 50;
        assertTrue(user.getPoid() == poid);
    }

    @Test
    public void getTaille() throws Exception {
        int taille = 150;
        assertTrue(user.getTaille() == taille);
    }

    @Test
    public void getNumero() throws Exception {
        int num = 330000;
        assertTrue(user.getNumero() == num);
    }

    @Test
    public void getNom() throws Exception {
        String nom = "nom";
        assertTrue(user.getNom().equals(nom));
    }

    @Test
    public void getPrenom() throws Exception {
        String prenom = "prenom";
        assertTrue(user.getPrenom().equals(prenom));
    }

    @Test
    public void setIdFacebook() throws Exception {
        String id = "2";
        user.setIdFacebook(id);
        assertTrue(user.getIdFacebook().equals(id));
    }

    @Test
    public void setIdStatut() throws Exception {
        int id = 2;
        user.setIdStatut(id);
        assertTrue(user.getIdStatut() == 2);
    }

    @Test
    public void setNom() throws Exception {
        String prenom = "prenom";
        user.setNom(prenom);
        assertTrue(user.getNom().equals(prenom));
    }

    @Test
    public void setPrenom() throws Exception {
        String nom = "nom";
        user.setPrenom(nom);
        assertTrue(user.getPrenom().equals(nom));
    }

    @Test
    public void setImg() throws Exception {
        String img = "img2";
        user.setImg(img);
        assertTrue(user.getImg().equals(img));
    }


}