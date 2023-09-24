package com.example.searchapp.models;

import java.util.List;

public class University {
    public int sno;
    public String name;
    public String country;
    public List<String> web_pages;

    public University(int sno, String name, String country, List<String> web_pages) {
        this.sno = sno;
        this.name = name;
        this.country = country;
        this.web_pages = web_pages;
    }

    public int getSno() {

        return sno;
    }

    public String getName() {

        return name;
    }

    public String getCountry() {

        return country;
    }

    public List<String> getWeb_pages() {
        return web_pages;
    }

    public void setWeb_pages(List<String> web_pages) {
        this.web_pages = web_pages;
    }
}
