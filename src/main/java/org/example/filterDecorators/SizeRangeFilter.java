package org.example.filterDecorators;

import org.example.entities.Property;

import java.util.List;
import java.util.stream.Collectors;

public class SizeRangeFilter implements PropertyFilter {
    private final int min;
    private final int max;

    public SizeRangeFilter(String sizeRange) {
        String[] parts = sizeRange.split("-");
        this.min = Integer.parseInt(parts[0]);
        this.max = (parts.length > 1) ? Integer.parseInt(parts[1]) : Integer.MAX_VALUE;
    }

    @Override
    public List<Property> apply(List<Property> properties) {
        return properties.stream()
                .filter(p -> p.getSize() >= min && p.getSize() <= max)
                .collect(Collectors.toList());
    }
}
