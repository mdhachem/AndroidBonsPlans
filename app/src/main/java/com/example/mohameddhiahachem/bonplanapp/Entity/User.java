package com.example.mohameddhiahachem.bonplanapp.Entity;

public class User {

    private int id;
    private String email;
    private String roles;
    private String password;
    private String firstName;
    private String lastName;
    private String image;
    private String address;
    private String telephone;

    public User(int id ,String email,String password, String first_name, String last_name, String image, String address, String telephone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = first_name;
        this.lastName = last_name;
        this.image = image;
        this.address = address;
        this.telephone = telephone;
    }

    public User(int id, String email, String roles, String password, String firstName, String lastName, String image, String address, String telephone) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.address = address;
        this.telephone = telephone;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
