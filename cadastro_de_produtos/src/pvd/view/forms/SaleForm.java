/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pvd.view.forms;

import javax.swing.table.DefaultTableModel;
import pvd.controller.CustomerController;
import pvd.controller.ProductController;
import pvd.controller.SaleController;
import pvd.model.Customer;
import pvd.model.Product;
import pvd.view.helpers.WindowHelper;

/**
 *
 * @author denis
 */
public class SaleForm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SaleForm.class.getName());

    /**
     * Creates new form SaleForm
     */
    public SaleForm() {
        initComponents();
        this.listCustomerData();
        this.listProductData();
//        WindowHelper.centralize(this);
    }

    private void listCustomerData() {
        String[][] rows = getCustomerRows();
        String[] columns = getCustomerColumns();

        DefaultTableModel model = new DefaultTableModel(rows, columns);
        customerTable.setModel(model);
    }

    private void listProductData() {
        String[][] rows = getProductRows();
        String[] columns = getProductColumns();

        DefaultTableModel model = new DefaultTableModel(rows, columns);
        productsTable.setModel(model);
    }

    private String[] getCustomerColumns() {
        return new String[]{
            "Nome",
            "Código",};
    }

    private String[][] getCustomerRows() {
        CustomerController customerController = new CustomerController();
        java.util.List<Customer> customers = customerController.getAll();

        String[][] rows = new String[customers.size()][getCustomerColumns().length];

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);

            rows[i][0] = customer.getName();
            rows[i][1] = (customer.getCode() != null) ? customer.getCode() : "-";
        }
        return rows;
    }

    private String[] getProductColumns() {
        return new String[]{
            "Nome",
            "Código",
            "Preço",
            "estoque",
            "unidade",
            "Quantidade"
        };
    }

    private String[][] getProductRows() {
        ProductController controller = new ProductController();
        java.util.List<Product> products = controller.getAll();

        String[][] rows = new String[products.size()][getProductColumns().length];

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            rows[i][0] = product.getName();
            rows[i][1] = (product.getCode() != null) ? product.getCode() : "-";
            rows[i][2] = String.format("%.2f", product.getPrice());
            rows[i][3] = String.format("%.2f", product.getStockQuantity());
            rows[i][4] = product.getUnit();
        }

        return rows;
    }

    private boolean buy() {
        try {
            int[] selectedRows = productsTable.getSelectedRows();
            if (selectedRows.length == 0) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Selecione ao menos um produto para realizar a compra.",
                        "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            Integer customerId = null;
            int selectedCustomerRow = customerTable.getSelectedRow();
            if (selectedCustomerRow != -1) {
                CustomerController customerController = new CustomerController();
                java.util.List<Customer> customers = customerController.getAll();
                if (selectedCustomerRow < customers.size()) {
                    customerId = customers.get(selectedCustomerRow).getId();
                }
            }

            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
            double totalSale = 0.0;

            pvd.model.Sale sale = new pvd.model.Sale();
            sale.setCustomerId(customerId);
            sale.setPaymentMethod(paymentMethod);
            sale.setSaleDateTime(new java.util.Date());

            ProductController productController = new ProductController();
            java.util.List<Product> allProducts = productController.getAll();

            java.util.List<pvd.model.SaleItem> saleItems = new java.util.ArrayList<>();

            for (int row : selectedRows) {
                Product product = allProducts.get(row);

                Object quantityObj = productsTable.getValueAt(row, 5);
                if (quantityObj == null || quantityObj.toString().isBlank()) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Informe a quantidade para o produto: " + product.getName(),
                            "Quantidade inválida", javax.swing.JOptionPane.WARNING_MESSAGE);
                    return false;
                }

                int quantity = Integer.parseInt(quantityObj.toString());
                if (quantity <= 0) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "A quantidade do produto deve ser maior que zero: " + product.getName(),
                            "Quantidade inválida", javax.swing.JOptionPane.WARNING_MESSAGE);
                    return false;
                }

                if (product.getStockQuantity() < quantity) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Estoque insuficiente para o produto: " + product.getName(),
                            "Erro de estoque", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                double price = product.getPrice();
                double total = price * quantity;
                totalSale += total;

                pvd.model.SaleItem item = new pvd.model.SaleItem();
                item.setProductId(product.getId());
                item.setQuantity(quantity);
                item.setPrice(price);
                item.setTotal(total);

                saleItems.add(item);
            }

            sale.setTotal(totalSale);

            pvd.controller.SaleController saleController = new SaleController();
            int saleId = saleController.createAndReturnId(sale);

            if (saleId <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Erro ao registrar a venda. ID retornado inválido.",
                        "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }

            pvd.controller.SaleItemController saleItemController = new pvd.controller.SaleItemController();

            for (pvd.model.SaleItem item : saleItems) {
                item.setSaleId(saleId);
                saleItemController.create(item);

                Product product = productController.getById(item.getProductId());
                product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                productController.update(product);
                productController.updateLastSaleDateTime(product.getId(), new java.util.Date());
            }
            
            totalPriceText.setText(String.format("%.2f", totalSale));

            javax.swing.JOptionPane.showMessageDialog(this,
                    "Venda realizada com sucesso! Total: R$ " + String.format("%.2f", totalSale),
                    "Sucesso", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            int printOption = javax.swing.JOptionPane.showConfirmDialog(this,
                    "Deseja imprimir a notinha?", "Imprimir", javax.swing.JOptionPane.YES_NO_OPTION);

            if (printOption == javax.swing.JOptionPane.YES_OPTION) {
                this.print();
            }
            this.listProductData();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Erro ao realizar a compra: " + e.getMessage(),
                    "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void print() {
        try {
            java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
            job.setJobName("Imprimir janela atual");

            job.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) {
                    return java.awt.print.Printable.NO_SUCH_PAGE;
                }
                
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) graphics;
                g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                double scaleX = pageFormat.getImageableWidth() / this.getWidth();
                double scaleY = pageFormat.getImageableHeight() / this.getHeight();
                double scale = Math.min(scaleX, scaleY);
                g2.scale(scale, scale);
                
                this.printAll(g2);

                return java.awt.print.Printable.PAGE_EXISTS;
            });

            if (job.printDialog()) {
                job.print();
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Erro ao imprimir: " + e.getMessage(),
                    "Erro de impressão",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        paymentMethodComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        totalPriceText = new javax.swing.JLabel();
        confirmButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        productsTable.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(productsTable);

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(customerTable);

        jLabel1.setText("Selecione o cliente (opcional)");

        jLabel2.setText("Selecione o produto e digite a quantidade desejada");

        paymentMethodComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Crédito", "Débito", "Pix", "Dinheiro" }));

        jLabel4.setText("Forma de pagamento");

        jLabel5.setText("Total a pagar: ");

        totalPriceText.setText("0");

        confirmButton.setText("Confirmar compra");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1)
                    .addComponent(paymentMethodComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalPriceText, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)))
                        .addGap(87, 87, 87))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(paymentMethodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(totalPriceText))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        this.buy();
    }//GEN-LAST:event_confirmButtonActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new SaleForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirmButton;
    private javax.swing.JTable customerTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> paymentMethodComboBox;
    private javax.swing.JTable productsTable;
    private javax.swing.JLabel totalPriceText;
    // End of variables declaration//GEN-END:variables
}
