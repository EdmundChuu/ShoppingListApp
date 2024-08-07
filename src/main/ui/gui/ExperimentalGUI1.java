package ui.gui;

import model.EventLog;
import model.Event;
import model.ShoppingItemList;
import model.ItemShopped;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class ExperimentalGUI1 extends JFrame {
    private static final String JSON_STORE = "./data/shoppinglist.json";
    private ShoppingItemList shoppingItemList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel splashPanel;
    private JPanel mainPanel;
    private JPanel detailPanel;

    public ExperimentalGUI1() {
        shoppingItemList = new ShoppingItemList("Your Shopping List");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setTitle("Shopping List Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Initialize panels
        initializeSplashScreen();
        initializeMainPanel();
        initializeDetailPanel();

        // Add panels to frame
        add(splashPanel, "splash");
        add(mainPanel, "main");
        add(detailPanel, "details");

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
        JLabel gifLabel = new JLabel(new ImageIcon("src/main/ui/gui/shopneonsign.gif"));
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

    private void initializeMainPanel() {
        // mainPanel = new BackgroundPanel("src/main/ui/gui/giphy/gif"); //changed
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //CHANGE
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(new AddItemListener());
        buttonPanel.add(addButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveListener());
        buttonPanel.add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new LoadListener());
        buttonPanel.add(loadButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Display area
        updateDisplay();
    }

    private void initializeDetailPanel() {
        detailPanel = new JPanel();
        detailPanel.setLayout(new BorderLayout());

        // Edit panel
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(4, 2));
        editPanel.add(new JLabel("Item Name:"));
        JTextField editNameField = new JTextField();
        editPanel.add(editNameField);
        editPanel.add(new JLabel("New Price:"));
        JTextField editPriceField = new JTextField();
        editPanel.add(editPriceField);
        editPanel.add(new JLabel("New Quantity:"));
        JTextField editQuantityField = new JTextField();
        editPanel.add(editQuantityField);

        JButton saveEditButton = new JButton("Save Changes");
        saveEditButton.addActionListener(new SaveEditListener(editNameField, editPriceField, editQuantityField));
        editPanel.add(saveEditButton);

        detailPanel.add(editPanel, BorderLayout.CENTER);

        // Remove panel
        // JPanel removePanel = new JPanel();
        // removePanel.setLayout(new FlowLayout());
        // JButton removeButton = new JButton("Remove Item");
        // removeButton.addActionListener(new RemoveItemListener(item));
        // removePanel.add(removeButton);
        // detailPanel.add(removePanel, BorderLayout.SOUTH);
    }

    private void showSplashScreen() {
        CardLayout cl = (CardLayout)(getContentPane().getLayout());
        cl.show(getContentPane(), "splash");
    }

    private void showMainPanel() {
        CardLayout cl = (CardLayout)(getContentPane().getLayout());
        cl.show(getContentPane(), "main");
    }

    // private void showDetailPanel() {
    //     CardLayout cl = (CardLayout)(getContentPane().getLayout());
    //     cl.show(getContentPane(), "details");
    // }

    private void updateDisplay() {
        mainPanel.removeAll(); // Clear existing components

        JPanel itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));

        List<ItemShopped> items = shoppingItemList.getList();
        for (ItemShopped item : items) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel itemLabel = new JLabel(String.format("Name: %s, Price: $%.2f, Quantity: %d, Purchased: %b",
                    item.getName(), item.getPrice(), item.getAmount(), item.getPurchased()));
            itemPanel.add(itemLabel);

            JButton purchasedButton = new JButton(item.getPurchased() ? "Unmark" : "Mark as Purchased");
            purchasedButton.addActionListener(new PurchasedButtonListener(item));
            itemPanel.add(purchasedButton);

            JButton editButton = new JButton("Edit");
            editButton.addActionListener(new EditButtonListener(item));
            itemPanel.add(editButton);

            itemListPanel.add(itemPanel);
        }

        getLoadButton(itemListPanel);

        revalidate();
        repaint();
    }

    private void getLoadButton(JPanel itemListPanel) {
        JScrollPane scrollPane = new JScrollPane(itemListPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the button panel again
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(new AddItemListener());
        buttonPanel.add(addButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveListener());
        buttonPanel.add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new LoadListener());
        buttonPanel.add(loadButton);

        JButton leaveButton = new JButton("Leave");
        leaveButton.addActionListener(new LeaveListener());
        buttonPanel.add(leaveButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
    }


    private class AddItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField nameField = new JTextField();
            JTextField priceField = new JTextField();
            JTextField quantityField = new JTextField();

            JPanel panel = getPanel(nameField, priceField, quantityField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Add New Item", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());

                    if (name.isEmpty() || price <= 0 || quantity <= 0) {
                        JOptionPane.showMessageDialog(ExperimentalGUI1.this, "INVALID. Empty or negative input",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ItemShopped item = new ItemShopped(name, price, quantity);
                    shoppingItemList.addItem(item);
                    updateDisplay();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ExperimentalGUI1.this, "INVALID. Price & Quantity must be numbers.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private JPanel getPanel(JTextField nameField, JTextField priceField, JTextField quantityField) {
            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Item Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);
            panel.add(new JLabel("Quantity:"));
            panel.add(quantityField);
            return panel;
        }
    }

    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.write(shoppingItemList);
                JOptionPane.showMessageDialog(ExperimentalGUI1.this, "Shopping list saved to file.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ExperimentalGUI1.this, "Error saving file.",
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
                JOptionPane.showMessageDialog(ExperimentalGUI1.this, "Shopping list loaded from file.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ExperimentalGUI1.this, "Error loading file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SaveEditListener implements ActionListener {
        private JTextField editNameField;
        private JTextField editPriceField;
        private JTextField editQuantityField;

        public SaveEditListener(JTextField editNameField, JTextField editPriceField, JTextField editQuantityField) {
            this.editNameField = editNameField;
            this.editPriceField = editPriceField;
            this.editQuantityField = editQuantityField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String oldName = editNameField.getText();
            try {
                double newPrice = Double.parseDouble(editPriceField.getText());
                int newQuantity = Integer.parseInt(editQuantityField.getText());

                if (oldName.isEmpty() || newPrice <= 0 || newQuantity <= 0) {
                    extractedMessage2();
                    return;
                }

                List<ItemShopped> items = shoppingItemList.getList();
                for (ItemShopped item : items) {
                    if (item.getName().equalsIgnoreCase(oldName)) {
                        item.setPrice(newPrice);
                        item.setAmount(newQuantity);
                        updateDisplay();
                        extractedMessage3();
                        return;
                    }
                }

                JOptionPane.showMessageDialog(ExperimentalGUI1.this, "Item not found.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                extractedMessage();
            }
        }

        private void extractedMessage3() {
            JOptionPane.showMessageDialog(ExperimentalGUI1.this, "Item updated successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        private void extractedMessage2() {
            JOptionPane.showMessageDialog(ExperimentalGUI1.this, "Invalid input. Please check your entries.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        private void extractedMessage() {
            JOptionPane.showMessageDialog(ExperimentalGUI1.this, "INVALID. Price & Quantity must be numbers.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class PurchasedButtonListener implements ActionListener {
        private ItemShopped item;

        public PurchasedButtonListener(ItemShopped item) {
            this.item = item;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            item.setPurchased(!item.getPurchased());
            updateDisplay();
        }
    }

    private class EditButtonListener implements ActionListener {
        private ItemShopped item;

        public EditButtonListener(ItemShopped item) {
            this.item = item;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField nameField = new JTextField(item.getName());
            JTextField priceField = new JTextField(String.valueOf(item.getPrice()));
            JTextField quantityField = new JTextField(String.valueOf(item.getAmount()));

            JPanel panel = getPanel(nameField, priceField, quantityField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Edit Item", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());

                    if (name.isEmpty() || price <= 0 || quantity <= 0) {
                        JOptionPane.showMessageDialog(ExperimentalGUI1.this, "INVALID, empty/negative input",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    extracted(name, price, quantity);
                    updateDisplay();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ExperimentalGUI1.this, "INVALID. Price & Quantity must be numbers.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void extracted(String name, double price, int quantity) {
            item.setName(name);
            item.setPrice(price);
            item.setAmount(quantity);
        }

        private JPanel getPanel(JTextField nameField, JTextField priceField, JTextField quantityField) {
            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Item Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);
            panel.add(new JLabel("Quantity:"));
            panel.add(quantityField);
            return panel;
        }
    }

    // private class RemoveItemListener implements ActionListener {
    //     private ItemShopped item;

    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         if (item.getName() != null && !item.getName().isEmpty()) {
    //             boolean itemFound = false;
    //             List<ItemShopped> items = shoppingItemList.getList();
    //             for (int i = 0; i < items.size(); i++) {
    //                 if (items.get(i).getName().equalsIgnoreCase(item.getName())) {
    //                     items.remove(i);
    //                     itemFound = true;
    //                     break;
    //                 }
    //             }

    //             if (itemFound) {
    //                 updateDisplay();
    //                 JOptionPane.showMessageDialog(ExperimentalGUI1.this, "Item removed successfully.",
    //                         "Success", JOptionPane.INFORMATION_MESSAGE);
    //             } else {
    //                 JOptionPane.showMessageDialog(ExperimentalGUI1.this, "Item not found.",
    //                         "Error", JOptionPane.ERROR_MESSAGE);
    //             }
    //         }
    //     }
    // }

    private class LeaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(ExperimentalGUI1.this, "Thanks for using the Shopping List App. Goodbye!",
                    "Goodbye", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            printLoggedEvents();
            
        }
    }

    // EFFECTS:
    // Prints all logged events to the console.
    private void printLoggedEvents() {
        System.out.println("Logged Events:");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }
}