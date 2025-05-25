package org.example.filterDecorators;

import org.example.entities.ListType;
import org.example.entities.Property;

import java.util.List;
import java.util.stream.Collectors;

public class ListTypeFilter implements PropertyFilter {
    private final ListType requiredType;

    public ListTypeFilter(String listingType) {
        this.requiredType = ListType.valueOf(listingType.toUpperCase());
    }

    @Override
    public List<Property> apply(List<Property> properties) {
        return properties.stream()
                .filter(p -> p.getListType() == requiredType)
                .collect(Collectors.toList());
    }
}
