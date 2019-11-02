package AutoSparePartsManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Osama
 */
public class AddProduct extends javax.swing.JInternalFrame {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public AddProduct() {
        initComponents();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/autosparepart?user=root&password=");

            Statement stmt = connect.createStatement();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addItem() {
        Statement stmt = null;

        try {
            stmt = connect.createStatement();
            String sql = "INSERT INTO parts (part_no, name, purchase_price, sales_price, stock_location) "
                    + "VALUES(?,?,?,?,?)";

            preparedStatement = connect.prepareStatement(sql);

            preparedStatement.setString(1, txt_partno.getText());
            preparedStatement.setString(2, txt_partname.getText().toString());
            preparedStatement.setString(4, txt_saleprice.getText());
            preparedStatement.setString(3, txt_purprice.getText());
            preparedStatement.setString(5, txt_location.getText());
            

            preparedStatement.executeUpdate();

            
            JOptionPane.showMessageDialog(null, "Item Added succesfully");
            txt_partno.setText("");
            txt_partname.setText("");
            txt_purprice.setText("");
            txt_saleprice.setText("");
            txt_location.setText("");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Add Item failed. Reason either due to duplicate entry or due to some problem.\n" + e);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_partno = new javax.swing.JTextField();
        txt_partname = new javax.swing.JTextField();
        txt_saleprice = new javax.swing.JTextField();
        txt_purprice = new javax.swing.JTextField();
        txt_location = new javax.swing.JTextField();
        btn_add_product = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Auto Part No.");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 95, -1, 27));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Auto Part Name");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 160, -1, 27));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sales Price");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 160, 83, 27));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Purchase Price");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 96, 100, 27));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Stock location");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 235, 100, 27));

        txt_partno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_partno.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_partno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_partnoActionPerformed(evt);
            }
        });
        jPanel1.add(txt_partno, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 92, 150, 30));

        txt_partname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_partname.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_partname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_partnameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_partname, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 159, 150, 30));

        txt_saleprice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_saleprice.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_saleprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_salepriceActionPerformed(evt);
            }
        });
        jPanel1.add(txt_saleprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(621, 159, 125, 30));

        txt_purprice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_purprice.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_purprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_purpriceActionPerformed(evt);
            }
        });
        jPanel1.add(txt_purprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(621, 92, 125, 30));

        txt_location.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_location.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_location.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_locationActionPerformed(evt);
            }
        });
        jPanel1.add(txt_location, new org.netbeans.lib.awtextra.AbsoluteConstraints(621, 234, 125, 30));

        btn_add_product.setBackground(new java.awt.Color(0, 102, 0));
        btn_add_product.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_add_product.setText("Add Product");
        btn_add_product.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        btn_add_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_productActionPerformed(evt);
            }
        });
        jPanel1.add(btn_add_product, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 370, 177, 35));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_partnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_partnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_partnameActionPerformed

    private void txt_partnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_partnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_partnoActionPerformed

    private void txt_purpriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_purpriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_purpriceActionPerformed

    private void txt_locationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_locationActionPerformed
     addItem();
    }//GEN-LAST:event_txt_locationActionPerformed

    private void txt_salepriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_salepriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_salepriceActionPerformed

    private void btn_add_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_productActionPerformed
        addItem();
    }//GEN-LAST:event_btn_add_productActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_product;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txt_location;
    private javax.swing.JTextField txt_partname;
    private javax.swing.JTextField txt_partno;
    private javax.swing.JTextField txt_purprice;
    private javax.swing.JTextField txt_saleprice;
    // End of variables declaration//GEN-END:variables
}
