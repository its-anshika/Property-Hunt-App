package org.example;

import org.example.entities.Property;
import org.example.entities.User;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static final Database instance = new Database();
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Property> properties = new HashMap<>();

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
}
