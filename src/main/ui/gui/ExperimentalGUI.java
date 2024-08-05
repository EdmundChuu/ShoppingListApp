// package ui.gui;

// import model.ShoppingItemList;
// import model.shoppingItem;
// import persistence.JsonReader;
// import persistence.JsonWriter;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.io.IOException;
// import java.util.List;

// public class ExperimentalGUI extends JFrame {
//     private static final String JSON_STORE = "./data/shoppinglist.json";
//     private ShoppingItemList shoppingItemList;
//     private JsonWriter jsonWriter;
//     private JsonReader jsonReader;
//     private JPanel splashPanel;
//     private JPanel mainPanel;
//     private JPanel detailPanel;

//     public ExperimentalGUI() {
//         shoppingItemList = new ShoppingItemList("Your Shopping List");
//         jsonWriter = new JsonWriter(JSON_STORE);
//         jsonReader = new JsonReader(JSON_STORE);

//         setTitle("Shopping List Application");
//         setDefaultCloseOperation(EXIT_ON_CLOSE);
//         setLayout(new CardLayout());

//         // Initialize panels
//         initializeSplashScreen();
//         initializeMainPanel();
//         initializeDetailPanel();

//         // Add panels to frame
//         add(splashPanel, "splash");
//         add(mainPanel, "main");
//         add(detailPanel, "details");

//         // Show splash screen initially
//         showSplashScreen();

//         pack();
//         setLocationRelativeTo(null);
//         setVisible(true);
//     }

//     private void initializeSplashScreen() {
//         splashPanel = new JPanel();
//         splashPanel.setLayout(new BorderLayout());
//         splashPanel.setBackground(Color.BLACK);

//         // Add GIF to splash screen
//         JLabel gifLabel = new JLabel(new ImageIcon("src/main/ui/gui/shopneonsign.gif"));
//         gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
//         gifLabel.setVerticalAlignment(SwingConstants.CENTER);
//         splashPanel.add(gifLabel, BorderLayout.CENTER);

//         // Add click-to-start instruction
//         JLabel clickToStartLabel = new JLabel("Click anywhere to start", SwingConstants.CENTER);
//         clickToStartLabel.setForeground(Color.WHITE);
//         clickToStartLabel.setFont(new Font("Arial", Font.BOLD, 20));
//         splashPanel.add(clickToStartLabel, BorderLayout.SOUTH);

//         // Add mouse listener to splash panel
//         splashPanel.addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseClicked(MouseEvent e) {
//                 showMainPanel();
//             }
//         });
//     }

//     private void initializeMainPanel() {
//         mainPanel = new JPanel();
//         mainPanel.setLayout(new BorderLayout());

//         // Button panel
//         JPanel buttonPanel = new JPanel();
//         buttonPanel.setLayout(new GridLayout(1, 3));

//         JButton addButton = new JButton("Add Item");
//         addButton.addActionListener(new AddItemListener());
//         buttonPanel.add(addButton);

//         JButton saveButton = new JButton("Save");
//         saveButton.addActionListener(new SaveListener());
//         buttonPanel.add(saveButton);

//         JButton loadButton = new JButton("Load");
//         loadButton.addActionListener(new LoadListener());
//         buttonPanel.add(loadButton);

//         mainPanel.add(buttonPanel, BorderLayout.NORTH);

//         // Display area
//         updateDisplay();
//     }

//     private void initializeDetailPanel() {
//         detailPanel = new JPanel();
//         detailPanel.setLayout(new BorderLayout());

//         // Edit panel
//         JPanel editPanel = new JPanel();
//         editPanel.setLayout(new GridLayout(4, 2));
//         editPanel.add(new JLabel("Item Name:"));
//         JTextField editNameField = new JTextField();
//         editPanel.add(editNameField);
//         editPanel.add(new JLabel("New Price:"));
//         JTextField editPriceField = new JTextField();
//         editPanel.add(editPriceField);
//         editPanel.add(new JLabel("New Quantity:"));
//         JTextField editQuantityField = new JTextField();
//         editPanel.add(editQuantityField);

//         JButton saveEditButton = new JButton("Save Changes");
//         saveEditButton.addActionListener(new SaveEditListener(editNameField, editPriceField, editQuantityField));
//         editPanel.add(saveEditButton);

//         detailPanel.add(editPanel, BorderLayout.CENTER);

//         // Remove panel
//         JPanel removePanel = new JPanel();
//         removePanel.setLayout(new FlowLayout());
//         JButton removeButton = new JButton("Remove Item");
//         removeButton.addActionListener(new RemoveItemListener());
//         removePanel.add(removeButton);
//         detailPanel.add(removePanel, BorderLayout.SOUTH);
//     }

//     private void showSplashScreen() {
//         CardLayout cl = (CardLayout)(getContentPane().getLayout());
//         cl.show(getContentPane(), "splash");
//     }

//     private void showMainPanel() {
//         CardLayout cl = (CardLayout)(getContentPane().getLayout());
//         cl.show(getContentPane(), "main");
//     }

