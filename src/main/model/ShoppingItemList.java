package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a shopping list with a name and items
public class ShoppingItemList implements Writable {
    private String name;
    private boolean completion;
    private ArrayList<shoppingItem> theList;

    // Constructor
    public ShoppingItemList(String name) {
        this.name = name;
        this.completion = false;
        this.theList = new ArrayList<>();
    }

    // EFFECTS: returns this ShoppingItemList as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("completion", completion);
        json.put("items", itemsToJson());
        return json;
    }

    // EFFECTS: converts the list of items to a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (shoppingItem item : theList) {
            jsonArray.put(item.toJson());
        }
        return jsonArray;
    }

    // Getters and setters
    public void setListName(String settedName) {
        this.name = settedName;
    }

    public String getListName() {
        return this.name;
    }

    public double getTotPrice() {
        double totPrice = 0;
        for (shoppingItem si : this.theList) {
            totPrice += si.getPrice() * si.getAmount();
        }
        return totPrice;
    }

    public void setCompletion(boolean settedCompletion) {
        this.completion = settedCompletion;
    }

    public boolean getCompletion() {
        return this.completion;
    }

    public ArrayList<shoppingItem> getList() {
        return this.theList;
    }

    public void addItem(shoppingItem item) {
        this.theList.add(item);
    }
}
