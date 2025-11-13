/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pvd.view.lists;

import javax.swing.table.DefaultTableModel;
import pvd.controller.CustomerController;
import pvd.controller.SaleController;
import pvd.model.Customer;
import pvd.model.Sale;
import pvd.view.helpers.WindowHelper;

/**
 *
 * @author denis
 */
public class SalesList extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SalesList.class.getName());

    /**
     * Creates new form SalesList
     */
    public SalesList() {
        initComponents();
        this.listSalesData();
        this.listTotalPerPaymentMethodData();
        WindowHelper.centralize(this);
    }

    private void listSalesData() {
        String[][] rows = getSalesRows();
        String[] columns = getSalesColumns();

        DefaultTableModel model = new DefaultTableModel(rows, columns);
        salesTable.setModel(model);
    }

    private void listTotalPerPaymentMethodData() {
        String[][] rows = getTotalPerPaymentMethodRows();
        String[] columns = getTotalPerPaymentMethodColumns();

        DefaultTableModel model = new DefaultTableModel(rows, columns);
        totalPerPaymentMethodTable.setModel(model);
    }

    private String[] getSalesColumns() {
        return new String[]{
            "ID",
            "Código do Cliente",
            "Forma de pagamento",
            "Valor total"
        };
    }

    private String[] getTotalPerPaymentMethodColumns() {
        return new String[]{
            "Forma de pagamento",
            "Valor total",};
    }

    private String[][] getSalesRows() {
        SaleController controller = new SaleController();
        CustomerController customerController = new CustomerController();

        java.util.List<Sale> sales = controller.getAll();
        String[][] rows = new String[sales.size()][getSalesColumns().length];

        for (int i = 0; i < sales.size(); i++) {
            Sale sale = sales.get(i);

            rows[i][0] = String.valueOf(sale.getId());

            // Busca o cliente usando o controller
            String customerCode = "-";
            try {
                if (sale.getCustomerId() != null) {
                    Customer customer = customerController.getById(sale.getCustomerId());
                    if (customer != null && customer.getCode() != null) {
                        customerCode = customer.getCode();
                    }
                }
            } catch (Exception e) {
                customerCode = "Erro";
                e.printStackTrace();
            }

            rows[i][1] = customerCode;
            rows[i][2] = sale.getPaymentMethod();
            rows[i][3] = String.format("%.2f", sale.getTotal());
        }

        return rows;
    }

    private String[][] getTotalPerPaymentMethodRows() {
        SaleController controller = new SaleController();
        java.util.List<Sale> sales = controller.getAll();

        java.util.Map<String, Double> totalsByMethod = new java.util.HashMap<>();

        for (Sale sale : sales) {
            String method = sale.getPaymentMethod();
            if (method == null || method.isBlank()) {
                method = "Não informado";
            }

            totalsByMethod.put(method,
                    totalsByMethod.getOrDefault(method, 0.0) + sale.getTotal());
        }

        String[][] rows = new String[totalsByMethod.size()][getTotalPerPaymentMethodColumns().length];
        int i = 0;
        for (java.util.Map.Entry<String, Double> entry : totalsByMethod.entrySet()) {
            rows[i][0] = entry.getKey();
            rows[i][1] = String.format("%.2f", entry.getValue());
            i++;
        }

        return rows;
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
        salesTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        totalPerPaymentMethodTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        printButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        salesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(salesTable);

        jLabel1.setText("Vendas");

        totalPerPaymentMethodTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(totalPerPaymentMethodTable);

        jLabel2.setText("Valor recebido por método de pagamento");

        printButton.setText("Imprimir");
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        this.print();
    }//GEN-LAST:event_printButtonActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new SalesList().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton printButton;
    private javax.swing.JTable salesTable;
    private javax.swing.JTable totalPerPaymentMethodTable;
    // End of variables declaration//GEN-END:variables
}
