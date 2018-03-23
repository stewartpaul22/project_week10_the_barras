package com.codeclan.models;

import java.util.GregorianCalendar;

public class Advert {

    private int id;
    private String title;
    private String description;
    private double askingPrice;
    GregorianCalendar startDate;
    Category category;
    User user;

    public Advert() {
    }

    public Advert(String title, String description, double askingPrice, GregorianCalendar startDate, Category category) {
        this.title = title;
        this.description = description;
        this.askingPrice = askingPrice;
        this.startDate = startDate;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAskingPrice() {
        return askingPrice;
    }

    public void setAskingPrice(double askingPrice) {
        this.askingPrice = askingPrice;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
