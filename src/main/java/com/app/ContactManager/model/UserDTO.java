package com.app.ContactManager.model;

import java.util.List;

public class UserDTO {
    private String name;
    private String email;
    private String role;
    private String about;
    private String imageUrl;

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.about = user.getAbout();
        this.imageUrl = user.getImageUrl();
    }

    public UserDTO(String name, String email, String role, String about, String imageUrl, List<Contact> contacts) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.about = about;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
