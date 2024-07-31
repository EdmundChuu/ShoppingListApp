package ui.gui;

import model.ShoppingItemList;
import model.shoppingItem;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ListAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/shoppinglist.json";
    private ShoppingItemList shoppingItemList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JTextArea itemListArea;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField editNameField;
    private JTextField editPriceField;
    private JTextField editQuantityField;
    private JPanel splashPanel;
    private JPanel mainPanel;

    public ListAppGUI() {
        shoppingItemList = new ShoppingItemList("Your Shopping List");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setTitle("Shopping List Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Initialize splash screen
        initializeSplashScreen();
        // Initialize main panel
        initializeMainPanel();

        // Add panels to frame
        add(splashPanel, "splash");
        add(mainPanel, "main");

        // Show splash screen initially
        showSplashScreen();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeSplashScreen() {
        splashPanel = new JPanel();
        splashPanel.setLayout(new BorderLayout());
        splashPanel.setBackground(Color.BLACK);

        // Add GIF to splash screen
        JLabel gifLabel = new JLabel(new ImageIcon("src/main/ui/gui/shopneonsign.gif")); // Replace with your GIF path
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gifLabel.setVerticalAlignment(SwingConstants.CENTER);
        splashPanel.add(gifLabel, BorderLayout.CENTER);

        // Add click-to-start instruction
        JLabel clickToStartLabel = new JLabel("Click anywhere to start", SwingConstants.CENTER);
        clickToStartLabel.setForeground(Color.WHITE);
        clickToStartLabel.setFont(new Font("Arial", Font.BOLD, 20));
        splashPanel.add(clickToStartLabel, BorderLayout.SOUTH);

        // Add mouse listener to splash panel
        splashPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMainPanel();
            }
        });
    }
    //!!! break apart
    private void initializeMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Input panel for adding items
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(new AddItemListener());
        buttonPanel.add(addButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveListener());
        buttonPanel.add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new LoadListener());
        buttonPanel.add(loadButton);

        JButton editButton = new JButton("Edit Item");
        editButton.addActionListener(new EditItemListener());
        buttonPanel.add(editButton);

        JButton markPurchasedButton = new JButton("Mark as Purchased");
        markPurchasedButton.addActionListener(new MarkPurchasedListener());
        buttonPanel.add(markPurchasedButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Display area
        itemListArea = new JTextArea(15, 40);
        itemListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(itemListArea);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Edit panel
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(4, 2));
        editPanel.add(new JLabel("Edit Item Name:"));
        editNameField = new JTextField();
        editPanel.add(editNameField);
        editPanel.add(new JLabel("New Price:"));
        editPriceField = new JTextField();
        editPanel.add(editPriceField);
        editPanel.add(new JLabel("New Quantity:"));
        editQuantityField = new JTextField();
        editPanel.add(editQuantityField);
        mainPanel.add(editPanel, BorderLayout.EAST);
    }

    private void showSplashScreen() {
        CardLayout cl = (CardLayout)(getContentPane().getLayout());
        cl.show(getContentPane(), "splash");
    }

    private void showMainPanel() {
        CardLayout cl = (CardLayout)(getContentPane().getLayout());
        cl.show(getContentPane(), "main");
    }

    private void updateDisplay() {
        itemListArea.setText("");
        for (shoppingItem item : shoppingItemList.getList()) {
            itemListArea.append(String.format("Name: %s, Price: $%.2f, Quantity: %d, Purchased: %b%n",
                    item.getName(), item.getPrice(), item.getAmount(), item.getPurchased()));
        }
    }

    private class AddItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                if (name.isEmpty() || price <= 0 || quantity <= 0) {
                    JOptionPane.showMessageDialog(ListAppGUI.this, "Invalid input. Please check your entries.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                shoppingItem item = new shoppingItem(name, price, quantity);
                shoppingItemList.addItem(item);
                updateDisplay();
                nameField.setText("");
                priceField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ListAppGUI.this, "Invalid input. Price and Quantity must be numbers.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.write(shoppingItemList);
                JOptionPane.showMessageDialog(ListAppGUI.this, "Shopping list saved to file.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ListAppGUI.this, "Error saving file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                shoppingItemList = jsonReader.read();
                updateDisplay();
                JOptionPane.showMessageDialog(ListAppGUI.this, "Shopping list loaded from file.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ListAppGUI.this, "Error loading file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class EditItemListener implements ActionListener {
        @Override
        
        @SuppressWarnings("methodlength")
        public void actionPerformed(ActionEvent e) {
            try {
                String oldName = editNameField.getText();
                double newPrice = Double.parseDouble(editPriceField.getText());
                int newQuantity = Integer.parseInt(editQuantityField.getText());

                if (oldName.isEmpty() || newPrice <= 0 || newQuantity <= 0) {
                    JOptionPane.showMessageDialog(ListAppGUI.this, "Invalid input. Please check your entries.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean itemFound = false;
                for (shoppingItem item : shoppingItemList.getList()) {
                    if (item.getName().equalsIgnoreCase(oldName)) {
                        item.setPrice(newPrice);
                        item.setAmount(newQuantity);
                        itemFound = true;
                        break;
                    }
                }

                if (itemFound) {
                    updateDisplay();
                    JOptionPane.showMessageDialog(ListAppGUI.this, "Item updated successfully.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ListAppGUI.this, "Item not found.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ListAppGUI.this, "Invalid input. Price and Quantity must be numbers.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class MarkPurchasedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog(ListAppGUI.this, "Enter item name to mark as purchased:");
            if (name != null && !name.isEmpty()) {
                boolean itemFound = false;
                for (shoppingItem item : shoppingItemList.getList()) {
                    if (item.getName().equalsIgnoreCase(name)) {
                        item.setPurchased(true);
                        itemFound = true;
                        break;
                    }
                }

                if (itemFound) {
                    updateDisplay();
                    JOptionPane.showMessageDialog(ListAppGUI.this, "Item marked as purchased.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ListAppGUI.this, "Item not found.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(ListAppGUI.this, "Item name cannot be empty.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
