package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class shoppingItemTest {
    private shoppingItem item;
    
    @Before
    public void setUp() {
        item = new shoppingItem("Milk", 1.5, 2);
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
}
