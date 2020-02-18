package com.example.mohameddhiahachem.bonplanapp.Entity;

import java.io.StringReader;

public class Product {

    private int id;
    private String name;
    private String image;
    private String price;
    private String address;
    private String city;
    private String governorate;

    public Product(int id, String name, String image, String price, String address, String city, String governorate) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
