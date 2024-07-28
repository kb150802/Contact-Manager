package com.app.ContactManager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CONTACT")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cId;
    private String name;
    private String secondName;
    private String work;
    private String phone;
    private String email;
    private String image;
    @Column(length = 50000)
    private String description;
    @ManyToOne
    private User user;

    public Contact(String name, String secondName, String work, String phone, String email, String image, String description) {
        this.name = name;
        this.secondName = secondName;
        this.work = work;
        this.phone = phone;
        this.email = email;
        this.image = image;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
