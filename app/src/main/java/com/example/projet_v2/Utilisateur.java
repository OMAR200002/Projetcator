package com.example.projet_v2;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private int id;
    private String login;
    private String motPass;
    private String email;
    private String status;

    public Utilisateur() {
    }
    public Utilisateur(String login, String motPass) {
        this.login = login;
        this.motPass = motPass;
    }
    public Utilisateur(String login, String motPass, String email) {
        this.login = login;
        this.motPass = motPass;
        this.email = email;
    }
    public Utilisateur(int id,String login, String motPass, String email) {
        this.id = id;
        this.login = login;
        this.motPass = motPass;
        this.email = email;
    }
    public Utilisateur(String login, String motPass, String email, String status) {
        this.id = id;
        this.login = login;
        this.motPass = motPass;
        this.email = email;
        this.status = status;
    }
    public Utilisateur(int id,String login, String motPass, String email, String status) {
        this.id = id;
        this.login = login;
        this.motPass = motPass;
        this.email = email;
        this.status = status;
    }
    //Getters and Setters
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getLogin() {
        return login;
    }
    public String getMotPass() {
        return motPass;
    }
    public String getEmail() {
        return email;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setMotPass(String motPass) {
        this.motPass = motPass;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
