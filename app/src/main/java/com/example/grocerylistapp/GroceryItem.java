package com.example.grocerylistapp;

public class GroceryItem {
<<<<<<< HEAD
    // FIX 1: Renamed variable to follow Java conventions (lowercase start)
    private String id;
    private String item; // Was "Item"
    private int quantity;

    // Default constructor is required for Firebase DataSnapshot.getValue(GroceryItem.class)
    public GroceryItem() { }

    // Constructor with updated parameter name for consistency
    public GroceryItem(String id, String item, int quantity) {
        this.id = id;
        this.item = item; // Was "this.Item = Item"
        this.quantity = quantity;
    }

    // --- Getters and Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // FIX 2: Renamed getter and setter to match the 'item' variable
    public String getItem() {
        return item; // Was "return Item"
    }

    public void setItem(String item) {
        this.item = item; // Was "this.Item = Item"
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
=======
    private String id;
    private String name;
    private int quantity;


    public GroceryItem() { }


    public GroceryItem(String id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
>>>>>>> 1dff3c344a2083488f6992bc02c8ba79f81ae847
