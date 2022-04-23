package com.example.myapplication.model;

import java.util.Set;

public class User {

    private Long id;
    private String login;
    private String password;
    private String salt;
    private Set<Dictionary> dictionaries;

    public User() {
    }

    public User(String login, String password, String salt) {
        this.login = login;
        this.password = password;
        this.salt = salt;
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

    public Set<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(Set<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
