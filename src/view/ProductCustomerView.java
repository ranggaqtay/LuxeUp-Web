package view;

import Controller.ShoppingCartController;
import Model.ShoppingCartModel;
import View.SignInView;
import controller.ProductCustomerController;
import model.ProductAdminModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class ProductCustomerView extends JFrame {

    private ProductCustomerController customerController;
    private JPanel productPanel;
    private JButton cartButton, logoutButton;

    public ProductCustomerView(ProductCustomerController controller) {
        this.customerController = controller;


        setTitle("Luxe Up - Product List");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
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

        cartButton = new JButton("Cart");
        logoutButton = new JButton("Logout");

        buttonPanel.add(cartButton);
        buttonPanel.add(logoutButton);

        buttonPanel.setBounds(500, 10, 500, 50);
        headerPanel.add(buttonPanel);

        headerPanel.setPreferredSize(new Dimension(1920, 70));
        add(headerPanel, BorderLayout.NORTH);

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

        
        cartButton.addActionListener(e -> {
            dispose();
            new ShoppingCartView(customerController); 
        });

        
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

        // Product Panel
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

        setVisible(true);
    }

    private void loadProducts() {
        productPanel.removeAll();

        
        List<ProductAdminModel> products = customerController.getUpdatedProductList();

        if (products == null || products.isEmpty()) {
            JLabel noProductsLabel = new JLabel("No products available.", JLabel.CENTER);
            noProductsLabel.setFont(new Font("Poppins", Font.BOLD, 18));
            noProductsLabel.setForeground(Color.RED);
            productPanel.add(noProductsLabel);
        } else {
            for (ProductAdminModel product : products) {
                JPanel card = createProductCard(product);
                productPanel.add(card);
            }
        }

        productPanel.revalidate();
        productPanel.repaint();
    }

    private JPanel createProductCard(ProductAdminModel product) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(200, 250));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        card.setBackground(Color.WHITE);

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 150));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            if (product.getImage() != null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(product.getImage());
                BufferedImage bufferedImage = ImageIO.read(bais);
                if (bufferedImage != null) {
                    ImageIcon icon = new ImageIcon(bufferedImage.getScaledInstance(200, 150, Image.SCALE_SMOOTH));
                    imageLabel.setIcon(icon);
                }
            } else {
                imageLabel.setIcon(new ImageIcon("placeholder.jpg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
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

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Poppins", Font.PLAIN, 12));
        addToCartButton.addActionListener(e -> {
            if (product.getStock() > 0) {
                ShoppingCartModel cartItem = new ShoppingCartModel(product.getName(), product.getPrice(), 1);
                ShoppingCartController.addToCart(cartItem);
                product.setStock(product.getStock() - 1); 
                stockLabel.setText("Stock: " + product.getStock()); 
                JOptionPane.showMessageDialog(this, product.getName() + " added to cart!", "Cart", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Out of stock!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        infoPanel.add(nameLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(stockLabel);
        infoPanel.add(addToCartButton);

        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

    public void refreshProducts() {
        loadProducts(); 
    }
}
