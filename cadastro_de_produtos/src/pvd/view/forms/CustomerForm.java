/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pvd.view.forms;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import pvd.controller.AddressController;
import pvd.controller.CustomerController;
import pvd.model.Address;
import pvd.model.Customer;
import pvd.view.helpers.WindowHelper;
import pvd.view.lists.CustomerList;

/**
 *
 * @author denis
 */
public class CustomerForm extends Form {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CustomerForm.class.getName());
    
    private int addressToUpdateId = 0; 
    /**
     * Creates new form CostumerForm
     */
    public CustomerForm(CustomerList parentList) {
        super(parentList);
        initComponents();
    }

    public CustomerForm(CustomerList parentList, int customerId) {
        super(parentList, customerId);
        initComponents();
        this.fillFields();
        WindowHelper.centralize(this);
    }

    private Address buildAddress() {
        Address address = new Address();

        address.setZipCode(getZipCodeTextInput().getText().trim());
        address.setStreet(getStreetTextInput().getText().trim());
        address.setNumber(Integer.parseInt(getNumberTextInput().getText().trim()));
        address.setCity(getCityTextInput().getText().trim());
        address.setState(getStateComboBox().getSelectedItem().toString());
        address.setComplement(getComplementTextInput().getText().trim());

        return address;
    }

    private Customer buildCustomer() {
        Customer customer = new Customer();

        String name = getNameTextInput().getText().trim();
        String code = getCodeTextInput().getText().trim();
        String email = getEmailTextInput().getText().trim();
        String phoneNumber = getPhoneTextInput().getText().trim();

        customer.setName(name);
        customer.setCode(code);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);

        return customer;
    }

    @Override
    protected boolean create() {
        try {
            Address address = this.buildAddress();
            AddressController addressController = new AddressController();

            int addressId = addressController.createAndReturnId(address);
            if (addressId <= 0) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar endereço.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            Customer customer = this.buildCustomer();
            customer.setAddressId(addressId);

            CustomerController customerController = new CustomerController();
            boolean created = customerController.create(customer);

            return created;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Número inválido. Verifique o campo 'número' do endereço.",
                    "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro inesperado ao criar o cliente: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    protected boolean update() {
        try {
            Address address = this.buildAddress();
            Customer customer = this.buildCustomer();
            customer.setId(this.itemToUpdateId);
            customer.setAddressId(this.addressToUpdateId);
            address.setId(this.addressToUpdateId);
            
            AddressController addressController = new AddressController();
            CustomerController customerController = new CustomerController();

            boolean addressUpdated = addressController.update(address);
            if (!addressUpdated) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar endereço.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            boolean updated = customerController.update(customer);
            return updated;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Número inválido. Verifique o campo 'número' do endereço.",
                    "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro inesperado ao atualizar o cliente: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    protected void fillFields() {
        try {
            CustomerController customerController = new CustomerController();
            AddressController addressController = new AddressController();
            Customer customer = customerController.getById(this.itemToUpdateId);
            Address address = addressController.getById(customer.getAddressId());
            
            this.addressToUpdateId = customer.getAddressId();

            if (customer != null) {
                // Dados do cliente
                getNameTextInput().setText(customer.getName());
                getCodeTextInput().setText(customer.getCode());
                getEmailTextInput().setText(customer.getEmail());
                getPhoneTextInput().setText(customer.getPhoneNumber());

                // Dados do endereço
//                Address address = customer.getAddress();
                if (address != null) {
                    getZipCodeTextInput().setText(address.getZipCode());
                    getStreetTextInput().setText(address.getStreet());
                    getNumberTextInput().setText(String.valueOf(address.getNumber()));
                    getCityTextInput().setText(address.getCity());
                    getStateComboBox().setSelectedItem(address.getState());
                    getComplementTextInput().setText(address.getComplement());
                }

            } else {
                JOptionPane.showMessageDialog(this,
                        "O cliente selecionado não foi encontrado.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                this.dispose();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados do cliente: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JTextField getNameTextInput() {
        return nameTextInput;
    }

    public JTextField getCodeTextInput() {
        return codeTextInput;
    }

    public JTextField getEmailTextInput() {
        return emailTextInput;
    }

    public JTextField getPhoneTextInput() {
        return phoneTextInput;
    }

    public JTextField getZipCodeTextInput() {
        return zipCodeTextInput;
    }

    public JTextField getStreetTextInput() {
        return streetTextInput;
    }

    public JTextField getNumberTextInput() {
        return numberTextInput;
    }

    public JTextField getCityTextInput() {
        return cityTextInput;
    }

    public JComboBox getStateComboBox() {
        return stateComboBox;
    }

    public JTextField getComplementTextInput() {
        return complementTextInput;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        codeTextInput = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        nameTextInput = new javax.swing.JTextField();
        phoneTextInput = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        emailTextInput = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cityTextInput = new javax.swing.JTextField();
        zipCodeTextInput = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        streetTextInput = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        numberTextInput = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        complementTextInput = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        stateComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel2.setText("Nome");

        codeTextInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeTextInputActionPerformed(evt);
            }
        });

        jLabel3.setText("Código");

        jLabel4.setText("Telefone");

        jLabel5.setText("Email");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codeTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Cliente");

        jLabel10.setText("Endereço");

        jLabel6.setText("Cidade");

        jLabel7.setText("Estado");

        jLabel8.setText("CEP");

        jLabel9.setText("Logradouro");

        jLabel11.setText("Número");

        jLabel12.setText("Complemento");

        stateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC - Acre  ", "AL - Alagoas  ", "AP - Amapá  ", "AM - Amazonas  ", "BA - Bahia  ", "CE - Ceará  ", "DF - Distrito Federal  ", "ES - Espírito Santo  ", "GO - Goiás  ", "MA - Maranhão  ", "MT - Mato Grosso  ", "MS - Mato Grosso do Sul  ", "MG - Minas Gerais  ", "PA - Pará  ", "PB - Paraíba  ", "PR - Paraná  ", "PE - Pernambuco  ", "PI - Piauí  ", "RJ - Rio de Janeiro  ", "RN - Rio Grande do Norte  ", "RS - Rio Grande do Sul  ", "RO - Rondônia  ", "RR - Roraima  ", "SC - Santa Catarina  ", "SP - São Paulo  ", "SE - Sergipe  ", "TO - Tocantins", " " }));
        stateComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stateComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(complementTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numberTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(streetTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zipCodeTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cityTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cityTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(zipCodeTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(streetTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(complementTextInput, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codeTextInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeTextInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codeTextInputActionPerformed

    private void stateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stateComboBoxActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cityTextInput;
    private javax.swing.JTextField codeTextInput;
    private javax.swing.JTextField complementTextInput;
    private javax.swing.JTextField emailTextInput;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField nameTextInput;
    private javax.swing.JTextField numberTextInput;
    private javax.swing.JTextField phoneTextInput;
    private javax.swing.JComboBox<String> stateComboBox;
    private javax.swing.JTextField streetTextInput;
    private javax.swing.JTextField zipCodeTextInput;
    // End of variables declaration//GEN-END:variables
}
