package controller;

import java.util.Date;
import model.ExpressOrderModel;
import model.OrderModel;
import model.RegularOrderModel;

public class OrderController {
    private OrderModel currentOrder;

    
    public void createRegularOrder(int orderId, double totalPrice, String customer, double deliveryFee, int estimatedTime) {
        System.out.println("Creating Regular Order for customer: " + customer);
        this.currentOrder = new RegularOrderModel(orderId, new Date(), totalPrice, customer, deliveryFee, estimatedTime);
    }

    
    public void createExpressOrder(int orderId, double totalPrice, String customer, double deliveryFee, int estimatedTime) {
        System.out.println("Creating Express Order for customer: " + customer);
        this.currentOrder = new ExpressOrderModel(orderId, new Date(), totalPrice, customer, deliveryFee, estimatedTime);
    }

    
    public OrderModel getCurrentOrder() {
        if (currentOrder == null) {
            throw new IllegalStateException("No order has been created yet.");
        }
        return currentOrder;
    }

    
    public String getInvoice() {
        return getCurrentOrder().generateInvoice();
    }

    
    public void selectOption(String option) {
        getCurrentOrder().pilihOpsi(option);
    }

    
    public double calculateTotal() {
        return getCurrentOrder().calculateTotal();
    }
}
