package AutoSparePartsManagementSystem;

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
public class PurchaseItems extends javax.swing.JInternalFrame {

    private Connection connect=null;
    private Statement statement=null;
    private PreparedStatement preparedstatement=null;
    private ResultSet resultSet=null;
    
    int data0 = 1;
    int bill_no;
    float grandTotal = 0;
    float amt = 0;
    float tamt = 0;
    public String category;
    
    
    public PurchaseItems() {
        initComponents();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/autosparepart?user=root&password=");

            Statement stmt = connect.createStatement();

        } catch (Exception e) {
            System.out.println(e);
        }
        
        supplierscombo();
        productcombo();
        
    }
       
    
    public void productcombo() {
        try {
            Statement stmt = connect.createStatement();

            String sql = "SELECT part_no FROM parts ORDER BY part_no ASC";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String refferedby = rs.getString("part_no");
                cmb_part_no.addItem(refferedby);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    public void supplierscombo() {
        try {
            Statement stmt = connect.createStatement();

            String sql = "SELECT name FROM supplier";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String refferedby = rs.getString("name");
                cmb_supplier.addItem(refferedby);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    public void updateprice() {
        try {
            Statement stmt = connect.createStatement();
            String selecteditem = cmb_part_no.getSelectedItem().toString();

            String sql = "SELECT part_no, name, stock_unit, sales_price, purchase_price FROM parts where part_no = '" + selecteditem + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String salesprice = rs.getString("sales_price");
                String Stock = rs.getString("stock_unit");
                //category = rs.getString("CATEGORY");
                txt_price.setText(salesprice);
                txt_unit.setText(Stock);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void updatetotal() {

        String sprice = txt_price.getText();
        String discount = txt_discount.getText();
        String quant = txt_unit.getText();

        System.out.println("The total Q : " + quant);
        
        float Dis = Float.parseFloat(discount);
        float q = Float.parseFloat(quant);
        float sp = Float.parseFloat(sprice);
        amt = q * sp;

        float ftotal = amt - Dis;
        Float totalprice = (Float) (ftotal);
        txt_total.setText(totalprice.toString());

        }
    
    public void sellproduct() {


        String data1 = cmb_part_no.getSelectedItem().toString();
        String data2 = txt_unit.getText();
        String data3 = txt_price.getText();
        String data4 = txt_discount.getText();
        String data5 = txt_total.getText();

        Object[] row = {data0, data1, data3, data2, amt, data4,  data5 };
        DefaultTableModel model = (DefaultTableModel) table_items.getModel();
        System.out.println("About to add row");
        model.addRow(row);
        System.err.println("Added Table Row");
        data0++;
       float gt = Float.parseFloat(data5);
       grandTotal += gt;
        System.out.println("Grand total calculated");
        txt_bill_total.setText(String.valueOf(grandTotal));
        
        cmb_part_no.setSelectedIndex(0);
        txt_unit.setText("");
        txt_price.setText("");
        txt_discount.setText("");
        txt_total.setText("");

    }

    public void stockUpdate(String product, int qty) {
        
        System.out.println("I am about to purchase "+product+" and how much "+qty);

        int istock = 0;
        try {
            Statement stmt = connect.createStatement();

            String sql = "SELECT part_no, name, stock_unit, sales_price, purchase_price FROM parts where part_no = '" + product + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Found an item and I am about to update stock");
                String stock = rs.getString("stock_unit");
                System.out.println("Stock in db " + stock);
                istock = Integer.parseInt(stock);
                System.out.println("iStock in db " + stock);
            }
            System.out.println(istock - qty);
            int tstock = istock + qty;
            String usql = "UPDATE parts SET stock_unit = '" + tstock + "' WHERE part_no = '" + product + "'";
            stmt.executeUpdate(usql);
            System.out.println("Stock updated");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    
    public void purchaseupdate() {

        PreparedStatement pstm = null ;
        ResultSet rs;
        int index = 1;
        int count = table_items.getRowCount();
        long key = -1L;

        try {
            bill_no = Integer.parseInt(txt_inv_no.getText());
            String isql = "insert into purchases (supplier_name, invoice_no, grand_total) values(?,?,?)";

            preparedstatement = (PreparedStatement) connect.prepareStatement(isql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedstatement.setString(1, cmb_supplier.getSelectedItem().toString());
            preparedstatement.setInt(2, bill_no);
            preparedstatement.setFloat(3, grandTotal);

            preparedstatement.executeUpdate();

            rs = preparedstatement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                key = rs.getLong(1); //Last inserted id of purchases table
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error occurred while adding Purchase summary :"+e);
        }

        try {

            for (int i = 0; i < count; i++) {
                Object obj1 = GetData(table_items, i, 0);
                Object obj2 = GetData(table_items, i, 1);
                Object obj3 = GetData(table_items, i, 2);
                Object obj4 = GetData(table_items, i, 3);
                Object obj5 = GetData(table_items, i, 4);
                Object obj6 = GetData(table_items, i, 5);
                Object obj7 = GetData(table_items, i, 6);
                //Object obj8 = GetData(table_items, i, 7);

                int value1 = Integer.parseInt(obj1.toString());  //Item SNO
                System.out.println(value1);
                String value2 = obj2.toString();                  //Part No.
                System.out.println(value2);
                float value3 = Float.parseFloat(obj3.toString());                  //Part name
                System.out.println(value3);
                int value4 = Integer.parseInt(obj4.toString());   //Qty
                System.out.println(value4);
                float value5 = Float.parseFloat(obj5.toString()); //Price
                System.out.println(value5);
                float value6 = Float.parseFloat(obj6.toString()); //Amount
                System.out.println(value6);
                float value7 = Float.parseFloat(obj7.toString()); //Discount
                System.out.println(value7);
                //float value8 = Float.parseFloat(obj8.toString()); //Item total
                //System.out.println(value8);
                
                String salesql = "insert into purchaseitems (invoice_item_id, part_no, price, unit, amount, discount, items_total, invoice_id, bill_total, purch_invoice_no)"
                        + "values(?,?,?,?,?,?,?,?,?,?)";

                pstm = (PreparedStatement) connect.prepareStatement(salesql, PreparedStatement.RETURN_GENERATED_KEYS);
                System.out.println("Preperation");
                pstm.setInt(1, value1);
                pstm.setString(2, value2);
                pstm.setFloat(3, value3);
                pstm.setInt(4, value4);
                pstm.setFloat(5, value5);
                pstm.setFloat(6, value6);
                pstm.setFloat(7, value7);
               // pstm.setFloat(8, value8);
                pstm.setInt(8, (int) key);
                pstm.setFloat(9, grandTotal);
                pstm.setInt(10, bill_no);

                index++;
                pstm.executeUpdate();
                
                stockUpdate(value2, value4);
            }
            System.out.println("execute");
            System.out.println("saved Successfully");
            String skey = Integer.toString((int) key);
            JOptionPane.showMessageDialog(null, "Purchase entry successful");
            this.dispose();
        } catch (Exception e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(rootPane, "Error occured while adding Purchased items :"+e);
        }
    }

    
    public Object GetData(JTable table_items, int row_index, int col_index) {
        return table_items.getModel().getValueAt(row_index, col_index);
    }
    
    
//    public void printBill(String skey) {
//        try {
//
//            JasperDesign jd = JRXmlLoader.load("BillPOS.jrxml");
//            String rSQL = "SELECT * FROM purchases INNER JOIN purchaseitems ON purchases.id=purchaseitems.invoice_no WHERE purchasebook.id = '" + skey + "'";
//            JRDesignQuery newQuery = new JRDesignQuery();
//            newQuery.setText(rSQL);
//            jd.setQuery(newQuery);
//
//            JasperReport jr = JasperCompileManager.compileReport(jd);
//            JasperPrint jp = JasperFillManager.fillReport(jr, null, connect);
//            JasperViewer.viewReport(jp, false);
//
//        } catch (JRException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }

    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmb_part_no = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txt_unit = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_price = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_discount = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_inv_no = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cmb_supplier = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_items = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txt_bill_total = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(978, 533));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Auto Part  N0.");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 135, -1, 32));

        cmb_part_no.setEditable(true);
        cmb_part_no.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmb_part_no.setToolTipText("");
        cmb_part_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_part_noActionPerformed(evt);
            }
        });
        jPanel1.add(cmb_part_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 137, 150, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Unit");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 178, -1, 32));

        txt_unit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_unit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_unit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_unitActionPerformed(evt);
            }
        });
        jPanel1.add(txt_unit, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 179, 150, 31));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Price");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 248, -1, 32));

        txt_price.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_price.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_priceActionPerformed(evt);
            }
        });
        jPanel1.add(txt_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 249, 150, 31));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Discount");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 291, -1, 32));

        txt_discount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_discount.setText("0");
        txt_discount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_discount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_discountActionPerformed(evt);
            }
        });
        jPanel1.add(txt_discount, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 292, 150, 31));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 334, -1, 32));

        txt_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        jPanel1.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 335, 150, 31));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Invoice No.");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 82, -1, 29));

        txt_inv_no.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_inv_no.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        txt_inv_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_inv_noActionPerformed(evt);
            }
        });
        jPanel1.add(txt_inv_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, 86, 341, 31));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Supplier's Name");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 47, -1, 29));

        cmb_supplier.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmb_supplier.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        cmb_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_supplierActionPerformed(evt);
            }
        });
        jPanel1.add(cmb_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, 46, 341, 30));

        table_items.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        table_items.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.no", "Auto Part No.", "Price", "Unit", "Amount", "Discount", "Total Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_items);
        if (table_items.getColumnModel().getColumnCount() > 0) {
            table_items.getColumnModel().getColumn(0).setMinWidth(50);
            table_items.getColumnModel().getColumn(0).setPreferredWidth(50);
            table_items.getColumnModel().getColumn(0).setMaxWidth(50);
            table_items.getColumnModel().getColumn(1).setMinWidth(100);
            table_items.getColumnModel().getColumn(1).setPreferredWidth(100);
            table_items.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 930, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Grand Total:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 90, 30));

        txt_bill_total.setBackground(new java.awt.Color(0, 102, 102));
        txt_bill_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_bill_total.setForeground(new java.awt.Color(255, 255, 255));
        txt_bill_total.setText("Bill total");
        txt_bill_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bill_totalActionPerformed(evt);
            }
        });
        jPanel1.add(txt_bill_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 60, 80, 30));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Add to Purchases");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 420, 160, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmb_part_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_part_noActionPerformed
        updateprice();
        txt_unit.requestFocusInWindow();
    }//GEN-LAST:event_cmb_part_noActionPerformed

    private void txt_unitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_unitActionPerformed
      txt_price.requestFocusInWindow();
      txt_discount.requestFocusInWindow();
    }//GEN-LAST:event_txt_unitActionPerformed

    private void txt_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_priceActionPerformed
        txt_discount.requestFocusInWindow();  
    }//GEN-LAST:event_txt_priceActionPerformed

    private void txt_discountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_discountActionPerformed
            updatetotal();
            txt_total.requestFocusInWindow();
    }//GEN-LAST:event_txt_discountActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        sellproduct();
        
        cmb_part_no.requestFocusInWindow();
        
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_inv_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_inv_noActionPerformed
        cmb_part_no.requestFocusInWindow();
    }//GEN-LAST:event_txt_inv_noActionPerformed

    private void txt_bill_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bill_totalActionPerformed
        
    }//GEN-LAST:event_txt_bill_totalActionPerformed

    private void cmb_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_supplierActionPerformed
        
    }//GEN-LAST:event_cmb_supplierActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txt_inv_no.getText().equals("") || txt_inv_no.getText() == null) {
            JOptionPane.showMessageDialog(null, "Please Enter purchase bill no. It cannot be Blank");
            txt_inv_no.requestFocusInWindow();
        } else {
            purchaseupdate();
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmb_part_no;
    private javax.swing.JComboBox<String> cmb_supplier;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_items;
    private javax.swing.JTextField txt_bill_total;
    private javax.swing.JTextField txt_discount;
    private javax.swing.JTextField txt_inv_no;
    private javax.swing.JTextField txt_price;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_unit;
    // End of variables declaration//GEN-END:variables
}
