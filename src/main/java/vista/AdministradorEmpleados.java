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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author davidalejandroruizhernandez
 */
public class AdministradorEmpleados extends javax.swing.JPanel {

    private MenuPrincipal menuPrincipal;
    DefaultTableModel model;
    String sqlMetodo="";
    
    /**
     * Creates new form AdministradorEmpleados
     */
    public AdministradorEmpleados(MenuPrincipal menuPrincipal) {
        initComponents();
        this.menuPrincipal = menuPrincipal;
        setPreferredSize(new Dimension(1200, 786));
        //this.menuPrincipal.setResizable(false);
        menuPrincipal.revalidate();
        menuPrincipal.repaint();
        
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Domicilio");
        model.addColumn("Teléfono");
        model.addColumn("Fecha de Ingreso");
        model.addColumn("Activo");
        model.addColumn("Tipo de Empleado");
        model.addColumn("NIP");
        
        // Asignar modelo a la tabla
        TableEmpleados.setModel(model);
        
    }
    
    public void limpiarCampos(){
      TextFieldConfirmaNip.setText("");
      TextFieldDomicilioEmpleado.setText("");
      TextFieldNip.setText("");
      TextFieldNombreEmpleado.setText("");
      TextFieldTelefonoEmpleado.setText("");
    }
    
    public void habilitarCampos(boolean state){
      TextFieldConfirmaNip.setEnabled(state);
      TextFieldDomicilioEmpleado.setEnabled(state);
      TextFieldNip.setEnabled(state);
      TextFieldNombreEmpleado.setEnabled(state);
      TextFieldTelefonoEmpleado.setEnabled(state);
    }
    
    public void habilitarBotones(boolean state){
        ButtonCancelarEmpleados.setEnabled(state);
        ButtonEditarEmpleado.setEnabled(state);
        ButtonEliminarEmpleado.setEnabled(state);
        ButtonGrabarEmpleado.setEnabled(state);
        ButtonMenuPrincipal.setEnabled(state);
        ButtonNuevoEmpleado.setEnabled(state);
    }
    
