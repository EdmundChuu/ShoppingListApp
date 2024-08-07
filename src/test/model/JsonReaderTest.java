package model;

// import model.ShoppingItemList;
// import model.shoppingItem;
import persistence.JsonReader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ShoppingItemList shoppingItemList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyShoppingList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyShoppingList.json");
        try {
            ShoppingItemList shoppingItemList = reader.read();
            assertEquals("Test List", shoppingItemList.getListName());
            assertFalse(shoppingItemList.getCompletion());
            assertEquals(0, shoppingItemList.getList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralShoppingList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralShoppingList.json");
        try {
            ShoppingItemList shoppingItemList = reader.read();
            assertEquals("Test List", shoppingItemList.getListName());
            assertFalse(shoppingItemList.getCompletion());
            List<ItemShopped> items = shoppingItemList.getList();
            assertEquals(2, items.size());
            checkShoppingItem("Apple", 1.0, 3, false, items.get(0));
            checkShoppingItem("Banana", 0.5, 5, true, items.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
