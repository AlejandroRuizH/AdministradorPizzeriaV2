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
public class AdministradorPizzas extends javax.swing.JPanel {

    /**
     * Creates new form AdministradorPizzas
     */
    
    
    private MenuPrincipal menuPrincipal;
    DefaultTableModel model;
    String sqlMetodo="";
    
    public AdministradorPizzas(MenuPrincipal menuPrincipal) {
        
        initComponents();
        this.menuPrincipal = menuPrincipal;
        setPreferredSize(new Dimension(990, 730));
        //this.setResizable(false);
        menuPrincipal.revalidate();
        menuPrincipal.repaint();
        
        //Creacion de la tabla para los productos
        model = new DefaultTableModel();
        model.addColumn("ID de la Pizza");
        model.addColumn("Nombre de la Pizza");
        model.addColumn("Tamaño");
        model.addColumn("Ingredientes");
        model.addColumn("Costo");
        // Asignar modelo a la tabla
        TableTiposPizza.setModel(model); 
        
        //leer la tabla de ingredientes y llenar la tabla con los datos
        listarPizzas();
       
        // Deshabilitar los text Fiel de los productos
        habilitarCampos(false);
        
        //Deshabilitar los botones
        ButtonCancelarPizza.setEnabled(false);
        ButtonEditarPizza.setEnabled(false);
        ButtonEliminarPizza.setEnabled(false);
        ButtonGrabarPizza.setEnabled(false);
        ButtonNuevaPizza.setEnabled(true);
        
    }
    
    private void habilitarCampos(boolean estado){
        TextFieldNombrePizza.setEnabled(estado);
        ComboBoxSizePizza.setEnabled(estado);
        TextAreaIngredientes.setEnabled(estado);
        TextFieldCostoPizza.setEnabled(estado);
        TextFieldIDPizza.setEnabled(estado);
    }
    private void limpiarCampos(){
        TextFieldNombrePizza.setText(""); 
        ComboBoxSizePizza.setSelectedIndex(0);
        TextAreaIngredientes.setText("");
        TextFieldCostoPizza.setText("");
        TextFieldIDPizza.setText("");
    }
    
