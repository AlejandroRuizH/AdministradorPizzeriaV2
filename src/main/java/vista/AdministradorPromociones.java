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
public class AdministradorPromociones extends javax.swing.JPanel {

    /**
     * Creates new form AdministradorPromociones
     */
    
    private MenuPrincipal menuPrincipal;
     DefaultTableModel model;
     String sqlMetodo="";
    
    public AdministradorPromociones(MenuPrincipal menuPrincipal) {
        
        this.menuPrincipal = menuPrincipal;
        initComponents();
        setPreferredSize(new Dimension(1070, 690));
        //this.setResizable(false);
        menuPrincipal.revalidate();
        menuPrincipal.repaint();
        
        //Creacion de la tabla para los productos
        model = new DefaultTableModel();
        model.addColumn("ID de la Promocion");
        model.addColumn("Descripcion");
        model.addColumn("Precio");
        model.addColumn("Productos");
        model.addColumn("Tamaño");
        

         // Asignar modelo a la tabla
        TablePromociones.setModel(model); 
        
        //leer la tabla de ingredientes y llenar la tabla con los datos
        listarPromociones();
       
        // Deshabilitar los text Fiel de los productos
        habilitarCampos(false);
        
        //Deshabilitar los botones
        ButtonCancelarPromocion.setEnabled(false);
        ButtonEditarPromocion.setEnabled(false);
        ButtonEliminarPromocion.setEnabled(false);
        ButtonGrabarPromocion.setEnabled(false);
        ButtonNuevaPromocion.setEnabled(true);    
        
    }
    
     private void habilitarCampos(boolean estado){
        TextAreaProductosPromocion.setEnabled(estado);
        TextFieldDescripcionPromocion.setEnabled(estado);
        TextFieldIDPromocion.setEnabled(estado);
        TextFieldPrecioPromocion.setEnabled(estado);
        TextFieldSizePromocion.setEnabled(estado);
    }
    private void limpiarCampos(){
        TextAreaProductosPromocion.setText(""); 
        TextFieldDescripcionPromocion.setText(""); 
        TextFieldIDPromocion.setText("");
        TextFieldPrecioPromocion.setText("");
        TextFieldSizePromocion.setText("");
    }
    
