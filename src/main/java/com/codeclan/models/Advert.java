package com.codeclan.models;

import com.codeclan.enums.CategoryType;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "adverts")
public class Advert {

    private int id;
    private String title;
    private String description;
    private double askingPrice;
    GregorianCalendar startDate;
    Category category;
    private Set<User> users;

    public Advert() {
    }

    public Advert(String title, String description, double askingPrice, GregorianCalendar startDate, Category category) {
        this.title = title;
        this.description = description;
        this.askingPrice = askingPrice;
        this.startDate = startDate;
        this.category = category;
        this.users = new HashSet<User>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price")
    public double getAskingPrice() {
        return askingPrice;
    }

    public void setAskingPrice(double askingPrice) {
        this.askingPrice = askingPrice;
    }

    @Column(name = "start_date")
    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "advert_user",
            inverseJoinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            joinColumns = {@JoinColumn(name = "advert_id", nullable = false, updatable = false)}
    )
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String returnSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        simpleDateFormat.setCalendar(this.startDate);
        String newFormat = simpleDateFormat.format(this.startDate.getTime());


        String[] splitDate = newFormat.split("-");
        Integer days = Integer.parseInt(splitDate[0]);
        Integer month = Integer.parseInt(splitDate[1]);
        Integer year = Integer.parseInt(splitDate[2]);

        String finalDate = days.toString() + "-" + month.toString() + "-" + year.toString();

        return finalDate;
    }

}
