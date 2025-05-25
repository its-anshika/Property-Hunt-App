package org.example.filterDecorators;

import org.example.entities.Property;

import java.util.stream.Collectors;
import java.util.List;

public class LocationFilter implements PropertyFilter {
    private final String location;

    public LocationFilter(String location) {
        this.location = location.toLowerCase();
    }

    @Override
    public List<Property> apply(List<Property> properties) {
        return properties.stream()
                .filter(p -> p.getLocation().toLowerCase().equals(location))
                .collect(Collectors.toList());
    }
}
