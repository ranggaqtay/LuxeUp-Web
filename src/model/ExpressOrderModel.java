package model;

public class ExpressOrderModel extends OrderModel {
    private double expressDeliveryFee;
    private int estimatedDeliveryTime;

    public ExpressOrderModel(int orderId, java.util.Date orderDate, double totalPrice, String customer, double expressDeliveryFee, int estimatedDeliveryTime) {
        super(orderId, orderDate, totalPrice, customer);
        this.expressDeliveryFee = expressDeliveryFee;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public double getExpressDeliveryFee() {
        return expressDeliveryFee;
    }

    public void setExpressDeliveryFee(double expressDeliveryFee) {
        this.expressDeliveryFee = expressDeliveryFee;
    }

    public int getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(int estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    @Override
    public String generateInvoice() {
        return "=== Express Order Invoice ===\n" +
               "Order ID: " + getOrderId() + "\n" +
               "Date: " + getOrderDate() + "\n" +
               "Customer: " + getCustomer() + "\n" +
               "Base Total Price: Rp." + getTotalPrice() + "\n" +
               "Express Delivery Fee: Rp." + expressDeliveryFee + "\n" +
               "Estimated Delivery Time: " + estimatedDeliveryTime + " hours\n" +
               "Total (including delivery): Rp." + calculateTotal();
    }

    @Override
    public double calculateTotal() {
        return getTotalPrice() + expressDeliveryFee;
    }

    @Override
    public void pilihOpsi(String option) {
        System.out.println("Express order option selected: " + option);
    }
}
