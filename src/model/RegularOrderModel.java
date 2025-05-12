package model;

public class RegularOrderModel extends OrderModel {
    private double regularDeliveryFee;
    private int estimatedDeliveryTime;

    public RegularOrderModel(int orderId, java.util.Date orderDate, double totalPrice, String customer, double regularDeliveryFee, int estimatedDeliveryTime) {
        super(orderId, orderDate, totalPrice, customer);
        this.regularDeliveryFee = regularDeliveryFee;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public double getRegularDeliveryFee() {
        return regularDeliveryFee;
    }

    public void setRegularDeliveryFee(double regularDeliveryFee) {
        this.regularDeliveryFee = regularDeliveryFee;
    }

    public int getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(int estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    @Override
    public String generateInvoice() {
        return "=== Regular Order Invoice ===\n" +
               "Order ID: " + getOrderId() + "\n" +
               "Date: " + getOrderDate() + "\n" +
               "Customer: " + getCustomer() + "\n" +
               "Base Total Price: Rp." + getTotalPrice() + "\n" +
               "Regular Delivery Fee: Rp." + regularDeliveryFee + "\n" +
               "Estimated Delivery Time: " + estimatedDeliveryTime + " days\n" +
               "Total (including delivery): Rp." + calculateTotal();
    }

    @Override
    public double calculateTotal() {
        return getTotalPrice() + regularDeliveryFee;
    }

    @Override
    public void pilihOpsi(String option) {
        System.out.println("Regular order option selected: " + option);
    }
}
