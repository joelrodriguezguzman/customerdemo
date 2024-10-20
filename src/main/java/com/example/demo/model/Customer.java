package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name") 
    private String name;
    @Column(name = "mname") 
    private String mname;
    @Column(name = "lname")
    private String lname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }
    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        if (email.contains("@"))  {
            return email;
        } else {
            throw new IllegalArgumentException("Email is not valid.");
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        if (phone.matches("\\d{7,}") && phone.matches("[^-]*")) {
            return phone;
        } else {
            throw new IllegalArgumentException("Phone number must contain a minimum of 7 numbers and no letters, dashes are allowed.");
        }
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