//     private void showDetailPanel() {
//         CardLayout cl = (CardLayout)(getContentPane().getLayout());
//         cl.show(getContentPane(), "details");
//     }

//     private void updateDisplay() {
//         mainPanel.removeAll(); // Clear existing components

//         JPanel itemListPanel = new JPanel();
//         itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));

//         List<shoppingItem> items = shoppingItemList.getList();
//         for (shoppingItem item : items) {
//             JPanel itemPanel = new JPanel();
//             itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

//             JLabel itemLabel = new JLabel(String.format("Name: %s, Price: $%.2f, Quantity: %d, Purchased: %b",
//                     item.getName(), item.getPrice(), item.getAmount(), item.getPurchased()));
//             itemPanel.add(itemLabel);

//             JButton purchasedButton = new JButton(item.getPurchased() ? "Unmark" : "Mark as Purchased");
//             purchasedButton.addActionListener(new PurchasedButtonListener(item));
//             itemPanel.add(purchasedButton);

//             JButton editButton = new JButton("Edit");
//             editButton.addActionListener(new EditButtonListener(item));
//             itemPanel.add(editButton);

//             itemListPanel.add(itemPanel);
//         }

//         JScrollPane scrollPane = new JScrollPane(itemListPanel);
//         mainPanel.add(scrollPane, BorderLayout.CENTER);

//         // Add the button panel again
//         JPanel buttonPanel = new JPanel();
//         buttonPanel.setLayout(new GridLayout(1, 3));

//         JButton addButton = new JButton("Add Item");
//         addButton.addActionListener(new AddItemListener());
//         buttonPanel.add(addButton);

//         JButton saveButton = new JButton("Save");
//         saveButton.addActionListener(new SaveListener());
//         buttonPanel.add(saveButton);

//         JButton loadButton = new JButton("Load");
//         loadButton.addActionListener(new LoadListener());
//         buttonPanel.add(loadButton);

//         mainPanel.add(buttonPanel, BorderLayout.NORTH);

//         revalidate();
//         repaint();
//     }


//     private class AddItemListener implements ActionListener {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             JTextField nameField = new JTextField();
//             JTextField priceField = new JTextField();
//             JTextField quantityField = new JTextField();

//             JPanel panel = new JPanel(new GridLayout(3, 2));
//             panel.add(new JLabel("Item Name:"));
//             panel.add(nameField);
//             panel.add(new JLabel("Price:"));
//             panel.add(priceField);
//             panel.add(new JLabel("Quantity:"));
//             panel.add(quantityField);

//             int result = JOptionPane.showConfirmDialog(null, panel, "Add New Item", JOptionPane.OK_CANCEL_OPTION);
//             if (result == JOptionPane.OK_OPTION) {
//                 try {
//                     String name = nameField.getText();
//                     double price = Double.parseDouble(priceField.getText());
//                     int quantity = Integer.parseInt(quantityField.getText());

//                     if (name.isEmpty() || price <= 0 || quantity <= 0) {
//                         JOptionPane.showMessageDialog(ExperimentalGUI.this, "Invalid input. Please check your entries.",
//                                 "Error", JOptionPane.ERROR_MESSAGE);
//                         return;
//                     }

//                     shoppingItem item = new shoppingItem(name, price, quantity);
//                     shoppingItemList.addItem(item);
//                     updateDisplay();
//                 } catch (NumberFormatException ex) {
//                     JOptionPane.showMessageDialog(ExperimentalGUI.this, "INVALID. Price & Quantity must be numbers.",
//                             "Error", JOptionPane.ERROR_MESSAGE);
//                 }
//             }
//         }
//     }

//     private class SaveListener implements ActionListener {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             try {
//                 jsonWriter.write(shoppingItemList);
//                 JOptionPane.showMessageDialog(ExperimentalGUI.this, "Shopping list saved to file.",
//                         "Success", JOptionPane.INFORMATION_MESSAGE);
//             } catch (IOException ex) {
//                 JOptionPane.showMessageDialog(ExperimentalGUI.this, "Error saving file.",
//                         "Error", JOptionPane.ERROR_MESSAGE);
//             }
//         }
//     }

//     private class LoadListener implements ActionListener {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             try {
//                 shoppingItemList = jsonReader.read();
//                 updateDisplay();
//                 JOptionPane.showMessageDialog(ExperimentalGUI.this, "Shopping list loaded from file.",
//                         "Success", JOptionPane.INFORMATION_MESSAGE);
//             } catch (IOException ex) {
//                 JOptionPane.showMessageDialog(ExperimentalGUI.this, "Error loading file.",
//                         "Error", JOptionPane.ERROR_MESSAGE);
//             }
//         }
//     }

//     private class SaveEditListener implements ActionListener {
//         private JTextField editNameField;
//         private JTextField editPriceField;
//         private JTextField editQuantityField;

