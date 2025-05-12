package controller;

import model.ProductAdminModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCustomerController {
    private List<ProductAdminModel> productList;

    public ProductCustomerController() {
        this.productList = new ArrayList<>();
        
        loadProductsFromDatabase();
    }

    private void loadProductsFromDatabase() {
        String dbUrl = "jdbc:mysql://localhost:3306/luxeup"; 
        String dbUser = "root"; 
        String dbPassword = ""; 

        String query = "SELECT * FROM products";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int productId = rs.getInt("productId");
                String name = rs.getString("name");
                String category = rs.getString("category");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                byte[] image = rs.getBytes("image"); 

                
                productList.add(new ProductAdminModel(productId, name, category, price, stock, image));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ProductAdminModel> getProductList() {
        return productList;
    }

   public List<ProductAdminModel> getUpdatedProductList() {
    List<ProductAdminModel> productList = new ArrayList<>();
    String dbUrl = "jdbc:mysql://localhost:3306/luxeup"; 
    String dbUser = "root"; 
    String dbPassword = ""; 

    String query = "SELECT * FROM products";

    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int productId = rs.getInt("productId"); 
            String name = rs.getString("name"); 
            String category = rs.getString("category"); 
            double price = rs.getDouble("price"); 
            int stock = rs.getInt("stock"); 
            byte[] image = rs.getBytes("image"); 

            
            ProductAdminModel product = new ProductAdminModel(productId, name, category, price, stock, image);
            productList.add(product);
        }
    } catch (SQLException e) {
        System.err.println("Error fetching updated product list: " + e.getMessage());
    }

    return productList; 
    }
}   
