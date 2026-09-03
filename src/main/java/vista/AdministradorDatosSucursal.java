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
    
    public AdministradorDatosSucursal(MenuPrincipal menuPrincipal) {
        
        this.menuPrincipal = menuPrincipal;
        initComponents();
        setPreferredSize(new Dimension(650, 440));
        //this.setResizable(false);
        menuPrincipal.revalidate();
        menuPrincipal.repaint();  
        cargarDatosSuc();
        
        TextFieldIDSucursal.setEnabled(false);
        
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
        ButtonActualizarDatosSucursal = new javax.swing.JButton();
        ButtonCancelarDatosSucursal = new javax.swing.JButton();
        SubPanelDatosGerente = new javax.swing.JPanel();
        TextFieldNombreGerenteSucursal = new javax.swing.JTextField();

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

        ButtonActualizarDatosSucursal.setText("Actualizar");
        ButtonActualizarDatosSucursal.addActionListener(this::ButtonActualizarDatosSucursalActionPerformed);

        ButtonCancelarDatosSucursal.setText("Cancelar");
        ButtonCancelarDatosSucursal.addActionListener(this::ButtonCancelarDatosSucursalActionPerformed);

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
                            .addComponent(LabelColoniaDatosSucursal)
                            .addComponent(LabelEstadoDatosSucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                                .addComponent(TextFieldEstadoSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(LabelMunicipioDatosSucursal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextFieldMunicipioSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                                .addComponent(TextFieldColoniaSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(LabelCPDatosSucursal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextFieldCPSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonActualizarDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonCancelarDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        SubPanelDatosSucursalLayout.setVerticalGroup(
            SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(ButtonActualizarDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SubPanelDatosSucursalLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelIDSucursal)
                            .addComponent(TextFieldIDSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelNombreSucursal)
                            .addComponent(TextFieldNombreSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFieldDomicilioSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelDomicilioSucursal))))
                .addGap(18, 18, 18)
                .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LabelColoniaDatosSucursal)
                    .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TextFieldColoniaSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LabelCPDatosSucursal)
                        .addComponent(TextFieldCPSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SubPanelDatosSucursalLayout.createSequentialGroup()
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFieldEstadoSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelEstadoDatosSucursal)
                            .addComponent(TextFieldMunicipioSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelMunicipioDatosSucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFieldRFCSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelRFCDatosSucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelContribuyenteDatosSucursal)
                            .addComponent(TextFieldContribuyenteSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(ButtonCancelarDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelRegimenDatosSucursal)
                    .addComponent(TextFieldRegimenSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextFieldTelefono1Sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTelefono1DatosSucursal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubPanelDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextFieldTelefono2Sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTelefono2DatosSucursal))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SubPanelDatosSucursalMainLayout = new javax.swing.GroupLayout(SubPanelDatosSucursalMain);
        SubPanelDatosSucursalMain.setLayout(SubPanelDatosSucursalMainLayout);
        SubPanelDatosSucursalMainLayout.setHorizontalGroup(
            SubPanelDatosSucursalMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosSucursalMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SubPanelDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        SubPanelDatosSucursalMainLayout.setVerticalGroup(
            SubPanelDatosSucursalMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosSucursalMainLayout.createSequentialGroup()
                .addComponent(SubPanelDatosSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Datos Sucursal", SubPanelDatosSucursalMain);

        javax.swing.GroupLayout SubPanelDatosGerenteLayout = new javax.swing.GroupLayout(SubPanelDatosGerente);
        SubPanelDatosGerente.setLayout(SubPanelDatosGerenteLayout);
        SubPanelDatosGerenteLayout.setHorizontalGroup(
            SubPanelDatosGerenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosGerenteLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(TextFieldNombreGerenteSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        SubPanelDatosGerenteLayout.setVerticalGroup(
            SubPanelDatosGerenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelDatosGerenteLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(TextFieldNombreGerenteSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(328, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos del Gerente", SubPanelDatosGerente);

        javax.swing.GroupLayout PanelPrincipalDatosSucursalLayout = new javax.swing.GroupLayout(PanelPrincipalDatosSucursal);
        PanelPrincipalDatosSucursal.setLayout(PanelPrincipalDatosSucursalLayout);
        PanelPrincipalDatosSucursalLayout.setHorizontalGroup(
            PanelPrincipalDatosSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalDatosSucursalLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(PanelPrincipalDatosSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipalDatosSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonActualizarDatosSucursal;
    private javax.swing.JButton ButtonCancelarDatosSucursal;
    private javax.swing.JLabel LabelCPDatosSucursal;
    private javax.swing.JLabel LabelColoniaDatosSucursal;
    private javax.swing.JLabel LabelContribuyenteDatosSucursal;
    private javax.swing.JLabel LabelDomicilioSucursal;
    private javax.swing.JLabel LabelEstadoDatosSucursal;
    private javax.swing.JLabel LabelIDSucursal;
    private javax.swing.JLabel LabelMunicipioDatosSucursal;
    private javax.swing.JLabel LabelNombreSucursal;
    private javax.swing.JLabel LabelRFCDatosSucursal;
    private javax.swing.JLabel LabelRegimenDatosSucursal;
    private javax.swing.JLabel LabelTelefono1DatosSucursal;
    private javax.swing.JLabel LabelTelefono2DatosSucursal;
    private javax.swing.JPanel PanelPrincipalDatosSucursal;
    private javax.swing.JPanel SubPanelDatosGerente;
    private javax.swing.JPanel SubPanelDatosSucursal;
    private javax.swing.JPanel SubPanelDatosSucursalMain;
    private javax.swing.JTextField TextFieldCPSucursal;
    private javax.swing.JTextField TextFieldColoniaSucursal;
    private javax.swing.JTextField TextFieldContribuyenteSucursal;
    private javax.swing.JTextField TextFieldDomicilioSucursal;
    private javax.swing.JTextField TextFieldEstadoSucursal;
    private javax.swing.JTextField TextFieldIDSucursal;
    private javax.swing.JTextField TextFieldMunicipioSucursal;
    private javax.swing.JTextField TextFieldNombreGerenteSucursal;
    private javax.swing.JTextField TextFieldNombreSucursal;
    private javax.swing.JTextField TextFieldRFCSucursal;
    private javax.swing.JTextField TextFieldRegimenSucursal;
    private javax.swing.JTextField TextFieldTelefono1Sucursal;
    private javax.swing.JTextField TextFieldTelefono2Sucursal;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
