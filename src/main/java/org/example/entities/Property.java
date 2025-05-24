package org.example.entities;

import java.util.UUID;

public class Property {
    private final String id;
    private String ownerName;
    private final String location;
    private final Integer price;
    private final Double size;
    private final int rooms;
    private final ListType type; // SELL or RENT
    private boolean isSold;

    public Property(String name, String location, Integer price, Double size,
                    int rooms, ListType type) {
        this.id = UUID.randomUUID().toString();
        this.ownerName = name;
        this.location = location;
        this.price = price;
        this.size = size;
        this.rooms = rooms;
        this.type = type;
    }

    public boolean isSold() {
        return isSold;
    }
    public void setSold(boolean sold){
        this.isSold = sold;
    }
    public Double getSize() {
        return size;
    }

    public int getRooms() {
        return rooms;
    }

    public String getOwner() {
        return this.ownerName;
    }

    public String getId() {
        return this.id;
    }

    public String getLocation() {
        return this.location;
    }

    public Integer getPrice() {
        return price;
    }

    public ListType getType() {
        return type;
    }

    public ListType getListType() {
        return this.type;
    }

    public void setOwner(String name) {
        this.ownerName=name;
    }

}

