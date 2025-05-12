package Controller;

import Model.ShoppingCartModel;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ShoppingCartController {
    private static List<ShoppingCartModel> cartItems = new ArrayList<>();

    
    public static void addToCart(ShoppingCartModel item) {
    for (ShoppingCartModel cartItem : cartItems) {
        if (cartItem.getName().equals(item.getName())) {
            
            int availableStock = getAvailableStock(item.getName());

            
            if (cartItem.getQuantity() < availableStock) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            } else {
                
                JOptionPane.showMessageDialog(null, 
                    "Stok tidak cukup untuk produk " + item.getName() + ". Stok yang tersedia: " + availableStock, 
                    "Peringatan Stok", JOptionPane.WARNING_MESSAGE);
            }
            return;
        }
    }

    
    int availableStock = getAvailableStock(item.getName());
    if (item.getQuantity() <= availableStock) {
        cartItems.add(item);
    } else {
        
        JOptionPane.showMessageDialog(null, 
            "Stok tidak cukup untuk produk " + item.getName() + ". Stok yang tersedia: " + availableStock, 
            "Peringatan Stok", JOptionPane.WARNING_MESSAGE);
    }
}


    
    public static List<ShoppingCartModel> getCartItems() {
        return cartItems;
    }

    
    public static void clearCart() {
        cartItems.clear();
    }

    
    public static void returnStockToDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/luxeup", "root", "")) {
            for (ShoppingCartModel item : cartItems) {
                String updateQuery = "UPDATE products SET stock = stock + ? WHERE name = ?";
                PreparedStatement ps = conn.prepareStatement(updateQuery);
                ps.setInt(1, item.getQuantity());
                ps.setString(2, item.getName());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Error returning stock to database: " + e.getMessage());
        }
    }

    
    public static double calculateTotalPrice() {
        return cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    
    public static void removeItemFromCart(ShoppingCartModel item) {
        cartItems.removeIf(cartItem -> cartItem.getName().equals(item.getName()));
    }

    
    public static boolean updateStockAfterCheckout() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/luxeup", "root", "")) {
            for (ShoppingCartModel item : cartItems) {
                String updateQuery = "UPDATE products SET stock = stock - ? WHERE name = ?";
                PreparedStatement ps = conn.prepareStatement(updateQuery);
                ps.setInt(1, item.getQuantity()); 
                ps.setString(2, item.getName());
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 0) {
                    System.out.println("Failed to update stock for: " + item.getName());
                    return false;
                }
            }
            return true; 
        } catch (Exception e) {
            System.err.println("Error updating stock after checkout: " + e.getMessage());
            return false;
        }
    }

     
    public static void increaseItemQuantity(ShoppingCartModel item) {
    for (ShoppingCartModel cartItem : cartItems) {
        if (cartItem.getName().equals(item.getName())) {
            
            int availableStock = getAvailableStock(item.getName());

            
            if (cartItem.getQuantity() < availableStock) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            } else {
                
                JOptionPane.showMessageDialog(null, 
                    "Stok tidak cukup untuk produk " + item.getName() + ". Stok yang tersedia: " + availableStock, 
                    "Peringatan Stok", JOptionPane.WARNING_MESSAGE);
            }
            break;
        }
    }
}


    
    public static void decreaseItemQuantity(ShoppingCartModel item) {
        for (ShoppingCartModel cartItem : cartItems) {
            if (cartItem.getName().equals(item.getName())) {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }
                break;
            }
        }
    }

    private static int getAvailableStock(String productName) {
    String dbUrl = "jdbc:mysql://localhost:3306/luxeup"; 
    String dbUser = "root"; 
    String dbPassword = ""; 

    String query = "SELECT stock FROM products WHERE name = ?";
    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
         PreparedStatement ps = conn.prepareStatement(query)) {
        
        ps.setString(1, productName);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("stock");
        }
    } catch (SQLException e) {
        System.err.println("Error fetching available stock: " + e.getMessage());
    }

    return 0; 
    }   
}
