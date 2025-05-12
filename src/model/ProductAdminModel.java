package model;

public class ProductAdminModel {
    private int productId;
    private String name;
    private String category;
    private double price;
    private int stock;
    private byte[] image; 

   
    public ProductAdminModel(int productId, String name, String category, double price, int stock, byte[] image) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

   
    public int getProductId() {
        return productId;
    }

   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

  
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

   
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
