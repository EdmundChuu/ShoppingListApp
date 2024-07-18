package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShoppingItemListTest {
    private ShoppingItemList shoppingItemList;
    private shoppingItem item1;
    private shoppingItem item2;

    @Before
    public void setUp() {
        shoppingItemList = new ShoppingItemList("Groceries");
        item1 = new shoppingItem("Milk", 1.5, 2);
        item2 = new shoppingItem("Bread", 2.0, 1);
        shoppingItemList.getList().add(item1);
        shoppingItemList.getList().add(item2);
    }

    @Test
    public void testSetListName() {
        shoppingItemList.setListName("Weekly Shopping");
        assertEquals("Weekly Shopping", shoppingItemList.getListName());
    }

    @Test
    public void testGetListName() {
        assertEquals("Groceries", shoppingItemList.getListName());
    }


    @Test
    public void testGetTotPrice() {
        assertEquals(5, shoppingItemList.getTotPrice(), 0.0);
    }

    @Test
    public void testSetCompletion() {
        shoppingItemList.setCompletion(true);
        assertTrue(shoppingItemList.getCompletion());
    }

    @Test
    public void testGetCompletion() {
        assertFalse(shoppingItemList.getCompletion());
        shoppingItemList.setCompletion(true);
        assertTrue(shoppingItemList.getCompletion());
    }

    @Test
    public void testGetList() {
        ArrayList<shoppingItem> list = shoppingItemList.getList();
        assertEquals(2, list.size());
        assertTrue(list.contains(item1));
        assertTrue(list.contains(item2));
    }
}
