package com.example.mohameddhiahachem.bonplanapp.Entity;

public class PlanInfo {

    private int id;
    private String description;
    private String startDay;
    private String finalDay;
    private String startTime;
    private String finalTime;
    private String name;
    private String address;
    private String city;
    private String governorate;
    private String image;
    private String telephone;
    private String email;

    public PlanInfo(int id, String description, String startDay, String finalDay, String startTime, String finalTime, String name, String address, String city, String governorate, String image, String telephone, String email) {
        this.id = id;
        this.description = description;
        this.startDay = startDay;
        this.finalDay = finalDay;
        this.startTime = startTime;
        this.finalTime = finalTime;
        this.name = name;
        this.address = address;
        this.city = city;
        this.governorate = governorate;
        this.image = image;
        this.telephone = telephone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getFinalDay() {
        return finalDay;
    }

    public void setFinalDay(String finalDay) {
        this.finalDay = finalDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PlanInfo{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", startDay='" + startDay + '\'' +
                ", finalDay='" + finalDay + '\'' +
                ", startTime='" + startTime + '\'' +
                ", finalTime='" + finalTime + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", governorate='" + governorate + '\'' +
                ", image='" + image + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
