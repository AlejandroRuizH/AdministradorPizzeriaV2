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
public class AdministradoringredientesExtra extends javax.swing.JPanel {

    /**
     * Creates new form AdministradoringredientesExtra
     */
    
     private MenuPrincipal menuPrincipal;
     DefaultTableModel model;
     String sqlMetodo="";
    
    public AdministradoringredientesExtra(MenuPrincipal menuPrincipal) {
         
        this.menuPrincipal = menuPrincipal;
        initComponents();
        setPreferredSize(new Dimension(977, 704));
        //this.setResizable(false);
        menuPrincipal.revalidate();
        menuPrincipal.repaint();
        //
        
        //Creacion de la tabla para los productos
        model = new DefaultTableModel();
        model.addColumn("ID del producto");
        model.addColumn("Descripcion");
        model.addColumn("Precio");
        model.addColumn("Tipo");
        // Asignar modelo a la tabla
        TablaIngredientesExtra.setModel(model); 
        
        //leer la tabla de ingredientes y llenar la tabla con los datos
        listarIngredientes();
       
        // Deshabilitar los text Fiel de los productos
        habilitarCampos(false);
        
        //Deshabilitar los botones
        ButtonCancelarIngrediente.setEnabled(false);
        ButtonEditarIngrediente.setEnabled(false);
        ButtonEliminarIngrediente.setEnabled(false);
        ButtonGrabarIngrediente.setEnabled(false);
        ButtonNuevoIngrediente.setEnabled(true);
        
    }
    
    private void habilitarCampos(boolean estado){
        TextFieldDescripcionIngrediente.setEnabled(estado);
        ComboBoxTipoIngrediente.setEnabled(estado);
        TextFieldIDIngrediente.setEnabled(estado);
        TextFieldPrecioIngrediente.setEnabled(estado);
    }
    private void limpiarCampos(){
        TextFieldDescripcionIngrediente.setText(""); 
        ComboBoxTipoIngrediente.setSelectedIndex(0);
        TextFieldIDIngrediente.setText("");
        TextFieldPrecioIngrediente.setText("");
    }
    
