/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author davidalejandroruizhernandez
 */
public class MenuPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuPrincipal.class.getName());
    private LoginAdministrador loginAdministrador;
    private LoginGerente loginGerente;
    private CardLayout cardLayout;
    private String panelActual = "";

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        
       
       //FlatLightLaf.setup();
       FlatDarkLaf.setup();
       SwingUtilities.invokeLater(() -> {
            initComponents();
            loginAdministrador = new LoginAdministrador(this,"");
            loginGerente = new LoginGerente(this,"");
            cardLayout = new CardLayout();
            PanelPrincipal.removeAll();
            PanelPrincipal.setLayout(cardLayout);
            PanelPrincipal.add(new MainMenu(this), PanelDestino.MAIN_MENU.getCardName());
            PanelPrincipal.add(new AdministradorClientes(this), PanelDestino.ADMIN_CLIENTES.getCardName());
            PanelPrincipal.add(new AdministradorEmpleados(this), PanelDestino.ADMIN_EMPLEADOS.getCardName());
            PanelPrincipal.add(new AdministradorPizzas(this), PanelDestino.ADMIN_PIZZAS.getCardName());
            PanelPrincipal.add(new AdministradorProductos(this), PanelDestino.ADMIN_PRODUCTOS.getCardName());
            PanelPrincipal.add(new AdministradorPromociones(this), PanelDestino.ADMIN_PROMOCIONES.getCardName());
            PanelPrincipal.add(new AdministradoringredientesExtra(this), PanelDestino.ADMIN_INGREDIENTES.getCardName());
            PanelPrincipal.add(new ReimpresionTickets(this), PanelDestino.PANEL_CANCELACIONES.getCardName());
            PanelPrincipal.add(new CorteFinal(this), PanelDestino.CORTE_FINAL.getCardName());
            PanelPrincipal.add(new CorteParcial(this), PanelDestino.CORTE_PARCIAL.getCardName());
            PanelPrincipal.add(loginAdministrador, PanelDestino.LOGIN_ADMIN.getCardName());
            PanelPrincipal.add(loginGerente, PanelDestino.LOGIN_GERENTE.getCardName());
            PanelPrincipal.add(new PanelConexionBD(this), PanelDestino.PANEL_CONN_DB.getCardName());
            PanelPrincipal.add(new PanelDatosSucursal(this), PanelDestino.PANEL_DATOS_SUC.getCardName());
            PanelPrincipal.add(new ReimpresionTickets(this), PanelDestino.REIMPRESION.getCardName());
            PanelPrincipal.add(new ReimpresionTickets(this), PanelDestino.REPORTE_VENTAS.getCardName());
            setPreferredSize(new Dimension(500, 550));
            cardLayout.show(PanelPrincipal,PanelDestino.MAIN_MENU.getCardName()); 
            PanelPrincipal.revalidate();
            PanelPrincipal.repaint();
            setLocationRelativeTo(null);
            //mostrarPanel(PanelDestino.MAIN_MENU.getCardName());
            pack();
            
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            
            addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                

                if ("LOGIN_ADMIN".equals(panelActual)) {
                    mostrarPanel("MENU");
                } else if ("LOGIN_GERENTE".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("ADMIN_CLIENTES".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("ADMIN_EMPLEADOS".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("ADMIN_PRODUCTOS".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("ADMIN_PROMOCIONES".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("ADMIN_INGREDIENTES".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("CANCELACIONES".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("CORTE_FINAL".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("CORTE_PARCIAL".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("PANEL_CONN_DB".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("PANEL_DATOS_SUC".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("REIMPRESION".equals(panelActual)){
                    mostrarPanel("MENU");
                } else if ("REPORTE_VENTAS".equals(panelActual)){
                    mostrarPanel("MENU");
                } else {
                    dispose(); // o System.exit(0)
                }
            }
             });
            

       
        });      
    }
    // empty comment
    
    public enum PanelDestino{
        MAIN_MENU("MENU"),
        ADMIN_CLIENTES("ADMIN_CLIENTES"),
        ADMIN_EMPLEADOS("ADMIN_EMPLEADOS"),
        ADMIN_PIZZAS("ADMIN_PIZZAS"),
        ADMIN_PRODUCTOS("ADMIN_PRODUCTOS"),
        ADMIN_PROMOCIONES("ADMIN_PROMOCIONES"),
        ADMIN_INGREDIENTES("ADMIN_INGREDIENTES"),
        CORTE_FINAL("CORTE_FINAL"),
        CORTE_PARCIAL("CORTE_PARCIAL"),
        LOGIN_ADMIN("LOGIN_ADMIN"),
        LOGIN_GERENTE("LOGIN_GERENTE"),
        PANEL_CONN_DB("PANEL_CONN_DB"),
        PANEL_DATOS_SUC("PANEL_DATOS_SUC"),
        PANEL_CANCELACIONES("CANCELACIONES"),
        REIMPRESION("REIMPRESION"),
        REPORTE_VENTAS("REPORTE_VENTAS");
        
        
        private final String cardName;
        
        PanelDestino(String cardName){
           this.cardName = cardName;
        }
        
        public String getCardName(){
            return cardName;
        }
        
    }
    /*
    
    */
    public void mostrarPanel(String nombre){
        CardLayout c1 =(CardLayout) PanelPrincipal.getLayout();
        c1.show(PanelPrincipal, nombre);
        panelActual = nombre;
        
        Component panelVisible = null;
        
        for (Component c : PanelPrincipal.getComponents()) {
          if (c.isVisible()) {
            panelVisible = c;
            break;
          }
        }

        if (panelVisible != null) {
            setSize(panelVisible.getPreferredSize());
            setLocationRelativeTo(null);
        }
        
    }
    
    public void mostrarLogin(String panelDestino){
        
        loginAdministrador.setPanelDestino(panelDestino);
        mostrarPanel(PanelDestino.LOGIN_ADMIN.getCardName());
    
    }
    
    public void mostrarLoginGerente(String panelDestino){
        
        loginGerente.setPanelDestino(panelDestino);
        mostrarPanel(PanelDestino.LOGIN_GERENTE.getCardName());
    
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPrincipal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout PanelPrincipalLayout = new javax.swing.GroupLayout(PanelPrincipal);
        PanelPrincipal.setLayout(PanelPrincipalLayout);
        PanelPrincipalLayout.setHorizontalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        PanelPrincipalLayout.setVerticalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPrincipal;
    // End of variables declaration//GEN-END:variables
}
