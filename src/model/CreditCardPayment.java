package model;

import java.util.Random;
import javax.swing.JOptionPane;

public class CreditCardPayment implements Payment {
    Random random = new Random();
    private int noVA;
    private String paymentDetails;
    
    public CreditCardPayment(){
        this.noVA = 100000 + random.nextInt(900000); 
    }
    
    @Override
    public String processPayment(){
         return "=========Payment========\n" +
                "Segera Melakukan Pembayaran\n" +
                "Virtual Account Number :" + noVA;
    }
}
