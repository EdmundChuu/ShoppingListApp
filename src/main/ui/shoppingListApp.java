package ui;

import model.shoppingItem;
import exceptions.InvalidInputException;
import java.util.ArrayList;
import java.util.Scanner;

public class shoppingListApp {
    private ArrayList<shoppingItem> shoppingList;
    private Scanner scanner;

    public shoppingListApp() {
        shoppingList = new ArrayList<>();
        scanner = new Scanner(System.in);
        runApp();
    }

    // Main loop to run the application
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
            }
        }
    }

    // Print menu options
    private void printMenu() {
        System.out.println("\nShopping List Menu:");
        System.out.println("1. Add Item");
        System.out.println("2. View Items");
        System.out.println("3. Mark Item as Purchased");
        System.out.println("4. Delete Item");
        System.out.println("5. Clear Purchased Items");
        System.out.println("6. Edit Item");
        System.out.println("0. Exit");
        System.out.println("Enter your choice: ");
    }

    // Add an item to the shopping list
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

            shoppingList.add(new shoppingItem(name, price, amount));
            System.out.println("Item added to the shopping list.");
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input. Price must be a number and quantity must be an integer.");
        }
    }

    // View all items in the shopping list
    private void viewItems() {
        if (shoppingList.isEmpty()) {
            System.out.println("Shopping list is empty.");
        } else {
            for (shoppingItem item : shoppingList) {
                System.out.println(item.getName() + " - " + item.getAmount() 
                    + " units - $" + item.getPrice() + " - Purchased: " + item.getPurchased());
            }
        }
    }

    // Mark an item as purchased
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

    // Delete an item from the shopping list
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

    // Clear all purchased items from the shopping list
    private void clearPurchasedItems() {
        shoppingList.removeIf(shoppingItem::getPurchased);
        System.out.println("All purchased items have been cleared from the shopping list.");
    }

    // Edit an item in the shopping list
    private void editItem() throws InvalidInputException {
        System.out.print("Enter item name to edit: ");
        String name = scanner.nextLine();
        for (shoppingItem item : shoppingList) {
            if (item.getName().equalsIgnoreCase(name)) {
                try {
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
                    return;
                } catch (NumberFormatException e) {
                    throw new InvalidInputException("Invalid input. Price must be a number and quantity must be an integer.");
                } 
            }
        }
        System.out.println("Item not found in the shopping list.");
    }

    // Validate item name
    private void validateName(String name) throws InvalidInputException {
        if (name.isEmpty()) {
            throw new InvalidInputException("Name cannot be empty.");
        }
    }

    // Validate item price
    private void validatePrice(double price) throws InvalidInputException {
        if (price <= 0) {
            throw new InvalidInputException("Price must be a positive number.");
        }
    }

    // Validate item quantity
    private void validateAmount(int amount) throws InvalidInputException {
        if (amount <= 0) {
            throw new InvalidInputException("Quantity must be a positive integer.");
        }
    }
}
