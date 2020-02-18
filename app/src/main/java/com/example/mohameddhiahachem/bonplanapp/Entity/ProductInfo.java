package com.example.mohameddhiahachem.bonplanapp.Entity;

import java.io.StringReader;

public class ProductInfo {

    private int id;
    private String name;
    private String description;
    private String image;
    private String price;
    private String telephone;
    private int plan;
    private String email;
    private String address;
    private String city;
    private String governorate;


    public ProductInfo(int id, String name, String description, String image, String price, String telephone, int plan, String email, String address, String city, String governorate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.telephone = telephone;
        this.plan = plan;
        this.email = email;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                ", telephone='" + telephone + '\'' +
                ", plan=" + plan +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", governorate='" + governorate + '\'' +
                '}';
    }
}
