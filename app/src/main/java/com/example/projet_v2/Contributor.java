package com.example.projet_v2;

public class Contributor {
    private String email;
    private String login;



    public Contributor(String email) {
        this.email = email;
    }

    public Contributor() {
    }

    public Contributor(String login, String email) {
        this.email = email;
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


}
