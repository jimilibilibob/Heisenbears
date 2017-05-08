package com.example.thomas.projet100h.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class PublicationTest {

    private Publication publi;

    @Before
    public void setUp() throws Exception {
        publi = new Publication("media", "texte", 1, 1, true);
    }

    @Test
    public void getMedia() throws Exception {
        String media = "media";
        assertTrue(publi.getMedia().equals(media));
    }

    @Test
    public void getTexte() throws Exception {
        String texte = "texte";
        assertTrue(publi.getTexte().equals(texte));
    }

    @Test
    public void getIdPublication() throws Exception {
        int id = 1;
        assertTrue(publi.getIdPublication() == id);
    }

    @Test
    public void getIdMedia() throws Exception {
        int id = 1;
        assertTrue(publi.getIdMedia() == id);
    }

    @Test
    public void isVisibility() throws Exception {
        assertTrue(publi.isVisibility());
    }

    @Test
    public void setVisibility() throws Exception {
        publi.setVisibility(false);
        assertTrue(!publi.isVisibility());
    }

}