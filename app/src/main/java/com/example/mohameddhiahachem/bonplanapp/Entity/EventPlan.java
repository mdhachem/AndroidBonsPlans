package com.example.mohameddhiahachem.bonplanapp.Entity;

public class EventPlan {

    private int id;
    private String name;
    private String dateEvent;
    private String description;
    private String image;
    private String address;
    private String city;
    private String governorate;

    public EventPlan(int id, String name, String dateEvent, String description, String image, String address, String city, String governorate) {
        this.id = id;
        this.name = name;
        this.dateEvent = dateEvent;
        this.description = description;
        this.image = image;
        this.address = address;
        this.city = city;
        this.governorate = governorate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }
}
