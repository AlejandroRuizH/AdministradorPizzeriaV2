/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorpizzeriav2;

import java.util.Properties;
import java.io.*;
import java.nio.file.*;

/**
 *
 * @author davidalejandroruizhernandez
 */
public class DBConfiguration {
    
    private static final String DIRECTORIO = "config";
    private static final String ARCHIVO = DIRECTORIO + File.separator + "config.properties";
    private static final Properties propiedades = new Properties();
    
    static{
        inicializar();
    }
    
    private static void inicializar() {
    
        try {
            // Crear el directorio si no existe
            Path directorio = Paths.get(DIRECTORIO);
            System.out.println(DIRECTORIO);
            System.out.println(ARCHIVO);
            
            if(!Files.exists(directorio)){
                Files.createDirectories(directorio);
                System.out.println("Se creo el directorio config");
            }
            
            File archivo = new File(ARCHIVO);
            
            // Si no existe crear uno por defecto
            if(!archivo.exists()) {
                crearArchivoPorDefecto(archivo);
                System.out.println("Se creo el archivo");
            }
            
            //Cargar Propiedades
            try (FileInputStream fis = new FileInputStream(archivo)){
                propiedades.load(fis);
            }
            
        } catch (IOException e) {
            throw new RuntimeException("No fue posible cargar la Configuracion de la BD", e);
        }
    
    }
    
  private static void crearArchivoPorDefecto(File archivo) throws IOException {
      
      Properties defaults = new Properties();
      
      defaults.setProperty("db.host", "localhost");
      defaults.setProperty("db.port", "3306");
      defaults.setProperty("db.name", "pizzeria_pos");
      defaults.setProperty("db.user", "root");
      defaults.setProperty("db.password", "");
      try (FileOutputStream fis = new FileOutputStream(archivo)) {
        defaults.store(fis, "Configuracion de la Base de Datos");
    }
  }      
    private static void guardarConfiguracion() throws IOException{
        try(FileOutputStream fis = new FileOutputStream(ARCHIVO)){
            propiedades.store(fis,"Configuracion de la Base de Datos");
        }
    }
    
    public static void actualizarConfiguracion( String host, String puerto, String baseDatos, String usuario, String password ) throws IOException{
        propiedades.setProperty("db.host", host);
        propiedades.setProperty("db.port", puerto);
        propiedades.setProperty("db.name", baseDatos);
        propiedades.setProperty("db.user", usuario);
        propiedades.setProperty("db.password", password);
        
        guardarConfiguracion();
    }
    
      public static String getHost() {
        return propiedades.getProperty("db.host");
      }

      public static int getPuerto() {
        return Integer.parseInt(propiedades.getProperty("db.port"));
      }

      public static String getBaseDatos() {
         return propiedades.getProperty("db.name");
      }

      public static String getUsuario() {
        return propiedades.getProperty("db.user");
      }

      public static String getPassword() {
        return propiedades.getProperty("db.password");
      }

 }
     