    private void listarPromociones(){
       try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "SELECT idPromocion, Descripcion, Precio, Productos, Size FROM promociones ORDER BY idPromocion ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()){
               Object[] empleado ={
                  rs.getInt("idPromocion"),
                  rs.getString("Descripcion"),
                  rs.getFloat("Precio"),
                  rs.getString("Productos"),
                  rs.getString("Size"),
               };
            model.addRow(empleado);
          }
      } catch (SQLException ex) {
            System.getLogger(AdministradoringredientesExtra.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    private void eliminarPromocion(){
       try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM promocion WHERE idPromocion=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // ID del registro a eliminar
            ps.setInt(1,Integer.parseInt(TextFieldIDPromocion.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Promocion eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la promocion.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
    }
    
    private boolean capturarPromocion(String sqlOption){
            //Agregar las validaciones aqui
            boolean validacion = true;
            if(sqlOption.matches("CREATE")){
                validacion=true;
                String msgErr = "";
                int numErr=0;
                try (Connection conn = DatabaseConnection.getConnection()){
                    String sql = "INSERT INTO promociones (idPromocion, Descripcion, Precio, Productos, Size) VALUES (?, ?, ?, ?,?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    //Validacion ID
                    if (TextFieldIDPromocion.getText().isEmpty()){
                        validacion=false;
                        msgErr="Ingrese el ID de la Promocion";
                        numErr++;
                    }else{
                        st.setInt(1,Integer.parseInt(TextFieldIDPromocion.getText()));
                    }
                    // Validacion Nombre
                    if (TextFieldDescripcionPromocion.getText().isEmpty()) {
                        if (numErr > 1) {
                           msgErr=msgErr+"\n Ingrese la descripcion de la promocion";
                           numErr++;
                        } else{
                           msgErr="Ingrese la descripcion de la promocion";
                        }
                    }else{
                        st.setString(2,TextFieldDescripcionPromocion.getText());
                    }
                    //Validacion Domicilio
                    if (TextFieldPrecioPromocion.getText().isEmpty()) {
                        validacion=false;
                        if (numErr > 1) {
                           msgErr=msgErr+"\n Ingrese el Precio de la Promocion";
                           numErr++;
                        } else{
                           msgErr="Ingrese el Precio de la Promocion";
                        }
                    }else{
                        st.setFloat(3,Float.parseFloat(TextFieldPrecioPromocion.getText()));    
                    }
                    //Validacion Telefono
                    if (TextAreaProductosPromocion.getText().isEmpty()) {
                        validacion=false;
                        if (numErr > 1){
                           msgErr=msgErr + "\n Ingrese los productos de la promocion";
                           numErr++;
                        }else{
                           msgErr="Ingrese los productos de la promocion";
                        }
                    }else{
                          st.setString(4,TextAreaProductosPromocion.getText());
                    }  
                    if (TextFieldSizePromocion.getText().isEmpty()) {
                        validacion=false;
                        if (numErr > 1){
                           msgErr=msgErr + "\n Ingrese el tamano de la promocion";
                           numErr++;
                        }else{
                           msgErr="Ingrese el tamano de la promocion";
                        }
                    }else{
                          st.setString(5,TextFieldSizePromocion.getText());
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
                       String sql = "UPDATE promocion SET Descripcion=?, Precio=?, Productos=?, Size=? WHERE idPromocion=?";
                       PreparedStatement ps = conn.prepareStatement(sql);

                       // Asignar valores desde tus componentes
                       ps.setString(1,TextFieldDescripcionPromocion.getText());
                       ps.setFloat(2,Float.parseFloat(TextFieldPrecioPromocion.getText()));
                       ps.setString(3,TextAreaProductosPromocion.getText());
                       ps.setString(4,TextFieldSizePromocion.getText());
                       
                       ps.setInt(5,Integer.parseInt(TextFieldIDPromocion.getText()));
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

        PanelPrincipalAdminPromociones = new javax.swing.JPanel();
        PanelTitleAdminPromociones = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PanelTableAdminPromociones = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablePromociones = new javax.swing.JTable();
        PanelButtonsPromociones = new javax.swing.JPanel();
        LabelDescripcionTitle = new javax.swing.JLabel();
        TextFieldDescripcionPromocion = new javax.swing.JTextField();
        LabelPrecioPromocion = new javax.swing.JLabel();
        TextFieldPrecioPromocion = new javax.swing.JTextField();
        LabelProductosPromocion = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextAreaProductosPromocion = new javax.swing.JTextArea();
        LabelSizePromocion = new javax.swing.JLabel();
        TextFieldSizePromocion = new javax.swing.JTextField();
        LabelIDPromocion = new javax.swing.JLabel();
        TextFieldIDPromocion = new javax.swing.JTextField();
        ButtonNuevaPromocion = new javax.swing.JButton();
        ButtonEditarPromocion = new javax.swing.JButton();
        ButtonGrabarPromocion = new javax.swing.JButton();
        ButtonEliminarPromocion = new javax.swing.JButton();
        ButtonCancelarPromocion = new javax.swing.JButton();
        PanelImagePromociones = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1070, 686));

        PanelPrincipalAdminPromociones.setPreferredSize(new java.awt.Dimension(1084, 686));

        PanelTitleAdminPromociones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel1.setText("Panel Administración de Promociones");

        javax.swing.GroupLayout PanelTitleAdminPromocionesLayout = new javax.swing.GroupLayout(PanelTitleAdminPromociones);
        PanelTitleAdminPromociones.setLayout(PanelTitleAdminPromocionesLayout);
        PanelTitleAdminPromocionesLayout.setHorizontalGroup(
            PanelTitleAdminPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTitleAdminPromocionesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelTitleAdminPromocionesLayout.setVerticalGroup(
            PanelTitleAdminPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTitleAdminPromocionesLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        PanelTableAdminPromociones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TablePromociones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID de la Promocion", "Descripcion", "Precio", "Productos", "Tamaño"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablePromociones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablePromocionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablePromociones);

        javax.swing.GroupLayout PanelTableAdminPromocionesLayout = new javax.swing.GroupLayout(PanelTableAdminPromociones);
        PanelTableAdminPromociones.setLayout(PanelTableAdminPromocionesLayout);
        PanelTableAdminPromocionesLayout.setHorizontalGroup(
            PanelTableAdminPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
        );
        PanelTableAdminPromocionesLayout.setVerticalGroup(
            PanelTableAdminPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        PanelButtonsPromociones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LabelDescripcionTitle.setText("* Descripcion de la Promoción");

        LabelPrecioPromocion.setText("* Precio de la Promoción");

        TextFieldPrecioPromocion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldPrecioPromocionKeyTyped(evt);
            }
        });

        LabelProductosPromocion.setText("* Productos de la Promoción");

        TextAreaProductosPromocion.setColumns(20);
        TextAreaProductosPromocion.setRows(5);
        jScrollPane2.setViewportView(TextAreaProductosPromocion);

        LabelSizePromocion.setText("* Tamaños de la Promoción");

        LabelIDPromocion.setText("* ID de la Promoción");

        TextFieldIDPromocion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldIDPromocionKeyTyped(evt);
            }
        });

        ButtonNuevaPromocion.setText("<html><center>Nuevo<br>Promocion</center></html>");
        ButtonNuevaPromocion.setToolTipText("");
        ButtonNuevaPromocion.addActionListener(this::ButtonNuevaPromocionActionPerformed);

        ButtonEditarPromocion.setText("<html><center>Editar<br>Promocion</center></html>");
        ButtonEditarPromocion.setToolTipText("");
        ButtonEditarPromocion.addActionListener(this::ButtonEditarPromocionActionPerformed);

        ButtonGrabarPromocion.setText("Grabar");
        ButtonGrabarPromocion.setToolTipText("");
        ButtonGrabarPromocion.addActionListener(this::ButtonGrabarPromocionActionPerformed);

        ButtonEliminarPromocion.setText("Eliminar");
        ButtonEliminarPromocion.setToolTipText("");
        ButtonEliminarPromocion.addActionListener(this::ButtonEliminarPromocionActionPerformed);

        ButtonCancelarPromocion.setText("Cancelar");
        ButtonCancelarPromocion.setToolTipText("");
        ButtonCancelarPromocion.addActionListener(this::ButtonCancelarPromocionActionPerformed);

        javax.swing.GroupLayout PanelButtonsPromocionesLayout = new javax.swing.GroupLayout(PanelButtonsPromociones);
        PanelButtonsPromociones.setLayout(PanelButtonsPromocionesLayout);
        PanelButtonsPromocionesLayout.setHorizontalGroup(
            PanelButtonsPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelButtonsPromocionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelButtonsPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelButtonsPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(LabelDescripcionTitle)
                        .addComponent(TextFieldDescripcionPromocion)
                        .addComponent(LabelPrecioPromocion)
                        .addComponent(TextFieldPrecioPromocion)
                        .addComponent(LabelProductosPromocion)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                    .addComponent(LabelSizePromocion)
                    .addComponent(TextFieldSizePromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelIDPromocion)
                    .addComponent(TextFieldIDPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelButtonsPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonCancelarPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEditarPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonNuevaPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonGrabarPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEliminarPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelButtonsPromocionesLayout.setVerticalGroup(
            PanelButtonsPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelButtonsPromocionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelButtonsPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonNuevaPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelButtonsPromocionesLayout.createSequentialGroup()
                        .addComponent(LabelDescripcionTitle)
                        .addGap(4, 4, 4)
                        .addComponent(TextFieldDescripcionPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelPrecioPromocion)))
                .addGroup(PanelButtonsPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelButtonsPromocionesLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(ButtonEditarPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButtonGrabarPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButtonEliminarPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButtonCancelarPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelButtonsPromocionesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(TextFieldPrecioPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelProductosPromocion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelSizePromocion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextFieldSizePromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelIDPromocion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextFieldIDPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        PanelImagePromociones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout PanelImagePromocionesLayout = new javax.swing.GroupLayout(PanelImagePromociones);
        PanelImagePromociones.setLayout(PanelImagePromocionesLayout);
        PanelImagePromocionesLayout.setHorizontalGroup(
            PanelImagePromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PanelImagePromocionesLayout.setVerticalGroup(
            PanelImagePromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelPrincipalAdminPromocionesLayout = new javax.swing.GroupLayout(PanelPrincipalAdminPromociones);
        PanelPrincipalAdminPromociones.setLayout(PanelPrincipalAdminPromocionesLayout);
        PanelPrincipalAdminPromocionesLayout.setHorizontalGroup(
            PanelPrincipalAdminPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalAdminPromocionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPrincipalAdminPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelTableAdminPromociones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelTitleAdminPromociones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPrincipalAdminPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelButtonsPromociones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanelImagePromociones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelPrincipalAdminPromocionesLayout.setVerticalGroup(
            PanelPrincipalAdminPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalAdminPromocionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPrincipalAdminPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelImagePromociones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelTitleAdminPromociones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPrincipalAdminPromocionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelButtonsPromociones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelTableAdminPromociones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipalAdminPromociones, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelPrincipalAdminPromociones, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TextFieldPrecioPromocionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldPrecioPromocionKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '.') {
            evt.consume(); // evita que se escriba el carácter
        }
        
        if (c == '.' && TextFieldPrecioPromocion.getText().contains(".")){
            evt.consume();
        }
    }//GEN-LAST:event_TextFieldPrecioPromocionKeyTyped

    private void TextFieldIDPromocionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldIDPromocionKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldIDPromocionKeyTyped

    private void ButtonNuevaPromocionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNuevaPromocionActionPerformed
        // TODO add your handling code here:
        habilitarCampos(true);
        limpiarCampos();
        ButtonNuevaPromocion.setEnabled(true);
        ButtonEditarPromocion.setEnabled(false);
        ButtonGrabarPromocion.setEnabled(true);
        ButtonEliminarPromocion.setEnabled(false);
        ButtonCancelarPromocion.setEnabled(true);
    }//GEN-LAST:event_ButtonNuevaPromocionActionPerformed

    private void ButtonEditarPromocionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarPromocionActionPerformed
        // TODO add your handling code here:
        sqlMetodo = "UPDATE";
        if(capturarPromocion(sqlMetodo)){
              limpiarCampos();
              listarPromociones();  
              habilitarCampos(false);
              ButtonNuevaPromocion.setEnabled(true);
              ButtonEditarPromocion.setEnabled(false);
              ButtonGrabarPromocion.setEnabled(false);
              ButtonEliminarPromocion.setEnabled(false);
              ButtonCancelarPromocion.setEnabled(false);
           }
    }//GEN-LAST:event_ButtonEditarPromocionActionPerformed

    private void ButtonGrabarPromocionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonGrabarPromocionActionPerformed
        // TODO add your handling code here:
        sqlMetodo = "CREATE";
        if(capturarPromocion(sqlMetodo)){
              limpiarCampos();
              listarPromociones();  
              habilitarCampos(false);
              ButtonNuevaPromocion.setEnabled(true);
              ButtonEditarPromocion.setEnabled(false);
              ButtonGrabarPromocion.setEnabled(false);
              ButtonEliminarPromocion.setEnabled(false);
              ButtonCancelarPromocion.setEnabled(false);
           }
    }//GEN-LAST:event_ButtonGrabarPromocionActionPerformed

    private void ButtonEliminarPromocionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEliminarPromocionActionPerformed
        // TODO add your handling code here:
        eliminarPromocion();
        listarPromociones();
        habilitarCampos(false);
        ButtonNuevaPromocion.setEnabled(true);
        ButtonEditarPromocion.setEnabled(false);
        ButtonGrabarPromocion.setEnabled(false);
        ButtonEliminarPromocion.setEnabled(false);
        ButtonCancelarPromocion.setEnabled(false);
    }//GEN-LAST:event_ButtonEliminarPromocionActionPerformed

    private void ButtonCancelarPromocionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelarPromocionActionPerformed
        // TODO add your handling code here:
        habilitarCampos(false);
        limpiarCampos();
        ButtonNuevaPromocion.setEnabled(true);
        ButtonEditarPromocion.setEnabled(false);
        ButtonGrabarPromocion.setEnabled(false);
        ButtonEliminarPromocion.setEnabled(false);
        ButtonCancelarPromocion.setEnabled(false);
    }//GEN-LAST:event_ButtonCancelarPromocionActionPerformed

    private void TablePromocionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablePromocionesMouseClicked
        // TODO add your handling code here:
        int fila = TablePromociones.getSelectedRow();
        ButtonNuevaPromocion.setEnabled(false);
        ButtonEditarPromocion.setEnabled(true);
        ButtonGrabarPromocion.setEnabled(false);
        ButtonEliminarPromocion.setEnabled(true);  
        ButtonCancelarPromocion.setEnabled(true);
        habilitarCampos(true);
        TextFieldIDPromocion.setEnabled(false);
        
        if (fila >= 0) {
            // Extraer valores de cada columna Nombre, Domicilio, Telefono, Fecha de Ingreso, Activo, Tipo de Empleado
            int idPromocion = Integer.parseInt(TablePromociones.getValueAt(fila, 0).toString());
            String descripcionTabla = TablePromociones.getValueAt(fila, 1).toString();
            String precioTabla = TablePromociones.getValueAt(fila, 2).toString();
            String productosTabla = TablePromociones.getValueAt(fila, 3).toString();
            String sizesTabla = TablePromociones.getValueAt(fila, 4).toString();
             
            // Pasar los valores a los text fields
            //JTextField1 = Producto
            TextFieldDescripcionPromocion.setText(descripcionTabla);
            TextFieldPrecioPromocion.setText(precioTabla);
            TextAreaProductosPromocion.setText(productosTabla);
            TextFieldSizePromocion.setText(sizesTabla);
            TextFieldIDPromocion.setText(String.valueOf(idPromocion));
            
        }
    }//GEN-LAST:event_TablePromocionesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCancelarPromocion;
    private javax.swing.JButton ButtonEditarPromocion;
    private javax.swing.JButton ButtonEliminarPromocion;
    private javax.swing.JButton ButtonGrabarPromocion;
    private javax.swing.JButton ButtonNuevaPromocion;
    private javax.swing.JLabel LabelDescripcionTitle;
    private javax.swing.JLabel LabelIDPromocion;
    private javax.swing.JLabel LabelPrecioPromocion;
    private javax.swing.JLabel LabelProductosPromocion;
    private javax.swing.JLabel LabelSizePromocion;
    private javax.swing.JPanel PanelButtonsPromociones;
    private javax.swing.JPanel PanelImagePromociones;
    private javax.swing.JPanel PanelPrincipalAdminPromociones;
    private javax.swing.JPanel PanelTableAdminPromociones;
    private javax.swing.JPanel PanelTitleAdminPromociones;
    private javax.swing.JTable TablePromociones;
    private javax.swing.JTextArea TextAreaProductosPromocion;
    private javax.swing.JTextField TextFieldDescripcionPromocion;
    private javax.swing.JTextField TextFieldIDPromocion;
    private javax.swing.JTextField TextFieldPrecioPromocion;
    private javax.swing.JTextField TextFieldSizePromocion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
