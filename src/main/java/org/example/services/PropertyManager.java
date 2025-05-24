//package org.example.services;
//
//import org.example.Database;
//import org.example.entities.ListType;
//import org.example.entities.Property;
//import org.example.entities.User;
//
//import javax.xml.crypto.Data;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Stream;
//
//public class PropertyManager {
//    private final UserService userService;
//    private final PropertyService propertyService;
//    private final ShortlistService shortlistService;
//    Database db;
//    public PropertyManager(UserService userService,
//                           PropertyService propertyService,
//                           ShortlistService shortlistService, Database db) {
//        this.userService = userService;
//        this.propertyService = propertyService;
//        this.shortlistService = shortlistService;
//        this.db = db;
//    }
//
//    // User operations
//    public void registerUser(String username) {
//        userService.register(username);
//    }
//
//    public boolean loginUser(String username) {
//        return userService.login(username);
//    }
//
//    public void logoutUser(String username) {
//        userService.logout(username);
//    }
//
//    // Property listing
//    public void listProperty(Property property) {
//        User activeUser = userService.getActiveUser();
//        if (activeUser == null) {
//            System.out.println("No active user logged in.");
//            return;
//        }
//        db.getProperties().put(property.getId(), property);
//        property.setOwner(activeUser.getName());
//        propertyService.addProperty(property);
//    }
//
//    // View all properties listed by current user
//    public List<Property> viewListedProperties() {
//        User activeUser = userService.getActiveUser();
//        if (activeUser == null) {
//            System.out.println("No active user logged in.");
//            return List.of();
//
//        }
//        propertyService.viewListedProperties();
//        return propertyService.getPropertiesByOwner(activeUser);
//    }
//
//    // Shortlist a property by id
//    public boolean shortlistProperty(String propertyId) {
//        User activeUser = userService.getActiveUser();
//        if (activeUser == null) {
//            System.out.println("No active user logged in.");
//            return false;
//        }
//        return shortlistService.addToShortlist(activeUser, propertyId);
//    }
//
//    // View shortlisted properties of active user
//    public List<Property> viewShortlistedProperties() {
//        User activeUser = userService.getActiveUser();
//        if (activeUser == null) {
//            System.out.println("No active user logged in.");
//            return List.of();
//        }
//
//        propertyService.printProperties(shortlistService.getShortlistedProperties(activeUser));
//        return shortlistService.getShortlistedProperties(activeUser);
//    }
//
//    // Mark a property sold
//    public void markPropertySold(String propertyId) {
//        User activeUser = userService.getActiveUser();
//        if (activeUser == null) {
//            System.out.println("No active user logged in.");
//            return;
//        }
//        Property property = propertyService.getPropertyById(propertyId);
//        if (property == null || !property.getOwner().equals(activeUser)) {
//            System.out.println("Property not found or you are not the owner.");
//            return;
//        }
//        propertyService.markSold(propertyId);
//    }
//
//    public List<Property> search(
//            String location,         // exact match, case-insensitive, or null if ignore
//            String priceRange,       // e.g. "1000-5000" or "3000", or null
//            String listingType,      // "SELL", "RENT", or null
//            String sizeRange,        // e.g. "500-1000" or "600", or null
//            String rooms,            // "1", "2", "3", or null
//            String sortBy            // "price", "size", or null (no sort)
//    ) {
//        List<Property> all = new ArrayList<>(db.getProperties().values());
//
//        Stream<Property> stream = all.stream();
//
//        if (location != null && !location.isBlank()) {
//            String locLower = location.toLowerCase();
//            stream = stream.filter(p -> p.getLocation().toLowerCase().equals(locLower));
//        }
//
//        if (priceRange != null && !priceRange.isBlank()) {
//            double[] priceBounds = parseRange(priceRange);
//            stream = stream.filter(p -> p.getPrice() >= priceBounds[0] && p.getPrice() <= priceBounds[1]);
//        }
//
//        if (listingType != null && !listingType.isBlank()) {
//            ListType type = ListType.valueOf(listingType.toUpperCase());
//            stream = stream.filter(p -> p.getListType() == type);
//        }
//
//        if (sizeRange != null && !sizeRange.isBlank()) {
//            int[] sizeBounds = parseIntRange(sizeRange);
//            stream = stream.filter(p -> p.getSize() >= sizeBounds[0] && p.getSize() <= sizeBounds[1]);
//        }
//
//        if (rooms != null && !rooms.isBlank()) {
//            int roomsCount = Integer.parseInt(rooms);
//            stream = stream.filter(p -> p.getRooms() == roomsCount);
//        }
//
//        List<Property> filtered = stream.toList();
//
//        // Sort by price ascending if requested
//        if ("price".equalsIgnoreCase(sortBy)) {
//            filtered.sort(Comparator.comparingDouble(Property::getPrice));
//        }
//        // Or sort by size ascending
//        else if ("size".equalsIgnoreCase(sortBy)) {
//            filtered.sort(Comparator.comparingDouble(Property::getSize));
//        }
//
//        return filtered;
//    }
//
//    // Parses range string like "X-Y" or just "X" (means >= X)
//    private double[] parseRange(String range) {
//        String[] parts = range.split("-");
//        double low = Double.parseDouble(parts[0]);
//        double high = (parts.length > 1) ? Double.parseDouble(parts[1]) : Double.MAX_VALUE;
//        return new double[] { low, high };
//    }
//
//    private int[] parseIntRange(String range) {
//        String[] parts = range.split("-");
//        int low = Integer.parseInt(parts[0]);
//        int high = (parts.length > 1) ? Integer.parseInt(parts[1]) : Integer.MAX_VALUE;
//        return new int[] { low, high };
//    }
//
//}
//
