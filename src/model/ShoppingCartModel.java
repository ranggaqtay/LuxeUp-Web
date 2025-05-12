package Model;

public class ShoppingCartModel {
    private String name;
    private double price;
    private int quantity;

    public ShoppingCartModel(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;   
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}