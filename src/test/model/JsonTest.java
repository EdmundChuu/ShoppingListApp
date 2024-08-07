package model;

// import model.shoppingItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkShoppingItem(String name, double price, int amount, boolean purchased, ItemShopped item) {
        assertEquals(name, item.getName());
        assertEquals(price, item.getPrice());
        assertEquals(amount, item.getAmount());
        assertEquals(purchased, item.getPurchased());
    }
}
