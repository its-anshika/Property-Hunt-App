package org.example.filterDecorators;

import org.example.entities.Property;

import java.util.List;
import java.util.stream.Collectors;

public class PriceRangeFilter implements PropertyFilter{
        private final double min;
        private final double max;

        public PriceRangeFilter(String priceRange) {
            String[] parts = priceRange.split("-");
            this.min = Double.parseDouble(parts[0]);
            this.max = (parts.length > 1) ? Double.parseDouble(parts[1]) : Double.MAX_VALUE;
        }

        @Override
        public List<Property> apply(List<Property> properties) {
            return properties.stream()
                    .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                    .collect(Collectors.toList());
        }
    }

