package view;

import Controller.ShoppingCartController;
import Model.ShoppingCartModel;
import controller.OrderController;
import controller.ProductCustomerController;

import javax.swing.*;
import java.awt.*;
import model.CreditCardPayment;

public class ShoppingCartView extends JFrame {
    private ProductCustomerController customerController;
    private JPanel cartPanel;
    private JLabel totalPriceLabel;

    public ShoppingCartView(ProductCustomerController customerController) {
        this.customerController = customerController;

        
        setTitle("Your Shopping Cart");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JLabel headerLabel = new JLabel("Your Shopping Cart");
        headerLabel.setFont(new Font("Poppins", Font.BOLD, 36));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0x3A3845));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(headerLabel, BorderLayout.NORTH);

        
        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(cartPanel);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);

        totalPriceLabel = new JLabel("Total: Rp." + ShoppingCartController.calculateTotalPrice());
        totalPriceLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        totalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(0xD9534F));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Poppins", Font.BOLD, 16));

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(new Color(0x3A3845));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFont(new Font("Poppins", Font.BOLD, 16));

        buttonPanel.add(backButton);
        buttonPanel.add(checkoutButton);

        footerPanel.add(totalPriceLabel, BorderLayout.NORTH);
        footerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        
        backButton.addActionListener(e -> {
            dispose();
            new ProductCustomerView(customerController);
        });

        
        checkoutButton.addActionListener(e -> {
            if (ShoppingCartController.getCartItems().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double totalPrice = ShoppingCartController.calculateTotalPrice();
            OrderController orderController = new OrderController();

            
            String[] options = {"Regular Order", "Express Order"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Please select the type of order:",
                    "Order Type Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(this, "Checkout cancelled.");
                return;
            }

            
            if (choice == 0) {
                orderController.createRegularOrder(1, totalPrice, "Customer Name", 20000.0, 3);
            } else if (choice == 1) {
                orderController.createExpressOrder(1, totalPrice, "Customer Name", 100000.0, 1);
            }

            
            boolean stockUpdated = ShoppingCartController.updateStockAfterCheckout();
            if (!stockUpdated) {
                JOptionPane.showMessageDialog(this, "Failed to update stock in database.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            JOptionPane.showMessageDialog(this, orderController.getInvoice(), "Invoice", JOptionPane.INFORMATION_MESSAGE);

            
            CreditCardPayment payment = new CreditCardPayment();
            JOptionPane.showMessageDialog(this, payment.processPayment(), "Payment", JOptionPane.INFORMATION_MESSAGE);

            
            JOptionPane.showMessageDialog(this, "Anda telah berhasil melakukan pembayaran.", "Payment Success", JOptionPane.INFORMATION_MESSAGE);

            ShoppingCartController.clearCart();
            dispose();
            new ProductCustomerView(customerController);
        });

        
        populateCart();
        setVisible(true);
    }

    private void populateCart() {
        cartPanel.removeAll();
        for (ShoppingCartModel item : ShoppingCartController.getCartItems()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
            itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            itemPanel.setBackground(Color.WHITE);

            JLabel itemName = new JLabel(item.getName());
            itemName.setFont(new Font("Poppins", Font.BOLD, 18));

            JLabel itemPrice = new JLabel("Price: Rp." + item.getPrice());
            itemPrice.setFont(new Font("Poppins", Font.PLAIN, 16));

            JLabel itemQuantity = new JLabel("Qty: " + item.getQuantity());
            itemQuantity.setFont(new Font("Poppins", Font.PLAIN, 16));

            JButton plusButton = new JButton("+");
            JButton minusButton = new JButton("-");
            plusButton.setPreferredSize(new Dimension(35, 35));
            minusButton.setPreferredSize(new Dimension(35, 35));

            plusButton.addActionListener(e -> {
                ShoppingCartController.increaseItemQuantity(item);
                updateCartDisplay();
            });

            minusButton.addActionListener(e -> {
                ShoppingCartController.decreaseItemQuantity(item);
                updateCartDisplay();
            });

            itemPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            itemPanel.add(itemName);
            itemPanel.add(Box.createHorizontalStrut(20));
            itemPanel.add(itemPrice);
            itemPanel.add(Box.createHorizontalStrut(20));
            itemPanel.add(itemQuantity);
            itemPanel.add(Box.createHorizontalStrut(10));
            itemPanel.add(minusButton);
            itemPanel.add(Box.createHorizontalStrut(5));
            itemPanel.add(plusButton);

            cartPanel.add(itemPanel);
        }
        cartPanel.revalidate();
        cartPanel.repaint();
    }

    private void updateCartDisplay() {
        populateCart();
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        totalPriceLabel.setText("Total: Rp." + ShoppingCartController.calculateTotalPrice());
    }
}
