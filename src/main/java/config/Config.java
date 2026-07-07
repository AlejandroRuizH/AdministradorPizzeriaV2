/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author davidalejandroruizhernandez
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    
    private static final Properties properties = new Properties();
    
    
    static {
             
            try {
                 Path ruta = Paths.get("config", "config.properties");

                try (FileInputStream input = new FileInputStream(ruta.toFile())) {
                         properties.load(input);
                }

            } catch (IOException e) {
                throw new RuntimeException("No se pudo cargar config/config.properties", e);
            }
             
            
            /*try(InputStream input = Config.class.getClassLoader().getResourceAsStream("src/main/resources/config.properties")){
                if (input == null){
                    throw new RuntimeException("No se encontro el arhivo config.properties");
                }
                
                properties.load(input);
                
             } catch (IOException e) {
               throw new RuntimeException("Error cargando config.properties", e);
             }
              */  
    }
    
    public static String get(String key){
        return properties.getProperty(key);
    }
    
}
