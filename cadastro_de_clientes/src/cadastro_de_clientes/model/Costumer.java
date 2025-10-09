/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastro_de_clientes.model;

/**
 *
 * @author denis
 */
public class Costumer extends Person {

    private String cpf;

    public Costumer(int id, String name, String phone, String email, String cep, String street,
            String number, String complement, String neighborhood, String city,
            String state, String cpf) {
        super(id, name, phone, email, cep, street, number, complement, neighborhood, city, state);
        this.cpf = cpf;
    }
    
        public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
