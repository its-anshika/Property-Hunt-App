package org.example.filterDecorators;

import org.example.entities.Property;

import java.util.List;
import java.util.stream.Collectors;

public class RoomsFilter implements PropertyFilter {
    private final int requiredRooms;

    public RoomsFilter(String rooms) {
        this.requiredRooms = Integer.parseInt(rooms);
    }

    @Override
    public List<Property> apply(List<Property> properties) {
        return properties.stream()
                .filter(p -> p.getRooms() == requiredRooms)
                .collect(Collectors.toList());
    }
}
