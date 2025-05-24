package org.example.services;
import org.example.Database;
import org.example.entities.Property;
import org.example.entities.User;

import java.util.*;

public class ShortlistService {
    // Map to keep track of each user's shortlisted properties
    private final Map<String, Set<Property>> userShortlistMap = new HashMap<>();
    Database db;
    PropertyService propertyService;
    public ShortlistService(Database db, PropertyService propertyService){
        this.propertyService=propertyService;
        this.db = db;
    }
    // Add a property to the user's shortlist
    public boolean addToShortlist(User user, String propertyId) {
        Property property= propertyService.getPropertyById(propertyId);
        userShortlistMap
                .computeIfAbsent(user.getId(), k -> new HashSet<>())
                .add(property);
        return true;
    }

    // Remove a property from user's shortlist (optional)
    public void removeFromShortlist(String userId, Property property) {
        Set<Property> shortlist = userShortlistMap.get(userId);
        if (shortlist != null) {
            shortlist.remove(property);
        }
    }

    // Get all shortlisted properties for a user (including sold)
    public void viewShortlistedProperties(User user) {
        Set<Property> shortlist = userShortlistMap.getOrDefault(user.getId(), Collections.emptySet());
        propertyService.printProperties(new ArrayList<>(shortlist));
    }
}
