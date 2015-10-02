package com.realdolmen.rdtravel.domain;

/**
 * Created by JSTAX29 on 2/10/2015.
 */
public abstract class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    protected User(){}

    public Long getId() {
        return id;
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
}
