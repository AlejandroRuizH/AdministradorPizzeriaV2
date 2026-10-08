/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorpizzeriav2;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import config.AppConfig;
import vista.MenuPrincipal;

public class Application {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                // 1. Configuración de la aplicación
                AppConfig.initialize();

                // 2. Configuración visual
                configureLookAndFeel();

                // 3. Inicio de la interfaz
                startApplication();

            } catch (Exception e) {

                handleStartupError(e);
            }
        });
    }

    private static void configureLookAndFeel() {

        FlatLightLaf.setup();

        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        UIManager.put("TextComponent.arc", 8);
    }

    private static void startApplication() {

        MenuPrincipal menuPrincipal = new MenuPrincipal();

        menuPrincipal.setLocationRelativeTo(null);
        menuPrincipal.setVisible(true);
    }

    private static void handleStartupError(Exception e) {

        e.printStackTrace();

        JOptionPane.showMessageDialog(
                null,
                "No fue posible iniciar la aplicación.\n\n"
                + "Detalle: " + e.getMessage(),
                "Error de inicio",
                JOptionPane.ERROR_MESSAGE
        );

        System.exit(1);
    }
}