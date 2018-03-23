package com.codeclan.models;

import java.util.HashSet;
import java.util.Set;

public class User {

    private int id;
    private String username;
    private Set<Advert> adverts;

    public User() {
    }

    public User(String username) {
        this.username = username;
        this.adverts = new HashSet<Advert>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Advert> getAdverts() {
        return adverts;
    }

    public void setAdverts(Set<Advert> adverts) {
        this.adverts = adverts;
    }
}
