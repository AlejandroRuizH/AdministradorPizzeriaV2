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
public class AdministradorDatosSucursal extends javax.swing.JPanel {

    /**
     * Creates new form AdministradorDatosSucursal
     */
    
     private MenuPrincipal menuPrincipal;
     String sqlMetodo="";
     DefaultTableModel model;
    
    public AdministradorDatosSucursal(MenuPrincipal menuPrincipal) {
        
        this.menuPrincipal = menuPrincipal;
        initComponents();
        setPreferredSize(new Dimension(800, 540));
        //this.setResizable(false);
        menuPrincipal.revalidate();
        menuPrincipal.repaint();  
        cargarDatosSuc();
        
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Teléfono");
        model.addColumn("Gerente");
        model.addColumn("Contrasena");
        model.addColumn("Fecha de Ingreso");
        
        // Asignar modelo a la tabla
        TablaAdministradoresSucursal.setModel(model);
        
        TextFieldIDSucursal.setEnabled(false);
        listarAdministrador();
        
    }
    
    public void cargarDatosSuc(){
        try(Connection conn = DatabaseConnection.getConnection()){
            String sql = """
                         SELECT idSucursal, nombreSucursal, domicilio, colonia, 
                         CodigoPostal, estado, municipio, rfc, telefono1, telefono2, 
                         nombreContribuyente,
                         regimenFiscal FROM datossucursal
                         WHERE idSucursal = 1
                         """;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            
            if (rs.next()){
                
                TextFieldIDSucursal.setText(String.valueOf(rs.getInt("idSucursal")));
                TextFieldNombreSucursal.setText(rs.getString("nombreSucursal"));
                TextFieldDomicilioSucursal.setText(rs.getString("domicilio"));
                TextFieldColoniaSucursal.setText(rs.getString("colonia"));
                TextFieldCPSucursal.setText(String.valueOf(rs.getInt("CodigoPostal")));
                TextFieldEstadoSucursal.setText(rs.getString("estado"));
                TextFieldMunicipioSucursal.setText(rs.getString("municipio"));
                TextFieldRFCSucursal.setText(rs.getString("rfc"));
                TextFieldContribuyenteSucursal.setText(rs.getString("nombreContribuyente"));
                TextFieldRegimenSucursal.setText(rs.getString("regimenFiscal"));
                TextFieldTelefono1Sucursal.setText(rs.getString("telefono1"));
                TextFieldTelefono2Sucursal.setText(rs.getString("telefono2"));
                //TextFieldNombreGerenteSucursal.setText(rs.getString("nombreGerente"));
            }
        } catch (SQLException ex) {
             System.getLogger(AdministradorDatosSucursal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
         }
  
    }

    public void actualizarDatosSucursal(){
        
        boolean validacion = true;
        String msgErr="";
        
        String nombreSucursal = TextFieldNombreSucursal.getText();
        if(nombreSucursal.isEmpty()){
            validacion = false;
            msgErr= msgErr +"Nombre de la Sucursal \n";
        }
        
        String domicilioSucursal = TextFieldDomicilioSucursal.getText();
        if (domicilioSucursal.isEmpty()){
           validacion = false;
           msgErr= msgErr +"Domicilio de la Sucursal \n";
        }
        String coloniaSucursal = TextFieldColoniaSucursal.getText();
        if (coloniaSucursal.isEmpty()){
           validacion = false;
           msgErr= msgErr +"Colonia de la Sucursal \n";
        } 
        
        Integer CPSucursal = Integer.parseInt(TextFieldCPSucursal.getText());
        if (TextFieldCPSucursal.getText().isEmpty()){
           validacion = false;
           msgErr= msgErr +"Codigo Postal de la Sucursal \n";
        } 
        
        String estadoSucursal = TextFieldEstadoSucursal.getText();
        if (estadoSucursal.isEmpty()){
           validacion = false;
           msgErr= msgErr +"Estadol de la Sucursal \n";
        } 
        
        String municipioSucursal = TextFieldMunicipioSucursal.getText();
        if (municipioSucursal.isEmpty()){
           validacion = false;
           msgErr= msgErr +"Municipio de la Sucursal \n";
        }
        
        String rfcSucursal = TextFieldRFCSucursal.getText();
        if (rfcSucursal.isEmpty()){
           validacion = false;
           msgErr= msgErr +"RFC del Contribuyente \n";
        }
        
        String nombreContribuyente = TextFieldContribuyenteSucursal.getText();
        if (nombreContribuyente.isEmpty()){
           validacion = false;
           msgErr= msgErr +"Nombre del Contribuyente \n";
        }
        
        String regimenSucursal = TextFieldRegimenSucursal.getText();
        if (regimenSucursal.isEmpty()){
           validacion = false;
           msgErr= msgErr +"Regimen Fiscal \n";
        }
        
        String telefono1Sucursal = TextFieldTelefono1Sucursal.getText();
        if (telefono1Sucursal.isEmpty()){
           validacion = false;
           msgErr= msgErr +"El telefono No.1 de la Sucursal \n";
        }
        
        String telefono2Sucursal = TextFieldTelefono2Sucursal.getText();
        if (telefono2Sucursal.isEmpty()){
           validacion = false;
           msgErr= msgErr +"El telefono No.2 de la Sucursal \n";
        }
        
        Integer IDSucursal = Integer.parseInt(TextFieldIDSucursal.getText());
        if (TextFieldIDSucursal.getText().isEmpty()){
           validacion = false;
           msgErr= msgErr +"El ID de la Sucursal \n";
        }
        
         if (validacion){
            String sql = """
                                UPDATE datossucursal
                                SET 
                                nombreSucursal = ?,
                                domicilio = ?, 
                                colonia = ?,
                                CodigoPostal = ?,
                                estado = ?,
                                municipio = ?,
                                rfc = ?,
                                nombreContribuyente = ?,
                                regimenFiscal = ?,
                                telefono1 = ?,
                                telefono2 = ?
                                WHERE idSucursal = ?
                                """;   
        try (Connection conn = DatabaseConnection.getConnection()) {
              
              PreparedStatement ps = conn.prepareStatement(sql);
              
              ps.setString(1,nombreSucursal);
              ps.setString(2,domicilioSucursal);
              ps.setString(3,coloniaSucursal);         
              ps.setInt(4,CPSucursal);       
              ps.setString(5,estadoSucursal);
              ps.setString(6,municipioSucursal);         
              ps.setString(7,rfcSucursal);
              ps.setString(8,nombreContribuyente);
              ps.setString(9, regimenSucursal);
              ps.setString(10,telefono1Sucursal);
              ps.setString(11,telefono2Sucursal);
              ps.setInt(12, IDSucursal);
              
              int filas = ps.executeUpdate();
              if (filas > 0) {
                  JOptionPane.showMessageDialog(null, "Registro actualizado correctamente.");
              } else {
                 JOptionPane.showMessageDialog(null, "No se encontró el registro.");
              }
        
         } catch (SQLException ex) {
             System.getLogger(AdministradorDatosSucursal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
         }
         }else{
                JOptionPane.showMessageDialog(null, "Los siguientes campos estan incompletos: \n" + msgErr);
         }
        
    }
    
   public void listarAdministrador(){
       try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "SELECT id_administrador, nombre, gerente, contrasena, telefono, fecha_ingreso FROM administradores ORDER BY id_administrador ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()){
               Object[] empleado ={
                  rs.getInt("id_administrador"),
                  rs.getString("nombre"),
                  rs.getString("telefono"),
                  rs.getBoolean("gerente"),
                  rs.getString("contrasena"),                
                  rs.getDate("fecha_ingreso")
               };
            model.addRow(empleado);
          }
      } catch (SQLException ex) {
            System.getLogger(AdministradorEmpleados.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
   
       public void actualizarAdministrador(){
           
        boolean validacion=true;
        String msgErr="";
           
         try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE administradores SET nombre=?, gerente=?, contrasena=?, telefono=?, feha_ingreso=? WHERE id_administrador=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                  
                  /*
                  *******      Validacion de todos los campos    **********
                  */
                  
                  // Validacion del TextField Nombre Empleado
                  if (TextFieldNombreGerenteSucursal.getText().isEmpty()){
                      msgErr=msgErr+"Campo Nombre del Administrador esta vacio";
                      validacion=false; 
                  }
                  if (!CheckBoxAdministrador.isSelected() && !CheckboxGerente.isSelected()){
                      msgErr=msgErr+"Selecciona el tipo de puesto del usuario";
                      validacion=false;      
                  }       
                  // Validacion del TextField Contrasena
                  if (TextFieldPassAdministrador.getText().isEmpty()){
                      msgErr=msgErr+"Campo Contrasena del Administrador esta vacio";
                      validacion=false;
                  }
                  // Validacion del TextField Telefono
                  if (TextFieldTelefonoAdministrador.getText().isEmpty()){
                      msgErr=msgErr+"Campo Telefono del Administrador esta vacio";
                      validacion=false;
                  }
                  // Validacion del DateChosserEmpleado
                   if (DateChooserAdministrador.getDate() == null ){
                      msgErr=msgErr+"Campo Telefono del Administrador esta vacio";
                      validacion=false;
                  }
                  // Validacion del ID Empleado
                  if(TextFieldIDAdministrador.getText().isEmpty()){
                      msgErr=msgErr+"Campo Telefono del Administrador esta vacio";
                      validacion=false;
                  }
                  
                  if(validacion){
                     ps.setString(1,TextFieldNombreGerenteSucursal.getText());
                     if (CheckboxGerente.isSelected()) ps.setBoolean(2, true);
                     if (CheckBoxAdministrador.isSelected()) ps.setBoolean(2, false);
                     ps.setString(3,TextFieldPassAdministrador.getText());
                     ps.setString(4,TextFieldTelefonoAdministrador.getText());
                     java.util.Date fechaUtil = DateChooserAdministrador.getDate();
                     java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());
                     ps.setDate(5, fechaSQL);     
                     ps.setInt(6,Integer.parseInt(TextFieldIDAdministrador.getText()));
                     
                    
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
    }

        private void capturarAdministrador(){
           
            //Agregar las validaciones aqui
            boolean validacion = true;
            String msgErr="";
            
            try (Connection conn = DatabaseConnection.getConnection()){
                    String sql = "INSERT INTO administradores (id_administrador, nombre, gerente, contrasena, telefono, fecha_ingreso) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    
                    /*
                  *******      Validacion de todos los campos    **********
                  */
                  
                  // Validacion del TextField Nombre Empleado
                  if (TextFieldNombreGerenteSucursal.getText().isEmpty()){
                      msgErr=msgErr+"Campo Nombre del Administrador esta vacio";
                      validacion=false; 
                  }
                  if (!CheckBoxAdministrador.isSelected() && !CheckboxGerente.isSelected()){
                      msgErr=msgErr+"Selecciona el tipo de puesto del usuario";
                      validacion=false;      
                  }       
                  // Validacion del TextField Contrasena
                  if (TextFieldPassAdministrador.getText().isEmpty()){
                      msgErr=msgErr+"Campo Contrasena del Administrador esta vacio";
                      validacion=false;
                  }
                  // Validacion del TextField Telefono
                  if (TextFieldTelefonoAdministrador.getText().isEmpty()){
                      msgErr=msgErr+"Campo Telefono del Administrador esta vacio";
                      validacion=false;
                  }
                  // Validacion del DateChosserEmpleado
                   if (DateChooserAdministrador.getDate() == null ){
                      msgErr=msgErr+"Campo Telefono del Administrador esta vacio";
                      validacion=false;
                  }
                  // Validacion del ID Empleado
                  if(TextFieldIDAdministrador.getText().isEmpty()){
                      msgErr=msgErr+"Campo Telefono del Administrador esta vacio";
                      validacion=false;
                  }

                   if(validacion){
                     st.setInt(1,Integer.parseInt(TextFieldIDAdministrador.getText()));
                     st.setString(2,TextFieldNombreGerenteSucursal.getText());
                     if (CheckboxGerente.isSelected()) st.setBoolean(3, true);
                     if (CheckBoxAdministrador.isSelected()) st.setBoolean(3, false);
                     st.setString(4,TextFieldPassAdministrador.getText());
                     st.setString(5,TextFieldTelefonoAdministrador.getText());
                     java.util.Date fechaUtil = DateChooserAdministrador.getDate();
                     java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());
                     st.setDate(6, fechaSQL);     
                     
                     st.executeUpdate();
                   }else{
                       JOptionPane.showMessageDialog(null, "Algunos de los datos del Administrador no fueron llenados correctamente");
                       validacion=false; 
                   }
                    
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    validacion=false;
               }      
    }
    
    private void eliminarAministrador(){
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM administradores WHERE idEmpleado=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // ID del registro a eliminar
            ps.setInt(1,Integer.parseInt(TextFieldIDAdministrador.getText()));

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

        PanelPrincipalDatosSucursal = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        SubPanelDatosSucursalMain = new javax.swing.JPanel();
        SubPanelDatosSucursal = new javax.swing.JPanel();
        LabelIDSucursal = new javax.swing.JLabel();
        LabelNombreSucursal = new javax.swing.JLabel();
        LabelDomicilioSucursal = new javax.swing.JLabel();
        LabelColoniaDatosSucursal = new javax.swing.JLabel();
        LabelCPDatosSucursal = new javax.swing.JLabel();
        LabelEstadoDatosSucursal = new javax.swing.JLabel();
        LabelMunicipioDatosSucursal = new javax.swing.JLabel();
        LabelRFCDatosSucursal = new javax.swing.JLabel();
        LabelTelefono1DatosSucursal = new javax.swing.JLabel();
        LabelTelefono2DatosSucursal = new javax.swing.JLabel();
        LabelContribuyenteDatosSucursal = new javax.swing.JLabel();
        LabelRegimenDatosSucursal = new javax.swing.JLabel();
        TextFieldIDSucursal = new javax.swing.JTextField();
        TextFieldNombreSucursal = new javax.swing.JTextField();
        TextFieldDomicilioSucursal = new javax.swing.JTextField();
        TextFieldColoniaSucursal = new javax.swing.JTextField();
        TextFieldCPSucursal = new javax.swing.JTextField();
        TextFieldEstadoSucursal = new javax.swing.JTextField();
        TextFieldMunicipioSucursal = new javax.swing.JTextField();
        TextFieldRFCSucursal = new javax.swing.JTextField();
        TextFieldTelefono1Sucursal = new javax.swing.JTextField();
        TextFieldTelefono2Sucursal = new javax.swing.JTextField();
        TextFieldContribuyenteSucursal = new javax.swing.JTextField();
        TextFieldRegimenSucursal = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        ButtonActualizarDatosSucursal = new javax.swing.JButton();
        ButtonCancelarDatosSucursal = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        SubPanelDatosGerente = new javax.swing.JPanel();
        SubPanelTablaAdministradores = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaAdministradoresSucursal = new javax.swing.JTable();
        SubPanelDatosAdministradores = new javax.swing.JPanel();
        LabelNombreAdministradores = new javax.swing.JLabel();
        LabelTelefonoAdministradores = new javax.swing.JLabel();
        LabelPassAdministradores = new javax.swing.JLabel();
        LabelPuestoAdministradores = new javax.swing.JLabel();
        LabelFechaIngresoAdministradores = new javax.swing.JLabel();
        LabelIDAdministradores = new javax.swing.JLabel();
        CheckboxGerente = new javax.swing.JCheckBox();
        CheckBoxAdministrador = new javax.swing.JCheckBox();
        TextFieldTelefonoAdministrador = new javax.swing.JTextField();
        TextFieldNombreGerenteSucursal = new javax.swing.JTextField();
        TextFieldPassAdministrador = new javax.swing.JTextField();
        TextFieldIDAdministrador = new javax.swing.JTextField();
        DateChooserAdministrador = new com.toedter.calendar.JDateChooser();
        ButtonGuardarAdministrador = new javax.swing.JButton();
        ButtonEditarAdministrador = new javax.swing.JButton();
        ButtonNuevoAdministrador = new javax.swing.JButton();
        ButtonEliminarAdministrador = new javax.swing.JButton();

        PanelPrincipalDatosSucursal.setPreferredSize(new java.awt.Dimension(670, 440));

        SubPanelDatosSucursal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LabelIDSucursal.setText("* ID de la Sucursal:");

        LabelNombreSucursal.setText("* Nombre de la Sucursal:");

        LabelDomicilioSucursal.setText("* Domicilio:");

        LabelColoniaDatosSucursal.setText("* Colonia:");

        LabelCPDatosSucursal.setText("* CP:");

        LabelEstadoDatosSucursal.setText("* Estado:");

        LabelMunicipioDatosSucursal.setText("* Municipio:");

        LabelRFCDatosSucursal.setText("* RFC:");

        LabelTelefono1DatosSucursal.setText("* Telefono 1:");

        LabelTelefono2DatosSucursal.setText("* Telefono 2:");

        LabelContribuyenteDatosSucursal.setText("* Contribuyente:");

        LabelRegimenDatosSucursal.setText("* Regime Fiscal:");

        TextFieldCPSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldCPSucursalKeyTyped(evt);
            }
        });

