/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pvd.model;

/**
 *
 * @author denis
 */
public class Customer extends Model {
    private String name;
    private String code;
    private String email;
    private String phoneNumber;
    private int addressId;
    
    public String getName() {
        return this.name;
    }
    public String getCode() {
        return this.code;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public int getAddressId() {
        return this.addressId;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
