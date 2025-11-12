/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pvd.model;
import java.util.Date;

/**
 *
 * @author denis
 */
public class Sale extends Model {
    private Date saleDateTime;
    private double total;
    private Integer customerId;
    private String paymentMethod;

    public Date getSaleDateTime() {
        return saleDateTime;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setSaleDateTime(Date saleDateTime) {
        this.saleDateTime = saleDateTime;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}

