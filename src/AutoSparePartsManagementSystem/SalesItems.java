package AutoSparePartsManagementSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Osama
 */
public class SalesItems extends javax.swing.JInternalFrame {
        private Connection connect=null;
        Connection conn1 = null;
        private Statement statement=null;
        private PreparedStatement preparedstatement=null;
        private ResultSet resultSet=null;
        
        
        
        int data0 = 1;
        float grandTotal = 0;
        float amt = 0;
        float tamt = 0;
        public String category, type, productno="";

    
    public SalesItems() {
        initComponents();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/autosparepart?user=root&password=");

            Statement stmt = connect.createStatement();

        } catch (Exception e) {
            System.out.println(e);
        }
        productcombo();
    }
    
    
    public void productcombo() {
        try {
            Statement stmt = connect.createStatement();
            String sql = "SELECT id,  part_no,  name,  stock_unit,  sales_price, purchase_price,  FROM parts";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String refferedby = rs.getString("name") + " - " + rs.getString("part_no");
                cbo_prodNamesearch.addItem(refferedby);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    
    public void updatetotal() {

        String sprice = txt_price.getText();
        String Dis = txt_dis.getText();
        String quant = txt_quantity.getText();
        float d = Float.parseFloat(Dis);
        float sp = Float.parseFloat(sprice);
        float q = Float.parseFloat(quant);
        amt = q * sp;
        Float totalprice = (Float) (q * sp) - d;
        tx_total.setText(totalprice.toString());
    }

    
    public void sellproduct() {

        String data1 = productno;
        String data2 = cbo_prodNamesearch.getSelectedItem().toString();
        String data3 = txt_quantity.getText();
        String data4 = txt_price.getText();
        String data5 = txt_dis.getText();
        String data6 = tx_total.getText();

        Object[] row = {data0, data1, data2, data3, data4, amt, data5, data6};

        DefaultTableModel model = (DefaultTableModel) invoice_table.getModel();

        model.addRow(row);
        data0++;

        float gt = Float.parseFloat(data6);
        grandTotal += gt;
        lbl_billtotal.setText(String.valueOf(grandTotal));
    }

    
    
    public void stockUpdate(String product, int qty) {

        int istock = 0;
        try {
            Statement stmt = connect.createStatement();

            String sql = "SELECT id, name, stock_unit sales_price, purchase_price, FROM parts where name = '" + product + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String stock = rs.getString("stock");
                System.out.println("Stock in db " + stock);
                istock = Integer.parseInt(stock);
                System.out.println("iStock in db " + stock);
            }
            System.out.println(istock - qty);
            int tstock = istock - qty;
            String usql = "UPDATE parts SET STOCK = '" + tstock + "' WHERE name = '" + product + "'";
            stmt.executeUpdate(usql);
            System.out.println("Stock updated");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    public Object GetData(JTable jTable1, int row_index, int col_index) {
        return jTable1.getModel().getValueAt(row_index, col_index);
    }

    
    public void sellupdate() throws IOException {

        PreparedStatement pstm = null;
        ResultSet rs;
        int index = 1;
        int count = invoice_table.getRowCount();
        long key = -1L;

        try {
            String isql = "insert into invoice (PARTYSNAME, INVOICETOTAL) values(?,?)";

            preparedstatement = (PreparedStatement) connect.prepareStatement(isql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedstatement.setString(1, txt_customer.getText().toString());
            preparedstatement.setFloat(2, grandTotal);

            preparedstatement.executeUpdate();
            rs = preparedstatement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                key = rs.getLong(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invoice creation failed " + e);
        }
        try {

            for (int i = 0; i < (count); i++) {
                System.out.println("value of i " + i);
                Object obj1 = GetData(invoice_table, i, 0);
                Object obj2 = GetData(invoice_table, i, 1);
                Object obj3 = GetData(invoice_table, i, 2);
                Object obj4 = GetData(invoice_table, i, 3);
                Object obj5 = GetData(invoice_table, i, 4);
                Object obj6 = GetData(invoice_table, i, 5);
                Object obj7 = GetData(invoice_table, i, 6);
                Object obj8 = GetData(invoice_table, i, 7);

                int value1 = Integer.parseInt(obj1.toString());  //Item SNO
                System.out.println("item SNO " + value1);
                String value2 = obj2.toString();                  //Part No.
                System.out.println("Product Name " + value2);
                String value3 = "";                                 //Description
                if (obj3.toString().equals(null)) {
                    value3 = "";
                } else {
                    value3 = obj3.toString();
                }

                System.out.println(value3);
                int value4 = Integer.parseInt(obj4.toString());   //Qty
                System.out.println(value4);
                float value5 = Float.parseFloat(obj5.toString()); //Price
                System.out.println(value5);
                float value6 = Float.parseFloat(obj6.toString()); //Amount
                System.out.println(value6);
                float value7 = Float.parseFloat(obj7.toString()); //Discount
                System.out.println(value7);
                float value8 = Float.parseFloat(obj8.toString()); //Item total
                System.out.println(value8);

                stockUpdate(value2, value4);

                String salesql = "insert into solditems (INVITEMSNO, PRODUCT, DESCRIPTION, QUANTITY, PRICE, AMOUNT, DISCOUNT, ITEMTOTAL, INVOICEID, BILLTOTAL) "
                        + "values(?,?,?,?,?,?,?,?,?,?)";

                //connect = MySQLConnection.ConnectMySQL();
                pstm = connect.prepareStatement(salesql);
                System.out.println("Preperation");
                pstm.setInt(1, value1);
                pstm.setString(2, value2);
                pstm.setString(3, value3);
                pstm.setInt(4, value4);
                pstm.setFloat(5, value5);
                pstm.setFloat(6, value6);
                pstm.setFloat(7, value7);
                pstm.setFloat(8, value8);
                pstm.setInt(9, (int) key);
                pstm.setFloat(10, grandTotal);

                index++;
                System.out.println("the sql is " + salesql);
                pstm.executeUpdate();
                System.out.println("The pstm executed value of i " + i + " index value " + index);
            }
            System.out.println("execute");
            //pstm.executeUpdate();
            System.out.println("saved Successfully");
            String skey = Integer.toString((int) key);
            //printBill(skey);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbo_prodNamesearch = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txt_stock = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_price = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_dis = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_customer = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        invoice_table = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        tx_total = new javax.swing.JTextField();
        txt_address = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_billtotal = new javax.swing.JLabel();
        btn_sell = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txt_quantity = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Auto Part Name");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 165, -1, 32));

        cbo_prodNamesearch.setEditable(true);
        cbo_prodNamesearch.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbo_prodNamesearch.setToolTipText("");
        cbo_prodNamesearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_prodNamesearchActionPerformed(evt);
            }
        });
        jPanel2.add(cbo_prodNamesearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 167, 130, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Stock");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, 32));

        txt_stock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_stock.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_stockActionPerformed(evt);
            }
        });
        jPanel2.add(txt_stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 130, 31));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Price");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, 32));

        txt_price.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_price.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_priceActionPerformed(evt);
            }
        });
        jPanel2.add(txt_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 130, 31));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Discount");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, 32));

        txt_dis.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_dis.setText("0");
        txt_dis.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_dis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_disActionPerformed(evt);
            }
        });
        jPanel2.add(txt_dis, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 130, 31));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Customer Name");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 41, -1, 32));

        txt_customer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_customer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_customerActionPerformed(evt);
            }
        });
        jPanel2.add(txt_customer, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 42, 346, 31));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Phone Number");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 84, -1, 32));

        txt_phone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_phone.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_phoneActionPerformed(evt);
            }
        });
        jPanel2.add(txt_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 85, 346, 31));

        invoice_table.setFont(new java.awt.Font("Myriad Web Pro", 0, 14)); // NOI18N
        invoice_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S. No.", "Part No.", "Description", "Quantity", "Price", "Amount", "Discount", "Total Amt"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        invoice_table.setRowHeight(24);
        invoice_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                invoice_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(invoice_table);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 165, 735, 315));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 77, 32));

        tx_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tx_total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        tx_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_totalActionPerformed(evt);
            }
        });
        jPanel2.add(tx_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 130, 31));

        txt_address.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_address.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_addressActionPerformed(evt);
            }
        });
        jPanel2.add(txt_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 123, 346, 31));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Address");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 122, 65, 32));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Grand Total:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(647, 53, -1, 32));

        lbl_billtotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_billtotal.setForeground(new java.awt.Color(255, 255, 255));
        lbl_billtotal.setText("Grand Total:");
        jPanel2.add(lbl_billtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(751, 53, -1, 32));

        btn_sell.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_sell.setText("Save And Print");
        btn_sell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sellActionPerformed(evt);
            }
        });
        jPanel2.add(btn_sell, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 470, 160, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Unit");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, 32));

        txt_quantity.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_quantity.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_quantityActionPerformed(evt);
            }
        });
        jPanel2.add(txt_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 130, 31));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Unit");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, 32));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1090, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_disActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_disActionPerformed
            updatetotal();
             tx_total.requestFocusInWindow();// TODO add your handling code here:
    }//GEN-LAST:event_txt_disActionPerformed

    private void txt_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_priceActionPerformed

    private void txt_stockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stockActionPerformed

    private void cbo_prodNamesearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_prodNamesearchActionPerformed
                       // TODO add your handling code here:
    }//GEN-LAST:event_cbo_prodNamesearchActionPerformed

    private void txt_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_customerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_customerActionPerformed

    private void txt_phoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_phoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_phoneActionPerformed

    private void invoice_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invoice_tableMouseClicked
        
    }//GEN-LAST:event_invoice_tableMouseClicked

    private void tx_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_totalActionPerformed
              String rStock = txt_stock.getText();
        int irStock = Integer.parseInt(rStock);

        String rQ = txt_quantity.getText();
        int irQ = Integer.parseInt(rQ);

        if ((irStock - irQ) <= -1 && type.equals("Product")) {
            JOptionPane.showMessageDialog(null, "You don't have enough stock");
            txt_quantity.requestFocusInWindow();

        } else {
            sellproduct();
            cbo_prodNamesearch.requestFocusInWindow();
        }
    }         // TODO add your handling code here:
    }//GEN-LAST:event_tx_totalActionPerformed

    private void txt_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_addressActionPerformed

    private void btn_sellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sellActionPerformed
    
    }//GEN-LAST:event_btn_sellActionPerformed

    private void txt_quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_quantityActionPerformed

                // TODO add your handling code here:
    }//GEN-LAST:event_txt_quantityActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sell;
    private javax.swing.JComboBox<String> cbo_prodNamesearch;
    private javax.swing.JTable invoice_table;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_billtotal;
    private javax.swing.JTextField tx_total;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_customer;
    private javax.swing.JTextField txt_dis;
    private javax.swing.JTextField txt_phone;
    private javax.swing.JTextField txt_price;
    private javax.swing.JTextField txt_quantity;
    private javax.swing.JTextField txt_stock;
    // End of variables declaration//GEN-END:variables
}
