package model;

import java.sql.Date;

public abstract class OrderModel {
    private int orderId;
    private Date orderDate;
    private double totalPrice;
    private String customer;


    public OrderModel(int orderId, java.util.Date orderDate, double totalPrice, String customer) {
        this.orderId = orderId;
        this.orderDate = new Date(orderDate.getTime());
        this.totalPrice = totalPrice;
        this.customer = customer;
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getCustomer() {
        return customer;
    }

    

    public abstract String generateInvoice();

    public abstract double calculateTotal();

    public abstract void pilihOpsi(String option);
}
