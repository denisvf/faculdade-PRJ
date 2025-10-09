/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cadastro_de_clientes.view;

import cadastro_de_clientes.Database;
import cadastro_de_clientes.model.Supplier;
import cadastro_de_clientes.view.forms.JFrRegistrySupplier;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author denis
 */
public class JFrListSuppliers extends JFrList {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFrListSuppliers.class.getName());
    private ArrayList<Supplier> suppliers = new ArrayList<>();

    /**
     * Creates new form JFrListSuppliers
     */
    public JFrListSuppliers() {
        super();
        suppliers = new ArrayList<>();
        this.setTitle("Lista de fornecedores");
        this.listData();
    }

    @Override
    protected void openForm() {
        JFrRegistrySupplier form = new JFrRegistrySupplier();
        form.setVisible(true);
    }

    @Override
    protected String[][] getRows() {
        fetchSuppliers();
        String[][] rows = new String[suppliers.size()][12];

        for (int i = 0; i < suppliers.size(); i++) {
            Supplier s = suppliers.get(i);
            rows[i][0] = String.valueOf(s.getId());
            rows[i][1] = s.getName();
            rows[i][2] = s.getPhone();
            rows[i][3] = s.getEmail();
            rows[i][4] = s.getCep();
            rows[i][5] = s.getStreet();
            rows[i][6] = s.getNumber();
            rows[i][7] = s.getComplement();
            rows[i][8] = s.getNeighborhood();
            rows[i][9] = s.getCity();
            rows[i][10] = s.getState();
            rows[i][11] = s.getCnpj();
        }
        return rows;
    }

    @Override
    protected String[] getColumns() {
        return new String[]{
            "Id", "Nome", "Telefone", "Email", "Cep", "Rua", "Número", "Complemento",
            "Bairro", "Cidade", "Estado", "CNPJ"
        };
    }

    private void fetchSuppliers() {
        suppliers.clear();
        String sql = "SELECT * FROM supplier";

        try (Connection conn = Database.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                suppliers.add(new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("cep"),
                        rs.getString("street"),
                        rs.getString("number"),
                        rs.getString("complement"),
                        rs.getString("neighborhood"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("cnpj")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void deleteItem(int selectedRow) {
        String sql = "DELETE FROM supplier WHERE id = ?";
        Supplier s = getSelectedSupplier(selectedRow);
        try (java.sql.Connection conn = Database.getConnection(); java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, s.getId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                suppliers.remove(selectedRow);
                listData();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Falha ao excluir fornecedor.");
            }

        } catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    @Override
    protected void editItem(int selectedRow) {

        Supplier s = getSelectedSupplier(selectedRow);

        JFrRegistrySupplier form = new JFrRegistrySupplier();

        form.getNameText().setText(s.getName());
        form.getPhoneNumberText().setText(s.getPhone());
        form.getEmailText().setText(s.getEmail());
        form.getCpfText().setText(s.getCnpj());
        form.getCepText().setText(s.getCep());
        form.getStreetText().setText(s.getStreet());
        form.getNumberText().setText(s.getNumber());
        form.getComplementText().setText(s.getComplement());
        form.getNeighborhoodText().setText(s.getNeighborhood());
        form.getCityText().setText(s.getCity());
        form.getStateComboBox().setSelectedItem(s.getState());

        for (ActionListener al : form.getSaveButton().getActionListeners()) {
            form.getSaveButton().removeActionListener(al);
        }
        
        form.getSaveButton().addActionListener(evt -> {
            s.setName(form.getNameText().getText());
            s.setPhone(form.getPhoneNumberText().getText());
            s.setEmail(form.getEmailText().getText());
            s.setCnpj(form.getCpfText().getText());
            s.setCep(form.getCepText().getText());
            s.setStreet(form.getStreetText().getText());
            s.setNumber(form.getNumberText().getText());
            s.setComplement(form.getComplementText().getText());
            s.setNeighborhood(form.getNeighborhoodText().getText());
            s.setCity(form.getCityText().getText());
            s.setState((String) form.getStateComboBox().getSelectedItem());

            String sql = "UPDATE supplier SET name=?, phone=?, email=?, cnpj=?, cep=?, street=?, number=?, complement=?, neighborhood=?, city=?, state=? WHERE id=?";
            try (java.sql.Connection conn = Database.getConnection(); java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, s.getName());
                stmt.setString(2, s.getPhone());
                stmt.setString(3, s.getEmail());
                stmt.setString(4, s.getCnpj());
                stmt.setString(5, s.getCep());
                stmt.setString(6, s.getStreet());
                stmt.setString(7, s.getNumber());
                stmt.setString(8, s.getComplement());
                stmt.setString(9, s.getNeighborhood());
                stmt.setString(10, s.getCity());
                stmt.setString(11, s.getState());
                stmt.setInt(12, s.getId());

                stmt.executeUpdate();

                javax.swing.JOptionPane.showMessageDialog(form, "Cliente atualizado com sucesso!");
                listData();
                form.dispose();

            } catch (java.sql.SQLException ex) {
                ex.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(form, "Erro: " + ex.getMessage());
            }
        });

        form.setVisible(true);
    }

    public Supplier getSelectedSupplier(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < suppliers.size()) {
            return suppliers.get(rowIndex);
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de Fornecedores");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 815, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new JFrListSuppliers().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
