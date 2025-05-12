package View;


import controller.OrderController;
import javax.swing.*;
import java.awt.*;

public class OrderView extends JFrame {
    private OrderController orderController;

    public OrderView(OrderController orderController) {
        this.orderController = orderController;

        setTitle("Order Page");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

       
        JLabel headerLabel = new JLabel("Order Summary");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        try {
            detailsPanel.add(new JLabel("Order ID: " + orderController.getCurrentOrder().getOrderId()));
            detailsPanel.add(new JLabel("Order Date: " + orderController.getCurrentOrder().getOrderDate()));
            detailsPanel.add(new JLabel("Customer: " + orderController.getCurrentOrder().getCustomer())); 
            detailsPanel.add(new JLabel("Total Price: $" + orderController.getCurrentOrder().getTotalPrice()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error displaying order details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        add(detailsPanel, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton invoiceButton = new JButton("Generate Invoice");
        JButton completeButton = new JButton("Complete Order");
        buttonPanel.add(invoiceButton);
        buttonPanel.add(completeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        
        invoiceButton.addActionListener(e -> {
            String invoice = orderController.getInvoice();
            JOptionPane.showMessageDialog(this, invoice, "Invoice", JOptionPane.INFORMATION_MESSAGE);
        });

        
        completeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Order Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); 
        });

        setVisible(true);
    }
}