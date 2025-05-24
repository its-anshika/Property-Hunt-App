package org.example.services;

import org.example.entities.ListType;
import org.example.entities.Property;

import java.util.UUID;

public class PropertyFactory {
    public static Property create( String owner, String location, Integer price, Double size, Integer rooms, String type) {
            return new Property(owner, location, price, size, rooms, ListType.valueOf(type.toUpperCase()));
    }

}
