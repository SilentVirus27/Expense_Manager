package com.expence.em;

public class User {
    public String name, email,contact;

    public User() {
    }

    public User(String name, String email,String contact) {
        this.name = name;
        this.email = email;
        this.contact=contact;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
}