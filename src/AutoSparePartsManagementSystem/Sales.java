
package AutoSparePartsManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Osama
 */
public class Sales extends javax.swing.JInternalFrame {

    
    private Connection connect=null;
    private Statement statement=null;
    private PreparedStatement preparedstatement=null;
    private ResultSet resultSet=null;
    
    
    int data0 = 1;
    float grandTotal = 0;
    float amt = 0;
    float tamt = 0;
    public String category;

    
    
    public Sales() {
        initComponents();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/autosparepart?user=root&password=");

            Statement stmt = connect.createStatement();

        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    public void billData(String invoiceid) {
        updateHead(invoiceid);
        try {
            inv_no.setText(invoiceid);
            Statement stmt = connect.createStatement();

            String sql = "SELECT `INVITEMSNO` AS `S.NO.`, PARTNO, PARTNAME, QUANTITY, PRICE, AMOUNT, DISCOUNT,`ITEMTOTAL` AS `ITEM TOTAL` FROM salesitems INNER JOIN invoice ON salesitems.INVOICEID=invoice.INVOICEID WHERE invoice.INVOICEID = '" + invoiceid + "'";
            ResultSet rs = stmt.executeQuery(sql);
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void updateHead(String invoiceid) {
        try {
            inv_no.setText(invoiceid);
            Statement stmt = connect.createStatement();

            String sql = "SELECT INVITEMSNO, PARTNO, PARTNAME, QUANTITY, PRICE, AMOUNT, DISCOUNT,ITEMTOTAL, BILLTOTAL, PARTYSNAME FROM salesitems INNER JOIN invoice ON salesitems.INVOICEID=invoice.INVOICEID WHERE invoice.INVOICEID = '" + invoiceid + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String refferedby = rs.getString("BILLTOTAL");
                gtotal.setText(refferedby);
                suppliers_name.removeAllItems();
                suppliers_name.addItem(rs.getString("PARTYSNAME"));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        inv_no = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        gtotal = new javax.swing.JLabel();
        next_btn = new javax.swing.JButton();
        prev_btn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        suppliers_name = new javax.swing.JComboBox<>();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sales Invoice No.");

        inv_no.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        inv_no.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        inv_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inv_noActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Grand Total:");

        gtotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gtotal.setForeground(new java.awt.Color(255, 255, 255));
        gtotal.setText("Bill Total");

        next_btn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        next_btn.setForeground(new java.awt.Color(204, 0, 0));
        next_btn.setText(">");
        next_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next_btnActionPerformed(evt);
            }
        });

        prev_btn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        prev_btn.setForeground(new java.awt.Color(204, 0, 0));
        prev_btn.setText("<");
        prev_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prev_btnActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Customer Name");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        suppliers_name.setEditable(true);
        suppliers_name.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        suppliers_name.setToolTipText("");
        suppliers_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suppliers_nameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(inv_no, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(prev_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(next_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel7)
                        .addGap(6, 6, 6)
                        .addComponent(suppliers_name, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(gtotal)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inv_no, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(suppliers_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(next_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(gtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(prev_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inv_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inv_noActionPerformed
       billData(inv_no.getText()); // TODO add your handling code here:
    }//GEN-LAST:event_inv_noActionPerformed

    private void next_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_next_btnActionPerformed
           String next = inv_no.getText();
        int inext = Integer.parseInt(next);
        int fnext = inext++;
        String snext = Integer.toString(inext);
        billData(snext);        // TODO add your handling code here:
    }//GEN-LAST:event_next_btnActionPerformed

    private void prev_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prev_btnActionPerformed
        String next = inv_no.getText();
        int inext = Integer.parseInt(next);
        int fnext = inext--;
        String snext = Integer.toString(inext);
        billData(snext);        // TODO add your handling code here:
    }//GEN-LAST:event_prev_btnActionPerformed

    private void suppliers_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suppliers_nameActionPerformed
        
    }//GEN-LAST:event_suppliers_nameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel gtotal;
    private javax.swing.JTextField inv_no;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton next_btn;
    private javax.swing.JButton prev_btn;
    private javax.swing.JComboBox<String> suppliers_name;
    // End of variables declaration//GEN-END:variables
}