        TextFieldTelefono1Sucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldTelefono1SucursalKeyTyped(evt);
            }
        });

        TextFieldTelefono2Sucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldTelefono2SucursalKeyTyped(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ButtonActualizarDatosSucursal.setText("Actualizar");
        ButtonActualizarDatosSucursal.addActionListener(this::ButtonActualizarDatosSucursalActionPerformed);

        ButtonCancelarDatosSucursal.setText("Cancelar");
        ButtonCancelarDatosSucursal.addActionListener(this::ButtonCancelarDatosSucursalActionPerformed);

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(ButtonCancelarDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ButtonActualizarDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(ButtonActualizarDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(ButtonCancelarDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SubPanelDatosSucursalLayout = new javax.swing.GroupLayout(SubPanelDatosSucursal);
        SubPanelDatosSucursal.setLayout(SubPanelDatosSucursalLayout);
        SubPanelDatosSucursalLayout.setHorizontalGroup(
            SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LabelDomicilioSucursal)
                            .addComponent(LabelNombreSucursal)
                            .addComponent(LabelIDSucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TextFieldIDSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(TextFieldNombreSucursal)
                            .addComponent(TextFieldDomicilioSucursal)))
                    .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LabelTelefono1DatosSucursal)
                            .addComponent(LabelRFCDatosSucursal)
                            .addComponent(LabelTelefono2DatosSucursal)
                            .addComponent(LabelContribuyenteDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelRegimenDatosSucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TextFieldRFCSucursal)
                            .addComponent(TextFieldTelefono1Sucursal)
                            .addComponent(TextFieldTelefono2Sucursal, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                            .addComponent(TextFieldContribuyenteSucursal)
                            .addComponent(TextFieldRegimenSucursal)))
                    .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                                .addComponent(LabelColoniaDatosSucursal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextFieldColoniaSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(LabelCPDatosSucursal))
                            .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                                .addComponent(LabelEstadoDatosSucursal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextFieldEstadoSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelMunicipioDatosSucursal)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TextFieldMunicipioSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextFieldCPSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        SubPanelDatosSucursalLayout.setVerticalGroup(
            SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelIDSucursal)
                            .addComponent(TextFieldIDSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelNombreSucursal)
                            .addComponent(TextFieldNombreSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelDomicilioSucursal)
                            .addComponent(TextFieldDomicilioSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFieldCPSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelCPDatosSucursal)
                            .addComponent(TextFieldColoniaSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelColoniaDatosSucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFieldMunicipioSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelMunicipioDatosSucursal)
                            .addComponent(TextFieldEstadoSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelEstadoDatosSucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFieldRFCSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelRFCDatosSucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFieldContribuyenteSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelContribuyenteDatosSucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFieldRegimenSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelRegimenDatosSucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelTelefono1DatosSucursal)
                            .addComponent(TextFieldTelefono1Sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFieldTelefono2Sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelTelefono2DatosSucursal)))
                    .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SubPanelDatosSucursalMainLayout = new javax.swing.GroupLayout(SubPanelDatosSucursalMain);
        SubPanelDatosSucursalMain.setLayout(SubPanelDatosSucursalMainLayout);
        SubPanelDatosSucursalMainLayout.setHorizontalGroup(
            SubPanelDatosSucursalMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosSucursalMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SubPanelDatosSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SubPanelDatosSucursalMainLayout.setVerticalGroup(
            SubPanelDatosSucursalMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosSucursalMainLayout.createSequentialGroup()
                .addComponent(SubPanelDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 62, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Sucursal", SubPanelDatosSucursalMain);

        SubPanelDatosGerente.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        SubPanelTablaAdministradores.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TablaAdministradoresSucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Telefono", "Puesto", "Contrasena", "Fecha de Ingreso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaAdministradoresSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaAdministradoresSucursalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaAdministradoresSucursal);
        if (TablaAdministradoresSucursal.getColumnModel().getColumnCount() > 0) {
            TablaAdministradoresSucursal.getColumnModel().getColumn(0).setMinWidth(20);
            TablaAdministradoresSucursal.getColumnModel().getColumn(0).setPreferredWidth(20);
            TablaAdministradoresSucursal.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        javax.swing.GroupLayout SubPanelTablaAdministradoresLayout = new javax.swing.GroupLayout(SubPanelTablaAdministradores);
        SubPanelTablaAdministradores.setLayout(SubPanelTablaAdministradoresLayout);
        SubPanelTablaAdministradoresLayout.setHorizontalGroup(
            SubPanelTablaAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
        SubPanelTablaAdministradoresLayout.setVerticalGroup(
            SubPanelTablaAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelTablaAdministradoresLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        SubPanelDatosAdministradores.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LabelNombreAdministradores.setText("* Nombre:");

        LabelTelefonoAdministradores.setText("* Telefono:");

        LabelPassAdministradores.setText("* Contrasena:");

        LabelPuestoAdministradores.setText("* Puesto:");

        LabelFechaIngresoAdministradores.setText("* Fecha de Ingreso:");

        LabelIDAdministradores.setText("* ID");

        CheckboxGerente.setText("Gerente");

        CheckBoxAdministrador.setText("Administrador");
        CheckBoxAdministrador.setToolTipText("");

        TextFieldTelefonoAdministrador.addActionListener(this::TextFieldTelefonoAdministradorActionPerformed);
        TextFieldTelefonoAdministrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldTelefonoAdministradorKeyTyped(evt);
            }
        });

        TextFieldIDAdministrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldIDAdministradorKeyTyped(evt);
            }
        });

        ButtonGuardarAdministrador.setText("Guardar");
        ButtonGuardarAdministrador.addActionListener(this::ButtonGuardarAdministradorActionPerformed);

        ButtonEditarAdministrador.setText("Editar");
        ButtonEditarAdministrador.addActionListener(this::ButtonEditarAdministradorActionPerformed);

        ButtonNuevoAdministrador.setText("<html>Nuevo<br>Administrador</html>");

        ButtonEliminarAdministrador.setText("Eliminar");
        ButtonEliminarAdministrador.addActionListener(this::ButtonEliminarAdministradorActionPerformed);

        javax.swing.GroupLayout SubPanelDatosAdministradoresLayout = new javax.swing.GroupLayout(SubPanelDatosAdministradores);
        SubPanelDatosAdministradores.setLayout(SubPanelDatosAdministradoresLayout);
        SubPanelDatosAdministradoresLayout.setHorizontalGroup(
            SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SubPanelDatosAdministradoresLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(DateChooserAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(SubPanelDatosAdministradoresLayout.createSequentialGroup()
                .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SubPanelDatosAdministradoresLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SubPanelDatosAdministradoresLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(CheckboxGerente)
                                .addGap(18, 18, 18)
                                .addComponent(CheckBoxAdministrador)
                                .addGap(16, 16, 16))
                            .addGroup(SubPanelDatosAdministradoresLayout.createSequentialGroup()
                                .addComponent(LabelPassAdministradores)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TextFieldPassAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SubPanelDatosAdministradoresLayout.createSequentialGroup()
                                .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(SubPanelDatosAdministradoresLayout.createSequentialGroup()
                                        .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(LabelNombreAdministradores)
                                            .addComponent(LabelTelefonoAdministradores))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TextFieldTelefonoAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TextFieldNombreGerenteSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(SubPanelDatosAdministradoresLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(LabelPuestoAdministradores)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(SubPanelDatosAdministradoresLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SubPanelDatosAdministradoresLayout.createSequentialGroup()
                                .addComponent(LabelFechaIngresoAdministradores)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(SubPanelDatosAdministradoresLayout.createSequentialGroup()
                                .addComponent(LabelIDAdministradores)
                                .addGap(31, 31, 31)
                                .addComponent(TextFieldIDAdministrador))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SubPanelDatosAdministradoresLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ButtonNuevoAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonGuardarAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ButtonEliminarAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonEditarAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        SubPanelDatosAdministradoresLayout.setVerticalGroup(
            SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosAdministradoresLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelNombreAdministradores)
                    .addComponent(TextFieldNombreGerenteSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTelefonoAdministradores)
                    .addComponent(TextFieldTelefonoAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelPuestoAdministradores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckboxGerente)
                    .addComponent(CheckBoxAdministrador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelPassAdministradores)
                    .addComponent(TextFieldPassAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelFechaIngresoAdministradores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DateChooserAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelIDAdministradores)
                    .addComponent(TextFieldIDAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonNuevoAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEditarAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SubPanelDatosAdministradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonGuardarAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEliminarAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SubPanelDatosGerenteLayout = new javax.swing.GroupLayout(SubPanelDatosGerente);
        SubPanelDatosGerente.setLayout(SubPanelDatosGerenteLayout);
        SubPanelDatosGerenteLayout.setHorizontalGroup(
            SubPanelDatosGerenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosGerenteLayout.createSequentialGroup()
                .addComponent(SubPanelTablaAdministradores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SubPanelDatosAdministradores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SubPanelDatosGerenteLayout.setVerticalGroup(
            SubPanelDatosGerenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosGerenteLayout.createSequentialGroup()
                .addGroup(SubPanelDatosGerenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(SubPanelTablaAdministradores, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SubPanelDatosGerenteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SubPanelDatosAdministradores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos del Gerente", SubPanelDatosGerente);

        javax.swing.GroupLayout PanelPrincipalDatosSucursalLayout = new javax.swing.GroupLayout(PanelPrincipalDatosSucursal);
        PanelPrincipalDatosSucursal.setLayout(PanelPrincipalDatosSucursalLayout);
        PanelPrincipalDatosSucursalLayout.setHorizontalGroup(
            PanelPrincipalDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalDatosSucursalLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelPrincipalDatosSucursalLayout.setVerticalGroup(
            PanelPrincipalDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalDatosSucursalLayout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelPrincipalDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelPrincipalDatosSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TextFieldCPSucursalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldCPSucursalKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldCPSucursalKeyTyped

    private void TextFieldTelefono1SucursalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldTelefono1SucursalKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldTelefono1SucursalKeyTyped

    private void TextFieldTelefono2SucursalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldTelefono2SucursalKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldTelefono2SucursalKeyTyped

    private void ButtonActualizarDatosSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonActualizarDatosSucursalActionPerformed
        // TODO add your handling code here:
        actualizarDatosSucursal();
    }//GEN-LAST:event_ButtonActualizarDatosSucursalActionPerformed

    private void ButtonCancelarDatosSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelarDatosSucursalActionPerformed
        // TODO add your handling code here:
        menuPrincipal.mostrarPanel(MenuPrincipal.PanelDestino.PANEL_DATOS_SUC.getCardName());
    }//GEN-LAST:event_ButtonCancelarDatosSucursalActionPerformed

    private void TextFieldTelefonoAdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldTelefonoAdministradorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldTelefonoAdministradorActionPerformed

    private void TextFieldTelefonoAdministradorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldTelefonoAdministradorKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldTelefonoAdministradorKeyTyped

    private void TextFieldIDAdministradorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldIDAdministradorKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldIDAdministradorKeyTyped

    private void TablaAdministradoresSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaAdministradoresSucursalMouseClicked
        // TODO add your handling code here:
        int fila = TablaAdministradoresSucursal.getSelectedRow();
        
        /*ButtonCancelarEmpleados.setEnabled(false);
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
        TextFieldIDEmpleado.setEnabled(false);
         */
        
        if (fila >= 0) {
            // Extraer valores de cada columna Nombre, Domicilio, Telefono, Fecha de Ingreso, Activo, Tipo de Empleado
            int idAdministrador = Integer.parseInt(TablaAdministradoresSucursal.getValueAt(fila, 0).toString());
            String nombreTabla = TablaAdministradoresSucursal.getValueAt(fila, 1).toString();
            String telefonoTabla = TablaAdministradoresSucursal.getValueAt(fila, 2).toString();
            boolean statusTabla = (boolean)TablaAdministradoresSucursal.getValueAt(fila,3);
            
             if (statusTabla){
                CheckboxGerente.setSelected(true);
                CheckBoxAdministrador.setSelected(false);
            }else{
                CheckboxGerente.setSelected(false);
                CheckBoxAdministrador.setSelected(true);         
            }
                    
            String passTabla = TablaAdministradoresSucursal.getValueAt(fila, 4).toString();
            String fechaIngresoTabla = TablaAdministradoresSucursal.getValueAt(fila, 5).toString();
            // Definir el formato esperado
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            try {
                  Date fecha = formato.parse(fechaIngresoTabla);
                  DateChooserAdministrador.setDate(fecha);
            } catch (ParseException e) {
                  System.out.println("Error al convertir la fecha: " + e.getMessage());
            }
            
           
            TextFieldNombreGerenteSucursal.setText(nombreTabla);
            TextFieldTelefonoAdministrador.setText(telefonoTabla);
            TextFieldPassAdministrador.setText(passTabla);
            TextFieldIDAdministrador.setText(String.valueOf(idAdministrador));
        }
    }//GEN-LAST:event_TablaAdministradoresSucursalMouseClicked

    private void ButtonGuardarAdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonGuardarAdministradorActionPerformed
        // TODO add your handling code here:
        capturarAdministrador();
        listarAdministrador();
    }//GEN-LAST:event_ButtonGuardarAdministradorActionPerformed

    private void ButtonEliminarAdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEliminarAdministradorActionPerformed
        // TODO add your handling code here:
        eliminarAministrador();
        listarAdministrador();
    }//GEN-LAST:event_ButtonEliminarAdministradorActionPerformed

    private void ButtonEditarAdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarAdministradorActionPerformed
        // TODO add your handling code here:
        actualizarAdministrador();
        listarAdministrador();
    }//GEN-LAST:event_ButtonEditarAdministradorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonActualizarDatosSucursal;
    private javax.swing.JButton ButtonCancelarDatosSucursal;
    private javax.swing.JButton ButtonEditarAdministrador;
    private javax.swing.JButton ButtonEliminarAdministrador;
    private javax.swing.JButton ButtonGuardarAdministrador;
    private javax.swing.JButton ButtonNuevoAdministrador;
    private javax.swing.JCheckBox CheckBoxAdministrador;
    private javax.swing.JCheckBox CheckboxGerente;
    private com.toedter.calendar.JDateChooser DateChooserAdministrador;
    private javax.swing.JLabel LabelCPDatosSucursal;
    private javax.swing.JLabel LabelColoniaDatosSucursal;
    private javax.swing.JLabel LabelContribuyenteDatosSucursal;
    private javax.swing.JLabel LabelDomicilioSucursal;
    private javax.swing.JLabel LabelEstadoDatosSucursal;
    private javax.swing.JLabel LabelFechaIngresoAdministradores;
    private javax.swing.JLabel LabelIDAdministradores;
    private javax.swing.JLabel LabelIDSucursal;
    private javax.swing.JLabel LabelMunicipioDatosSucursal;
    private javax.swing.JLabel LabelNombreAdministradores;
    private javax.swing.JLabel LabelNombreSucursal;
    private javax.swing.JLabel LabelPassAdministradores;
    private javax.swing.JLabel LabelPuestoAdministradores;
    private javax.swing.JLabel LabelRFCDatosSucursal;
    private javax.swing.JLabel LabelRegimenDatosSucursal;
    private javax.swing.JLabel LabelTelefono1DatosSucursal;
    private javax.swing.JLabel LabelTelefono2DatosSucursal;
    private javax.swing.JLabel LabelTelefonoAdministradores;
    private javax.swing.JPanel PanelPrincipalDatosSucursal;
    private javax.swing.JPanel SubPanelDatosAdministradores;
    private javax.swing.JPanel SubPanelDatosGerente;
    private javax.swing.JPanel SubPanelDatosSucursal;
    private javax.swing.JPanel SubPanelDatosSucursalMain;
    private javax.swing.JPanel SubPanelTablaAdministradores;
    private javax.swing.JTable TablaAdministradoresSucursal;
    private javax.swing.JTextField TextFieldCPSucursal;
    private javax.swing.JTextField TextFieldColoniaSucursal;
    private javax.swing.JTextField TextFieldContribuyenteSucursal;
    private javax.swing.JTextField TextFieldDomicilioSucursal;
    private javax.swing.JTextField TextFieldEstadoSucursal;
    private javax.swing.JTextField TextFieldIDAdministrador;
    private javax.swing.JTextField TextFieldIDSucursal;
    private javax.swing.JTextField TextFieldMunicipioSucursal;
    private javax.swing.JTextField TextFieldNombreGerenteSucursal;
    private javax.swing.JTextField TextFieldNombreSucursal;
    private javax.swing.JTextField TextFieldPassAdministrador;
    private javax.swing.JTextField TextFieldRFCSucursal;
    private javax.swing.JTextField TextFieldRegimenSucursal;
    private javax.swing.JTextField TextFieldTelefono1Sucursal;
    private javax.swing.JTextField TextFieldTelefono2Sucursal;
    private javax.swing.JTextField TextFieldTelefonoAdministrador;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
