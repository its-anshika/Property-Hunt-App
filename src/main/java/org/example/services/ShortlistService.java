package org.example.services;
import org.example.Database;
import org.example.entities.Property;
import org.example.entities.User;

import java.util.*;

public class ShortlistService {
    Database db;
    PropertyService propertyService;
    public ShortlistService(Database db, PropertyService propertyService){
        this.propertyService=propertyService;
        this.db = db;
    }
    // Add a property to the user's shortlist
    public boolean addToShortlist(User user, String propertyId) {
        Property property= propertyService.getPropertyById(propertyId);
        db.getUserShortlistMap()
                .computeIfAbsent(user.getId(), k -> new HashSet<>())
                .add(property);
        return true;
    }

    // Remove a property from user's shortlist (optional)
    public void removeFromShortlist(String userId, Property property) {
        Set<Property> shortlist = db.getUserShortlistMap().get(userId);
        if (shortlist != null) {
            shortlist.remove(property);
        }
    }

    // Get all shortlisted properties for a user (including sold)
    public void viewShortlistedProperties(User user) {
        Set<Property> shortlist = db.getUserShortlistMap().getOrDefault(user.getId(), Collections.emptySet());
        propertyService.printProperties(new ArrayList<>(shortlist));
    }
}
