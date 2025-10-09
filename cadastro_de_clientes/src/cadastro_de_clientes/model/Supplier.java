/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastro_de_clientes.model;

/**
 *
 * @author denis
 */

public class Supplier extends Person {

    private String cnpj;

    public Supplier(int id, String name, String phone, String email, String cep, String street,
                    String number, String complement, String neighborhood, String city, 
                    String state, String cnpj) {
        super(id, name, phone, email, cep, street, number, complement, neighborhood, city, state);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    // Se necessário, métodos específicos para fornecedor
}
