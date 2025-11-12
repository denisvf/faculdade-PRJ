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
public class Product extends Model {

    private String code;
    private String name;
    private String unit;
    private double price;
    private double stockQuantity;
    private Date lastSaleDate;

    public String getName() {
        return this.name;
    }
    
    public String getCode() {
        return this.code;
    }

    public String getUnit() {
        return this.unit;
    }

    public double getPrice() {
        return this.price;
    }

    public double getStockQuantity() {
        return this.stockQuantity;
    }

    public Date getLastSaleDate() {
        return this.lastSaleDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setLastSaleDate(Date date) {
        this.lastSaleDate = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStockQuantity(double stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

}