    private void listarPizzas(){
       try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "SELECT idPizza, nombre, size, costo, ingredientes FROM tipoPizza ORDER BY idPizza ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()){
               Object[] empleado ={
                  rs.getInt("idPizza"),
                  rs.getString("nombre"),
                  rs.getString("size"),
                  rs.getString("ingredientes"),
                  rs.getDouble("costo"),
               };
            model.addRow(empleado);
          }
      } catch (SQLException ex) {
            System.getLogger(AdministradoringredientesExtra.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
        private boolean capturarPizza(String sqlOption){
            //Agregar las validaciones aqui
            boolean validacion = true;
            if(sqlOption.matches("CREATE")){
                validacion=true;
                String msgErr = "";
                int numErr=0;
                try (Connection conn = DatabaseConnection.getConnection()){
                    String sql = "INSERT INTO tipoPizza (idPizza, nombre, size, costo, ingredientes) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    //Validacion ID
                    if (TextFieldIDPizza.getText().isEmpty()){
                        validacion=false;
                        msgErr="Ingrese el ID del Ingrediente";
                        numErr++;
                    }else{
                        st.setInt(1,Integer.parseInt(TextFieldIDPizza.getText()));
                    }
                    // Validacion Nombre de la Pizza
                    if (TextFieldNombrePizza.getText().isEmpty()) {
                        if (numErr > 1) {
                           msgErr=msgErr+"\n Ingrese el nombre de la Pizza";
                           numErr++;
                        } else{
                           msgErr="Ingrese el nombre de la Pizza";
                        }
                    }else{
                        st.setString(2,TextFieldNombrePizza.getText());
                    }
                    // Validacion tamano de la pizza
                    if (ComboBoxSizePizza.getSelectedItem().toString().isEmpty()) {
                        validacion=false;
                        if (numErr > 1){
                           msgErr = msgErr+"\nSeleccione el tamaño de la pizza";
                           numErr++;
                        }else{
                           msgErr="Seleccione el tamaño de la pizza";
                        }
                        
                    }else{
                        st.setString(3,ComboBoxSizePizza.getSelectedItem().toString());
                    }
     
                    //Validacion de los ingredientes de la pizza
                    if (TextAreaIngredientes.getText().isEmpty()) {
                        validacion=false;
                        if (numErr > 1) {
                           msgErr=msgErr+"\nIngrese los ingredientes de la pizza";
                           numErr++;
                        } else{
                           msgErr="Ingrese los ingredientes de la pizza";
                        }
                    }else{
                        st.setString(5,TextAreaIngredientes.getText());    
                    }
                    //Validacion del precio de la pizza
                    if (TextFieldCostoPizza.getText().isEmpty()) {
                        validacion=false;
                        if (numErr > 1){
                           msgErr=msgErr + "\n Ingrese el precio de la Pizza";
                           numErr++;
                        }else{
                           msgErr="Ingrese el precio de la Pizza";
                        }
                    }else{
                          st.setDouble(4,Double.parseDouble(TextFieldCostoPizza.getText()));
                    }  
                    if (msgErr.isEmpty() && (numErr == 0)){
                        st.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Pizza agregada correctamente.");
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
                       String sql = "UPDATE tipoPizza SET nombre=?, size=?, costo=?, ingredientes=? WHERE idPizza=?";
                       PreparedStatement ps = conn.prepareStatement(sql);

                       // Asignar valores desde tus componentes
                       ps.setString(1,TextFieldNombrePizza.getText()); //nombre
                       ps.setString(2,ComboBoxSizePizza.getSelectedItem().toString()); //size
                       ps.setDouble(3,Double.parseDouble(TextFieldCostoPizza.getText())); // costo
                       ps.setString(4,TextAreaIngredientes.getText()); //ingredientes 
                       
                       ps.setInt(5,Integer.parseInt(TextFieldIDPizza.getText()));
                       
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
        
          private void eliminarPizza(){
       try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM tipoPizza WHERE idPizza=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // ID del registro a eliminar
            ps.setInt(1,Integer.parseInt(TextFieldIDPizza.getText()));

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelTitleTiposPizza = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PanelTablaPizzas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableTiposPizza = new javax.swing.JTable();
        PanelDatosPizza = new javax.swing.JPanel();
        LabelTitleNombrePizza = new javax.swing.JLabel();
        TextFieldNombrePizza = new javax.swing.JTextField();
        LabelSizePizza = new javax.swing.JLabel();
        ComboBoxSizePizza = new javax.swing.JComboBox<>();
        LabelTitleIngredientes = new javax.swing.JLabel();
        LabelCostoPizza = new javax.swing.JLabel();
        ButtonNuevaPizza = new javax.swing.JButton();
        ButtonEditarPizza = new javax.swing.JButton();
        ButtonGrabarPizza = new javax.swing.JButton();
        ButtonEliminarPizza = new javax.swing.JButton();
        ButtonCancelarPizza = new javax.swing.JButton();
        TextFieldCostoPizza = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextAreaIngredientes = new javax.swing.JTextArea();
        LabelTitleIDPizza = new javax.swing.JLabel();
        TextFieldIDPizza = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();

        PanelTitleTiposPizza.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel1.setText("Panel Administracion Pizzas");

        javax.swing.GroupLayout PanelTitleTiposPizzaLayout = new javax.swing.GroupLayout(PanelTitleTiposPizza);
        PanelTitleTiposPizza.setLayout(PanelTitleTiposPizzaLayout);
        PanelTitleTiposPizzaLayout.setHorizontalGroup(
            PanelTitleTiposPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTitleTiposPizzaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelTitleTiposPizzaLayout.setVerticalGroup(
            PanelTitleTiposPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelTitleTiposPizzaLayout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(40, 40, 40))
        );

        PanelTablaPizzas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        TableTiposPizza.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID de la Pizza", "Nombre de la Pizza", "Tamaño", "Ingredientes", "Costo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableTiposPizza.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableTiposPizzaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableTiposPizza);

        javax.swing.GroupLayout PanelTablaPizzasLayout = new javax.swing.GroupLayout(PanelTablaPizzas);
        PanelTablaPizzas.setLayout(PanelTablaPizzasLayout);
        PanelTablaPizzasLayout.setHorizontalGroup(
            PanelTablaPizzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
        );
        PanelTablaPizzasLayout.setVerticalGroup(
            PanelTablaPizzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        PanelDatosPizza.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LabelTitleNombrePizza.setText("* Nombre de la Pizza");

        LabelSizePizza.setText("* Tamaño:");

        ComboBoxSizePizza.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mini", "Personal", "Mediana", "Grande", "Extra Grande", "Familiar", "Corazon" }));

        LabelTitleIngredientes.setText("* Ingredientes");

        LabelCostoPizza.setText("* Costo");

        ButtonNuevaPizza.setText("<html><center>Nueva<br>Pizza</center></html>");
        ButtonNuevaPizza.addActionListener(this::ButtonNuevaPizzaActionPerformed);

        ButtonEditarPizza.setText("<html><center>Editar<br>Pizza</center></html>");
        ButtonEditarPizza.addActionListener(this::ButtonEditarPizzaActionPerformed);

        ButtonGrabarPizza.setText("Grabar");
        ButtonGrabarPizza.setToolTipText("");
        ButtonGrabarPizza.addActionListener(this::ButtonGrabarPizzaActionPerformed);

        ButtonEliminarPizza.setText("Eliminar");
        ButtonEliminarPizza.addActionListener(this::ButtonEliminarPizzaActionPerformed);

        ButtonCancelarPizza.setText("Cancelar");
        ButtonCancelarPizza.addActionListener(this::ButtonCancelarPizzaActionPerformed);

        TextFieldCostoPizza.addActionListener(this::TextFieldCostoPizzaActionPerformed);
        TextFieldCostoPizza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldCostoPizzaKeyTyped(evt);
            }
        });

        TextAreaIngredientes.setColumns(20);
        TextAreaIngredientes.setRows(5);
        jScrollPane2.setViewportView(TextAreaIngredientes);

        LabelTitleIDPizza.setText("* ID de la Pizza");

        TextFieldIDPizza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldIDPizzaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout PanelDatosPizzaLayout = new javax.swing.GroupLayout(PanelDatosPizza);
        PanelDatosPizza.setLayout(PanelDatosPizzaLayout);
        PanelDatosPizzaLayout.setHorizontalGroup(
            PanelDatosPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosPizzaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDatosPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTitleNombrePizza)
                    .addComponent(TextFieldNombrePizza, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelSizePizza)
                    .addComponent(ComboBoxSizePizza, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTitleIngredientes)
                    .addComponent(TextFieldCostoPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelCostoPizza)
                    .addComponent(LabelTitleIDPizza)
                    .addComponent(TextFieldIDPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelDatosPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonCancelarPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEliminarPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonGrabarPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEditarPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonNuevaPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDatosPizzaLayout.setVerticalGroup(
            PanelDatosPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosPizzaLayout.createSequentialGroup()
                .addGroup(PanelDatosPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosPizzaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LabelTitleNombrePizza)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextFieldNombrePizza, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelSizePizza))
                    .addGroup(PanelDatosPizzaLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(ButtonNuevaPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(PanelDatosPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosPizzaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(ButtonEditarPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButtonGrabarPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButtonEliminarPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButtonCancelarPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelDatosPizzaLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(ComboBoxSizePizza, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelTitleIngredientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelCostoPizza)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextFieldCostoPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelTitleIDPizza)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextFieldIDPizza, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelTablaPizzas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelTitleTiposPizza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelDatosPizza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelTitleTiposPizza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelDatosPizza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelTablaPizzas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void TableTiposPizzaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableTiposPizzaMouseClicked
        // TODO add your handling code here:
        int fila = TableTiposPizza.getSelectedRow();
        ButtonNuevaPizza.setEnabled(false);
        ButtonEditarPizza.setEnabled(true);
        ButtonGrabarPizza.setEnabled(false);
        ButtonEliminarPizza.setEnabled(true);  
        ButtonCancelarPizza.setEnabled(true);
        habilitarCampos(true);
        TextFieldIDPizza.setEnabled(false);
        
        if (fila >= 0) {
            
            int idPizza = Integer.parseInt(TableTiposPizza.getValueAt(fila, 0).toString());
            String nombrePizzaTabla = TableTiposPizza.getValueAt(fila, 1).toString();
            String tamanoPizza = TableTiposPizza.getValueAt(fila, 2).toString();
            String ingredientesTabla = TableTiposPizza.getValueAt(fila, 3).toString();
            String costoTabla = String.valueOf(TableTiposPizza.getValueAt(fila, 4).toString());
             
            // Pasar los valores a los text fields
            //JTextField1 = Producto
            TextFieldNombrePizza.setText(nombrePizzaTabla);
            
            //jTextField2 = Costo
            TextFieldCostoPizza.setText(costoTabla);
            
            //jTextField3 = Ingredientes
            TextAreaIngredientes.setText(ingredientesTabla);
            
            //jComboBox1 = Tipo de Empleado
            boolean encontrado = false;
            for (int i = 0; i < ComboBoxSizePizza.getItemCount(); i++) {
                  String item = ComboBoxSizePizza.getItemAt(i);
                  if (item.equals(tamanoPizza)) {
                      encontrado = true;
                      // Selecciona el elemento en el combo
                      ComboBoxSizePizza.setSelectedIndex(i);
                      break;
                  }
            }
            
            //jTextField3 = ID
            TextFieldIDPizza.setText(String.valueOf(idPizza));
            
        }
    }//GEN-LAST:event_TableTiposPizzaMouseClicked

    private void ButtonNuevaPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNuevaPizzaActionPerformed
        // TODO add your handling code here:
        habilitarCampos(true);
        limpiarCampos();
        ButtonNuevaPizza.setEnabled(true);
        ButtonEditarPizza.setEnabled(false);
        ButtonGrabarPizza.setEnabled(true);
        ButtonEliminarPizza.setEnabled(false);
        ButtonCancelarPizza.setEnabled(true);
    }//GEN-LAST:event_ButtonNuevaPizzaActionPerformed

    private void ButtonEditarPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarPizzaActionPerformed
        // TODO add your handling code here:
        sqlMetodo = "UPDATE";
        if(capturarPizza(sqlMetodo)){
              limpiarCampos();
              listarPizzas();  
              habilitarCampos(false);
              ButtonNuevaPizza.setEnabled(true);
              ButtonEditarPizza.setEnabled(false);
              ButtonGrabarPizza.setEnabled(false);
              ButtonEliminarPizza.setEnabled(false);
              ButtonCancelarPizza.setEnabled(false);
           }
    }//GEN-LAST:event_ButtonEditarPizzaActionPerformed

    private void ButtonGrabarPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonGrabarPizzaActionPerformed
        // TODO add your handling code here:
        sqlMetodo = "CREATE";
        if(capturarPizza(sqlMetodo)){
              limpiarCampos();
              listarPizzas();  
              habilitarCampos(false);
              ButtonNuevaPizza.setEnabled(true);
              ButtonEditarPizza.setEnabled(false);
              ButtonGrabarPizza.setEnabled(false);
              ButtonEliminarPizza.setEnabled(false);
              ButtonCancelarPizza.setEnabled(false);
           }
    }//GEN-LAST:event_ButtonGrabarPizzaActionPerformed

    private void TextFieldCostoPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldCostoPizzaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldCostoPizzaActionPerformed

    private void TextFieldCostoPizzaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldCostoPizzaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '.') {
            evt.consume(); // evita que se escriba el carácter
        }
        
        if (c == '.' && TextFieldCostoPizza.getText().contains(".")){
            evt.consume();
        }
    }//GEN-LAST:event_TextFieldCostoPizzaKeyTyped

    private void TextFieldIDPizzaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldIDPizzaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '.') {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldIDPizzaKeyTyped

    private void ButtonEliminarPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEliminarPizzaActionPerformed
        // TODO add your handling code here:
        eliminarPizza();
        listarPizzas();
        habilitarCampos(false);
        ButtonNuevaPizza.setEnabled(true);
        ButtonEditarPizza.setEnabled(false);
        ButtonGrabarPizza.setEnabled(false);
        ButtonEliminarPizza.setEnabled(false);
        ButtonCancelarPizza.setEnabled(false);
    }//GEN-LAST:event_ButtonEliminarPizzaActionPerformed

    private void ButtonCancelarPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelarPizzaActionPerformed
        // TODO add your handling code here:
        habilitarCampos(false);
        limpiarCampos();
        ButtonNuevaPizza.setEnabled(true);
        ButtonEditarPizza.setEnabled(false);
        ButtonGrabarPizza.setEnabled(false);
        ButtonEliminarPizza.setEnabled(false);
        ButtonCancelarPizza.setEnabled(false);
    }//GEN-LAST:event_ButtonCancelarPizzaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCancelarPizza;
    private javax.swing.JButton ButtonEditarPizza;
    private javax.swing.JButton ButtonEliminarPizza;
    private javax.swing.JButton ButtonGrabarPizza;
    private javax.swing.JButton ButtonNuevaPizza;
    private javax.swing.JComboBox<String> ComboBoxSizePizza;
    private javax.swing.JLabel LabelCostoPizza;
    private javax.swing.JLabel LabelSizePizza;
    private javax.swing.JLabel LabelTitleIDPizza;
    private javax.swing.JLabel LabelTitleIngredientes;
    private javax.swing.JLabel LabelTitleNombrePizza;
    private javax.swing.JPanel PanelDatosPizza;
    private javax.swing.JPanel PanelTablaPizzas;
    private javax.swing.JPanel PanelTitleTiposPizza;
    private javax.swing.JTable TableTiposPizza;
    private javax.swing.JTextArea TextAreaIngredientes;
    private javax.swing.JTextField TextFieldCostoPizza;
    private javax.swing.JTextField TextFieldIDPizza;
    private javax.swing.JTextField TextFieldNombrePizza;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
