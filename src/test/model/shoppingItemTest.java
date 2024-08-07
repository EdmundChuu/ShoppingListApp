package model;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class shoppingItemTest {
    private ItemShopped item;

    @Before
    public void setUp() {
        item = new ItemShopped("Milk", 1.5, 2);
    }

    @Test
    public void testGetName() {
        assertEquals("Milk", item.getName());
    }

    @Test
    public void testSetName() {
        item.setName("Bread");
        assertEquals("Bread", item.getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(1.5, item.getPrice(), 0.0);
    }

    @Test
    public void testSetPrice() {
        item.setPrice(2.0);
        assertEquals(2.0, item.getPrice(), 0.0);
    }

    @Test
    public void testGetAmount() {
        assertEquals(2, item.getAmount());
    }

    @Test
    public void testSetAmount() {
        item.setAmount(3);
        assertEquals(3, item.getAmount());
    }

    @Test
    public void testGetPurchased() {
        assertFalse(item.getPurchased());
    }

    @Test
    public void testSetPurchased() {
        item.setPurchased(true);
        assertTrue(item.getPurchased());
    }

    @Test
    public void testToJson() {
        JSONObject json = item.toJson();
        assertEquals("Milk", json.getString("name"));
        assertEquals(1.5, json.getDouble("price"), 0.0);
        assertEquals(2, json.getInt("amount"));
        assertFalse(json.getBoolean("purchased"));

        // Testing with a purchased item
        item.setPurchased(true);
        json = item.toJson();
        assertTrue(json.getBoolean("purchased"));
    }
}
