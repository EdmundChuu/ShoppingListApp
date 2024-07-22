package model;

import model.ShoppingItemList;
import model.shoppingItem;
import persistence.JsonReader;
import persistence.JsonWriter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            ShoppingItemList shoppingItemList = new ShoppingItemList("Test List");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.write(shoppingItemList);
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyShoppingList() {
        try {
            ShoppingItemList shoppingItemList = new ShoppingItemList("Test List");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyShoppingList.json");
            writer.write(shoppingItemList);

            JsonReader reader = new JsonReader("./data/testWriterEmptyShoppingList.json");
            shoppingItemList = reader.read();
            assertEquals("Test List", shoppingItemList.getListName());
            assertFalse(shoppingItemList.getCompletion());
            assertEquals(0, shoppingItemList.getList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralShoppingList() {
        try {
            ShoppingItemList shoppingItemList = new ShoppingItemList("Test List");
            shoppingItemList.addItem(new shoppingItem("Apple", 1.0, 3));
            shoppingItemList.addItem(new shoppingItem("Banana", 0.5, 5));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralShoppingList.json");
            writer.write(shoppingItemList);

            JsonReader reader = new JsonReader("./data/testWriterGeneralShoppingList.json");
            shoppingItemList = reader.read();
            assertEquals("Test List", shoppingItemList.getListName());
            assertFalse(shoppingItemList.getCompletion());
            List<shoppingItem> items = shoppingItemList.getList();
            assertEquals(2, items.size());
            checkShoppingItem("Apple", 1.0, 3, false, items.get(0));
            checkShoppingItem("Banana", 0.5, 5, false, items.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
