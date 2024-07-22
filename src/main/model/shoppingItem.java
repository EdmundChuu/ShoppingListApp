package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a shopping item
public class shoppingItem implements Writable {
    private String name;
    private boolean purchased;
    private double price;
    private int amount;

    // Constructor
    public shoppingItem(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.purchased = false;
    }

    // EFFECTS: returns this shoppingItem as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("amount", amount);
        json.put("purchased", purchased);
        return json;
    }

    // Getters and setters
    public String getName() {
        return this.name;
    }

    public void setName(String settedName) {
        this.name = settedName;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double settedPrice) {
        this.price = settedPrice;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int settedAmount) {
        this.amount = settedAmount;
    }

    public boolean getPurchased() {
        return this.purchased;
    }

    public void setPurchased(boolean settedPurchased) {
        this.purchased = settedPurchased;
    }
}