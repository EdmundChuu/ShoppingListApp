package persistence;

import model.ItemShopped;
import model.ShoppingItemList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

// Writes JSON data to a file
public class JsonWriter {
    private String destination;

    // EFFECTS: constructs writer to write to the destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of shoppingItemList to file
    public void write(ShoppingItemList shoppingItemList) throws IOException {
        JSONObject json = new JSONObject();
        json.put("name", shoppingItemList.getListName());
        json.put("completion", shoppingItemList.getCompletion());
        json.put("items", itemsToJson(shoppingItemList));

        try (FileWriter file = new FileWriter(destination)) {
            file.write(json.toString(4));
        }
    }

    // EFFECTS: returns items in this shoppingItemList as a JSON array
    private JSONArray itemsToJson(ShoppingItemList shoppingItemList) {
        JSONArray jsonArray = new JSONArray();

        for (ItemShopped item : shoppingItemList.getList()) {
            jsonArray.put(itemToJson(item));
        }
        return jsonArray;
    }

    // EFFECTS: returns a JSON object representing a shopping item
    private JSONObject itemToJson(ItemShopped item) {
        JSONObject json = new JSONObject();
        json.put("name", item.getName());
        json.put("price", item.getPrice());
        json.put("amount", item.getAmount());
        json.put("purchased", item.getPurchased());
        return json;
    }
}
