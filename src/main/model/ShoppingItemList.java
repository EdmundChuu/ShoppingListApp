package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a shopping list with a name and items
public class ShoppingItemList implements Writable {
    private String name;
    private boolean completion;
    private ArrayList<ItemShopped> theList;

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
        for (ItemShopped item : theList) {
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
        for (ItemShopped si : this.theList) {
            totPrice += si.getPrice() * si.getAmount();
        }
        return totPrice;
    }

    public void setCompletion(boolean settedCompletion) {
        this.completion = settedCompletion;
        EventLog.getInstance().logEvent(new Event("Loaded Shopping List"));
    }

    public boolean getCompletion() {
        EventLog.getInstance().logEvent(new Event("Saved Shopping List"));
        return this.completion;  
    }

    public ArrayList<ItemShopped> getList() {
        return this.theList;
    }

    public void addItem(ItemShopped item) {
        this.theList.add(item);
        EventLog.getInstance().logEvent(new Event("Added " + item.getName() + " to shopping list"));
    }
}
