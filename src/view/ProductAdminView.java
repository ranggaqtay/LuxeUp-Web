package view;

import View.SignInView;
import controller.ProductAdminController;
import model.ProductAdminModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ProductAdminView extends JFrame {
    private ProductAdminController adminController;
    private JPanel productPanel;
    private JButton addButton, editButton, deleteButton, logoutButton;

    public ProductAdminView(ProductAdminController controller) {
        this.adminController = controller;

        
        setTitle("Luxe Up Admin");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

       
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(64, 64, 64));
        headerPanel.setLayout(null);

        JLabel titleLabel = new JLabel("LUXE UP");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBounds(10, 10, 200, 50);
        headerPanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(64, 64, 64));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        logoutButton = new JButton("Logout");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(logoutButton);

        buttonPanel.setBounds(500, 10, 600, 50);
        headerPanel.add(buttonPanel);

        headerPanel.setPreferredSize(new Dimension(1920, 70));
        add(headerPanel, BorderLayout.NORTH);

       
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(48, 48, 48)); 
        footerPanel.setLayout(new BorderLayout()); 
        footerPanel.setPreferredSize(new Dimension(1920, 150)); 

       
        JPanel leftFooterPanel = new JPanel();
        leftFooterPanel.setBackground(new Color(48, 48, 48)); 
        leftFooterPanel.setLayout(new GridBagLayout()); 

       
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 150, 0, 150); 

        
        JLabel footerTitleLabel = new JLabel("LUXE UP");
        footerTitleLabel.setForeground(Color.WHITE); 
        footerTitleLabel.setFont(new Font("Poppins", Font.BOLD, 20)); 
        leftFooterPanel.add(footerTitleLabel, gbc);

        gbc.gridy++; 
        JLabel footerCopyrightLabel = new JLabel("Copyright © 2023 LUXE UP | All Rights Reserved");
        footerCopyrightLabel.setForeground(Color.WHITE); 
        footerCopyrightLabel.setFont(new Font("Poppins", Font.PLAIN, 14)); 
        leftFooterPanel.add(footerCopyrightLabel, gbc);

        
        JPanel rightFooterPanel = new JPanel();
        rightFooterPanel.setBackground(new Color(48, 48, 48)); 
        rightFooterPanel.setLayout(new BorderLayout());
        rightFooterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 75)); 

        JLabel footerDescriptionLabel = new JLabel(
                "<html><p style='width:500px; text-align:justify;'>"
                        + "Sebagai Pusat Fashion Premium di Asia, LUXE UP menghadirkan beragam koleksi eksklusif yang memadukan kemewahan dan tren terkini. "
                        + "Kami memperluas pilihan gaya Anda dengan produk-produk premium dari merek internasional dan lokal yang terpercaya. "
                        + "Dengan LUXE UP, jadikan diri Anda sebagai pusat perhatian. LUXE UP Elevate Your Style."
                        + "</p></html>");
        footerDescriptionLabel.setForeground(Color.WHITE); 
        footerDescriptionLabel.setFont(new Font("Poppins", Font.PLAIN, 14)); 
        rightFooterPanel.add(footerDescriptionLabel, BorderLayout.CENTER);

        
        footerPanel.add(leftFooterPanel, BorderLayout.WEST); 
        footerPanel.add(rightFooterPanel, BorderLayout.EAST); 

        add(footerPanel, BorderLayout.SOUTH); 

        
        productPanel = new JPanel();
        productPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        productPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(productPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        
        loadProducts();

        
        addButton.addActionListener(e -> {
            try {
                String name = JOptionPane.showInputDialog("Product Name:");
                String category = JOptionPane.showInputDialog("Category:");
                double price = Double.parseDouble(JOptionPane.showInputDialog("Price:"));
                int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock:"));

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Product Image");
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File imageFile = fileChooser.getSelectedFile();
                    byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

                    int newId = adminController.getProductList().size() + 1;
                    adminController.addProduct(new ProductAdminModel(newId, name, category, price, stock, imageBytes));
                    refreshProducts();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to read image file.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editButton.addActionListener(e -> {
            try {
                int selectedProductIndex = selectProduct();
                if (selectedProductIndex >= 0) {
                    ProductAdminModel selectedProduct = adminController.getProductList().get(selectedProductIndex);

                    String name = JOptionPane.showInputDialog("Product Name:", selectedProduct.getName());
                    String category = JOptionPane.showInputDialog("Category:", selectedProduct.getCategory());
                    double price = Double.parseDouble(JOptionPane.showInputDialog("Price:", selectedProduct.getPrice()));
                    int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock:", selectedProduct.getStock()));

                    byte[] imageBytes = selectedProduct.getImage();
                    int confirmImageChange = JOptionPane.showConfirmDialog(
                            this,
                            "Do you want to change the product image?",
                            "Change Image",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmImageChange == JOptionPane.YES_OPTION) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Select New Product Image");
                        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                            File imageFile = fileChooser.getSelectedFile();
                            imageBytes = Files.readAllBytes(imageFile.toPath());
                        }
                    }

                    adminController.editProduct(selectedProductIndex,
                            new ProductAdminModel(selectedProduct.getProductId(), name, category, price, stock, imageBytes));
                    refreshProducts();
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a product to edit.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to read image file.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedProductIndex = selectProduct();
            if (selectedProductIndex >= 0) {
                adminController.deleteProduct(selectedProductIndex);
                refreshProducts();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a product to delete.");
            }
        });

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new SignInView();
            }
        });

        setVisible(true);
    }

    private void loadProducts() {
        productPanel.removeAll();
        for (ProductAdminModel product : adminController.getProductList()) {
            JPanel card = createProductCard(product);
            productPanel.add(card);
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    private JPanel createProductCard(ProductAdminModel product) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(200, 250));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        card.setBackground(Color.WHITE);

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 150));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        if (product.getImage() != null) {
            imageLabel.setIcon(new ImageIcon(product.getImage()));
        } else {
            imageLabel.setIcon(new ImageIcon("placeholder.jpg"));
        }
        card.add(imageLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 1));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(product.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("Poppins", Font.BOLD, 14));

        JLabel categoryLabel = new JLabel("Category: " + product.getCategory(), JLabel.CENTER);
        categoryLabel.setFont(new Font("Poppins", Font.BOLD, 12));

        JLabel priceLabel = new JLabel("Price: Rp." + product.getPrice(), JLabel.CENTER);
        priceLabel.setFont(new Font("Poppins", Font.BOLD, 12));

        JLabel stockLabel = new JLabel("Stock: " + product.getStock(), JLabel.CENTER);
        stockLabel.setFont(new Font("Poppins", Font.BOLD, 12));

        infoPanel.add(nameLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(stockLabel);

        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

    private int selectProduct() {
        String[] productNames = adminController.getProductList().stream()
                .map(ProductAdminModel::getName)
                .toArray(String[]::new);

        String selectedProduct = (String) JOptionPane.showInputDialog(
                null,
                "Select a product:",
                "Edit/Delete Product",
                JOptionPane.QUESTION_MESSAGE,
                null,
                productNames,
                productNames[0]);

        if (selectedProduct != null) {
            for (int i = 0; i < adminController.getProductList().size(); i++) {
                if (adminController.getProductList().get(i).getName().equals(selectedProduct)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void refreshProducts() {
        loadProducts();
    }
}
