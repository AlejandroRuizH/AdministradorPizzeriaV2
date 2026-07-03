/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author davidalejandroruizhernandez
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    
    private static final Properties properties = new Properties();
    
    static {
            try(InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")){
                if (input == null){
                    throw new RuntimeException("No se encontro el arhivo config.properties");
                }
                
                properties.load(input);
                
             } catch (IOException e) {
               throw new RuntimeException("Error cargando config.properties", e);
             }
                
    }
    
    public static String get(String key){
        return properties.getProperty(key);
    }
    
}
