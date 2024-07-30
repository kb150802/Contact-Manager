package com.app.ContactManager.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"USER\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uId;

    @Column(nullable = false)
    @NotBlank(message = "Missing required parameter name")
    private String name;

    @Email(message = "Email id is invalid")
    @NotBlank(message = "Missing required parameter email")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Missing required parameter password")
    private String password;


    private String role;
    private String imageUrl;

    @Column(length = 500)
    private String about;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() {
        return contacts;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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


    public User() {
    }

    public User(String password, String email, String name, String role, String imageUrl, String about,  List<Contact> contacts) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
        this.imageUrl = imageUrl;
        this.about = about;
        this.contacts = contacts;
    }
}
