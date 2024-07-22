package persistence;

import model.shoppingItem;
import model.ShoppingItemList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Reads JSON data from a file
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads JSON data from file and returns it as a ShoppingItemList
    public ShoppingItemList read() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        JSONObject json = new JSONObject(sb.toString());
        return parseShoppingItemList(json);
    }

    // EFFECTS: parses JSON object to create a ShoppingItemList
    private ShoppingItemList parseShoppingItemList(JSONObject json) {
        String name = json.getString("name");
        ShoppingItemList shoppingItemList = new ShoppingItemList(name);
        boolean completion = json.getBoolean("completion");
        shoppingItemList.setCompletion(completion);
        addItems(shoppingItemList, json.getJSONArray("items"));
        return shoppingItemList;
    }

    // MODIFIES: shoppingItemList
    // EFFECTS: adds items from JSON array to shoppingItemList
    private void addItems(ShoppingItemList shoppingItemList, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            String name = nextItem.getString("name");
            double price = nextItem.getDouble("price");
            int amount = nextItem.getInt("amount");
            shoppingItem item = new shoppingItem(name, price, amount);
            item.setPurchased(nextItem.getBoolean("purchased"));
            shoppingItemList.getList().add(item);
        }
    }
}
