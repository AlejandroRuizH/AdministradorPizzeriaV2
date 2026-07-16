/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

import database.DatabaseConnection;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author davidalejandroruizhernandez
 */
public class AdministradorProductos extends javax.swing.JPanel {

    /**
     * Creates new form AdministradorProductos
     */
    
    private MenuPrincipal menuPrincipal;
    DefaultTableModel model;
    String sqlMetodo="";
    
    public AdministradorProductos(MenuPrincipal menuPrincipal) {
        
        initComponents();
        this.menuPrincipal = menuPrincipal;
        setPreferredSize(new Dimension(1070, 720));
        //this.setResizable(false);
        menuPrincipal.revalidate();
        menuPrincipal.repaint();
        
         //Creacion de la tabla para los productos
        model = new DefaultTableModel();
        model.addColumn("ID del producto");
        model.addColumn("Nombre del Producto");
        model.addColumn("Descripcion");
        model.addColumn("Precio");
        // Asignar modelo a la tabla
        TableProductos.setModel(model); 
        
        //leer la tabla de ingredientes y llenar la tabla con los datos
        listarProductos();
       
        // Deshabilitar los text Fiel de los productos
        habilitarCampos(false);
        
        //Deshabilitar los botones
        ButtonCancelarProducto.setEnabled(false);
        ButtonEditarProducto.setEnabled(false);
        ButtonEliminarProducto.setEnabled(false);
        ButtonGrabarProducto.setEnabled(false);
        ButtonNuevoProducto.setEnabled(true);
        
    }
    
        private void habilitarCampos(boolean estado){
        TextFieldDescripcionProducto.setEnabled(estado);
        TextFieldNombreProducto.setEnabled(estado);
        TextFieldPrecioProducto.setEnabled(estado);
        TextFieldIDProducto.setEnabled(estado);
    }
    private void limpiarCampos(){
        TextFieldDescripcionProducto.setText(""); 
        TextFieldNombreProducto.setText(""); 
        TextFieldPrecioProducto.setText("");
        TextFieldIDProducto.setText("");
    }
    
