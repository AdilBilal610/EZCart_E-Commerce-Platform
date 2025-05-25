/**
 * EZCart E-Commerce Platform
 * 
 * @author Adil Bilal
 * @author Sana Bushra
 * @version 1.0
 * @copyright 2025 EZCart. All rights reserved.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Product Class
class Product {
    private String name;
    private double price;
    private int stock;
    private String category;

    public Product(String name, double price, int stock, String category) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public void reduceStock(int quantity) {
        // IMPORTANT: This method should be synchronized in a multi-threaded environment
        // to prevent race conditions when multiple users purchase the same product
        this.stock -= quantity;
    }
}

// Cart Class
class Cart {
    private ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public void clearCart() {
        products.clear();
    }
}

// Order Class
class Order {
    private Cart cart;
    private String customerName;

    public Order(Cart cart, String customerName) {
        this.cart = cart;
        this.customerName = customerName;
    }

    public void processOrder() {
        // TODO: Implement actual order processing logic
        // This should include inventory updates, order confirmation, etc.
        System.out.println("Order processed for " + customerName + " with total price: " + cart.getTotalPrice());
    }
}

// Payment Class
class Payment {
    public boolean processPayment(double amount) {
        // TODO: Replace with actual payment gateway integration
        // This is just a simulation - in production, connect to Stripe/PayPal/etc.
        System.out.println("Payment of " + amount + " processed successfully.");
        return true;
    }
}

// Customer Class
class Customer {
    private String name;
    private Cart cart;

    public Customer(String name) {
        this.name = name;
        this.cart = new Cart();
    }

    public String getName() {
        return name;
    }

    public Cart getCart() {
        return cart;
    }
}

// Admin Class
class Admin {
    private ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> getProductsByCategory(String category) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                result.add(product);
            }
        }
        return result;
    }
}

// Main GUI Class
public class EZCartApp {
    private JFrame frame;
    private Customer customer;
    private Admin admin;
    private JTextArea cartArea;
    private JComboBox<String> categoryComboBox;
    private JPanel mainPanel;
    private JPanel productSelectionPanel;
    private JLabel welcomeLabel;
    private JLabel totalLabel;

    public EZCartApp() {
        admin = new Admin();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("EZCart E-Commerce Platform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Login Panel
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Empty cell for layout
        loginPanel.add(loginButton);
        
        frame.add(loginPanel, BorderLayout.NORTH);

        // Main Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        
        // Welcome Panel
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomeLabel = new JLabel();
        welcomePanel.add(welcomeLabel);
        mainPanel.add(welcomePanel, BorderLayout.NORTH);

        // Product Selection Panel - CRITICAL: This is the core shopping interface
        // The layout and performance of this panel directly impacts user experience
        productSelectionPanel = new JPanel();
        productSelectionPanel.setLayout(new GridLayout(0, 3, 10, 10));
        mainPanel.add(new JScrollPane(productSelectionPanel), BorderLayout.CENTER);

        // Cart Panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.add(new JLabel("Your Cart:"), BorderLayout.NORTH);
        cartArea = new JTextArea();
        cartArea.setEditable(false);
        cartPanel.add(new JScrollPane(cartArea), BorderLayout.CENTER);
        
        // Total Panel
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: $0.00");
        totalPanel.add(totalLabel);
        cartPanel.add(totalPanel, BorderLayout.SOUTH);
        
        mainPanel.add(cartPanel, BorderLayout.EAST);

        // Category Selection Panel
        JPanel categoryPanel = new JPanel();
        categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing", "Books"});
        JButton viewCategoryButton = new JButton("View Category");
        categoryPanel.add(new JLabel("Select Category:"));
        categoryPanel.add(categoryComboBox);
        categoryPanel.add(viewCategoryButton);
        mainPanel.add(categoryPanel, BorderLayout.NORTH);

        // Checkout Panel
        JPanel checkoutPanel = new JPanel();
        JButton checkoutButton = new JButton("Checkout");
        checkoutPanel.add(checkoutButton);
        mainPanel.add(checkoutPanel, BorderLayout.SOUTH);

        // Initially hide main panel
        mainPanel.setVisible(false);

        // Add Action Listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Check password requirements
                    if (password.length() < 8) {
                        JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters long.");
                    } else if (password.contains(" ")) {
                        JOptionPane.showMessageDialog(frame, "Password cannot contain spaces.");
                    } else {
                        customer = new Customer(username);
                        loginPanel.setVisible(false);
                        mainPanel.setVisible(true);
                        welcomeLabel.setText("Welcome, " + username + "!");
                        loadSampleProducts();
                        showProductsByCategory("All");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
                }
            }
        });

        viewCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                showProductsByCategory(selectedCategory);
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (customer != null && !customer.getCart().getProducts().isEmpty()) {
                    double total = customer.getCart().getTotalPrice();
                    Payment payment = new Payment();
                    if (payment.processPayment(total)) {
                        Order order = new Order(customer.getCart(), customer.getName());
                        order.processOrder();
                        customer.getCart().clearCart();
                        updateCart();
                        JOptionPane.showMessageDialog(frame, "Order placed successfully! Thank you for shopping with us.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Your cart is empty. Add some products first!");
                }
            }
        });

        frame.setVisible(true);
    }

    private void loadSampleProducts() {
        // TODO: Replace with database integration
        // Currently using hardcoded products - should be replaced with DB call
        
        // Electronics
        admin.addProduct(new Product("Laptop", 800.0, 10, "Electronics"));
        admin.addProduct(new Product("Smartphone", 500.0, 20, "Electronics"));
        admin.addProduct(new Product("Tablet", 300.0, 15, "Electronics"));
        admin.addProduct(new Product("Headphones", 100.0, 30, "Electronics"));
        
        // Clothing
        admin.addProduct(new Product("T-Shirt", 20.0, 50, "Clothing"));
        admin.addProduct(new Product("Jeans", 50.0, 40, "Clothing"));
        admin.addProduct(new Product("Jacket", 80.0, 25, "Clothing"));
        
        // Books
        admin.addProduct(new Product("Java Programming", 45.0, 20, "Books"));
        admin.addProduct(new Product("Python for Beginners", 35.0, 25, "Books"));
        admin.addProduct(new Product("Web Development", 55.0, 15, "Books"));
    }

    private void showProductsByCategory(String category) {
        productSelectionPanel.removeAll();
        
        ArrayList<Product> productsToShow;
        if (category.equals("All")) {
            productsToShow = admin.getProducts();
        } else {
            productsToShow = admin.getProductsByCategory(category);
        }

        for (Product product : productsToShow) {
            JPanel productPanel = new JPanel(new BorderLayout());
            productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            JLabel nameLabel = new JLabel(product.getName(), SwingConstants.CENTER);
            JLabel priceLabel = new JLabel("$" + product.getPrice(), SwingConstants.CENTER);
            JButton addButton = new JButton("Add to Cart");
            
            productPanel.add(nameLabel, BorderLayout.NORTH);
            productPanel.add(priceLabel, BorderLayout.CENTER);
            productPanel.add(addButton, BorderLayout.SOUTH);
            
            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // IMPORTANT: Need to check stock availability before adding to cart
                    customer.getCart().addProduct(product);
                    updateCart();
                    JOptionPane.showMessageDialog(frame, product.getName() + " added to cart!");
                }
            });
            
            productSelectionPanel.add(productPanel);
        }
        
        productSelectionPanel.revalidate();
        productSelectionPanel.repaint();
    }

    private void updateCart() {
        // TODO: Add quantity display for each product in cart
        StringBuilder sb = new StringBuilder();
        for (Product product : customer.getCart().getProducts()) {
            sb.append(product.getName()).append(" - $").append(String.format("%.2f", product.getPrice())).append("\n");
        }
        cartArea.setText(sb.toString());
        totalLabel.setText("Total: $" + String.format("%.2f", customer.getCart().getTotalPrice()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EZCartApp();
            }
        });
    }
}