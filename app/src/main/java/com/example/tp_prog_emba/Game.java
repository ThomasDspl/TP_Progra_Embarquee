package com.example.tp_prog_emba;

public class Game {
    private long id;
    private String nom;
    private String annee;
    private String type;
    private String plateforme;
    private String editeur;
    private String description;
    private String url;

    public Game(String nom, String annee, String type, String plateforme, String editeur, String description, String url) {
        this.nom = nom;
        this.annee = annee;
        this.type = type;
        this.plateforme = plateforme;
        this.editeur = editeur;
        this.description = description;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlateforme() {
        return plateforme;
    }

    public void setPlateforme(String plateforme) {
        this.plateforme = plateforme;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
