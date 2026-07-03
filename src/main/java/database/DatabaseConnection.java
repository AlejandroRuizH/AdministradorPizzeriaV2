/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import config.Config;

import java.sql.*;

/**
 *
 * @author davidalejandroruizhernandez
 */
public class DatabaseConnection {
    
    private static Connection connection;
    
    public static Connection getConnection() {
    
            try {
                
                  if (connection == null || connection.isClosed() ){
                      
                      String host = Config.get("db.host");
                      String port = Config.get("db.port");
                      String database = Config.get("db.name");
                      String user = Config.get("db.user");
                      String password = Config.get("db.password");
                      
                      String url = "jdbc:mysql://" + host + ":" + port + "/" + database  
                                        + "?useSSL=false&serverTimezone=UTC";
                      
                      
                      connection = DriverManager.getConnection(url,user,password);

                      
                  }
            
            }catch (SQLException e) {
                e.printStackTrace();
            }
            
            
            
         return connection;
        
    }
    
    public static void closeConnection() {

        try {

            if (connection != null && !connection.isClosed()) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
