package ui;

import model.ShoppingItemList;
import model.shoppingItem;
import exceptions.InvalidInputException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ListApp {
    private static final String JSON_STORE = "./data/shoppinglist.json";
    private ShoppingItemList shoppingItemList;
    private Scanner scanner;
    private ArrayList<shoppingItem> shoppingList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public ListApp() {
        shoppingItemList = new ShoppingItemList("Your Shopping List");
        shoppingList = new ArrayList<>(shoppingItemList.getList());
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    private void runApp() {
        boolean running = true;
        while (running) {
            printMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addItem();
                        break;
                    case 2:
                        viewItems();
                        break;
                    case 3:
                        markAsPurchased();
                        break;
                    case 4:
                        deleteItem();
                        break;
                    case 5:
                        clearPurchasedItems();
                        break;
                    case 6:
                        editItem();
                        break;
                    case 7:
                        getTotal();
                        break;
                    case 8:
                        saveToFile();
                        break;
                    case 9:
                        loadFromFile();
                        break;
                    case 0:
                        System.out.println("Thank you! Come back again!");
                        running = false;
                        break;
                    default:
                        System.out.println("Sorry, input not recognized. Please enter a valid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("An error occurred while accessing the file.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nShopping List Menu:");
        System.out.println("1. Add Item");
        System.out.println("2. View Items");
        System.out.println("3. Mark Item as Purchased");
        System.out.println("4. Delete Item");
        System.out.println("5. Clear Purchased Items");
        System.out.println("6. Edit Item");
        System.out.println("7. Get Total Price");
        System.out.println("8. Save to File");
        System.out.println("9. Load from File");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // New methods for saving and loading
    private void saveToFile() throws IOException {
        jsonWriter.write(shoppingItemList);
        System.out.println("Saved shopping list to " + JSON_STORE);
    }

    private void loadFromFile() throws IOException {
        shoppingItemList = jsonReader.read();
        shoppingList = new ArrayList<>(shoppingItemList.getList());
        System.out.println("Loaded shopping list from " + JSON_STORE);
    }

    // MODIFIES: this.shoppingList
    // EFFECTS:  Adds a new item to the shopping list. If input is invalid, throws InvalidInputException.
    private void addItem() throws InvalidInputException {
        try {
            System.out.print("Enter item name: ");
            String name = scanner.nextLine();
            validateName(name);

            System.out.print("Enter item price: ");
            double price = Double.parseDouble(scanner.nextLine());
            validatePrice(price);

            System.out.print("Enter item quantity: ");
            int amount = Integer.parseInt(scanner.nextLine());
            validateAmount(amount);

            shoppingItem newItem = new shoppingItem(name, price, amount);
            shoppingList.add(newItem);
            shoppingItemList.addItem(newItem);
            System.out.println("Item added to the shopping list.");
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input. Price must be a number and quantity must be an integer.");
        }
    }

    // EFFECTS: Displays all items in the shopping list. If the list is empty, indicates it.
    private void viewItems() {
        if (shoppingList.isEmpty()) {
            System.out.println("Shopping list is empty.");
        } else {
            int index = 1;
            for (shoppingItem item : shoppingList) {
                System.out.println(index + ". Name: " + item.getName() + " - " + " Units: " + item.getAmount() 
                        + " - " + " Price: $" + item.getPrice() + " - Purchased: " + item.getPurchased());
                index++;
            }
        }
    }

    // MODIFIES: this.shoppingList
    // EFFECTS:  Marks an item as purchased in the shopping list and prints statement.
    //           If the item is not found, prints a statement.
    private void markAsPurchased() {
        System.out.print("Enter item name to mark as purchased: ");
        String name = scanner.nextLine();
        for (shoppingItem item : shoppingList) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setPurchased(true);
                System.out.println("Item marked as purchased.");
                return;
            }
        }
        System.out.println("Item not found in the shopping list.");
    }

    // MODIFIES: this.shoppingList
    // EFFECTS:  Deletes an item from the shopping list and prints statement.
    //           If the item is not found, prints statement.
    private void deleteItem() {
        System.out.print("Enter item name to delete: ");
        String name = scanner.nextLine();
        for (shoppingItem item : shoppingList) {
            if (item.getName().equalsIgnoreCase(name)) {
                shoppingList.remove(item);
                System.out.println("Item deleted from the shopping list.");
                return;
            }
        }
        System.out.println("Item not found in the shopping list.");
    }

    // MODIFIES: this.shoppingList
    // EFFECTS:  Clears all purchased items from the shopping list.
    private void clearPurchasedItems() {
        shoppingList.removeIf(shoppingItem::getPurchased);
        System.out.println("All purchased items have been cleared from the shopping list.");
    }

    // MODIFIES: this.shoppingList
    // EFFECTS:  Edits an existing item in the shopping list. If the item is not found, indicates so.
    //           If input is invalid, throws InvalidInputException.
    private void editItem() throws InvalidInputException {
        System.out.print("Enter item name to edit: ");
        String name = scanner.nextLine();
        for (shoppingItem item : shoppingList) {
            if (item.getName().equalsIgnoreCase(name)) {
                try {
                    updateItemDetails(item);
                    return; 
                } catch (NumberFormatException e) {
                    throw new InvalidInputException("Invalid input. Price: number, quantity: integer.");
                } 
            }
        }
        System.out.println("Item not found in the shopping list.");
    }

    // EFFECTS:  Prints the total price of all items in the shopping list.
    private void getTotal() {
        System.out.println("Total Price: $" + shoppingItemList.getTotPrice());
    }

    // EFFECTS:  Validates the item name. If the name is empty, throws InvalidInputException.
    private void validateName(String name) throws InvalidInputException {
        if (name.isEmpty()) {
            throw new InvalidInputException("Name cannot be empty.");
        }
    }

    // EFFECTS:  Validates the item price. If the price is not positive, throws InvalidInputException.
    private void validatePrice(double price) throws InvalidInputException {
        if (price <= 0) {
            throw new InvalidInputException("Price must be a positive number.");
        }
    }

    // EFFECTS:  Validates the item quantity. If the quantity is not positive, throws InvalidInputException.
    private void validateAmount(int amount) throws InvalidInputException {
        if (amount <= 0) {
            throw new InvalidInputException("Quantity must be a positive integer.");
        }
    }

    // MODIFIES: item
    // EFFECTS:  Updates the details of the given item using user input.
    //           Throws InvalidInputException if the input is invalid.
    private void updateItemDetails(shoppingItem item) throws InvalidInputException {
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        validateName(newName);

        System.out.print("Enter new price: ");
        double newPrice = Double.parseDouble(scanner.nextLine());
        validatePrice(newPrice);

        System.out.print("Enter new quantity: ");
        int newAmount = Integer.parseInt(scanner.nextLine());
        validateAmount(newAmount);

        item.setName(newName);
        item.setPrice(newPrice);
        item.setAmount(newAmount);

        System.out.println("Item updated in the shopping list.");
    }
}
