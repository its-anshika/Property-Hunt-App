package org.example.services;

import org.example.Database;
import org.example.entities.Property;
import org.example.entities.User;
import org.example.filterDecorators.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyService {
    private final UserService userService;
    Database db;
    public PropertyService(Database db, UserService userService) {
        this.db = db;
        this.userService = userService;
    }

    // ✅ List a property
    public void listProperty(String location, Integer price, Double size,
                             Integer rooms, String listingTypeStr) {
        User user = userService.getActiveUser();
        if (user == null) {
            System.out.println("No active user. Please login first.");
            return;
        }
        Property property = PropertyFactory.create(
                user.getName(), location, price, size, rooms, listingTypeStr);
        db.getProperties().put(property.getId(), property);
        System.out.println("Property listed successfully.");
    }

    public void printProperties(List<Property> props) {
        if (props.isEmpty()) {
            System.out.println("No properties found.");
            return;
        }

        for (Property p : props) {
            System.out.printf("ID: %s | Owner: %s | Location: %s | Price: %d | Size: %s | Rooms: %d | Type: %s | Status: %s\n",
                    p.getId(), p.getOwner(), p.getLocation(),
                    p.getPrice(), p.getSize(), p.getRooms(),
                    p.getListType().name(), p.isSold() ? "Sold" : "Available");
        }
    }

    // ✅ View listed properties
    public void viewListedProperties() {
        User user = userService.getActiveUser();
        if (user == null) {
            System.out.println("Please login to view listed properties.");
            return;
        }

        List<Property> listed = db.getProperties().values().stream()
                .filter(p -> p.getOwner().equalsIgnoreCase(user.getName()))
                .collect(Collectors.toList());

        printProperties(listed);
    }

    public List<Property> search(
            String location,         // exact match, case-insensitive, or null if ignore
            String priceRange,       // e.g. "1000-5000" or "3000", or null
            String listingType,      // "SELL", "RENT", or null
            String sizeRange,        // e.g. "500-1000" or "600", or null
            String rooms,            // "1", "2", "3", or null
            String sortBy            // "price", "size", or null (no sort)
    ) {
        List<Property> result = new ArrayList<>(db.getProperties().values());
        List<PropertyFilter> filters = new ArrayList<>();

        if (location != null && !location.isBlank()) {
            filters.add(new LocationFilter(location));
        }
        if (priceRange != null && !priceRange.isBlank()) {
            filters.add(new PriceRangeFilter(priceRange));
        }
        if(sizeRange != null && !sizeRange.isBlank()) {
            filters.add(new SizeRangeFilter(sizeRange));
        }
        if(rooms != null && !rooms.isBlank()) {
            filters.add(new RoomsFilter(rooms));
        }
        if(listingType != null && !listingType.isBlank()) {
            filters.add(new ListTypeFilter(listingType));
        }
        // Apply all filters
        for (PropertyFilter filter : filters) {
            result = filter.apply(result);
        }
//        Stream<Property> stream = result.stream();
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
//        List<Property> result = stream.collect(Collectors.toList());

        // Sort
        if ("price".equalsIgnoreCase(sortBy)) {
            result.sort(Comparator.comparingInt(Property::getPrice));
        } else if ("size".equalsIgnoreCase(sortBy)) {
            result.sort(Comparator.comparingDouble(Property::getSize));
        }

        return result;
    }

    // Utility to parse "X-Y" or "X" (means >= X)
//    private double[] parseRange(String range) {
//        String[] parts = range.split("-");
//        double low = Double.parseDouble(parts[0]);
//        double high = (parts.length > 1) ? Double.parseDouble(parts[1]) : Double.MAX_VALUE;
//        return new double[]{low, high};
//    }
//
//    private int[] parseIntRange(String range) {
//        String[] parts = range.split("-");
//        int low = Integer.parseInt(parts[0]);
//        int high = (parts.length > 1) ? Integer.parseInt(parts[1]) : Integer.MAX_VALUE;
//        return new int[]{low, high};
//    }

    public Property getPropertyById(String propertyId) {
        return db.getProperties().get(propertyId);
    }

    public void markSold(String propertyId) {
        getPropertyById(propertyId).setSold(true);
    }

    public List<Property> getPropertiesByOwner(User activeUser) {
        if (activeUser == null) return Collections.emptyList();

        String ownerName = activeUser.getName(); // or getId() if you have an ID field

        return db.getProperties().values()
                .stream()
                .filter(p -> p.getOwner().equals(ownerName))
                .toList();
    }
}
