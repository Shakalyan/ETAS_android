package com.example.myapplication.model;

import java.util.Set;

public class Dictionary {

    private Long id;
    private String name;
    private Set<Translation> translations;

    public Dictionary() {
    }

    public Dictionary(String name) {
        this.name = name;
        this.id = (long)0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Translation> translations) {
        this.translations = translations;
    }
}