    public void listarEmpleados(){
       try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "SELECT idEmpleado, nombre, domicilio, telefono, fechaIngreso, activo, tipoEmpleado, nip FROM empleados ORDER BY idEmpleado ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()){
               Object[] empleado ={
                  rs.getInt("idEmpleado"),
                  rs.getString("nombre"),
                  rs.getString("domicilio"),
                  rs.getString("telefono"),
                  rs.getDate("fechaIngreso"),
                  rs.getString("activo"),
                  rs.getString("tipoEmpleado"),
                  rs.getInt("nip")
               };
            model.addRow(empleado);
          }
      } catch (SQLException ex) {
            System.getLogger(AdministradorEmpleados.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
    
    private boolean capturarEmpleado(String sqlOption){
           
            //Agregar las validaciones aqui
            boolean validacion = true;
            if(sqlOption.matches("CREATE")){
                validacion=true;
                String msgErr = "";
                int numErr=0;
                try (Connection conn = DatabaseConnection.getConnection()){
                    String sql = "INSERT INTO empleados (idEmpleado, nombre, domicilio, telefono, fechaIngreso, activo, tipoEmpleado, nip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    //Validacion ID
                    if (TextFieldIDEmpleado.getText().isEmpty()){
                        validacion=false;
                        msgErr="Ingrese el ID del empleado";
                        numErr++;
                    }else{
                        st.setString(1,TextFieldIDEmpleado.getText());
                    }
                    // Validacion Nombre
                    if (TextFieldNombreEmpleado.getText().isEmpty()) {
                        if (numErr > 1) {
                           msgErr=msgErr+"\n Ingrese el Domicilio del empleado";
                           numErr++;
                        } else{
                           msgErr="Ingrese el Domicilio del empleado";
                        }
                    }else{
                        st.setString(2,TextFieldNombreEmpleado.getText());
                    }
                      if (TextFieldDomicilioEmpleado.getText().isEmpty()) {
                        if (numErr > 1) {
                           msgErr=msgErr+"\n Ingrese el Domicilio del empleado";
                           numErr++;
                        } else{
                           msgErr="Ingrese el Domicilio del empleado";
                        }
                    }else{
                        st.setString(3,TextFieldDomicilioEmpleado.getText());
                    }
                    //Validacion Telefono
                    if (TextFieldTelefonoEmpleado.getText().isEmpty()) {
                        validacion=false;
                        if (numErr > 1){
                           msgErr=msgErr + "\n Ingrese el numero de telefono del empleado";
                           numErr++;
                        }else{
                           msgErr="Ingrese el numero de telefono del empleado";
                        }
                    }else{
                          st.setString(4,TextFieldTelefonoEmpleado.getText());
                    }
                    // Validacion Fecha de Ingreso
                    java.util.Date fechaUtil = DateChosserEmpleado.getDate();
                    if (fechaUtil == null){
                        validacion=false;
                        if(numErr > 1){
                            msgErr=msgErr+"\n Por favor selecciona una fecha de Ingreso.";
                            numErr++;
                        }else{
                            msgErr="\n Por favor selecciona una fecha de Ingreso.";
                        }
                    }else{
                        java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());
                        st.setDate(5, fechaSQL);
                    }
                    //Validacion de Status del empleado
                    if( ! CheckBoxActivoEmpleado.isSelected() && !CheckBoxInactivoEmpleado.isSelected()) {
                        validacion=false;
                        if (numErr > 1){
                            msgErr = msgErr + "\n Seleccione el status del empleado";
                            numErr++;
                        }else{
                            msgErr="Seleccione el status del empleado";
                        }
                    }else{
                        if (CheckBoxActivoEmpleado.isSelected() )st.setString(6,"Si");
                        if (CheckBoxInactivoEmpleado.isSelected() )st.setString(6,"No");
                    }
                    if (ComboBoxTipoEmpleado.getSelectedItem().toString().isEmpty()) {
                        validacion=false;
                        if (numErr > 1){
                           msgErr = msgErr+"\nSeleccione el puesto del empleado";
                           numErr++;
                        }else{
                           msgErr="Seleccione el puesto del empleado";
                        }
                        
                    }else{
                        st.setString(7,ComboBoxTipoEmpleado.getSelectedItem().toString());
                    }        
                    if (TextFieldNip.getText().isEmpty() && TextFieldConfirmaNip.getText().isEmpty()) {
                       validacion=false;
                       if(numErr>1){
                           msgErr=msgErr+"\nIngrese y confirme el NIP de ventas del Empleado";
                           numErr++;
                       }else{
                          msgErr="Ingrese el NIP de ventas del Empleado";
                       }
                    }else if(!TextFieldNip.getText().isEmpty() && TextFieldConfirmaNip.getText().isEmpty()){
                      validacion=false;
                       if(numErr>1){
                           msgErr=msgErr+"\nConfirme el NIP de ventas del Empleado";
                           numErr++;
                       }else{
                           msgErr="Confirme el NIP de ventas del Empleado";
                       }
                    }else if(TextFieldNip.getText().isEmpty() && !TextFieldConfirmaNip.getText().isEmpty()){
                       validacion=false;
                       if(numErr>1){
                           msgErr=msgErr+"\nCampo NIP del empleado esta vacio";
                           numErr++;
                       }else{
                           msgErr="Campo NIP del empleado esta vacio";
                       }
                    }else{
                         //Los campos para el nip no estan vacios
                         if(TextFieldNip.getText().matches(TextFieldConfirmaNip.getText())){
                             st.setInt(8, Integer.parseInt(TextFieldNip.getText()));       
                         }else{
                            validacion=false;
                            if(numErr>1){
                               msgErr=msgErr+"\nLos NIP del empleados no son iguales";
                               numErr++;   
                            }else{
                               msgErr="Los NIP del empleados no son iguales";
                            }
                         }                              
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
                       String sql = "UPDATE empleados SET nombre=?, domicilio=?, telefono=?, fechaIngreso=?, activo=?, tipoEmpleado=?, nip=? WHERE idEmpleado=?";
                       PreparedStatement ps = conn.prepareStatement(sql);

                       // Asignar valores desde tus componentes
                       ps.setString(1,TextFieldNombreEmpleado.getText());
                       ps.setString(2,TextFieldDomicilioEmpleado.getText());
                       ps.setString(3,TextFieldTelefonoEmpleado.getText());
                       java.util.Date fechaUtil = DateChosserEmpleado.getDate();
                       java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());
                       ps.setDate(4, fechaSQL);
                       if (CheckBoxActivoEmpleado.isSelected() )ps.setString(5,"Si");
                       if (CheckBoxInactivoEmpleado.isSelected() )ps.setString(5,"No");
                       ps.setString(6,ComboBoxTipoEmpleado.getSelectedItem().toString());
                       // Agregar validacion para el NIP
                       if(TextFieldNip.getText().isEmpty() && !TextFieldConfirmaNip.getText().isEmpty()){
                           validacion=false;
                           JOptionPane.showMessageDialog(null, "Campo NIP del empleado esta vacio");    
                       }else{
                           //Los campos para el nip no estan vacios
                           if(TextFieldNip.getText().matches(TextFieldConfirmaNip.getText())){
                              
                             ps.setInt(7, Integer.parseInt(TextFieldNip.getText()));                             
                           }else{
                             validacion=false;
                             JOptionPane.showMessageDialog(null, "Los NIP del empleados no son iguales"); 
                         }                              
                        }
                       
                       
                       ps.setInt(8, Integer.parseInt(TextFieldIDEmpleado.getText()));
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
    
    private void eliminarUsuario(){
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM empleados WHERE idEmpleado=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // ID del registro a eliminar
            ps.setInt(1,Integer.parseInt(TextFieldIDEmpleado.getText()));

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
        PanelDatosAdministracionEmpleados = new javax.swing.JPanel();
        LabelTituloAdministracionEmpleados = new javax.swing.JLabel();
        PanelTablaEmpleados = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableEmpleados = new javax.swing.JTable();
        PanelControlesEmpleados = new javax.swing.JPanel();
        LabelNombreEmpleado = new javax.swing.JLabel();
        LabelDomicilioEmpleado = new javax.swing.JLabel();
        LabelTelefonoEmpleado = new javax.swing.JLabel();
        LabelFechaIngreso = new javax.swing.JLabel();
        LabelNipVentas = new javax.swing.JLabel();
        LabelConfirmaNip = new javax.swing.JLabel();
        LabelTipoEmpleado = new javax.swing.JLabel();
        TextFieldNombreEmpleado = new javax.swing.JTextField();
        TextFieldDomicilioEmpleado = new javax.swing.JTextField();
        TextFieldTelefonoEmpleado = new javax.swing.JTextField();
        TextFieldNip = new javax.swing.JTextField();
        TextFieldConfirmaNip = new javax.swing.JTextField();
        CheckBoxActivoEmpleado = new javax.swing.JCheckBox();
        CheckBoxInactivoEmpleado = new javax.swing.JCheckBox();
        DateChosserEmpleado = new com.toedter.calendar.JDateChooser();
        ComboBoxTipoEmpleado = new javax.swing.JComboBox<>();
        ButtonNuevoEmpleado = new javax.swing.JButton();
        ButtonEditarEmpleado = new javax.swing.JButton();
        ButtonGrabarEmpleado = new javax.swing.JButton();
        ButtonEliminarEmpleado = new javax.swing.JButton();
        ButtonCancelarEmpleados = new javax.swing.JButton();
        ButtonMenuPrincipal = new javax.swing.JButton();
        LabelIDEmpleado = new javax.swing.JLabel();
        TextFieldIDEmpleado = new javax.swing.JTextField();

        LabelTituloAdministracionEmpleados.setText("jLabel2");

        javax.swing.GroupLayout PanelDatosAdministracionEmpleadosLayout = new javax.swing.GroupLayout(PanelDatosAdministracionEmpleados);
        PanelDatosAdministracionEmpleados.setLayout(PanelDatosAdministracionEmpleadosLayout);
        PanelDatosAdministracionEmpleadosLayout.setHorizontalGroup(
            PanelDatosAdministracionEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosAdministracionEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelTituloAdministracionEmpleados)
                .addContainerGap(1123, Short.MAX_VALUE))
        );
        PanelDatosAdministracionEmpleadosLayout.setVerticalGroup(
            PanelDatosAdministracionEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosAdministracionEmpleadosLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(LabelTituloAdministracionEmpleados)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        TableEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Domicilio", "Teléfono", "Fecha de Ingreso", "Activo", "Tipo de Empleado", "NIP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableEmpleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableEmpleados);

        javax.swing.GroupLayout PanelTablaEmpleadosLayout = new javax.swing.GroupLayout(PanelTablaEmpleados);
        PanelTablaEmpleados.setLayout(PanelTablaEmpleadosLayout);
        PanelTablaEmpleadosLayout.setHorizontalGroup(
            PanelTablaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelTablaEmpleadosLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelTablaEmpleadosLayout.setVerticalGroup(
            PanelTablaEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTablaEmpleadosLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        LabelNombreEmpleado.setText("* Nombre:");

        LabelDomicilioEmpleado.setText("* Domicilio:");

        LabelTelefonoEmpleado.setText("* Telefono:");

        LabelFechaIngreso.setText("* Fecha de Ingreso:");

        LabelNipVentas.setText("* Nip para ventas");

        LabelConfirmaNip.setText("* Confirma Nip para ventas");

        LabelTipoEmpleado.setText("* Tipo de Empleado:");

        TextFieldTelefonoEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldTelefonoEmpleadoKeyTyped(evt);
            }
        });

        TextFieldNip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldNipKeyTyped(evt);
            }
        });

        TextFieldConfirmaNip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldConfirmaNipKeyTyped(evt);
            }
        });

        CheckBoxActivoEmpleado.setText("Activo");

        CheckBoxInactivoEmpleado.setText("Inactivo");

        ComboBoxTipoEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empleado Mostrador o Vendedor", "Empleado de Cocina", "Repartidor" }));

        ButtonNuevoEmpleado.setText("<html>Nuevo<br>Empleado</html>");
        ButtonNuevoEmpleado.addActionListener(this::ButtonNuevoEmpleadoActionPerformed);

        ButtonEditarEmpleado.setText("Editar Datos");

        ButtonGrabarEmpleado.setText("Grabar");
        ButtonGrabarEmpleado.addActionListener(this::ButtonGrabarEmpleadoActionPerformed);

        ButtonEliminarEmpleado.setText("Eliminar");

        ButtonCancelarEmpleados.setText("Cancelar");

        ButtonMenuPrincipal.setText("<html>Menu<br>Principal</html>");

        LabelIDEmpleado.setText("*ID del Empleado");

        TextFieldIDEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldIDEmpleadoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout PanelControlesEmpleadosLayout = new javax.swing.GroupLayout(PanelControlesEmpleados);
        PanelControlesEmpleados.setLayout(PanelControlesEmpleadosLayout);
        PanelControlesEmpleadosLayout.setHorizontalGroup(
            PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                        .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelNombreEmpleado)
                                    .addComponent(LabelDomicilioEmpleado)))
                            .addComponent(TextFieldDomicilioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextFieldNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(ButtonNuevoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                        .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TextFieldTelefonoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelFechaIngreso)
                            .addComponent(DateChosserEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelTelefonoEmpleado)
                            .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                                .addComponent(CheckBoxActivoEmpleado)
                                .addGap(40, 40, 40)
                                .addComponent(CheckBoxInactivoEmpleado))
                            .addComponent(LabelTipoEmpleado)
                            .addComponent(LabelIDEmpleado)
                            .addComponent(TextFieldIDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboBoxTipoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelNipVentas)
                            .addComponent(TextFieldNip, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelConfirmaNip)
                            .addComponent(TextFieldConfirmaNip, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ButtonEditarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonCancelarEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonMenuPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonGrabarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        PanelControlesEmpleadosLayout.setVerticalGroup(
            PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                                .addComponent(LabelNombreEmpleado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextFieldNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelDomicilioEmpleado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextFieldDomicilioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ButtonNuevoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelTelefonoEmpleado)
                                .addGap(111, 111, 111))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelControlesEmpleadosLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(ButtonEditarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addComponent(ButtonGrabarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                                .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                                        .addGap(131, 131, 131)
                                        .addComponent(ComboBoxTipoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LabelNipVentas)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextFieldNip, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelConfirmaNip)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextFieldConfirmaNip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                                        .addComponent(TextFieldTelefonoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelFechaIngreso)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DateChosserEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(PanelControlesEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(CheckBoxActivoEmpleado)
                                            .addComponent(CheckBoxInactivoEmpleado))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelTipoEmpleado)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelIDEmpleado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TextFieldIDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelControlesEmpleadosLayout.createSequentialGroup()
                                .addGap(218, 218, 218)
                                .addComponent(ButtonEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ButtonCancelarEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(ButtonMenuPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(232, Short.MAX_VALUE))
        );

        TextFieldTelefonoEmpleado.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(PanelTablaEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelControlesEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(PanelDatosAdministracionEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelDatosAdministracionEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelTablaEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelControlesEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void TableEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableEmpleadosMouseClicked
        // TODO add your handling code here:
        int fila = TableEmpleados.getSelectedRow();
        
        ButtonCancelarEmpleados.setEnabled(false);
        ButtonEditarEmpleado.setEnabled(true);
        ButtonEliminarEmpleado.setEnabled(false);
        ButtonGrabarEmpleado.setEnabled(false);
        ButtonMenuPrincipal.setEnabled(true); 
        ButtonNuevoEmpleado.setEnabled(true);
        
        TextFieldNombreEmpleado.setEnabled(true);
        TextFieldDomicilioEmpleado.setEnabled(true);
        TextFieldTelefonoEmpleado.setEnabled(true);
        DateChosserEmpleado.setEnabled(true);
        ComboBoxTipoEmpleado.setEnabled(true);
        TextFieldNip.setEnabled(true);
        TextFieldConfirmaNip.setEnabled(true);
        TextFieldIDEmpleado.setEnabled(true);
        
        if (fila >= 0) {
            // Extraer valores de cada columna Nombre, Domicilio, Telefono, Fecha de Ingreso, Activo, Tipo de Empleado
            int idEmpleado = Integer.parseInt(TableEmpleados.getValueAt(fila, 0).toString());
            String nombreTabla = TableEmpleados.getValueAt(fila, 1).toString();
            String domicilioTabla = TableEmpleados.getValueAt(fila, 2).toString();
            String telefonoTabla = TableEmpleados.getValueAt(fila, 3).toString();
            
            String fechaIngresoTabla = TableEmpleados.getValueAt(fila, 4).toString();
            String nip = TableEmpleados.getValueAt(fila, 7).toString();
            // Definir el formato esperado
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            try {
                  // Convertir la cadena a Date
                  Date fecha = formato.parse(fechaIngresoTabla);
                  //jDataChooser1 = Telefono
                  DateChosserEmpleado.setDate(fecha);
            } catch (ParseException e) {
                  System.out.println("Error al convertir la fecha: " + e.getMessage());
            }
            
            String statusTabla = TableEmpleados.getValueAt(fila, 5).toString();
            String tipoEmpleadoTabla = TableEmpleados.getValueAt(fila, 6).toString();

            TextFieldNombreEmpleado.setText(nombreTabla);
            TextFieldDomicilioEmpleado.setText(domicilioTabla);
            TextFieldTelefonoEmpleado.setText(telefonoTabla);
            
            if (TableEmpleados.isEnabled() ){
                CheckBoxActivoEmpleado.setSelected(true);
                CheckBoxInactivoEmpleado.setSelected(false);
            }else{
                CheckBoxActivoEmpleado.setSelected(false);
                CheckBoxInactivoEmpleado.setSelected(true);         
            }
            
            //jComboBox1 = Tipo de Empleado
            boolean encontrado = false;

            for (int i = 0; i < ComboBoxTipoEmpleado.getItemCount(); i++) {
                  String item = ComboBoxTipoEmpleado.getItemAt(i);
                  if (item.equals(tipoEmpleadoTabla)) {
                      encontrado = true;
                      // Selecciona el elemento en el combo
                      ComboBoxTipoEmpleado.setSelectedIndex(i);
                      break;
                  }
            }
            TextFieldNip.setText(nip);
            TextFieldConfirmaNip.setText(nip);
            TextFieldIDEmpleado.setText(String.valueOf(idEmpleado));
        }
    }//GEN-LAST:event_TableEmpleadosMouseClicked

    private void TextFieldTelefonoEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldTelefonoEmpleadoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldTelefonoEmpleadoKeyTyped

    private void TextFieldNipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldNipKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldNipKeyTyped

    private void TextFieldConfirmaNipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldConfirmaNipKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldConfirmaNipKeyTyped

    private void TextFieldIDEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldIDEmpleadoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldIDEmpleadoKeyTyped

    private void ButtonNuevoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNuevoEmpleadoActionPerformed
        // TODO add your handling code here:
        habilitarCampos(true);
        limpiarCampos();
        ButtonNuevoEmpleado.setEnabled(false);
        ButtonEditarEmpleado.setEnabled(false);
        ButtonGrabarEmpleado.setEnabled(true);
        ButtonCancelarEmpleados.setEnabled(false);
        ButtonEliminarEmpleado.setEnabled(true);
        sqlMetodo="CREATE";  
    }//GEN-LAST:event_ButtonNuevoEmpleadoActionPerformed

    private void ButtonGrabarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonGrabarEmpleadoActionPerformed
        // TODO add your handling code here:
        habilitarCampos(false);
        limpiarCampos();
        ButtonNuevoEmpleado.setEnabled(true);
        ButtonEditarEmpleado.setEnabled(false);
        ButtonGrabarEmpleado.setEnabled(false);
        ButtonCancelarEmpleados.setEnabled(false);
        ButtonEliminarEmpleado.setEnabled(false);
    }//GEN-LAST:event_ButtonGrabarEmpleadoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCancelarEmpleados;
    private javax.swing.JButton ButtonEditarEmpleado;
    private javax.swing.JButton ButtonEliminarEmpleado;
    private javax.swing.JButton ButtonGrabarEmpleado;
    private javax.swing.JButton ButtonMenuPrincipal;
    private javax.swing.JButton ButtonNuevoEmpleado;
    private javax.swing.JCheckBox CheckBoxActivoEmpleado;
    private javax.swing.JCheckBox CheckBoxInactivoEmpleado;
    private javax.swing.JComboBox<String> ComboBoxTipoEmpleado;
    private com.toedter.calendar.JDateChooser DateChosserEmpleado;
    private javax.swing.JLabel LabelConfirmaNip;
    private javax.swing.JLabel LabelDomicilioEmpleado;
    private javax.swing.JLabel LabelFechaIngreso;
    private javax.swing.JLabel LabelIDEmpleado;
    private javax.swing.JLabel LabelNipVentas;
    private javax.swing.JLabel LabelNombreEmpleado;
    private javax.swing.JLabel LabelTelefonoEmpleado;
    private javax.swing.JLabel LabelTipoEmpleado;
    private javax.swing.JLabel LabelTituloAdministracionEmpleados;
    private javax.swing.JPanel PanelControlesEmpleados;
    private javax.swing.JPanel PanelDatosAdministracionEmpleados;
    private javax.swing.JPanel PanelTablaEmpleados;
    private javax.swing.JTable TableEmpleados;
    private javax.swing.JTextField TextFieldConfirmaNip;
    private javax.swing.JTextField TextFieldDomicilioEmpleado;
    private javax.swing.JTextField TextFieldIDEmpleado;
    private javax.swing.JTextField TextFieldNip;
    private javax.swing.JTextField TextFieldNombreEmpleado;
    private javax.swing.JTextField TextFieldTelefonoEmpleado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
