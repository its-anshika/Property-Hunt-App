package org.example.filterDecorators;

import org.example.entities.Property;

import java.util.List;

public interface PropertyFilter {
    List<Property> apply(List<Property> properties);
}
