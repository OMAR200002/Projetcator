package com.example.projet_v2;

import java.io.Serializable;

public class Projet implements Serializable {
    private int id;
    private  String titre;
    private String description;
    private Double note;

    public Projet(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }
    public Projet(int id,String title, String description) {
        this.titre = title;
        this.description = description;
    }

    public Projet(int id, String titre, String description, Double note) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.note = note;
    }


    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }
}