    private void listarProductos(){
       try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "SELECT idProducto, producto, descripcion, precio FROM productos ORDER BY idProducto ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()){
               Object[] empleado ={
                  rs.getInt("idProducto"),
                  rs.getString("producto"),
                  rs.getString("descripcion"),
                  rs.getDouble("precio"),
               };
            model.addRow(empleado);
          }
      } catch (SQLException ex) {
            System.getLogger(AdministradoringredientesExtra.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
         private void eliminarProducto(){
       try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM productos WHERE idProducto=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // ID del registro a eliminar
            ps.setInt(1,Integer.parseInt(TextFieldIDProducto.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el Producto.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
    }
    
private boolean capturarProducto(String sqlOption){
           
            //Agregar las validaciones aqui
            boolean validacion = true;
            if(sqlOption.matches("CREATE")){
                validacion=true;
                String msgErr = "";
                int numErr=0;
                try (Connection conn = DatabaseConnection.getConnection()){
                    String sql = "INSERT INTO productos (idProducto, producto, descripcion, precio) VALUES (?, ?, ?, ?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    //Validacion ID
                    if (TextFieldIDProducto.getText().isEmpty()){
                        validacion=false;
                        msgErr="Ingrese el ID del Producto";
                        numErr++;
                    }else{
                        st.setInt(1,Integer.parseInt(TextFieldIDProducto.getText()));
                    }
                    // Validacion Nombre
                    if (TextFieldNombreProducto.getText().isEmpty()) {
                        if (numErr > 1) {
                           msgErr=msgErr+"\n Ingrese el nombre del producto";
                           numErr++;
                        } else{
                           msgErr="Ingrese el nombre del producto";
                        }
                    }else{
                        st.setString(2,TextFieldNombreProducto.getText());
                    }
                    //Validacion Domicilio
                    if (TextFieldDescripcionProducto.getText().isEmpty()) {
                        validacion=false;
                        if (numErr > 1) {
                           msgErr=msgErr+"\n Ingrese la Descripcion del Producto";
                           numErr++;
                        } else{
                           msgErr="Ingrese la Descripcion del Producto";
                        }
                    }else{
                        st.setString(3,TextFieldDescripcionProducto.getText());    
                    }
                    //Validacion Telefono
                    if (TextFieldPrecioProducto.getText().isEmpty()) {
                        validacion=false;
                        if (numErr > 1){
                           msgErr=msgErr + "\n Ingrese el precio del producto";
                           numErr++;
                        }else{
                           msgErr="Ingrese el precio del producto";
                        }
                    }else{
                          st.setFloat(4,Float.parseFloat(TextFieldPrecioProducto.getText()));
                    }  
                    if (msgErr.isEmpty() && (numErr == 0)){
                        st.executeUpdate();
                    }else{
                        JOptionPane.showMessageDialog(null, msgErr);
                    }
                    
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    validacion=false;
                }
            }else if (sqlOption.matches("UPDATE")){
                validacion = true;
                try (Connection conn = DatabaseConnection.getConnection()) {
                       String sql = "UPDATE productos SET producto=?, descripcion=?, precio=? WHERE idProducto=?";
                       PreparedStatement ps = conn.prepareStatement(sql);

                       // Asignar valores desde tus componentes
                       ps.setString(1,TextFieldNombreProducto.getText());
                       ps.setString(2,TextFieldDescripcionProducto.getText());
                       ps.setFloat(3,Float.parseFloat(TextFieldPrecioProducto.getText()));
                       
                       ps.setInt(4,Integer.parseInt(TextFieldIDProducto.getText()));
                       if(validacion){
                         int filas = ps.executeUpdate();
                         if (filas > 0) {
                           JOptionPane.showMessageDialog(null, "Registro actualizado correctamente.");
                          } else {
                           JOptionPane.showMessageDialog(null, "No se encontró el registro.");
                          }
                       }
                       
                }catch (SQLException e) {
                      JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
                }
            
           }else{
            
            }    
           return validacion;
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPrincipalAdminProductos = new javax.swing.JPanel();
        PanelTitleAdminProductos = new javax.swing.JPanel();
        LabelTitleAdminProductos = new javax.swing.JLabel();
        PanelTablaAdminProductos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableProductos = new javax.swing.JTable();
        PanelButtonsProductos = new javax.swing.JPanel();
        LabelNombreProducto = new javax.swing.JLabel();
        TextFieldNombreProducto = new javax.swing.JTextField();
        LabelDescripcionProducto = new javax.swing.JLabel();
        TextFieldDescripcionProducto = new javax.swing.JTextField();
        LabelPrecioProducto = new javax.swing.JLabel();
        TextFieldPrecioProducto = new javax.swing.JTextField();
        LabelIDProducto = new javax.swing.JLabel();
        TextFieldIDProducto = new javax.swing.JTextField();
        ButtonNuevoProducto = new javax.swing.JButton();
        ButtonEditarProducto = new javax.swing.JButton();
        ButtonGrabarProducto = new javax.swing.JButton();
        ButtonEliminarProducto = new javax.swing.JButton();
        ButtonCancelarProducto = new javax.swing.JButton();
        PanelImagenAdminProductos = new javax.swing.JPanel();

        PanelTitleAdminProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LabelTitleAdminProductos.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        LabelTitleAdminProductos.setText("Panel Administracion Productos");

        javax.swing.GroupLayout PanelTitleAdminProductosLayout = new javax.swing.GroupLayout(PanelTitleAdminProductos);
        PanelTitleAdminProductos.setLayout(PanelTitleAdminProductosLayout);
        PanelTitleAdminProductosLayout.setHorizontalGroup(
            PanelTitleAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTitleAdminProductosLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(LabelTitleAdminProductos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelTitleAdminProductosLayout.setVerticalGroup(
            PanelTitleAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTitleAdminProductosLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(LabelTitleAdminProductos)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        PanelTablaAdminProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID del Producto", "Nombre del Producto", "Descripcion", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableProductos);

        javax.swing.GroupLayout PanelTablaAdminProductosLayout = new javax.swing.GroupLayout(PanelTablaAdminProductos);
        PanelTablaAdminProductos.setLayout(PanelTablaAdminProductosLayout);
        PanelTablaAdminProductosLayout.setHorizontalGroup(
            PanelTablaAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );
        PanelTablaAdminProductosLayout.setVerticalGroup(
            PanelTablaAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        PanelButtonsProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LabelNombreProducto.setText("* Nombre del Producto");

        LabelDescripcionProducto.setText("* Descripcion del Producto");

        LabelPrecioProducto.setText("* Precio del Producto");

        TextFieldPrecioProducto.setVerifyInputWhenFocusTarget(false);
        TextFieldPrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldPrecioProductoKeyTyped(evt);
            }
        });

        LabelIDProducto.setText("* ID del Producto");

        TextFieldIDProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldIDProductoKeyTyped(evt);
            }
        });

        ButtonNuevoProducto.setText("<html><center>Nuevo<br>Producto</center></html>");
        ButtonNuevoProducto.addActionListener(this::ButtonNuevoProductoActionPerformed);

        ButtonEditarProducto.setText("<html><center>Editar<br>Producto</center></html>");
        ButtonEditarProducto.addActionListener(this::ButtonEditarProductoActionPerformed);

        ButtonGrabarProducto.setText("Grabar");
        ButtonGrabarProducto.addActionListener(this::ButtonGrabarProductoActionPerformed);

        ButtonEliminarProducto.setText("Eliminar");
        ButtonEliminarProducto.addActionListener(this::ButtonEliminarProductoActionPerformed);

        ButtonCancelarProducto.setText("Cancelar");
        ButtonCancelarProducto.addActionListener(this::ButtonCancelarProductoActionPerformed);

        javax.swing.GroupLayout PanelButtonsProductosLayout = new javax.swing.GroupLayout(PanelButtonsProductos);
        PanelButtonsProductos.setLayout(PanelButtonsProductosLayout);
        PanelButtonsProductosLayout.setHorizontalGroup(
            PanelButtonsProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelButtonsProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelButtonsProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelNombreProducto)
                    .addComponent(TextFieldNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelDescripcionProducto)
                    .addComponent(TextFieldDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPrecioProducto)
                    .addComponent(TextFieldPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelIDProducto)
                    .addComponent(TextFieldIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(PanelButtonsProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonCancelarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonGrabarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonNuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PanelButtonsProductosLayout.setVerticalGroup(
            PanelButtonsProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelButtonsProductosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PanelButtonsProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelButtonsProductosLayout.createSequentialGroup()
                        .addComponent(LabelNombreProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextFieldNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelDescripcionProducto))
                    .addComponent(ButtonNuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PanelButtonsProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelButtonsProductosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextFieldDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelPrecioProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TextFieldPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelButtonsProductosLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(ButtonEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(PanelButtonsProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelButtonsProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ButtonGrabarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextFieldIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LabelIDProducto))
                .addGap(18, 18, 18)
                .addComponent(ButtonEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ButtonCancelarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        PanelImagenAdminProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout PanelImagenAdminProductosLayout = new javax.swing.GroupLayout(PanelImagenAdminProductos);
        PanelImagenAdminProductos.setLayout(PanelImagenAdminProductosLayout);
        PanelImagenAdminProductosLayout.setHorizontalGroup(
            PanelImagenAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PanelImagenAdminProductosLayout.setVerticalGroup(
            PanelImagenAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelPrincipalAdminProductosLayout = new javax.swing.GroupLayout(PanelPrincipalAdminProductos);
        PanelPrincipalAdminProductos.setLayout(PanelPrincipalAdminProductosLayout);
        PanelPrincipalAdminProductosLayout.setHorizontalGroup(
            PanelPrincipalAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalAdminProductosLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(PanelPrincipalAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelTitleAdminProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelTablaAdminProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPrincipalAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelButtonsProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelImagenAdminProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelPrincipalAdminProductosLayout.setVerticalGroup(
            PanelPrincipalAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalAdminProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPrincipalAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelImagenAdminProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelTitleAdminProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPrincipalAdminProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelTablaAdminProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelButtonsProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipalAdminProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipalAdminProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TextFieldPrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldPrecioProductoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '.') {
            evt.consume(); // evita que se escriba el carácter
        }
        
        if (c == '.' && TextFieldPrecioProducto.getText().contains(".")){
            evt.consume();
        }
    }//GEN-LAST:event_TextFieldPrecioProductoKeyTyped

    private void TextFieldIDProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldIDProductoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldIDProductoKeyTyped

    private void ButtonNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNuevoProductoActionPerformed
        // TODO add your handling code here:
        habilitarCampos(true);
        limpiarCampos();
        ButtonNuevoProducto.setEnabled(true);
        ButtonEditarProducto.setEnabled(false);
        ButtonGrabarProducto.setEnabled(true);
        ButtonEliminarProducto.setEnabled(false);
        ButtonCancelarProducto.setEnabled(true);
    }//GEN-LAST:event_ButtonNuevoProductoActionPerformed

    private void ButtonEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarProductoActionPerformed
        // TODO add your handling code here:
        sqlMetodo = "UPDATE";
        if(capturarProducto(sqlMetodo)){
              limpiarCampos();
              listarProductos();  
              habilitarCampos(false);
              ButtonNuevoProducto.setEnabled(true);
              ButtonEditarProducto.setEnabled(false);
              ButtonGrabarProducto.setEnabled(false);
              ButtonEliminarProducto.setEnabled(false);
              ButtonCancelarProducto.setEnabled(false);
           }
    }//GEN-LAST:event_ButtonEditarProductoActionPerformed

    private void ButtonGrabarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonGrabarProductoActionPerformed
        // TODO add your handling code here:
        sqlMetodo = "CREATE";
        if(capturarProducto(sqlMetodo)){
              limpiarCampos();
              listarProductos();  
              habilitarCampos(false);
              ButtonNuevoProducto.setEnabled(true);
              ButtonEditarProducto.setEnabled(false);
              ButtonGrabarProducto.setEnabled(false);
              ButtonEliminarProducto.setEnabled(false);
              ButtonCancelarProducto.setEnabled(false);
           }
    }//GEN-LAST:event_ButtonGrabarProductoActionPerformed

    private void ButtonEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEliminarProductoActionPerformed
        // TODO add your handling code here:
        eliminarProducto();
        listarProductos();
        habilitarCampos(false);
        ButtonNuevoProducto.setEnabled(true);
        ButtonEditarProducto.setEnabled(false);
        ButtonGrabarProducto.setEnabled(false);
        ButtonEliminarProducto.setEnabled(false);
        ButtonCancelarProducto.setEnabled(false);
    }//GEN-LAST:event_ButtonEliminarProductoActionPerformed

    private void ButtonCancelarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelarProductoActionPerformed
        // TODO add your handling code here:
        habilitarCampos(false);
        limpiarCampos();
        ButtonNuevoProducto.setEnabled(true);
        ButtonEditarProducto.setEnabled(false);
        ButtonGrabarProducto.setEnabled(false);
        ButtonEliminarProducto.setEnabled(false);
        ButtonCancelarProducto.setEnabled(false);
    }//GEN-LAST:event_ButtonCancelarProductoActionPerformed

    private void TableProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductosMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        // Accion cuando se hace clic con el mouse
        int fila = TableProductos.getSelectedRow();
        ButtonNuevoProducto.setEnabled(false);
        ButtonEditarProducto.setEnabled(true);
        ButtonGrabarProducto.setEnabled(false);
        ButtonEliminarProducto.setEnabled(true);  
        ButtonCancelarProducto.setEnabled(true);
        habilitarCampos(true);
        TextFieldIDProducto.setEnabled(false);
        
        if (fila >= 0) {
            // Extraer valores de cada columna Nombre, Domicilio, Telefono, Fecha de Ingreso, Activo, Tipo de Empleado
            int idProducto = Integer.parseInt(TableProductos.getValueAt(fila, 0).toString());
            String productoTabla = TableProductos.getValueAt(fila, 1).toString();
            String descripcionTabla = TableProductos.getValueAt(fila, 2).toString();
            String precioTabla = TableProductos.getValueAt(fila, 3).toString();
             
            // Pasar los valores a los text fields
            //JTextField1 = Producto
            TextFieldNombreProducto.setText(productoTabla);
            
            //jTextField2 = Descripcion
            TextFieldDescripcionProducto.setText(descripcionTabla);
            
            //jTextField3 = Precio
            TextFieldPrecioProducto.setText(precioTabla);
            
            //jTextField3 = ID
            TextFieldIDProducto.setText(String.valueOf(idProducto));
            
        }
    }//GEN-LAST:event_TableProductosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCancelarProducto;
    private javax.swing.JButton ButtonEditarProducto;
    private javax.swing.JButton ButtonEliminarProducto;
    private javax.swing.JButton ButtonGrabarProducto;
    private javax.swing.JButton ButtonNuevoProducto;
    private javax.swing.JLabel LabelDescripcionProducto;
    private javax.swing.JLabel LabelIDProducto;
    private javax.swing.JLabel LabelNombreProducto;
    private javax.swing.JLabel LabelPrecioProducto;
    private javax.swing.JLabel LabelTitleAdminProductos;
    private javax.swing.JPanel PanelButtonsProductos;
    private javax.swing.JPanel PanelImagenAdminProductos;
    private javax.swing.JPanel PanelPrincipalAdminProductos;
    private javax.swing.JPanel PanelTablaAdminProductos;
    private javax.swing.JPanel PanelTitleAdminProductos;
    private javax.swing.JTable TableProductos;
    private javax.swing.JTextField TextFieldDescripcionProducto;
    private javax.swing.JTextField TextFieldIDProducto;
    private javax.swing.JTextField TextFieldNombreProducto;
    private javax.swing.JTextField TextFieldPrecioProducto;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
