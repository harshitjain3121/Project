import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class OnlineMarketplace extends JFrame {

    private static class Product {
        private int id;
        private String name;
        private double price;
        private int quantity;

        public Product(int id, String name, double price, int quantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void purchase(int quantity) {
            this.quantity -= quantity;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Price: $" + price + ", Quantity: " + quantity;
        }
    }

    private Map<Integer, Product> products = new HashMap<>();
    private int productIdCounter = 1;

    private JTextField productNameField;
    private JTextField productPriceField;
    private JTextField productQuantityField;
    private JTextField purchaseProductIdField;
    private JTextField purchaseQuantityField;
    private JTextArea displayArea;

    public OnlineMarketplace() {
        setTitle("Online Marketplace");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2, 10, 5));
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        inputPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        inputPanel.add(productNameField);

        inputPanel.add(new JLabel("Product Price ($):"));
        productPriceField = new JTextField();
        inputPanel.add(productPriceField);

        inputPanel.add(new JLabel("Product Quantity:"));
        productQuantityField = new JTextField();
        inputPanel.add(productQuantityField);

        JButton addProductButton = new JButton("Add Product");
        addProductButton.setBackground(new Color(0, 153, 51));
        addProductButton.setForeground(Color.WHITE);
        inputPanel.add(addProductButton);
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("Purchase Product ID:"));
        purchaseProductIdField = new JTextField();
        inputPanel.add(purchaseProductIdField);

        inputPanel.add(new JLabel("Purchase Quantity:"));
        purchaseQuantityField = new JTextField();
        inputPanel.add(purchaseQuantityField);

        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.setBackground(new Color(0, 102, 204));
        purchaseButton.setForeground(Color.WHITE);
        inputPanel.add(purchaseButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseProduct();
            }
        });
    }

    private void addProduct() {
        String name = productNameField.getText();
        try {
            double price = Double.parseDouble(productPriceField.getText());
            int quantity = Integer.parseInt(productQuantityField.getText());

            if (!name.isEmpty() && price > 0 && quantity > 0) {
                Product product = new Product(productIdCounter, name, price, quantity);
                products.put(productIdCounter, product);
                productIdCounter++;
                productNameField.setText("");
                productPriceField.setText("");
                productQuantityField.setText("");
                updateDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void purchaseProduct() {
        try {
            int productId = Integer.parseInt(purchaseProductIdField.getText());
            int quantity = Integer.parseInt(purchaseQuantityField.getText());

            if (products.containsKey(productId) && quantity > 0) {
                Product product = products.get(productId);
                if (product.getQuantity() >= quantity) {
                    product.purchase(quantity);
                    purchaseProductIdField.setText("");
                    purchaseQuantityField.setText("");
                    updateDisplay();
                } else {
                    JOptionPane.showMessageDialog(this, "Not enough quantity in stock.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Product ID not found or invalid quantity.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void updateDisplay() {
        StringBuilder displayText = new StringBuilder();
        for (Product product : products.values()) {
            displayText.append(product).append("\n");
        }
        displayArea.setText(displayText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new OnlineMarketplace().setVisible(true);
            }
        });
    }
}
