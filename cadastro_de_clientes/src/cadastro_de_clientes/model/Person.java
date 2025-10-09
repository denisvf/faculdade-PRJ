/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastro_de_clientes.model;

/**
 *
 * @author denis
 */

public class Person {
    private int id;
    private String name, phone, email, street, number, complement, neighborhood, city, state, cep;

    public Person(int id, String name, String phone, String email, String cep, String street,
                  String number, String complement, String neighborhood, String city,
                  String state) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.cep = cep;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getStreet() { return street; }
    public String getNumber() { return number; }
    public String getComplement() { return complement; }
    public String getNeighborhood() { return neighborhood; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCep() { return cep; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setStreet(String street) { this.street = street; }
    public void setNumber(String number) { this.number = number; }
    public void setComplement(String complement) { this.complement = complement; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setCep(String cep) { this.cep = cep; }
}
