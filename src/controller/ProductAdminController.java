package controller;

import Database.DatabaseConnection;
import model.ProductAdminModel;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProductAdminController {

    private ArrayList<ProductAdminModel> productList;

    public ProductAdminController() {
        productList = new ArrayList<>();
        loadProductsFromDatabase();
    }

    public ArrayList<ProductAdminModel> getProductList() {
        return productList;
    }

    public void addProduct(ProductAdminModel product) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            
            String checkQuery = "SELECT * FROM Products WHERE name = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, product.getName());
            ResultSet resultSet = checkStatement.executeQuery();

            
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Produk sudah ada bosku, ganti yang lain ye", "Error", JOptionPane.ERROR_MESSAGE);
                return;  
            }

            
            String query = "INSERT INTO Products (name, category, price, stock, image) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStock());
            statement.setBytes(5, product.getImage()); 
            statement.executeUpdate();

            
            loadProductsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editProduct(int index, ProductAdminModel product) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE Products SET name = ?, category = ?, price = ?, stock = ?, image = ? WHERE productId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStock());
            statement.setBytes(5, product.getImage()); 
            statement.setInt(6, product.getProductId());
            statement.executeUpdate();

            
            loadProductsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int index) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            ProductAdminModel product = productList.get(index);
            String query = "DELETE FROM Products WHERE productId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, product.getProductId());
            statement.executeUpdate();

            
            loadProductsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadProductsFromDatabase() {
        productList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Products";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                byte[] image = resultSet.getBytes("image"); 

                ProductAdminModel product = new ProductAdminModel(productId, name, category, price, stock, image);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
