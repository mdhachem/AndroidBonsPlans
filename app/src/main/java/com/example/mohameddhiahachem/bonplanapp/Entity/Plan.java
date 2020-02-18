package com.example.mohameddhiahachem.bonplanapp.Entity;

public class Plan {

    private int id;
    private String name;
    private String description;
    private String address;
    private String image;
    private String city;
    private String governorate;

    public Plan(int id, String name, String description, String address, String image, String city, String governorate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.image = image;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        return "Plan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", city='" + city + '\'' +
                ", governorate='" + governorate + '\'' +
                '}';
    }
}
