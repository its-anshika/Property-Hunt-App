package org.example.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final String name;
    private final String id;

    private final List<Property> listed = new ArrayList<>();
    private final List<Property> shortlisted = new ArrayList<>();
    private boolean loggedIn;

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.loggedIn = true;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
//    public setName(String name){
//        this.name = name;
    }

