package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShoppingItemListTest {
    private ShoppingItemList shoppingList;

    @Before
    public void setUp() {
        shoppingList = new ShoppingItemList("Groceries");
    }

    @Test
    public void testGetListName() {
        assertEquals("Groceries", shoppingList.getListName());
    }

    @Test
    public void testSetListName() {
        shoppingList.setListName("New Groceries");
        assertEquals("New Groceries", shoppingList.getListName());
    }

    @Test
    public void testAddItem() {
        ItemShopped item = new ItemShopped("Milk", 1.5, 2);
        shoppingList.addItem(item);
        assertEquals(1, shoppingList.getList().size());
        assertEquals("Milk", shoppingList.getList().get(0).getName());
    }

    @Test
    public void testGetTotPrice() {
        ItemShopped item1 = new ItemShopped("Milk", 1.5, 2);
        ItemShopped item2 = new ItemShopped("Bread", 2.0, 1);
        shoppingList.addItem(item1);
        shoppingList.addItem(item2);
        assertEquals(5.0, shoppingList.getTotPrice(), 0.0);
    }

    @Test
    public void testSetCompletion() {
        shoppingList.setCompletion(true);
        assertTrue(shoppingList.getCompletion());
    }

    @Test
    public void testGetCompletion() {
        assertFalse(shoppingList.getCompletion());
    }

    @Test
    public void testGetList() {
        ItemShopped item = new ItemShopped("Milk", 1.5, 2);
        shoppingList.addItem(item);
        ArrayList<ItemShopped> list = shoppingList.getList();
        assertEquals(1, list.size());
        assertEquals("Milk", list.get(0).getName());
    }

    @Test
    public void testToJson() {
        ItemShopped item = new ItemShopped("Milk", 1.5, 2);
        shoppingList.addItem(item);

        JSONObject json = shoppingList.toJson();
        assertEquals("Groceries", json.getString("name"));
        assertFalse(json.getBoolean("completion"));

        JSONArray items = json.getJSONArray("items");
        assertEquals(1, items.length());

        JSONObject itemJson = items.getJSONObject(0);
        assertEquals("Milk", itemJson.getString("name"));
        assertEquals(1.5, itemJson.getDouble("price"), 0.0);
        assertEquals(2, itemJson.getInt("amount"));
        assertFalse(itemJson.getBoolean("purchased"));
    }
}
