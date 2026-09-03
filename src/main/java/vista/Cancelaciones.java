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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author davidalejandroruizhernandez
 */
public class Cancelaciones extends javax.swing.JPanel {

    private MenuPrincipal menuPrincipal;
    
    /**
     * Creates new form Cancelaciones
     */
    public Cancelaciones(MenuPrincipal menuPrincipal) {
        initComponents();
         this.menuPrincipal = menuPrincipal;
        setPreferredSize(new Dimension(380, 280));
        //this.menuPrincipal.setResizable(false);
        menuPrincipal.revalidate();
        menuPrincipal.repaint();
    }
    
    public void getDate(){
       /* LocalDate fechaActual = LocalDate.now();
        //Dar el formato yyyy-mm-dd
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        String fecha = fechaActual.format(formato);
        System.out.println(fecha);
        */
    }
    
    public void cancelaPedido(){
        
         // Primero buscar el id_control que este activo
         int id_control=0;
         int id_pedido=0;
         boolean validacion=true;
         try (Connection conn = DatabaseConnection.getConnection()){
            String sql_id= """
                                   SELECT id_control
                                   FROM notas_venta
                                   WHERE activa = 1
                                   LIMIT 1
                                   """;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql_id);
            if (rs.next()){
               id_control = rs.getInt("id_control");
               System.out.println("id_control activo = " + id_control);
            }
         } catch (SQLException ex) {
            System.getLogger(AdministradoringredientesExtra.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
         
        System.out.println("id_control activo = " + id_control);
        Integer numeroNota = Integer.parseInt(TextFieldNumeroNota.getText());
        LocalDate fechaActual = LocalDate.now();
        
         try (Connection conn2 = DatabaseConnection.getConnection()) {        
                String sql = """
                           UPDATE pedidos
                           SET estado = 'CANCELADA'
                           WHERE numero_nota = ?
                           AND fecha_pedido = CURDATE()
                           AND id_control = ?
                            """;
                PreparedStatement ps = conn2.prepareStatement(sql);
                ps.setInt(1, numeroNota);
                ps.setInt(2,id_control);

                int filasActualizadas = ps.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("El pedido fue cancelado correctamente.");
                } else {
                    System.out.println("No se encontró un pedido activo con ese número para la fecha de hoy.");
                    JOptionPane.showMessageDialog(null,"No se encontró un pedido activo con ese número para la fecha de hoy.");
                    validacion=false;
                }
         } catch (SQLException e) {
                System.out.println("Error al cancelar el pedido: " + e.getMessage());
         }
         
         if (validacion){
         // Buscar id_pedidoen base al numero_nota y id_control en la tabla pedidos
         try (Connection conn3 = DatabaseConnection.getConnection()){
            String sql= """
                                   SELECT id_pedido
                                   FROM pedidos
                                   WHERE numero_nota = ?
                                   AND id_control = ?;
                                   """;
            try (PreparedStatement ps = conn3.prepareStatement(sql)) {
                 ps.setInt(1, numeroNota);
                 ps.setInt(2,id_control);
                 try(ResultSet rs = ps.executeQuery()){
                     if (rs.next()){
                        id_pedido = rs.getInt("id_pedido");
                        System.out.println("id_pedido = " + id_pedido);
                    } else {
                        System.out.println("No se encontró el pedido.");
                        
                     }
                 }
            }
                     
         } catch (SQLException ex) {
            System.getLogger(AdministradoringredientesExtra.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
         try (Connection conn2 = DatabaseConnection.getConnection()) {        
                String sql = """
                           UPDATE pedido_detalle
                           SET estado = 'CANCELADA'
                           WHERE id_pedido = ?
                           AND id_control = ?
                            """;
                PreparedStatement ps = conn2.prepareStatement(sql);
                ps.setInt(1, id_pedido);
                ps.setInt(2,id_control);

                int filasActualizadas = ps.executeUpdate();

                if (filasActualizadas > 0) {
                    JOptionPane.showMessageDialog(null,"El pedido fue cancelado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null,"No se encontró la nota ingresada.");
                }
         } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cancelar el pedido: " + e.getMessage());
         }
         
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

        PanelMainCancelaciones = new javax.swing.JPanel();
        PanelTituloCancelaciones = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        PanelDatosNotaCancelaciones = new javax.swing.JPanel();
        LabelNotaCancelaciones = new javax.swing.JLabel();
        TextFieldNumeroNota = new javax.swing.JTextField();
        ButtonAceptarCancelaciones = new javax.swing.JButton();
        ButtonCancelarCancelaciones = new javax.swing.JButton();

        PanelTituloCancelaciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel2.setText("Captura nota a Cancelar");

        javax.swing.GroupLayout PanelTituloCancelacionesLayout = new javax.swing.GroupLayout(PanelTituloCancelaciones);
        PanelTituloCancelaciones.setLayout(PanelTituloCancelacionesLayout);
        PanelTituloCancelacionesLayout.setHorizontalGroup(
            PanelTituloCancelacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTituloCancelacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(81, Short.MAX_VALUE))
        );
        PanelTituloCancelacionesLayout.setVerticalGroup(
            PanelTituloCancelacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTituloCancelacionesLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        PanelDatosNotaCancelaciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LabelNotaCancelaciones.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        LabelNotaCancelaciones.setText("* Nota:");

        TextFieldNumeroNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldNumeroNotaKeyTyped(evt);
            }
        });

        ButtonAceptarCancelaciones.setText("Aceptar");
        ButtonAceptarCancelaciones.addActionListener(this::ButtonAceptarCancelacionesActionPerformed);

        ButtonCancelarCancelaciones.setText("Cancelar");
        ButtonCancelarCancelaciones.addActionListener(this::ButtonCancelarCancelacionesActionPerformed);

        javax.swing.GroupLayout PanelDatosNotaCancelacionesLayout = new javax.swing.GroupLayout(PanelDatosNotaCancelaciones);
        PanelDatosNotaCancelaciones.setLayout(PanelDatosNotaCancelacionesLayout);
        PanelDatosNotaCancelacionesLayout.setHorizontalGroup(
            PanelDatosNotaCancelacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosNotaCancelacionesLayout.createSequentialGroup()
                .addGroup(PanelDatosNotaCancelacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosNotaCancelacionesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LabelNotaCancelaciones)
                        .addGap(18, 18, 18)
                        .addComponent(TextFieldNumeroNota, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelDatosNotaCancelacionesLayout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(ButtonAceptarCancelaciones)
                        .addGap(18, 18, 18)
                        .addComponent(ButtonCancelarCancelaciones)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDatosNotaCancelacionesLayout.setVerticalGroup(
            PanelDatosNotaCancelacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosNotaCancelacionesLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(PanelDatosNotaCancelacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelNotaCancelaciones)
                    .addComponent(TextFieldNumeroNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelDatosNotaCancelacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonAceptarCancelaciones)
                    .addComponent(ButtonCancelarCancelaciones))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelMainCancelacionesLayout = new javax.swing.GroupLayout(PanelMainCancelaciones);
        PanelMainCancelaciones.setLayout(PanelMainCancelacionesLayout);
        PanelMainCancelacionesLayout.setHorizontalGroup(
            PanelMainCancelacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMainCancelacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelTituloCancelaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMainCancelacionesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PanelDatosNotaCancelaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelMainCancelacionesLayout.setVerticalGroup(
            PanelMainCancelacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMainCancelacionesLayout.createSequentialGroup()
                .addComponent(PanelTituloCancelaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelDatosNotaCancelaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelMainCancelaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelMainCancelaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TextFieldNumeroNotaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldNumeroNotaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume(); // evita que se escriba el carácter
        }
    }//GEN-LAST:event_TextFieldNumeroNotaKeyTyped

    private void ButtonAceptarCancelacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAceptarCancelacionesActionPerformed
        // TODO add your handling code here:
        cancelaPedido();
    }//GEN-LAST:event_ButtonAceptarCancelacionesActionPerformed

    private void ButtonCancelarCancelacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelarCancelacionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonCancelarCancelacionesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonAceptarCancelaciones;
    private javax.swing.JButton ButtonCancelarCancelaciones;
    private javax.swing.JLabel LabelNotaCancelaciones;
    private javax.swing.JPanel PanelDatosNotaCancelaciones;
    private javax.swing.JPanel PanelMainCancelaciones;
    private javax.swing.JPanel PanelTituloCancelaciones;
    private javax.swing.JTextField TextFieldNumeroNota;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
