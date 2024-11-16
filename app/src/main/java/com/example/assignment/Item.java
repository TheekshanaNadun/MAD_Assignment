package com.example.assignment;

public class Item {
    private int id;     // ID of the item
    private String name; // Name of the item

    // Constructor to initialize the id and name
    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Optional: You can override toString() to display item information if needed
    @Override
    public String toString() {
        return "Item{id=" + id + ", name='" + name + "'}";
    }
}
