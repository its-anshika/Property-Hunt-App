package org.example;

import org.example.entities.Property;
import org.example.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Database {
    private static final Database instance = new Database();
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Property> properties = new HashMap<>();

    // Map to keep track of each user's shortlisted properties
    private final Map<String, Set<Property>> userShortlistMap = new HashMap<>();

    private Database() {}

    public static Database getInstance() {
        return instance;
    }

    public Map<String, User> getUsers() {
        return users;
    }
    public Map<String, Property> getProperties() {
        return properties;
    }

    public Map<String, Set<Property>> getUserShortlistMap() {
        return userShortlistMap;
    }
}
