package com.revature.models;


import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity //makes class a db entity
@Table(name = "users")
public class User {


    @Id //makes this the PK in the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) //makes id auto increment
    private int userId;

    //make all fields but role have to be filled out (not null)
    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true) //makes username need to be unique
    private String username;

    @Column(nullable = false)
    private String password;

    private String role = "employee"; //sets default role as user for the table

    public User() {
    }

    public User(int userId, String firstname, String lastname, String username, String password, String role) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