//         public SaveEditListener(JTextField editNameField, JTextField editPriceField, JTextField editQuantityField) {
//             this.editNameField = editNameField;
//             this.editPriceField = editPriceField;
//             this.editQuantityField = editQuantityField;
//         }

//         @Override
//         public void actionPerformed(ActionEvent e) {
//             String oldName = editNameField.getText();
//             try {
//                 double newPrice = Double.parseDouble(editPriceField.getText());
//                 int newQuantity = Integer.parseInt(editQuantityField.getText());

//                 if (oldName.isEmpty() || newPrice <= 0 || newQuantity <= 0) {
//                     JOptionPane.showMessageDialog(ExperimentalGUI.this, "Invalid input. Please check your entries.",
//                             "Error", JOptionPane.ERROR_MESSAGE);
//                     return;
//                 }

//                 List<shoppingItem> items = shoppingItemList.getList();
//                 for (shoppingItem item : items) {
//                     if (item.getName().equalsIgnoreCase(oldName)) {
//                         item.setPrice(newPrice);
//                         item.setAmount(newQuantity);
//                         updateDisplay();
//                         JOptionPane.showMessageDialog(ExperimentalGUI.this, "Item updated successfully.",
//                                 "Success", JOptionPane.INFORMATION_MESSAGE);
//                         return;
//                     }
//                 }

//                 JOptionPane.showMessageDialog(ExperimentalGUI.this, "Item not found.",
//                         "Error", JOptionPane.ERROR_MESSAGE);
//             } catch (NumberFormatException ex) {
//                 JOptionPane.showMessageDialog(ExperimentalGUI.this, "INVALID. Price & Quantity must be numbers.",
//                         "Error", JOptionPane.ERROR_MESSAGE);
//             }
//         }
//     }

//     private class PurchasedButtonListener implements ActionListener {
//         private shoppingItem item;

//         public PurchasedButtonListener(shoppingItem item) {
//             this.item = item;
//         }

//         @Override
//         public void actionPerformed(ActionEvent e) {
//             item.setPurchased(!item.getPurchased());
//             updateDisplay();
//         }
//     }

//     private class EditButtonListener implements ActionListener {
//         private shoppingItem item;

//         public EditButtonListener(shoppingItem item) {
//             this.item = item;
//         }

//         @Override
//         public void actionPerformed(ActionEvent e) {
//             JTextField nameField = new JTextField(item.getName());
//             JTextField priceField = new JTextField(String.valueOf(item.getPrice()));
//             JTextField quantityField = new JTextField(String.valueOf(item.getAmount()));

//             JPanel panel = new JPanel(new GridLayout(3, 2));
//             panel.add(new JLabel("Item Name:"));
//             panel.add(nameField);
//             panel.add(new JLabel("Price:"));
//             panel.add(priceField);
//             panel.add(new JLabel("Quantity:"));
//             panel.add(quantityField);

//             int result = JOptionPane.showConfirmDialog(null, panel, "Edit Item", JOptionPane.OK_CANCEL_OPTION);
//             if (result == JOptionPane.OK_OPTION) {
//                 try {
//                     String name = nameField.getText();
//                     double price = Double.parseDouble(priceField.getText());
//                     int quantity = Integer.parseInt(quantityField.getText());

//                     if (name.isEmpty() || price <= 0 || quantity <= 0) {
//                         JOptionPane.showMessageDialog(ExperimentalGUI.this, "Invalid input. Please check your entries.",
//                                 "Error", JOptionPane.ERROR_MESSAGE);
//                         return;
//                     }

//                     item.setName(name);
//                     item.setPrice(price);
//                     item.setAmount(quantity);
//                     updateDisplay();
//                 } catch (NumberFormatException ex) {
//                     JOptionPane.showMessageDialog(ExperimentalGUI.this, "INVALID. Price & Quantity must be numbers.",
//                             "Error", JOptionPane.ERROR_MESSAGE);
//                 }
//             }
//         }
//     }

//     private class RemoveItemListener implements ActionListener {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             String name = JOptionPane.showInputDialog("Enter the name of the item to remove:");
//             if (name != null && !name.isEmpty()) {
//                 boolean itemFound = false;
//                 List<shoppingItem> items = shoppingItemList.getList();
//                 for (int i = 0; i < items.size(); i++) {
//                     if (items.get(i).getName().equalsIgnoreCase(name)) {
//                         items.remove(i);
//                         itemFound = true;
//                         break;
//                     }
//                 }

//                 if (itemFound) {
//                     updateDisplay();
//                     JOptionPane.showMessageDialog(ExperimentalGUI.this, "Item removed successfully.",
//                             "Success", JOptionPane.INFORMATION_MESSAGE);
//                 } else {
//                     JOptionPane.showMessageDialog(ExperimentalGUI.this, "Item not found.",
//                             "Error", JOptionPane.ERROR_MESSAGE);
//                 }
//             }
//         }
//     }

//     public static void main(String[] args) {
//         new ExperimentalGUI();
//     }
// }