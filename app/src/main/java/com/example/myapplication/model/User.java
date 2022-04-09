package com.example.myapplication.model;

public class User {

    private Long id;
    private String login;
    private String password;


    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.id = (long)0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
