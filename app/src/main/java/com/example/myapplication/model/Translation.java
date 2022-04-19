package com.example.myapplication.model;

public class Translation {

    private Long id;
    private String flValue;
    private String slValue;

    public Translation() {
    }

    public Translation(String flValue, String slValue) {
        this.flValue = flValue;
        this.slValue = slValue;
        this.id = (long)0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlValue() {
        return flValue;
    }

    public void setFlValue(String flValue) {
        this.flValue = flValue;
    }

    public String getSlValue() {
        return slValue;
    }

    public void setSlValue(String slValue) {
        this.slValue = slValue;
    }
}