    private void listarIngredientes(){
       try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "SELECT idIngrediente, descripcion, precio, tipo FROM Ingredientesextra ORDER BY idIngrediente ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()){
               Object[] empleado ={
                  rs.getInt("idIngrediente"),
                  rs.getString("descripcion"),
                  rs.getDouble("precio"),
                  rs.getString("tipo"),
               };
            model.addRow(empleado);
          }
      } catch (SQLException ex) {
            System.getLogger(AdministradoringredientesExtra.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
       private void eliminarIngrediente(){
       try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM ingredientesextra WHERE idIngrediente=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // ID del registro a eliminar
            ps.setInt(1,Integer.parseInt(TextFieldIDIngrediente.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el registro.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
    }
    private boolean capturarIngrediente(String sqlOption){
            //Agregar las validaciones aqui
            boolean validacion = true;
            if(sqlOption.matches("CREATE")){
                validacion=true;
                String msgErr = "";
                int numErr=0;
                try (Connection conn = DatabaseConnection.getConnection()){
                    String sql = "INSERT INTO ingredientesextra (idIngrediente, descripcion, precio, tipo) VALUES (?, ?, ?, ?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    //Validacion ID
                    if (TextFieldIDIngrediente.getText().isEmpty()){
                        validacion=false;
                        msgErr="Ingrese el ID del Ingrediente";
                        numErr++;
                    }else{
                        st.setInt(1,Integer.parseInt(TextFieldIDIngrediente.getText()));
                    }
                    // Validacion Nombre
                    if (TextFieldDescripcionIngrediente.getText().isEmpty()) {
                        if (numErr > 1) {
                           msgErr=msgErr+"\n Ingrese la Descripcion del Ingrediente";
                           numErr++;
                        } else{
                           msgErr="Ingrese la Descripcion del Ingrediente";
                        }
                    }else{
                        st.setString(2,TextFieldDescripcionIngrediente.getText());
                    }
                    //Validacion Tipo de Ingrediente
                    if (ComboBoxTipoIngrediente.getSelectedItem().toString().isEmpty()) {
                        validacion=false;
                        if (numErr > 1){
                           msgErr = msgErr+"\nSeleccione el tipo de Ingrediente";
                           numErr++;
                        }else{
                           msgErr="Seleccione el tipo de Ingrediente";
                        }
                        
                    }else{
                        st.setString(4,ComboBoxTipoIngrediente.getSelectedItem().toString());
                    }
                    //Validacion Telefono
                    if (TextFieldPrecioIngrediente.getText().isEmpty()) {
                        validacion=false;
                        if (numErr > 1){
                           msgErr=msgErr + "\n Ingrese el precio del Ingrediente";
                           numErr++;
                        }else{
                           msgErr="Ingrese el precio del Ingrediente";
                        }
                    }else{
                          st.setDouble(3,Double.parseDouble(TextFieldPrecioIngrediente.getText()));
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
                       String sql = "UPDATE ingredientesextra SET descripcion=?, precio=?, tipo=? WHERE idIngrediente=?";
                       PreparedStatement ps = conn.prepareStatement(sql);

                       // Asignar valores desde tus componentes
                       ps.setString(1,TextFieldDescripcionIngrediente.getText());
                       ps.setDouble(2,Double.parseDouble(TextFieldPrecioIngrediente.getText()));
                       ps.setString(3,ComboBoxTipoIngrediente.getSelectedItem().toString());
                       
                       ps.setInt(4,Integer.parseInt(TextFieldIDIngrediente.getText()));
                       
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

        PanelTituloAdminIngredientes = new javax.swing.JPanel();
        LabelTituloIngredientesExtra = new javax.swing.JLabel();
        PanelTablaIngredientesExtra = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaIngredientesExtra = new javax.swing.JTable();
        PanelBotonesIngredientesExtra = new javax.swing.JPanel();
        LabelDescripcionIngrediente = new javax.swing.JLabel();
        ButtonNuevoIngrediente = new javax.swing.JButton();
        ButtonEditarIngrediente = new javax.swing.JButton();
        ButtonGrabarIngrediente = new javax.swing.JButton();
        ButtonEliminarIngrediente = new javax.swing.JButton();
        ButtonCancelarIngrediente = new javax.swing.JButton();
        TextFieldDescripcionIngrediente = new javax.swing.JTextField();
        LabelTipoIngrediente = new javax.swing.JLabel();
        TextFieldPrecioIngrediente = new javax.swing.JTextField();
        ComboBoxTipoIngrediente = new javax.swing.JComboBox<>();
        LabelPrecioIngrediente = new javax.swing.JLabel();
        LabelIDIngrediente = new javax.swing.JLabel();
        TextFieldIDIngrediente = new javax.swing.JTextField();
        PanelImagenPanelIngredientes = new javax.swing.JPanel();

        PanelTituloAdminIngredientes.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LabelTituloIngredientesExtra.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        LabelTituloIngredientesExtra.setText("Panel Administracion Ingredientes Extra");

        javax.swing.GroupLayout PanelTituloAdminIngredientesLayout = new javax.swing.GroupLayout(PanelTituloAdminIngredientes);
        PanelTituloAdminIngredientes.setLayout(PanelTituloAdminIngredientesLayout);
        PanelTituloAdminIngredientesLayout.setHorizontalGroup(
            PanelTituloAdminIngredientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTituloAdminIngredientesLayout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(LabelTituloIngredientesExtra)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelTituloAdminIngredientesLayout.setVerticalGroup(
            PanelTituloAdminIngredientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTituloAdminIngredientesLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(LabelTituloIngredientesExtra)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelTablaIngredientesExtra.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TablaIngredientesExtra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID del Producto", "Descripcion", "Precio", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaIngredientesExtra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaIngredientesExtraMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaIngredientesExtra);
        if (TablaIngredientesExtra.getColumnModel().getColumnCount() > 0) {
            TablaIngredientesExtra.getColumnModel().getColumn(0).setResizable(false);
            TablaIngredientesExtra.getColumnModel().getColumn(1).setResizable(false);
            TablaIngredientesExtra.getColumnModel().getColumn(2).setResizable(false);
            TablaIngredientesExtra.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout PanelTablaIngredientesExtraLayout = new javax.swing.GroupLayout(PanelTablaIngredientesExtra);
        PanelTablaIngredientesExtra.setLayout(PanelTablaIngredientesExtraLayout);
        PanelTablaIngredientesExtraLayout.setHorizontalGroup(
            PanelTablaIngredientesExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTablaIngredientesExtraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PanelTablaIngredientesExtraLayout.setVerticalGroup(
            PanelTablaIngredientesExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        PanelBotonesIngredientesExtra.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LabelDescripcionIngrediente.setText("* Descripcion del Ingrediente");

        ButtonNuevoIngrediente.setText("<html><center>Nuevo<br>Ingrediente</center></html>");
        ButtonNuevoIngrediente.addActionListener(this::ButtonNuevoIngredienteActionPerformed);

        ButtonEditarIngrediente.setText("<html><center>Editar<br>Ingrediente</center></html>");
        ButtonEditarIngrediente.addActionListener(this::ButtonEditarIngredienteActionPerformed);

        ButtonGrabarIngrediente.setText("Grabar");
        ButtonGrabarIngrediente.addActionListener(this::ButtonGrabarIngredienteActionPerformed);

        ButtonEliminarIngrediente.setText("Eliminar");
        ButtonEliminarIngrediente.addActionListener(this::ButtonEliminarIngredienteActionPerformed);

        ButtonCancelarIngrediente.setText("Cancelar");
        ButtonCancelarIngrediente.addActionListener(this::ButtonCancelarIngredienteActionPerformed);

        LabelTipoIngrediente.setText("* Tipo");

        TextFieldPrecioIngrediente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldPrecioIngredienteKeyTyped(evt);
            }
        });

        ComboBoxTipoIngrediente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Carne", "Vegetal", "Fruta", "Extra" }));

        LabelPrecioIngrediente.setText("* Precio");

        LabelIDIngrediente.setText("* ID del Ingrediente");

        javax.swing.GroupLayout PanelBotonesIngredientesExtraLayout = new javax.swing.GroupLayout(PanelBotonesIngredientesExtra);
        PanelBotonesIngredientesExtra.setLayout(PanelBotonesIngredientesExtraLayout);
        PanelBotonesIngredientesExtraLayout.setHorizontalGroup(
            PanelBotonesIngredientesExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotonesIngredientesExtraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelBotonesIngredientesExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextFieldDescripcionIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTipoIngrediente)
                    .addComponent(ComboBoxTipoIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPrecioIngrediente)
                    .addComponent(TextFieldPrecioIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelIDIngrediente)
                    .addComponent(TextFieldIDIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelDescripcionIngrediente))
                .addGap(18, 18, 18)
                .addGroup(PanelBotonesIngredientesExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonCancelarIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEditarIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonNuevoIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonGrabarIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEliminarIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        PanelBotonesIngredientesExtraLayout.setVerticalGroup(
            PanelBotonesIngredientesExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotonesIngredientesExtraLayout.createSequentialGroup()
                .addGroup(PanelBotonesIngredientesExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelBotonesIngredientesExtraLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(ButtonNuevoIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelBotonesIngredientesExtraLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(LabelDescripcionIngrediente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextFieldDescripcionIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelTipoIngrediente)))
                .addGroup(PanelBotonesIngredientesExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelBotonesIngredientesExtraLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(ButtonEditarIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButtonGrabarIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButtonEliminarIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelBotonesIngredientesExtraLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(ComboBoxTipoIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelPrecioIngrediente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextFieldPrecioIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelIDIngrediente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextFieldIDIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(ButtonCancelarIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        PanelImagenPanelIngredientes.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout PanelImagenPanelIngredientesLayout = new javax.swing.GroupLayout(PanelImagenPanelIngredientes);
        PanelImagenPanelIngredientes.setLayout(PanelImagenPanelIngredientesLayout);
        PanelImagenPanelIngredientesLayout.setHorizontalGroup(
            PanelImagenPanelIngredientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PanelImagenPanelIngredientesLayout.setVerticalGroup(
            PanelImagenPanelIngredientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelTablaIngredientesExtra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(PanelTituloAdminIngredientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelBotonesIngredientesExtra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelImagenPanelIngredientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelImagenPanelIngredientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelTituloAdminIngredientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelBotonesIngredientesExtra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanelTablaIngredientesExtra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TextFieldPrecioIngredienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldPrecioIngredienteKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '.') {
            evt.consume(); // evita que se escriba el carácter
        }
        
        if (c == '.' && TextFieldPrecioIngrediente.getText().contains(".")){
            evt.consume();
        }
    }//GEN-LAST:event_TextFieldPrecioIngredienteKeyTyped

    private void ButtonNuevoIngredienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNuevoIngredienteActionPerformed
        // TODO add your handling code here:
        habilitarCampos(true);
        limpiarCampos();
        ButtonCancelarIngrediente.setEnabled(true);
        ButtonEditarIngrediente.setEnabled(false);
        ButtonEliminarIngrediente.setEnabled(false);
        ButtonGrabarIngrediente.setEnabled(true);
        ButtonNuevoIngrediente.setEnabled(true);
    }//GEN-LAST:event_ButtonNuevoIngredienteActionPerformed

    private void ButtonCancelarIngredienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelarIngredienteActionPerformed
        // TODO add your handling code here:
        habilitarCampos(true);
        limpiarCampos();
        ButtonCancelarIngrediente.setEnabled(false);
        ButtonEditarIngrediente.setEnabled(false);
        ButtonEliminarIngrediente.setEnabled(false);
        ButtonGrabarIngrediente.setEnabled(false);
        ButtonNuevoIngrediente.setEnabled(true);
    }//GEN-LAST:event_ButtonCancelarIngredienteActionPerformed

    private void ButtonGrabarIngredienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonGrabarIngredienteActionPerformed
        // TODO add your handling code here:
        sqlMetodo = "CREATE";
        if(capturarIngrediente(sqlMetodo)){
              limpiarCampos();
              listarIngredientes();  
              habilitarCampos(false);
              ButtonNuevoIngrediente.setEnabled(true);
              ButtonEditarIngrediente.setEnabled(false);
              ButtonGrabarIngrediente.setEnabled(false);
              ButtonEliminarIngrediente.setEnabled(false);
              ButtonCancelarIngrediente.setEnabled(false);
           }
    }//GEN-LAST:event_ButtonGrabarIngredienteActionPerformed

    private void TablaIngredientesExtraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaIngredientesExtraMouseClicked
        // TODO add your handling code here:
        // Accion cuando se hace clic con el mouse
        int fila = TablaIngredientesExtra.getSelectedRow();
        ButtonNuevoIngrediente.setEnabled(false);
        ButtonEditarIngrediente.setEnabled(true);
        ButtonEliminarIngrediente.setEnabled(false);
        ButtonGrabarIngrediente.setEnabled(false);
        ButtonCancelarIngrediente.setEnabled(true);  
        habilitarCampos(true);
        TextFieldIDIngrediente.setEnabled(false);
        
        if (fila >= 0) {
            // Extraer valores de cada columna Nombre, Domicilio, Telefono, Fecha de Ingreso, Activo, Tipo de Empleado
            int idIngrediente = Integer.parseInt(TablaIngredientesExtra.getValueAt(fila, 0).toString());
            String descripcionTabla = TablaIngredientesExtra.getValueAt(fila, 1).toString();
            String precioTabla = String.valueOf(TablaIngredientesExtra.getValueAt(fila, 2).toString());
            String tipoTabla = TablaIngredientesExtra.getValueAt(fila, 3).toString();
             
            // Pasar los valores a los text fields
            //JTextField1 = Producto
            TextFieldDescripcionIngrediente.setText(descripcionTabla);
            
            //jComboBox1 = Tipo de Empleado
            boolean encontrado = false;

            for (int i = 0; i < ComboBoxTipoIngrediente.getItemCount(); i++) {
                  String item = ComboBoxTipoIngrediente.getItemAt(i);
                  if (item.equals(tipoTabla)) {
                      encontrado = true;
                      // Selecciona el elemento en el combo
                      ComboBoxTipoIngrediente.setSelectedIndex(i);
                      break;
                  }
            }
            
            //jTextField3 = Precio
            TextFieldPrecioIngrediente.setText(precioTabla);
            
            //jTextField3 = ID
            TextFieldIDIngrediente.setText(String.valueOf(idIngrediente));
            
        }
    }//GEN-LAST:event_TablaIngredientesExtraMouseClicked

    private void ButtonEditarIngredienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarIngredienteActionPerformed
        // TODO add your handling code here:
        sqlMetodo = "UPDATE";
        if(capturarIngrediente(sqlMetodo)){
              limpiarCampos();
              listarIngredientes();  
              habilitarCampos(false);
              ButtonNuevoIngrediente.setEnabled(true);
              ButtonEditarIngrediente.setEnabled(false);
              ButtonGrabarIngrediente.setEnabled(false);
              ButtonEliminarIngrediente.setEnabled(false);
              ButtonCancelarIngrediente.setEnabled(false);
           }
    }//GEN-LAST:event_ButtonEditarIngredienteActionPerformed

    private void ButtonEliminarIngredienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEliminarIngredienteActionPerformed
        // TODO add your handling code here:
         eliminarIngrediente();
         listarIngredientes();
         habilitarCampos(false);
         ButtonNuevoIngrediente.setEnabled(true);
         ButtonEditarIngrediente.setEnabled(false);
         ButtonGrabarIngrediente.setEnabled(false);
         ButtonEliminarIngrediente.setEnabled(false);
         ButtonCancelarIngrediente.setEnabled(false);
    }//GEN-LAST:event_ButtonEliminarIngredienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCancelarIngrediente;
    private javax.swing.JButton ButtonEditarIngrediente;
    private javax.swing.JButton ButtonEliminarIngrediente;
    private javax.swing.JButton ButtonGrabarIngrediente;
    private javax.swing.JButton ButtonNuevoIngrediente;
    private javax.swing.JComboBox<String> ComboBoxTipoIngrediente;
    private javax.swing.JLabel LabelDescripcionIngrediente;
    private javax.swing.JLabel LabelIDIngrediente;
    private javax.swing.JLabel LabelPrecioIngrediente;
    private javax.swing.JLabel LabelTipoIngrediente;
    private javax.swing.JLabel LabelTituloIngredientesExtra;
    private javax.swing.JPanel PanelBotonesIngredientesExtra;
    private javax.swing.JPanel PanelImagenPanelIngredientes;
    private javax.swing.JPanel PanelTablaIngredientesExtra;
    private javax.swing.JPanel PanelTituloAdminIngredientes;
    private javax.swing.JTable TablaIngredientesExtra;
    private javax.swing.JTextField TextFieldDescripcionIngrediente;
    private javax.swing.JTextField TextFieldIDIngrediente;
    private javax.swing.JTextField TextFieldPrecioIngrediente;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
